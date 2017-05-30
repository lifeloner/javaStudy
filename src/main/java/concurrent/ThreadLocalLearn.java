package concurrent;


/**
 * Created by fuyang on 2017/5/30.
 */
public class ThreadLocalLearn {
    private ThreadLocal<String> threadLocalString;
    private ThreadLocal<Integer> threadLocalInteger;

    public ThreadLocalLearn() {
        threadLocalString = new ThreadLocal() {
            @Override
            protected String initialValue() {
                return "fuyang";
            }
        };
        threadLocalInteger = new ThreadLocal() {
            @Override
            protected Integer initialValue() {
                return 666;
            }
        };
    }

    public void setString(String value) {
        threadLocalString.set(value);
    }

    public String getString() {
        return threadLocalString.get();
    }

    public void setInteger(int value) {
        threadLocalInteger.set(value);
    }

    public int getInteger() {
        return threadLocalInteger.get();
    }

    public static void main(String[] args) {
        ThreadLocalLearn threadLocalLearn = new ThreadLocalLearn();
        threadLocalLearn.setString("yangfu");
        System.out.println(Thread.currentThread().getName()+"\t"+threadLocalLearn.getString());
        threadLocalLearn.setString("fuyang");
        System.out.println(Thread.currentThread().getName()+"\t"+threadLocalLearn.getInteger());
        threadLocalLearn.setInteger(686);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocalLearn.setString("yangfu");
                threadLocalLearn.setInteger(888);
                System.out.println(Thread.currentThread().getName()+"\t"+threadLocalLearn.getString());
                System.out.println(Thread.currentThread().getName()+"\t"+threadLocalLearn.getInteger());
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello thread_local!");
        System.out.println(Thread.currentThread().getName()+"\t"+threadLocalLearn.getString());
        System.out.println(Thread.currentThread().getName()+"\t"+threadLocalLearn.getInteger());
    }
}
