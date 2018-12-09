package bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class L542 {

    /*
    * 这道题具体的解题思路参照BFS文档
    *   1. 尽量不要修改input, 重新建立一个 int[][] 数组
    *   2. 查环的条件: 源matrix是值为1, 代表还没有被访问过, 目标matrix的值为0, 代表还没有计算过距离
    * */
    public int[][] updateMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return matrix;
        }
        int row = matrix.length;
        int col = matrix[0].length;

        Queue<Integer> que = new LinkedList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 0) {
                    que.offer(i * col + j);
                }
            }
        }

        //尽量不要修改input:
        int[][] res = new int[row][col];

        int minLen = 1;
        while (!que.isEmpty()) {
            int size = que.size();
            while (size-- > 0) {
                int cur = que.poll();
                List<Integer> neighbors = getNeighbors(matrix, row, col, cur);
                for (int neighbor : neighbors) {
                    int nextI = neighbor / col;
                    int nextJ = neighbor % col;
                    //这里是查环, 只有值为 1 的节点, 并且距离为 0 (未 touch 过的节点才会被访问)
                    if (matrix[nextI][nextJ] == 1 && res[nextI][nextJ] == 0) {
                        que.offer(neighbor);
                        res[nextI][nextJ] = minLen;
                    }
                }
            }
            minLen++;
        }
        return res;
    }

    private List<Integer> getNeighbors(final int[][] matrix, final int row, final int col, final int cur) {
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


    /****************************************第二遍*******************************************/
    /*
    *   坑 1: 查环忘记了, 这里的查环跟第一种解法的查环还是不太一样,
    *           第一种解法是根据原matrix和结果matrix结合起来一起考虑: 只有值为 1 的节点, 并且距离为 0 (未 touch 过的节点才会被访问)
    *           第二种解法是单独定义了一个boolean数组来表示这个节点是否被touch过
    * */


    public int[][] updateMatrix2(int[][] matrix) {
        //corner case
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            throw new IllegalArgumentException("Invalid input");
        }

        int row = matrix.length;
        int col = matrix[0].length;

        int[][] res = new int[row][col];
        boolean[][] visited = new boolean[row][col];

        Queue<int[]> que = new LinkedList<>();
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(matrix[i][j] == 0){
                    res[i][j] = 0;
                    visited[i][j] = true;
                    que.offer(new int[]{i, j});
                }
            }
        }
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int len = 1;
        while (!que.isEmpty()){
            int size = que.size();
            while (size-- > 0){
                int[] cur = que.poll();
                for(int[] dir : directions){
                    int nextI = cur[0] + dir[0];
                    int nextJ = cur[1] + dir[1];
                    if(nextI >= 0 && nextI < row
                    && nextJ >= 0 && nextJ < col
                    && matrix[nextI][nextJ] == 1 && !visited[nextI][nextJ]){
                        res[nextI][nextJ] = len;
                        visited[nextI][nextJ] = true;
                        que.offer(new int[]{nextI, nextJ});
                    }
                }
            }
            len++;
        }
        return res;
    }







































}
