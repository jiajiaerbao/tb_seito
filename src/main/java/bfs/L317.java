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

}
