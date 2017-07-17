package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by youngfu on 2017/7/14.
 */
public class NioClient {
    private Selector selector;
    private Charset charset = Charset.forName("UTF-8");
    private static final int PORT = 6060;
    private static String userName="";

    public void init() {
        try {
            selector = Selector.open();
            //连接远程主机的IP和端口
            SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", PORT));
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
            //开辟一个新线程来读取从服务器端的数据
            new Thread(new ClientThread()).start();
            //在主线程中 从键盘读取数据输入到服务器端
            Scanner scan = new Scanner(System.in);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if(userName.length()==0){
                    userName=line;
                    sc.write(charset.encode(userName));
                }else {
                    System.out.println("Me: "+line);
                    sc.write(charset.encode(userName+"_"+line));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ClientThread implements Runnable {
        public void run() {
            try {
                while (true) {
                    int k = selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey sk = iterator.next();
                        iterator.remove();
                        if (sk.isReadable()) {
                            //使用 NIO 读取 Channel中的数据
                            SocketChannel sc = (SocketChannel) sk.channel();
                            ByteBuffer buff = ByteBuffer.allocate(1024);
                            String content = "";
                            while (sc.read(buff) > 0) {
                                buff.flip();
                                content += charset.decode(buff);
                            }
                            if (content.length() > 0) {
                                String[] strs = content.split("_");
                                if(strs.length>1) {
                                    System.out.println(strs[0] + ": " + strs[1]);
                                }else {
                                    if(content.equals("users already exists!")) {
                                        System.out.println(content);
                                        userName = "";
                                    }else {
                                        System.out.println(content);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NioClient().init();
    }
}
