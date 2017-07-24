package campus;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by fuyang on 2017/7/24.
 */
public class Main {

    public static int maxTaoZi(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int[] count = new int[array.length];
        int max = 0, k=1;
        count[0] = 1;
        for (int i = 1; i < array.length; i++) {
            k = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (array[i] >= array[j] && count[j] + 1 > k) {
                    k = count[j] + 1;
                }
            }
            count[i] = k;
            if (k > max) {
                max = k;
            }
        }
        return max;
    }

    public static int maxTao(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int[] count = new int[array.length+1];
        int max = 1,k;
        count[1] = 0;
        for (int i = 1; i < array.length; i++) {
            k=max;
            while(k>0&&array[i]<array[count[k]]){
                k--;
            }
            if(k>0){
                if(k+1>max){
                    max=k+1;
                    count[max]=i;
                }
            }
            if(count[k+1]==0||array[i]<array[count[k+1]]){
                count[k+1]=i;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = new int[n];
        int k = 0;
        while (n-- > 0) {
            array[k++] = scanner.nextInt();
        }
        System.out.println(maxTao(array));
    }

}
