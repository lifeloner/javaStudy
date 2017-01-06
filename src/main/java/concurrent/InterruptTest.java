package concurrent;

/**
 * Created by fuyang on 16/7/16.
 */
public class InterruptTest implements Runnable {
    public InterruptTest(Object object) {
        this.object = object;
    }

    private Object object;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start");
        synchronized (object) {
            try {
                object.wait(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // System.out.println(Thread.currentThread().getName()+" wait lock");
        System.out.println(Thread.currentThread().getName() + "finished");
    }

    public static void main(String[] args) {
        Object object = new Object();
        InterruptTest interruptTest = new InterruptTest(object);
        InterruptTest interruptTest1 = new InterruptTest(object);
        Thread thread = new Thread(interruptTest, "Thread_one");
        thread.start();
        Thread thread1 = new Thread(interruptTest1, "Thread_two");
        thread1.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (object) {
            object.notify();
        }
        System.out.println("main thread finished");
    }
}
