package concurrent;

import java.util.concurrent.Semaphore;

/**
 * Created by yang on 2016/9/5.
 */
public class SemaphoreThread implements Runnable{
private static final Semaphore semaphore=new Semaphore(6);
    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread()+" get");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<20;i++){
            new Thread(new SemaphoreThread()).start();
        }
    }
}
