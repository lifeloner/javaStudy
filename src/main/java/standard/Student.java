package standard;

/**
 * Created by fuyang on 2017/5/15.
 */
public class Student implements Cloneable{

    private int[] money;
    private int age;

    public Student(int[] money, int age) {
        this.money = money;
        this.age = age;
    }

    public int[] getMoney() {
        return money;
    }

    public int getAge() {
        return age;
    }

    public void setMoney(int money) {
        this.money[0] = money;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
//        return new Student(money.clone(),this.age);
        return super.clone();
    }

    public static void main(String[] args) {
        Student student=new Student(new int[]{100},25);
        try {
            Student cloneStu=(Student) student.clone();
            System.out.println(cloneStu.getMoney()[0]);
            student.setMoney(200);
            System.out.println(student.getMoney()[0]);
            System.out.println(cloneStu.getMoney()[0]);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


    }
}
