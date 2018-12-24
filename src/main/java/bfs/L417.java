package bfs;

import java.util.*;

@SuppressWarnings("Duplicates")
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

        /****************************************第二遍*******************************************/
        /*
        * 坑 1: 这里不用按层搜索, 所以不用写while(size-- > 0)
        * 坑 2: 把计算最后结果的过程放到 bfs 里面, 这时需要用到 selfSet 和 otherSet 两个 set: 就是在 selfSet 的时候, 确认一下 otherSet 的相同位置上的Cell是否也是true
        * 坑 3: 确认是否出现环用!selfSet[nextI][nextJ]来保证 (就是所有的matrix[nextI][nextJ] >= matrix[cur[0]][cur[1]]里面的Cell, 只要是touch过的, 都设置成true)
        * */


        public List<int[]> pacificAtlantic2(int[][] matrix) {
            List<int[]> res = new ArrayList<>();
            //corner case
            if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
                return  res;
            }
            int row = matrix.length;
            int col = matrix[0].length;

            boolean[][] pac = new boolean[row][col];
            boolean[][] alt = new boolean[row][col];
            Queue<int[]> que = new LinkedList<>();
            for(int i = 0; i < row; i++){
                pac[i][0] = true;  //这里不要忘记写了
                que.offer(new int[]{i, 0});
            }
            for(int j = 1; j < col; j++){ //这里 i = 1, 防止左上角的点重复加两次
                pac[0][j] = true;  //这里不要忘记写了
                que.offer(new int[]{0, j});
            }
            bfs(matrix, res, pac, alt, que);

            for(int i = 0; i < row; i++){
                alt[i][col - 1] = true;  //这里不要忘记写了
                que.offer(new int[]{i, col-1});
            }
            for(int j = 0; j < col - 1; j++){  //这里 j < col - 1, 防止左上角的点重复加两次
                alt[row - 1][j] = true;  //这里不要忘记写了
                que.offer(new int[]{row-1, j});
            }
            bfs(matrix, res, alt, pac, que);

            return res;
        }

        private void bfs(int[][] matrix, List<int[]> res, boolean[][] selfSet, boolean[][] otherSet, Queue<int[]> que){
            int[][] directions = {{ 1, 0}, { -1, 0}, { 0, 1}, { 0, -1}};
            int row = matrix.length;
            int col = matrix[0].length;
            boolean[][] visited = new boolean[row][col];
            while (!que.isEmpty()){
                int size = que.size();
                //while (size-- > 0){
                    int[] cur = que.poll();
                    if(otherSet[cur[0]][cur[1]]){
                        res.add(new int[]{cur[0], cur[1]});
                    }
                    for(int[] dir: directions){
                        int nextI = cur[0] + dir[0];
                        int nextJ = cur[1] + dir[1];
                        if(nextI >= 0 && nextI < row
                        && nextJ >= 0 && nextJ < col
                        && !selfSet[nextI][nextJ]
                        && matrix[nextI][nextJ] >= matrix[cur[0]][cur[1]]){
                            selfSet[nextI][nextJ] = true;
                            que.offer(new int[]{nextI, nextJ});
                        }
                    }
                //}
            }
        }


































}
