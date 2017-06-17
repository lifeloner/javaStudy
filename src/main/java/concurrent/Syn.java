package concurrent;

/**
 * Created by yang on 2017/6/17.
 */
public class Syn {

    public synchronized void print() {
        System.out.println("hello,world!");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Syn synOne = new Syn();
        Syn synTwo = synOne;
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread()+"-start");
                synOne.print();
                System.out.println(Thread.currentThread()+"-end");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread()+"-start");
                synTwo.print();
                System.out.println(Thread.currentThread()+"-end");
            }
        }).start();
    }
}
