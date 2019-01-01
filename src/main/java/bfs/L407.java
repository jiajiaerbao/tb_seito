package bfs;

import java.util.*;

public class L407 {
    /*
    * 题目不难, 有思路, 但是就是解不出来
    * 坑 1: 在建立 Cell 的时候, 一定要实现 Comparable<> interface, 在class 里面实现 object 的compareTo 方法, 这样在放入PriorityQueue的时候, 就会实现自动地从小到大的排序
    * 坑 2: 用 PriorityQueue 来代替普通的 Queue
    * 坑 3: 坑 BOSS:
    *   因为最开始, 在Queue里面的是不可能产生积水的Cell, 当有新的Cell加入到 Queue 的时候, 需要判断是否能产生积水, 用当前的Cell 高度 减去 next的Cell 高度, 如果为负则取零
    *   在加入到queue的时候, next的Cell的高度是取 当前Cell和Next Cell高度中的较大的值:
    *       如果 当前Cell高度 大于 Next Cell高度, 则按照 当前Cell高度产生积水
    *       如果 当前Cell高度 小于 Next Cell高度, 则不产生积水, next Cell高度按照自身的高度来计算
    * 坑 4: 时间复杂度是 O(m*n*lg(m+n)), 总共有 m*n 个节点, 因为这里的BFS的Queue用的是 PriorityQueue,
    *       放进 Queue 里面的元素最大个数是 (m+n), 每次放进去一个元素都需要 重新排序, 最多需要log(m+n)
    * */

    class Cell implements Comparable<Cell> {
        private int x;
        private int y;
        private int height;

        public Cell(final int x, final int y, final int height) {
            this.x = x;
            this.y = y;
            this.height = height;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        @Override
        public int compareTo(Cell that) {
            return this.height - that.height;
        }
    }

    public int trapRainWater(int[][] heightMap) {
        int res = 0;
        //corner case
        if (heightMap == null || heightMap.length == 0 || heightMap[0] == null || heightMap[0].length == 0) {
            return res; // throw IllegalArgumentException("Invalid input");
        }
        int row = heightMap.length;
        int col = heightMap[0].length;
        //BFS
        PriorityQueue<Cell> que = new PriorityQueue<>();
        boolean[][] visited = new boolean[row][col];

        //在加入到Queeu的时候, 不要忘记设置 visited 为 true
        for (int i = 0; i < row; i++) {
            que.offer(new Cell(i, 0, heightMap[i][0]));
            visited[i][0] = true;
            que.offer(new Cell(i, col - 1, heightMap[i][col - 1]));
            visited[i][col - 1] = true;
        }
        for (int j = 1; j < col - 1; j++) {
            que.offer(new Cell(0, j, heightMap[0][j]));
            visited[0][j] = true;
            que.offer(new Cell(row - 1, j, heightMap[row - 1][j]));
            visited[row - 1][j] = true;
        }

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!que.isEmpty()) {
            //这里不用按层来搜索, 按层搜索的逻辑可以省略
            //int size = que.size();
            //while (size-- > 0) {
            Cell cur = que.poll();
            for (int[] dir : directions) {
                int nextI = cur.getX() + dir[0];
                int nextJ = cur.getY() + dir[1];
                if (nextI >= 0 && nextI < row &&
                        nextJ >= 0 && nextJ < col &&
                        !visited[nextI][nextJ]) {
                    visited[nextI][nextJ] = true;
                    /*
                     * 下面这两句是关键:
                     *   从四周的 Cell 开始向里面一层一层地, 以高度从小到大的顺序进行扩展, 这时的积水积水是 curCell.height - nextCell.height
                     *       也就是说, 当下一个将要加入到 que 的节点的高度 高于 当前的节点的高度的时候, 则下一个节点无法产生积水
                     *   在加入到Queue的时候, 加入高度较高的那个值,
                     *      也就是说如果 下一个将要加入到 que 的节点的高度 高于 当前的节点的高度的时候, 按照下一个将要加入到 que 的节点高度计算, 这时next cell 不产生积水
                     *      当 下一个将要加入到 que 的节点的高度 小于 当前的节点的高度的时候, 按照 当前 的节点高度计算, 这时next cell 产生积水
                     * */
                    res += Math.max(0, cur.height - heightMap[nextI][nextJ]);
                    que.offer(new Cell(nextI, nextJ, Math.max(cur.height, heightMap[nextI][nextJ])));
                }
            }
            //}
        }
        return res;
    }
}