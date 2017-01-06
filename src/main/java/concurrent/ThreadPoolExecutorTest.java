package concurrent;

import java.util.concurrent.*;

/**
 * Created by fuyang on 16/7/19.
 */
public class ThreadPoolExecutorTest {
    private ExecutorService executorService;

    public ThreadPoolExecutorTest() {
    }

    public void newFixThread() {
        executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 6; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
    }

    public void newCacheThread() {
        executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
    }

    public void singleThreadExecutor() {
        executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 2; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        Thread.sleep(4200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
    }

    public void scheduledExectuor() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        for (int i = 0; i < 4; i++) {
            final int k = i;
            executorService = null;
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " " + k);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, 5, 5, TimeUnit.SECONDS);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        scheduledExecutorService.shutdown();
    }

    public void testRejectHandler(){
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(2,4,6,TimeUnit.SECONDS,new LinkedBlockingDeque<>(2),new RejectHandler());
        for(int i=0;i<8;i++) {
            final  int k=i;
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("fy"+k);
                    }
                    System.out.println(Thread.currentThread().getName()+"-"+k);
                }
            });
        }
        threadPoolExecutor.shutdown();
    }
    public static void main(String[] args) {
        ThreadPoolExecutorTest threadPoolExecutorTest = new ThreadPoolExecutorTest();
        threadPoolExecutorTest.testRejectHandler();
    }
}
