package concurrent;

/**
 * Created by fuyang on 16/7/28.
 */
public class SuperClass {

    public static void a(){
        System.out.println("parent a");
    }
    public final void b(){
        System.out.println("parent b");
    }
    public synchronized void  methodOne(){
        System.out.println("super method_one");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
