package standard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Created by fuyang on 2017/5/28.
 */
public class ListTest {

    public void testCurrentModify() {
        List<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        Iterator<Integer>iterator=list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            iterator.remove();
        }
        System.out.println(list);
    }


    public void testEquals(){
        Integer a=100;
        Integer b=100;
        Long c=100L;
        Long d=100L;
        Double e=100D;
        Double f=100D;
        Boolean m=true;
        Boolean n=Boolean.TRUE;
        Boolean x=new Boolean(true);
        Boolean t=new Boolean(true);
        System.out.println(t==x);
    }

    public static void main(String[] args) {
        ListTest listTest = new ListTest();
//        listTest.testCurrentModify();
        listTest.testEquals();
    }
}
