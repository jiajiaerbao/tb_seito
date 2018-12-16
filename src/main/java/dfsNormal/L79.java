package dfsNormal;

@SuppressWarnings("Duplicates")
public class L79 {
    /*
     *   坑 1: 在二维数组上面, 沿着四个方向的搜索, 要注意查环: boolean[][] visited
     *   坑 2: 设置 visited 为 true 和 false 的对象仅仅是针对当前层的dfs, 也就是 dfs 函数传进来的 idx 和 jdx
     *   坑 3: 为了提高效率, 不要跟整个word进行比较, 而是一个字母一个字母地进行比较
     *   ********第二遍***********
     *   坑 1: 关键是明确 i, j 和 path 的含义:
     *          [i, j]是当前层dfs将要touch的点
     *   坑 2: 坑BOSS, 这道题的特点是2D Board的每个点都有可能是起始点, 所以在dfs外面需要用两层for loop来指定i, j
     *          仅仅是字符的比较: 当且仅当 word 的idx上的字符 和 board[m][n]的字符相同的时候, 才进入到下一层dfs当中
     *   坑 3: 因为仅仅是比较某个位置上的字符是否相同, 而不涉及all possible solutions的问题, 所以不用保存Path
     *          如果用path的话, path表示的是 在访问i, j之前, 已经构成的路径, 每当用新的nextI, nextJ之前, 一定要检查边界
     *          本题里面在for loop里面没有读取m+dir[0], n+dir[1]位置上面的字符的操作, 所以不用check m+dir[0], n+dir[1]是否越界
     * */

    private int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};

    public boolean exist(char[][] board, String word) {
        //corner case
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return false;
        }
        int row = board.length;
        int col = board[0].length;
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

    /***************************第二遍*****************************************/
    public boolean exist2(char[][] board, String word) {
        //corner case
        if (board == null || board.length == 0 ||
                board[0] == null || board[0].length == 0 ||
                word == null || word.length() == 0) {
            return false;
        }
        int row = board.length;
        int col = board[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (dfs(board, word, new boolean[row][col], 0, i, j, row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, String word,
                        boolean[][] visited, int idx,
                        int m, int n,
                        int row, int col) {
        if (idx == word.length()) {
            return true;
        }
        if (m < 0 || m >= row ||
                n < 0 || n >= col ||
                visited[m][n] || word.charAt(idx) != board[m][n]) {
            return false;
        }
        visited[m][n] = true;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            if (dfs(board, word, visited, idx + 1, m + dir[0], n + dir[1], row, col)) {
                return true;
            }
        }
        visited[m][n] = false;
        return false;
    }

}













































