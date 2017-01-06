package concurrent;

/**
 * Created by fuyang on 16/7/28.
 */
public class ChildClass extends SuperClass {

    public static  void a(){
        System.out.println("child a");
    }


    public void testMethod(){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 methodOne();
             }
         }).start();
    }

    public  synchronized void methodOne(){
        System.out.println("child method_one");
        super.methodOne();
    }
    public static void main(String[] args) {
         // ChildClass childClass=new ChildClass();
       //   childClass.testMethod();
         // childClass.testMethod();
        SuperClass.a();
    }
}
