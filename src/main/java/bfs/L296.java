package bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class L296 {

    /*
    * 这道题用BFS来解的话, 超时, 记住: 如果半个小时做不完的话, 就是抄答案, 这样才能保证一天做十道题!!!!
    *
    * */

    public int minTotalDistance(int[][] grid) {
        //corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            throw new IllegalArgumentException("Invalid Input");
        }
        int row = grid.length;
        int col = grid[0].length;
        int[][] disc = new int[row][col];
        //put all 1 into the queue
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1) {
                    bfs(grid, row, col, i, j, disc);
                    /*for (int ii = 0; ii < row; ii++) {
                        for (int jj = 0; jj < col; jj++) {
                            System.out.print(disc[ii][jj] + ", ");
                        }
                        System.out.println();
                    }
                    System.out.println("==============");*/
                }
            }
        }
        int minLen = Integer.MAX_VALUE;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (disc[i][j] != 0) {
                    minLen = Math.min(minLen, disc[i][j]);
                }
            }
        }
        return minLen == Integer.MAX_VALUE ? 1 : minLen;
    }

    private void bfs(final int[][] grid, final int row, final int col, final int oriI, final int oriJ, int[][] disc) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(oriI * col + oriJ);
        //这里一定要注意查环
        boolean[][] visited = new boolean[row][col];
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                List<Integer> neighbors = getNeighbors(grid, row, col, cur);
                for (Integer neighbor : neighbors) {
                    int nextI = neighbor / col;
                    int nextJ = neighbor % col;
                    if (!visited[nextI][nextJ]) {
                        visited[nextI][nextJ] = true;
                        queue.offer(neighbor);
                        disc[nextI][nextJ] += Math.abs(oriI - nextI) + Math.abs(oriJ - nextJ);
                        /*System.out.println("[" + nextI + "," + nextJ + "]" + "oriI:" + oriI + ",nextI" + nextI + ";oriJ:" + oriJ + ",nextJ" + nextJ + ",disc:" + disc[nextI][nextJ]);*/
                    }
                }
            }
        }
    }

    private List<Integer> getNeighbors(final int[][] grid, final int row, final int col, final int cur) {
        List<Integer> res = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            int nextI = cur / col + dir[0];
            int nextJ = cur % col + dir[1];
            if (nextI >= 0 && nextI < row && nextJ >= 0 && nextJ < col) {
                res.add(nextI * col + nextJ);
            }
        }
        return res;
    }
}
