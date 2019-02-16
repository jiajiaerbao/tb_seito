package bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@SuppressWarnings("Duplicates")
public class L286 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 关键点是把什么放到Queue里面, 这里是把 Gate 放到Queue里面, 从 Gate 出发, 到所有 room 的距离
    *       如果实在记不住 哪个值对应着哪个点, 可以用下面这两个来代替
    *           private static final GATE = 0;
    *           private static final ROOM = Integer.MAX_VAL;
    * */
    public void wallsAndGates_1(int[][] rooms) {
        //corner case
        if (rooms == null || rooms.length == 0 || rooms[0] == null || rooms[0].length == 0) {
            return;
        }
        int row = rooms.length;
        int col = rooms[0].length;
        boolean[][] visited = new boolean[row][col];
        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }
        int dict = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                rooms[cur[0]][cur[1]] = dict;
                /*if (rooms[cur[0]][cur[1]] == Integer.MAX_VALUE) {
                    rooms[cur[0]][cur[1]] = dict;
                }*/
                for (int[] dir : dirs) {
                    int nextI = cur[0] + dir[0];
                    int nextJ = cur[1] + dir[1];
                    if (nextI >= 0 && nextI < row && nextJ >= 0 && nextJ < col &&
                            rooms[nextI][nextJ] == Integer.MAX_VALUE && !visited[nextI][nextJ]) {
                        queue.offer(new int[]{nextI, nextJ});
                        visited[nextI][nextJ] = true;
                    }
                }
            }
            dict++;
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (rooms[i][j] == Integer.MAX_VALUE) {
                    rooms[i][j] = -1;
                }
            }
        }
    }



    /******************************第一遍********************************************/
    /*
     * 把什么发到 Queue 里面:
     *   1. 刚开始的时候, 把所有的 gate (0) 放到 queue 里面
     *   2. 之后把 room (INF) 放到 queue 里面
     *
     * 如何查环:
     *   因为 room (INF) 的初始值是 INF, 当改变 room 的值时候, 就相当于 mark 成已经访问过了
     * */
    public void wallsAndGates_2(int[][] rooms) {
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


    /****************************************第二遍*******************************************/

    /*
    * 坑 0: 这里需要跟面试官clarify: throw new IllegalArgumentException("Invalid Input!");
    * 坑 1: 这里是把所有的零都放入到Queue里面
    * 坑 2: 改变 某个Cell的距离 的时机 是 touch 到了这个Cell, 但是在放入Queue之前
    * 坑 3: 最大值和最小值要区分开
    * */


    public void wallsAndGates2(int[][] rooms) {
        //corner case
        if(rooms == null || rooms.length == 0 || rooms[0] == null || rooms[0].length == 0){
            return;
        }
        int row = rooms.length;
        int col = rooms[0].length;
        Queue<int[]> que = new LinkedList<>();
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(rooms[i][j] == 0){
                   que.offer(new int[]{i, j});
                }
            }
        }
        int len = 1;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!que.isEmpty()){
            int size = que.size();
            while (size-- > 0){
                int[] cur = que.poll();
                for(int[] dir : directions){
                    int nextI = cur[0]+dir[0];
                    int nextJ = cur[1]+dir[1];
                    if(nextI >= 0 && nextI < row
                    && nextJ >= 0 && nextJ < col
                    && rooms[nextI][nextJ] == Integer.MAX_VALUE){
                        que.offer(new int[]{nextI, nextJ});
                        rooms[nextI][nextJ] = len;
                    }
                }
            }
            len++;
        }
    }
















































}
