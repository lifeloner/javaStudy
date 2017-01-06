package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by fuyang on 16/9/11.
 */
public class NioServer {
    private Selector selector;
    private ByteBuffer byteBuffer;

    public void initNioServer(){
        try {
            selector=Selector.open();
            byteBuffer=ByteBuffer.allocate(48);
            ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress("localhost",8000));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handler(){
        int a=2;
    }
}
