package campus;

import java.util.Arrays;

/**
 * Created by fuyang on 2017/9/2.
 */
public class Baidu {

    public static int[] maxRestTime(int numCities, int numRoads, int[][] travelTime,
                                    int numDeliveries, int[][] timeLimit) {

        int[][] edge = new int[numCities + 1][numCities + 1];
        for (int i = 0; i < numRoads; i++) {
            edge[travelTime[i][0]][travelTime[i][1]] = travelTime[i][2];
            edge[travelTime[i][1]][travelTime[i][0]] = travelTime[i][2];
        }
        int[] result = new int[numDeliveries];
        int min = Integer.MAX_VALUE, k = 1, t = 0, count = 1;
        boolean[] flag = new boolean[numCities];
        flag[0] = true;
        while (count < numCities) {
            min = Integer.MAX_VALUE;
            for (int i = 1; i < numCities; i++) {
                if (!flag[i] && edge[k][i + 1] > 0 && (edge[1][i + 1] == 0 || edge[1][k] + edge[k][i + 1] <= edge[1][i + 1])) {
                    edge[1][i + 1] = edge[1][k] + edge[k][i + 1];
                }
                if (!flag[i] && edge[1][i + 1] != 0 && edge[1][i + 1] < min) {
                    min = edge[1][i + 1];
                    t = i + 1;
                }
            }
            flag[t - 1] = true;
            k = t;
            count++;
            if (min == Integer.MAX_VALUE) {
                break;
            }
        }
        for (int i = 0; i < timeLimit.length; i++) {
            if (edge[1][timeLimit[i][0]] != 0 && timeLimit[i][1] > 2 * edge[1][timeLimit[i][0]]) {
                result[i] = timeLimit[i][1] - 2 * edge[1][timeLimit[i][0]];
            } else {
                result[i] = 0;
            }
        }
        System.out.println(Arrays.toString(result));
        return result;
    }
    // METHOD SIGNATURE ENDS

    public static void main(String[] args) {
        int numCities = 6;
        int numRoads = 6;
        int[][] travelTime = {{1, 2, 1}, {1,3,1},{2,3,1},{3, 4, 3}, {4, 5, 6}, {5, 6, 9}};
        int numDeliveries = 2;
        int[][] timeLimit = {{2, 10}, {3, 11}};
        maxRestTime(numCities, numRoads, travelTime, numDeliveries, timeLimit);
    }
}
