package concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by yang on 2016/9/5.
 */
public class CountDownThread implements Runnable {
    private CountDownLatch countDownLatch;

    public CountDownThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread() + "get");
            Thread.sleep(2000);
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(new CountDownThread(countDownLatch)).start();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                    Thread.sleep(6000);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("finished");
    }
}
