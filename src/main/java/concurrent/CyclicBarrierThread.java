package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by yang on 2016/9/5.
 */
public class CyclicBarrierThread implements Runnable{
private  CyclicBarrier cyclicBarrier;
public static  boolean a=true;
    public CyclicBarrierThread(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            cyclicBarrier.await();
            System.out.println(Thread.currentThread()+" get");
            cyclicBarrier.await();
            System.out.println(Thread.currentThread()+" get");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(6, new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                    if(a) {
                        System.out.println("cyc thread");
                        a=false;
                    }else{
                        System.out.println("cycs thread");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        for(int i=0;i<6;i++){
            new Thread(new CyclicBarrierThread(cyclicBarrier)).start();
        }
    }
}
