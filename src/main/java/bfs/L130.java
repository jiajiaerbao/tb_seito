package bfs;

import java.util.*;

public class L130 {
    /*
     * 把四周的 O 都放到 queue里面, 用BFS搜索, 然后遍历所有能连到一起的 O, 剩下的O就可以变成 x 了
     * 1. 首先找出在四周的所有 'O'
     * 3. 然后遍历通过这些 'O' 能找到的所有 O
     * 4. 最后遍历所有的node, 凡是从四周的 'O' 能到达的 'O' 全部变成 'X'
     *
     * 这道题最后卡在了用 BFS 的查环上面
     * */
    public void solve(char[][] board) {
        //corner case
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return;
        }
        final int row = board.length;
        final int col = board[0].length;

        Set<Integer> boarderO = getBoarderO(board, row, col);

        Set<Integer> remainO = bfs(board, row, col, boarderO);

        //遍历所有的 node, 只要不是 四周 O 所能达到的 O, 全部变成 X
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int ij = i * col + j;
                if (board[i][j] == 'O' && !remainO.contains(ij)) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    //获取四周的O, 这里仅仅是获取最外层四周的 O
    //注意点: 注意存的是 i*col + j
    private Set<Integer> getBoarderO(final char[][] board, final int row, final int col) {
        Set<Integer> res = new HashSet<>();
        for (int i = 0; i < row; i++) {
            if (board[i][0] == 'O') {
                res.add(i * col);
            }
            if (board[i][col - 1] == 'O') {
                res.add(i * col + col - 1);
            }
        }
        for (int j = 0; j < col; j++) {
            if (board[0][j] == 'O') {
                res.add(j);
            }
            if (board[row - 1][j] == 'O') {
                res.add((row - 1) * col + j);
            }
        }
        return res;
    }

    //这里是 bfs, 用来寻找所有的能通过四周的 O 遍历到的 O, 并把所有的 O 返回
    private Set<Integer> bfs(final char[][] board, final int row, final int col, final Set<Integer> boarderO) {
        Set<Integer> res = new HashSet<>();
        Queue<Integer> que = new LinkedList<>(boarderO);
        //这里是查环: 重要!!!
        //在queue进入 while 循环之前, 也要把 queue 里面的元素放入到 visited 里面
        boolean[][] visited = new boolean[row][col];
        for (Integer i : boarderO) {
            visited[i / col][i % col] = true;
        }
        //模板
        while (!que.isEmpty()) {
            int cur = que.poll();
            res.add(cur);
            List<Integer> nextNeighbors = getNextNeighbors(board, row, col, cur);
            for (int neighbor : nextNeighbors) {
                int i = neighbor / col;
                int j = neighbor % col;
                if (!visited[i][j]) {
                    que.offer(neighbor);
                }
                //查环
                visited[i][j] = true;
            }
        }
        return res;
    }

    //获取上下左右四个节点, 并且在 board 范围内, 而且是 O
    private List<Integer> getNextNeighbors(final char[][] board, final int row, final int col, final int cur) {
        List<Integer> res = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0},
                {0, -1}, {0, 1}};
        int i = cur / col;
        int j = cur % col;
        for (int[] dir : directions) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];
            if (nextI >= 0 && nextI < row
                    && nextJ >= 0 && nextJ < col
                    && board[nextI][nextJ] == 'O') {
                res.add(nextI * col + nextJ);
            }
        }
        return res;
    }
}
