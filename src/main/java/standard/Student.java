package standard;

/**
 * Created by fuyang on 2017/5/15.
 */
public class Student implements Cloneable{
    private Integer[] money;
    private int age;

    public Student(Integer[] money, int age) {
        this.money = money;
        this.age = age;
    }

    public Student() {
    }

    public Integer[] getMoney() {
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
        return new Student(money.clone(),this.age);
//        return super.clone();
    }

    public static void main(String[] args) {
        Student student=new Student(new Integer[]{100},25);
        Student []a=new Student[]{student};
        Student[]b=a.clone();
        a[0].setAge(12);
        System.out.println(a[0]==b[0]);
        System.out.println(b[0].getAge());
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
