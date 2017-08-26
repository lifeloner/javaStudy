package campus;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by fuyang on 2017/8/26.
 */
public class DiDi {

    public int findMax(){
        Scanner sc = new Scanner(System.in);
        int num,result=Integer.MIN_VALUE,max=result;
        while(sc.hasNext()){
            num=sc.nextInt();
            if(num==-1){
                break;
            }
            if(max<=0){
                max=num;
            }
            else {
                max+=num;
            }
            if(max>result){
                result=max;
            }
        }
        return result;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line=sc.nextLine();
        String []nums=line.split(" ");
        List<Integer>list=new ArrayList<Integer>(nums.length);
        for(int i=0;i<nums.length;i++){
            list.add(Integer.parseInt(nums[i]));
        }
        int n=sc.nextInt();
        PriorityQueue<Integer>queue=new PriorityQueue<Integer>(n);
        for(int i=0;i<n;i++){
            queue.add(list.get(i));
        }
        for(int i=n;i<list.size();i++){
            if(queue.peek()>=list.get(i)){
                continue;
            }
            queue.poll();
            queue.add(list.get(i));
        }
        System.out.println(queue.peek());
        sc.close();
    }
}
