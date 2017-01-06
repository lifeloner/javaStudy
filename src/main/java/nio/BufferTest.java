package nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by fuyang on 16/9/11.
 */
public class BufferTest {
    public void readFile(String file){
        RandomAccessFile randomAccessFile=null;
        ByteBuffer byteBuffer=ByteBuffer.allocate(48);
        try {
            randomAccessFile=new RandomAccessFile(file,"rw");
            FileChannel fileChannel=randomAccessFile.getChannel();
            int len=fileChannel.read(byteBuffer);
            while(len!=-1){
                byteBuffer.flip();
                while(byteBuffer.hasRemaining()){
                    System.out.println((char) byteBuffer.get());
                }
                byteBuffer.clear();
                len=fileChannel.read(byteBuffer);
            }
            fileChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void buffGetAndPut(){
        ByteBuffer byteBuffer=ByteBuffer.allocate(48);
        byteBuffer.put((byte)100);
        System.out.println(byteBuffer.position()+" "+byteBuffer.limit()+" "+byteBuffer.capacity());
        byteBuffer.flip();
        System.out.println(byteBuffer.position()+" "+byteBuffer.limit()+" "+byteBuffer.capacity());
        System.out.println(byteBuffer.get());
        System.out.println(byteBuffer.position()+" "+byteBuffer.limit()+" "+byteBuffer.capacity());
        byteBuffer.compact();
        System.out.println(byteBuffer.position()+" "+byteBuffer.limit()+" "+byteBuffer.capacity());
    }

    public void transferTo(String fileOne,String fileTwo){
        try {
            RandomAccessFile fromFile=new RandomAccessFile(fileOne,"rw");
            RandomAccessFile toFile=new RandomAccessFile(fileTwo,"rw");
            FileChannel fromChannel=fromFile.getChannel();
            FileChannel toChannel=toFile.getChannel();
            long count=fromChannel.size();
            System.out.println(count);
            fromChannel.transferTo(0,count-5,toChannel);
            toChannel.close();
            fromChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BufferTest bufferTest=new BufferTest();
      //  bufferTest.readFile("src/main/file/file_one.txt");
//        bufferTest.buffGetAndPut();
        bufferTest.transferTo("src/main/file/file_one.txt","src/main/file/file_two.txt");
    }

}
