package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by fuyang on 16/9/11.
 */
public class NioServer {
    private Selector selector;
    private static final int PORT = 6060;
    private static Charset charset = Charset.forName("UTF-8");
    private static Set<String>userNames=new HashSet<>();

    public void initNioServer() {
        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("NIO Server initial...Waiting for connecting on port 6060!");
            while (true) {
                int n = selector.select();
                if (n == 0) {
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    handleMessage(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessages(Selector selector,SelectionKey sk,String content){
        for(SelectionKey key:selector.keys()){
            if(key==sk){
                continue;
            }
            Channel channel=key.channel();
            if(channel instanceof SocketChannel){
                try {
                    ((SocketChannel) channel).write(charset.encode(content));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void handleMessage(SelectionKey key) {
        if (key.isAcceptable()) {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            try {
                SocketChannel socketChannel = ssc.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
              //  key.interestOps(SelectionKey.OP_ACCEPT);
                System.out.println("client:" + socketChannel.getRemoteAddress() + " is connecting");
                socketChannel.write(ByteBuffer.wrap("welcome guys,please input your name!".getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            try {
                socketChannel.read(buffer);
                buffer.flip();
                String content = charset.decode(buffer).toString();
                if(content!=null&&content.length()>0) {
                    String[] strings = content.split("_");
                    if(strings.length==1){
                        if(userNames.contains(strings[0])) {
                            socketChannel.write(ByteBuffer.wrap("users already exists!".getBytes()));
                        }
                        else {
                            userNames.add(content);
                            System.out.println(content+", registered!");
                            socketChannel.write(ByteBuffer.wrap(("welcome,"+content).getBytes()));
                            sendMessages(selector,key,strings[0]+",enter the char room!");
                        }
                    }
                    else {
                        sendMessages(selector,key,content);
                    }
                }
            } catch (IOException e) {
                key.cancel();
                if(socketChannel!=null){
                    try {
                        socketChannel.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NioServer().initNioServer();
    }

}
