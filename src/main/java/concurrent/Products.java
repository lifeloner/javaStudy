package concurrent;

/**
 * Created by fuyang on 16/7/27.
 */
public class Products {
    private int count;
    public final static int MAX_SIZE = 10;

    public Products() {
        count = 0;
    }

    public void produce(int m) {
        while (m > 0) {
            synchronized (this) {
                if (count >= MAX_SIZE) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count++;
                notifyAll();
                System.out.println(Thread.currentThread().getName() + " produce "+count);
            }
            m--;
        }
    }

    public void consume(int m) {
        while (m > 0) {
            synchronized (this){
                if(count<0){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count--;
                notifyAll();
                System.out.println(Thread.currentThread().getName()+" consume " +count);
            }
            m--;
        }
    }

    public static void main(String[] args) {
        Products products=new Products();
        Thread produce1=new Thread(new Runnable() {
            @Override
            public void run() {
                products.produce(3);
            }
        },"produce_1");
        produce1.start();
        Thread produce2=new Thread(new Runnable() {
            @Override
            public void run() {
                products.produce(10);
            }
        },"produce_2");
        produce2.start();
        Thread produce3=new Thread(new Runnable() {
            @Override
            public void run() {
                products.produce(5);
            }
        },"produce_3");
        produce3.start();
        Thread consumer=new Thread(new Runnable() {
            @Override
            public void run() {
                products.consume(2);
            }
        },"conusmer");
        consumer.start();
    }
}
