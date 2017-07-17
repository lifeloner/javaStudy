package nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by fuyang on 16/9/11.
 */
public class BufferTest {
    public void readFile(String file) {
        RandomAccessFile randomAccessFile = null;
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            int len = fileChannel.read(byteBuffer);
            while (len != -1) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    System.out.println((char) byteBuffer.get());
                }
                byteBuffer.clear();
                len = fileChannel.read(byteBuffer);
            }
            fileChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void buffGetAndPut() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        byteBuffer.put((byte) 100);
        System.out.println(byteBuffer.position() + " " + byteBuffer.limit() + " " + byteBuffer.capacity());
        byteBuffer.flip();
        System.out.println(byteBuffer.position() + " " + byteBuffer.limit() + " " + byteBuffer.capacity());
        System.out.println(byteBuffer.get());
        System.out.println(byteBuffer.position() + " " + byteBuffer.limit() + " " + byteBuffer.capacity());
        byteBuffer.compact();
        System.out.println(byteBuffer.position() + " " + byteBuffer.limit() + " " + byteBuffer.capacity());
    }

    public void transferTo(String fileOne, String fileTwo) {
        try {
            RandomAccessFile fromFile = new RandomAccessFile(fileOne, "rw");
            RandomAccessFile toFile = new RandomAccessFile(fileTwo, "rw");
            FileChannel fromChannel = fromFile.getChannel();
            FileChannel toChannel = toFile.getChannel();
            long count = fromChannel.size();
            System.out.println(count);
            fromChannel.transferTo(0, count - 5, toChannel);
            toChannel.close();
            fromChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void isDirectBuff() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
        CharBuffer charBuffer = CharBuffer.allocate(10);
        System.out.println(byteBuffer.isDirect());
        System.out.println(charBuffer.isDirect());
    }

    public void testByteBufferPut() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.putChar('1');
        byteBuffer.putInt(200);
        byteBuffer.putLong(300L);
        byteBuffer.flip();
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
    }

    public void testFileCopy() {
        try {
            RandomAccessFile randomAccessFile1 = new RandomAccessFile("/Users/fuyang/a", "rw");
            RandomAccessFile randomAccessFile2 = new RandomAccessFile("/Users/fuyang/b", "rw");
            FileChannel fileChannel1 = randomAccessFile1.getChannel();
            FileChannel fileChannel2 = randomAccessFile2.getChannel();
//            fileChannel1.transferTo(0, fileChannel1.size(), fileChannel2);
            fileChannel1.transferFrom(fileChannel2, 0, fileChannel2.size());
            fileChannel1.close();
            fileChannel2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // asReadOnlyBuffer duplicate slice 都是在源buff基础上复制，只是position limit capacity不同罢了
    public void testCopy() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.putInt(1);
        byteBuffer.flip();
        ByteBuffer byteBuffer1 = byteBuffer.slice();
//        ByteBuffer byteBuffer1 = byteBuffer.asReadOnlyBuffer();
//        ByteBuffer byteBuffer1 = byteBuffer.duplicate();
        byteBuffer.putInt(23);
        byteBuffer.putInt(12);
        System.out.println(byteBuffer1.position());
        byteBuffer1.putInt(122);
        System.out.println(byteBuffer1.position()+" "+byteBuffer1.capacity());
        //System.out.println(byteBuffer1.getInt());
        System.out.println(byteBuffer1.position());
        byteBuffer.flip();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getInt());
        // System.out.println(byteBuffer1.getInt());
//        System.out.println(byteBuffer.getInt());
//        System.out.println(byteBuffer.getInt());
    }

    public static void main(String[] args) {
        BufferTest bufferTest = new BufferTest();
        //  bufferTest.readFile("src/main/file/file_one.txt");
//        bufferTest.buffGetAndPut();
//        bufferTest.transferTo("src/main/file/file_one.txt","src/main/file/file_two.txt");
//        bufferTest.isDirectBuff();
//        bufferTest.testByteBufferPut();
//        bufferTest.testFileCopy();
        bufferTest.testCopy();
    }

}
