package search;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/*
* 坑 1: 先分别投射到 X 轴 和 Y 轴上面, 然后再分别计算 X 和 Y 的所有1到中位数的 1 距离
* 坑 2: 中位数的 1 的计算方法要记一下: list.get(list.size()/2)
* 坑 3: 坑 BOSS, 向 x 轴做投影的时候, for loop的最外层是 x 轴; 向 y 轴做投影的时候, for loop的最外层是 y 轴
* */
@SuppressWarnings("Duplicates")
public class L296 {
    public int minTotalDistance(int[][] grid) {
        //corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        List<Integer> xList = new ArrayList<>();
        List<Integer> yList = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    xList.add(i);
                }
            }
        }
        for (int j = 0; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][j] == 1) {
                    yList.add(j);
                }
            }
        }
        return getDiscFromMedian(xList) + getDiscFromMedian(yList);
    }

    private int getDiscFromMedian(List<Integer> list) {
        int res = 0;
        int median = list.get(list.size() / 2);
        for (int i : list) {
            res += Math.abs(i - median);
        }
        return res;
    }
}
