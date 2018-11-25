package bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class L286 {
    /*
     * 把什么发到 Queue 里面:
     *   1. 刚开始的时候, 把所有的 gate (0) 放到 queue 里面
     *   2. 之后把 room (INF) 放到 queue 里面
     *
     * 如何查环:
     *   因为 room (INF) 的初始值是 INF, 当改变 room 的值时候, 就相当于 mark 成已经访问过了
     * */
    public void wallsAndGates(int[][] rooms) {
        //corner case
        if (rooms == null || rooms.length == 0 || rooms[0] == null || rooms[0].length == 0) {
            //这里需要跟面试官clarify: throw new IllegalArgumentException("Invalid Input!");
            return;
        }

        int row = rooms.length;
        int col = rooms[0].length;
        //把所有的 gate 放入到 Queue 里面
        Queue<Integer> que = new LinkedList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (rooms[i][j] == 0) {
                    //这里总是出问题: i * col + j
                    que.offer(i * col + j);
                }

            }
        }
        int minLen = 1;
        while (!que.isEmpty()) {
            int size = que.size();
            while (size-- > 0) {
                int cur = que.poll();
                List<Integer> neighbors = getNeighbors(rooms, row, col, cur);
                for (int neighbor : neighbors) {
                    int nextI = neighbor / col;
                    int nextJ = neighbor % col;
                    if (rooms[nextI][nextJ] == Integer.MAX_VALUE) {
                        que.offer(neighbor);
                        rooms[neighbor / col][neighbor % col] = minLen;
                    }
                }
            }
            minLen++;
        }
    }

    private List<Integer> getNeighbors(final int[][] rooms, final int row, final int col, final int cur) {
        List<Integer> res = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0},
                {0, -1}, {0, 1}};
        int i = cur / col;
        int j = cur % col;
        for (int[] dir : directions) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];
            if (nextI >= 0 && nextI < row
                    && nextJ >= 0 && nextJ < col) {
                res.add(nextI * col + nextJ);
            }
        }
        return res;
    }
}
