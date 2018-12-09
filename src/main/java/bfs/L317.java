package bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class L317 {
    /*
     * 这道题有个坑:
     *   brute force解法要比变形的BFS解法的空间复杂度好
     *   具体的看bfs的比较
     *   1.corner case是否 throw exception需要跟面试官确认
     *   2.最后如果minSum还是MAX_VALUE的话, 该返回什么值需要跟面试官确认
     *   3.在bfs里面有一个小小的优化的地方, post processing, 就是当 bfs 扫描一遍之后, 还是无法到达的 0 全部置换为 2, 下次就不用再扫描了
     *   4.bfs 里面的查环要熟练: visited == false 和 grid == 0
     * */

    public int shortestDistance(int[][] grid) {
        //corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0; // throw IllegalArgumentException("Invalid Input");
        }
        int row = grid.length;
        int col = grid[0].length;

        int[][] res = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1) {
                    bfs(grid, row, col, res, i, j);
                }
            }
        }
        int minSum = Integer.MAX_VALUE;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 0) {
                    minSum = Math.min(minSum, res[i][j]);
                }
            }
        }
        return minSum == Integer.MAX_VALUE? -1 : minSum;
    }

    private void bfs(final int[][] grid, final int row, final int col, int[][] res, final int orii, final int orij) {
        Queue<Integer> que = new LinkedList<>();
        que.offer(orii * col + orij);
        boolean[][] visited = new boolean[row][col];
        int minLen = 1;
        while (!que.isEmpty()) {
            int size = que.size();
            while (size-- > 0) {
                int cur = que.poll();
                List<Integer> neighbors = getNeighbors(grid, row, col, cur);
                for (int neighbor : neighbors) {
                    int nextI = neighbor / col;
                    int nextJ = neighbor % col;
                    if (!visited[nextI][nextJ] && grid[nextI][nextJ] == 0) {
                        que.offer(neighbor);
                        visited[nextI][nextJ] = true;
                        res[nextI][nextJ] += minLen;
                    }
                }
            }
            minLen++;
        }
        //post processing
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (!visited[i][j] && grid[i][j] == 0) {
                    grid[i][j] = 2;
                }
            }
        }
    }

    private List<Integer> getNeighbors(final int[][] grid, final int row, final int col, final int cur) {
        List<Integer> res = new ArrayList<>();
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] dir : directions) {
            int nextI = cur / col + dir[0];
            int nextJ = cur % col + dir[1];
            if (nextI >= 0 && nextI < row
                    && nextJ >= 0 && nextJ < col) {
                res.add(nextI * col + nextJ);
            }
        }
        return res;
    }





    /****************************************第二遍*******************************************/
    /*
    * 坑 1: 在touch到某个点之后, 放入到Queue里面之前, 一定要设置该Cell的Visited为true
    * 坑 2: 千万不要忘记放到Queue里面
    * 坑 3: min的最后结果如果还是MAX_VALUE的话, 说明题目要求的节点不存在, 这时需要跟面试官确认该如何处理, 比如返回-1
    * 坑 4: post processing 忘记了
    * 坑 5: 最后在确定最小距离的两层for loop里面, 是grid[i][j] == 0, 是基于 Input 的数值来考虑的
    * */


    public int shortestDistance2(int[][] grid) {
        //corner case
        if(grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0){
            throw new IllegalArgumentException("Invalid Input");
        }

        int row = grid.length;
        int col = grid[0].length;

        int[][] disc = new int[row][col];

        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(grid[i][j] == 1){
                    bfs(grid, disc, i, j, row, col);
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(grid[i][j] == 0) {
                    min = Math.min(min, disc[i][j]);
                }
            }
        }
        return min == Integer.MAX_VALUE ? -1: min;
    }

    private void bfs(int[][] grid, int[][] disc, int idx, int jdx, int row, int col){
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        boolean[][] visited = new boolean[row][col];
        Queue<int[]> que = new LinkedList<>();
        que.offer(new int[]{idx, jdx});
        //visited[idx][jdx] = true; //这句话多余
        int len = 1;
        while (!que.isEmpty()){
            int size = que.size();
            while (size-- > 0){
                int[] cur = que.poll();
                for(int[] dir : directions){
                    int nextI = cur[0] + dir[0];
                    int nextJ = cur[1] + dir[1];
                    if(nextI >= 0 && nextI < row
                    && nextJ >= 0 && nextJ < col
                    && grid[nextI][nextJ] == 0
                    && !visited[nextI][nextJ]){
                        que.offer(new int[]{nextI, nextJ});
                        visited[nextI][nextJ] = true;
                        disc[nextI][nextJ] += len;
                    }
                }
            }
            len++;
        }
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(!visited[i][j] && grid[i][j] == 0){
                    grid[i][j] = 2 ;
                }
            }
        }
    }









































}
