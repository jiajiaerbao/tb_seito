package bit;

@SuppressWarnings("Duplicates")
public class L289 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: 坑BOSS
    *           不能一边遍历一遍转换, 如果一遍遍历一边转换的话, 会污染input
    *           把所有的点都遍历一遍, 每个点在下一层转换的时候, 应该变成什么样的状态, 先记录下来
    *           把所有的点的下次应该转换的状态都记录下来了之后, 最后一起转换
    *           这里利用了int是32bit的特性, 靠左的16bit用来保存下次转换的时候, 该cell应该变成什么样, 靠右的16bit用来保存当前这次的状态
    * */
    class FirstTime{
        public void gameOfLife(int[][] board) {
            //corner case
            if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
                return;
            }
            int row = board.length;
            int col = board[0].length;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    //右16bit 是该cell当前的状态
                    int curState = board[i][j] & 0xFFFF;
                    int neighborLives = countNeighbors(board, i, j);
                    int newState = 0;
                    if (curState == 1 && (neighborLives < 2)) {
                        newState = 0;
                    } else if (curState == 1 && (neighborLives == 2 || neighborLives == 3)) {
                        newState = 1;
                    } else if (curState == 1 && (neighborLives > 3)) {
                        newState = 0;
                    } else if (curState == 0 && (neighborLives == 3)) {
                        newState = 1;
                    }
                    //左16bit 是该cell变换后的状态
                    board[i][j] = (newState << 16) | curState;
                }
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    board[i][j] = board[i][j] >> 16;
                }
            }
        }
        //沿着8个方向找, 把所有八个方向的结果累加
        private int countNeighbors(int[][] board, int i, int j) {
            int neighborLives = 0;
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
            for (int[] dir : dirs) {
                int nextI = i + dir[0];
                int nextJ = j + dir[1];
                if (nextI >= 0 && nextI < board.length &&
                        nextJ >= 0 && nextJ < board[0].length &&
                        //这里是关键, 这里只是用了该cell的右16bit
                        (board[nextI][nextJ] & 0xFFFF) == 1) {
                    neighborLives++;
                }
            }
            return neighborLives;
        }
    }
}
