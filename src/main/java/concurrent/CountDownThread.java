package concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by yang on 2016/9/5.
 */
public class CountDownThread implements Runnable{
    private  CountDownLatch countDownLatch;

    public CountDownThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread()+"get");
            Thread.sleep(2000);
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        CountDownLatch countDownLatch=new CountDownLatch(6);
        try {
            for(int i=0;i<6;i++){
                new Thread(new CountDownThread(countDownLatch)).start();
            }
            countDownLatch.await();
            System.out.println("finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
