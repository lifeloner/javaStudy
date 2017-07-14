package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Created by youngfu on 2017/7/14.
 */
public class NioClient {
    private Selector selector;
    private Charset charset = Charset.forName("UTF-8");
    private static final int PORT = 6060;

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
                sc.write(charset.encode(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ClientThread implements Runnable {
        public void run() {
            try {
                while (selector.select() > 0) {
                    for (SelectionKey sk : selector.selectedKeys()) {
                        selector.selectedKeys().remove(sk);
                        if (sk.isReadable()) {
                            //使用 NIO 读取 Channel中的数据
                            SocketChannel sc = (SocketChannel) sk.channel();
                            ByteBuffer buff = ByteBuffer.allocate(1024);
                            String content = "";
                            while (sc.read(buff) > 0) {
                                buff.flip();
                                content += charset.decode(buff);
                            }
                            System.out.println("聊天信息： " + content);
                            sk.interestOps(SelectionKey.OP_READ);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        new NioClient().init();
    }
}
