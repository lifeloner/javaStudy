package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by fuyang on 16/7/27.
 */
public class ProductsLock {
    public final static int MAX_SIZE = 10;
    private Lock lock;
    private Condition full;
    private Condition empty;
    private int count;

    public ProductsLock() {
        lock = new ReentrantLock();
        full = lock.newCondition();
        empty = lock.newCondition();
        count = 0;
    }

    public void produce(int m) {
        while (m > 0) {
            lock.lock();
            try {
                while (count >= MAX_SIZE) {
                    try {
                        full.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count++;
                System.out.println(Thread.currentThread().getName() + " produce " + count);
                empty.signalAll();
            } finally {
                lock.unlock();
            }
            m--;
        }
    }

    public void consume(int m) {
        while (m > 0) {
            lock.lock();
            try {
                while (count < 0) {
                    try {
                        empty.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count--;
                System.out.println(Thread.currentThread().getName() + " consume " + count);
                full.signalAll();
            } finally {
                lock.unlock();
            }
            m--;
        }
    }

    public static void main(String[] args) {
        ProductsLock productsLock = new ProductsLock();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                productsLock.produce(3);
            }
        }, "produce1");
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                productsLock.produce(10);
            }
        }, "produce2");
        thread2.start();
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                productsLock.produce(4);
            }
        }, "produce3");
        thread3.start();
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                productsLock.consume(3);
            }
        }, "consume");
        thread4.start();
    }
}
