package campus;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by fuyang on 2017/8/12.
 */
public class WangYi {

    public static void findNum(int pos,int k,int[]array) {
        if(pos==array.length-1){
            if(array[array.length-1]==1000000007){
                array[array.length-1]=0;
            }else {
                array[array.length-1]++;
            }
            return;
        }
        int j;
        for(int i=1;i<=k;i++) {
            for (j = 0; j <= pos - 1; j++) {
                if(array[j]>i&&array[j]%i==0){
                    break;
                }
            }
            if(j==pos) {
                array[pos] = i;
                findNum(pos + 1, k, array);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        int x = scanner.nextInt();
//        int f = scanner.nextInt();
//        int d = scanner.nextInt();
//        int p = scanner.nextInt();
//        if(d/x<f){
//            System.out.println(d/x);
//        }
//        System.out.println(f+(d-f*x)/(x+p));
//        scanner.close();
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
        }
        for (int i = 0; i < n; i++) {
            y[i] = scanner.nextInt();
        }
        int [][] dis = new int [n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dis[i][j]=Math.abs(x[i]-x[j])+Math.abs(y[i]-y[j]);
                dis[j][i]=dis[i][j];
            }
        }
        long []result=new long[n];
        long k;
        for(int i=0;i<n;i++){
            result[i]=Long.MAX_VALUE;
            Arrays.sort(dis[i]);
        }
        System.out.println(Arrays.deepToString(dis));
        for(int i=0;i<n;i++){
            k=0;
            for(int j=0;j<n;j++){
                k=k+(long)dis[i][j];
                if(k<result[j]){
                    result[j]=k;
                }
            }
        }
        for(int i=0;i<n-1;i++){
            System.out.print(result[i]+" ");
        }
        System.out.println(result[n-1]);
//        int k=scanner.nextInt();
//        int []array=new int[n+1];
//        findNum(0,k,array);
//        System.out.println(array[array.length-1]);
//        scanner.close();
    }
}
