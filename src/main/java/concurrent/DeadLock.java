package concurrent;

/**
 * Created by fuyang on 16/7/19.
 */
public class DeadLock {
    public static void main(String[] args) {
        Object a=new Object();
        Object b=new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (a){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (b){
                        System.out.println(Thread.currentThread().getName()+" get a and b lock");
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (b) {
                    synchronized (a) {
                        System.out.println(Thread.currentThread().getName() + " get a and b lock");
                    }
                }
            }
        }).start();
        System.out.println(Thread.currentThread().getName()+" finshed");
    }
}

