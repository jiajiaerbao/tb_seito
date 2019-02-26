package search;

import java.util.PriorityQueue;

@SuppressWarnings("Duplicates")
public class L378 {
    /*
     * [1,  5, 9]
     * [10,11,13]
     * [12,13,15]
     * */
    /******************************第二遍********************************************/
    /*
    * 坑 1: 坑BOSS, 千万别忘了加一个visited, 用来去除重复访问节点
    * */
    class SecondTime {
        class Cell {
            int x;
            int y;
            int val;
            public Cell(int x, int y, int val) {
                this.x = x;
                this.y = y;
                this.val = val;
            }
        }
        public int kthSmallest(int[][] matrix, int k) {
            int cnt = k;
            int row = matrix.length;
            int col = matrix[0].length;
            boolean[][] visited = new boolean[row][col];

            PriorityQueue<Cell> minHeap = new PriorityQueue<>((c1, c2) -> c1.val - c2.val);
            minHeap.offer(new Cell(0, 0, matrix[0][0]));
            visited[0][0] = true;
            while (cnt > 1) {
                Cell cur = minHeap.poll();
                if (cur.x + 1 < row && !visited[cur.x + 1][cur.y]) {
                    visited[cur.x + 1][cur.y] = true;
                    minHeap.offer(new Cell(cur.x + 1, cur.y, matrix[cur.x + 1][cur.y]));
                }
                if (cur.y + 1 < col && !visited[cur.x][cur.y + 1]) {
                    visited[cur.x][cur.y + 1] = true;
                    minHeap.offer(new Cell(cur.x, cur.y + 1, matrix[cur.x][cur.y + 1]));
                }
                cnt--;
            }
            return minHeap.peek().val;
        }
    }
    /******************************第一遍********************************************/
    /*
    * 坑 1: 自己wrap一个类: Cell, 用来保存各个点的所有信息(横坐标, 纵坐标, 值)
    * 坑 2: 改写 wrap好的Cell的 compareTo, 用于放进 minHeap里面
    * 坑 3: 以每个
    * 坑 4: 因为数组是从左上角到右下角, 从小到大排好序的:
    *           找第k小的话, 用minHeap, pop k-1 次之后, heap.peak()即是结果
    *           找第k大的话, 用maxHeap, pop k-1 次之后, heap.peak()即是结果
    * */
    class FirstTime{
        public class Cell implements Comparable<Cell> {
            final int x;
            final int y;
            final int val;

            //从小到大排列
            public Cell(final int x, final int y, final int val) {
                this.x = x;
                this.y = y;
                this.val = val;
            }

            @Override
            public int compareTo(Cell that) {
                return this.val - that.val;
            }
        }

        public int kthSmallest(int[][] matrix, int k) {
            if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
                return 0;
            }
            //这里调用 Cell的 compareTo 方法
            PriorityQueue<Cell> minHeap = new PriorityQueue<>();
            final int row = matrix.length;
            final int col = matrix[0].length;
            boolean[][] visited = new boolean[row][col];
            minHeap.offer(new Cell(0, 0, matrix[0][0]));
            for (int i = 0; i < k - 1; i++) {
                Cell curCell = minHeap.poll();
                int nextI = curCell.x + 1;
                int nextJ = curCell.y + 1;
                if (nextI < row && !visited[nextI][curCell.y]) {
                    minHeap.offer(new Cell(nextI, curCell.y, matrix[nextI][curCell.y]));
                    visited[nextI][curCell.y] = true;
                }
                if (nextJ < col && !visited[curCell.x][nextJ]) {
                    minHeap.offer(new Cell(curCell.x, nextJ, matrix[curCell.x][nextJ]));
                    visited[curCell.x][nextJ] = true;
                }
            }
            return minHeap.peek().val;
        }
    }
}
