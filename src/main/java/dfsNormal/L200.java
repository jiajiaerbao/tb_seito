package dfsNormal;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("Duplicates")
/*
* 坑 1: 在主函数里面用两层 for loop 来遍历所有的点, 同时另外定义一个数组, visited[][] 来记录已经访问过的 '1' 节点
* 坑 2: 坑BOSS:
*           DFS的逻辑结构还是不熟练
*           base case:
*                       check 各种引起失败的情况, 比如i,j越界; 值不是'1'; 已经访问过了
*           body:
*                       第一次touch到这个点, 设置visited为true, 也就是 改变visited的值的timing是 每当访问到了一个新的 '1' 节点的时候, 设置visited[idxI][idxJ] 为 true
*                       沿着四个方向 call dfs
*
* 坑 3: BFS解法的坑BOSS, BFS的时候,
*           一定要在第一次访问到这个点的时候, 也就是第一次touch到这个点的时候, 设置visited为true,
*           同时根据把满足条件的点放进 Queue 里面
* */
public class L200 {
    //DFS做法
    public int numIslands(char[][] grid) {
        //corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        int cnt = 0;

        int row = grid.length;
        int col = grid[0].length;
        boolean[][] visited = new boolean[row][col];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    dfs(grid, i, j, visited);
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private void dfs(char[][] grid, int idxI, int idxJ, boolean[][] visited) {
        if (idxI < 0 || idxI >= grid.length ||
                idxJ < 0 || idxJ >= grid[0].length ||
                grid[idxI][idxJ] == '0' || visited[idxI][idxJ]) {
            return;
        }
        visited[idxI][idxJ] = true;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            int nextI = idxI + dir[0];
            int nextJ = idxJ + dir[1];
            dfs(grid, nextI, nextJ, visited);
        }
    }
    //BFS做法
    public int numIslands2(char[][] grid) {
        //corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        int cnt = 0;
        int row = grid.length;
        int col = grid[0].length;
        boolean[][] visited = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    System.out.println("for loop");
                    bfs(grid, i, j, visited);
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private void bfs(char[][] grid, int idxI, int idxJ, boolean[][] visited) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int row = grid.length;
        int col = grid[0].length;
        Queue<int[]> que = new LinkedList<>();
        que.offer(new int[]{idxI, idxJ});
        //第一次 touch 到的点放到 Queue 里面
        visited[idxI][idxJ] = true;
        while (!que.isEmpty()) {
            //System.out.println("bfs");
            int[] cur = que.poll();
            int curI = cur[0];
            int curJ = cur[1];
            //只在这里加的话, 为什么出错? 从来没有过 在出Queue 的时候, 设置visited为true的, 你还是基础掌握的不牢固
            //visited[curI][curJ] = true;
            for (int[] dir : directions) {
                int nextI = curI + dir[0];
                int nextJ = curJ + dir[1];
                if (nextI >= 0 && nextI < row &&
                        nextJ >= 0 && nextJ < col &&
                        !visited[nextI][nextJ] &&
                        grid[nextI][nextJ] == '1') {
                    //向四周扩散, 满足以上条件的, 第一次touch到的点 放到queue里面, 同时设置visited为true
                    que.offer(new int[]{nextI, nextJ});
                    visited[nextI][nextJ] = true;
                }
            }
        }
    }
}
