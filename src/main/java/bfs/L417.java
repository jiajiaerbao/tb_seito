package bfs;

import java.util.*;

public class L417 {
    public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> res = new ArrayList<>();
        //corner case
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return res; // 或者抛出异常
        }
        int row = matrix.length;
        int col = matrix[0].length;

        Queue<int[]> que = new LinkedList<>();

        boolean[][] pacific = new boolean[row][col];
        boolean[][] atlantic = new boolean[row][col];
        //对于pacific, 左, 上都设置为 true
        for (int i = 0; i < row; i++) {
            pacific[i][0] = true;
            que.offer(new int[]{i, 0});
        }
        //这里 i = 1, 防止左上角的点重复加两次
        for (int j = 1; j < col; j++) {
            pacific[0][j] = true;
            que.offer(new int[]{0, j});
        }
        //当执行完一遍bfs之后, que是空的, 也就是上一次 bfs 的执行结果不影响下一层执行 bfs
        //这里的bfs一定要分开执行: 因为此时otherSet是空, 所以只是保留了从pacific开始的所有可能节点
        bfs(matrix, row, col, res, que, pacific, atlantic);

        //对于atlantic
        for (int i = 0; i < row; i++) {
            atlantic[i][col - 1] = true;
            que.offer(new int[]{i, col - 1});
        }
        for (int j = 0; j < col - 1; j++) {
            atlantic[row - 1][j] = true;
            que.offer(new int[]{row - 1, j});
        }
        bfs(matrix, row, col, res, que, atlantic, pacific);

        return res;
    }

    private void bfs(final int[][] matrix, final int row, final int col, List<int[]> res, Queue<int[]> que, boolean[][] selfSet, boolean[][] otherSet) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!que.isEmpty()) {
            int[] cur = que.poll();
            //第一次执行bfs的时候, otherSet为空, 只有第二次执行的时候, 才会计算出唯一解
            if (otherSet[cur[0]][cur[1]]) {
                res.add(new int[]{cur[0], cur[1]});
            }
            for (int[] dir : directions) {
                int i = cur[0] + dir[0];
                int j = cur[1] + dir[1];
                if (i >= 0 && i < row
                        && j >= 0 && j < col
                        && matrix[i][j] >= matrix[cur[0]][cur[1]]
                        && !selfSet[i][j]) {
                    que.offer(new int[]{i, j});
                    selfSet[i][j] = true;
                }
            }
        }
    }
}
