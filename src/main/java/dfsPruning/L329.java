package dfsPruning;

@SuppressWarnings("Duplicates")

/*
 *   坑 1: 坑BOSS: pruning的话, 可以用[i,j]的值来 表示 从[i,j]开始 最长能递增的 字符串的长度
 *   坑 2: 这道题虽然是四个方向, 但是要求两个节点之间遵循固定的大小关系, 所以是有向边, 不会出环, 所以不用查环
 *   坑 3: 每个节点的可以延伸的最大长度是该节点沿着四个方向所能延伸的长度里面的最大值:
 *           max = Math.max(max,dfs(matrix, nextI, nextJ, men));
 *   坑 4: 这道题跟算法哥的答案不一样的地方就是, 答案是把 nextI, nextJ 的边界判断, 和上一个节点的大小关系比较都放在了下一层dfs里面,
 *          而你把nextI, nextJ的边界判断, 和跟下一个节点的比较放在了当前层
 * */
public class L329 {
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 ||
                matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int res = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                res = Math.max(res, dfs(matrix, i, j, new int[row][col]));
            }
        }
        return res;
    }

    private int dfs(int[][] matrix, int idxI, int idxJ, int[][] men) {
        if (men[idxI][idxJ] > 0) {
            return men[idxI][idxJ];
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int curVal = matrix[idxI][idxJ];
        int max = 0;
        for (int[] dir : directions) {
            int nextI = idxI + dir[0];
            int nextJ = idxJ + dir[1];
            if (nextI >= 0 && nextI < row &&
                    nextJ >= 0 && nextJ < col &&
                    matrix[nextI][nextJ] > curVal) {
                max = Math.max(max, dfs(matrix, nextI, nextJ, men));
            }
        }
        return men[idxI][idxJ] = max + 1;
    }
}
