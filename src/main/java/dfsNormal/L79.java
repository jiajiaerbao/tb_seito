package dfsNormal;

public class L79 {
    /*
     *   坑 1: 在二维数组上面, 沿着四个方向的搜索, 要注意查环: boolean[][] visited
     *   坑 2: 设置 visited 为 true 和 false 的对象仅仅是针对当前层的dfs, 也就是 dfs 函数传进来的 idx 和 jdx
     *   坑 3: 为了提高效率, 不要跟整个word进行比较, 而是一个字母一个字母地进行比较
     * */

    private int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};

    public boolean exist(char[][] board, String word) {
        //corner case
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return false;
        }
        int row = board.length;
        int col = board[0].length;
        int len = word.length();
        //2d matrix的四个方向的搜索, 一定要查环
        boolean[][] visited = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (helper(board, word, 0, row, col, i, j, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean helper(final char[][] board, final String word, final int curIdx, final int row, final int col, final int i, final int j, boolean[][] visited) {
        if (word.length() == curIdx) {
            return true;
        }
        //已经访问过了的元素就不要再访问了: visited[idx][jdx] is true
        //如果针对当前层的 idx, jdx, curIdx位置上的字符和board[idx][jdx]上的字符不一致的话, 返回!!!!(这样效率高)
        if (i < 0 || i >= row || j < 0 || j >= col || visited[i][j] || board[i][j] != word.charAt(curIdx)) {
            return false;
        }
        //仅仅对当前层的元素 标记为 已经访问过了
        visited[i][j] = true;
        for (int[] dir : DIRECTIONS) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];
            //这里不需要做边界检查, 也不需要做 visited 的检查, 因为每层的dfs只是做当前层的 check
            if (helper(board, word, curIdx + 1, row, col, nextI, nextJ, visited)) {
                return true;
            }
        }
        //把已经访问过了的 元素 setback
        visited[i][j] = false;
        return false;
    }
}
