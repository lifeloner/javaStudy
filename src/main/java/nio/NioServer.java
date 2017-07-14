package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by fuyang on 16/9/11.
 */
public class NioServer {
    private Selector selector;
    private static final int PORT = 6060;

    public void initNioServer() {
        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("NIO Server initial...Waiting for connecting on port 6060!");
            while(true){
                int n=selector.select();
                if(n==0){
                    continue;
                }
                Set<SelectionKey> keys=selector.selectedKeys();
                Iterator<SelectionKey>iterator=keys.iterator();
                while(iterator.hasNext()){
                    SelectionKey key=iterator.next();
                    iterator.remove();
                    if(key.isAcceptable()){
                        ServerSocketChannel ssc=(ServerSocketChannel) key.channel();
                        SocketChannel socketChannel=ssc.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);
                        key.interestOps(SelectionKey.OP_ACCEPT);
                        System.out.println("client:"+socketChannel.getRemoteAddress()+" is connecting to server");
                    }else if(key.isReadable()){
                        SocketChannel socketChannel=(SocketChannel) key.channel();
                        ByteBuffer buffer=ByteBuffer.allocate(1024);
                        socketChannel.read(buffer);
                        String content=new String(buffer.array());
                        System.out.println("receive data form client:"+socketChannel.getRemoteAddress()+" data:"+content);
                        key.interestOps(SelectionKey.OP_READ);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException
    {
        new NioServer().initNioServer();
    }

}
