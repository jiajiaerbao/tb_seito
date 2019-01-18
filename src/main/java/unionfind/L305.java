package unionfind;

import java.util.ArrayList;
import java.util.List;

/*
* 坑 1:
* 坑 2: parent[]的取值意义: -1代表还没有touch到的(还是海洋), cellParent[cur] = cur 表明第一次touch到, 自己指向自己, 还没有跟其它的cell发生过合并
* 坑 3: 如何处理二维数组的Union-Find问题:
*           对每个Cell需要保存的信息:
*               parent指针: 单独建立一个一维数组(row*col-1), 单独存放
*               各个cell所在岛屿的size: 拉直了存放在一个一维的数组里面
* 坑 4: 在getRoot里面不要忘记: 1.压缩路径; 2.parent跳两步
*               cellParent[cur] = cellParent[cellParent[cur]]
*       while的条件是parent不指向自己
*               cellParent[cur] != cur
* 坑 5: 坑BOSS: 在Union的时候:
*       在找到了root1和roo2之后, 要注意: Union后面都是基于找到的root的操作,
*       需要判断以 root1 和 root2 根的 size 分别是多少, 然后使 root1 指向 root2 或者使 root2 指向 root1
* 坑 6: addIsland的时候, 先要判断一下是否是海洋
* */
@SuppressWarnings("Duplicates")
public class L305 {
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<>();
        if (m <= 0 || n <= 0 || positions == null || positions.length == 0 || positions[0] == null || positions[0].length == 0) {
            return res;
        }
        //corner case
        UnionFind unionFind = new UnionFind(m, n);
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] pose : positions) {
            int i = pose[0];
            int j = pose[1];
            int idx = i * n + j;
            unionFind.addIsland(idx);
            for (int[] dir : dirs) {
                int nextI = i + dir[0];
                int nextJ = j + dir[1];
                int nextIdx = nextI * n + nextJ;
                if (nextI >= 0 && nextI < m &&
                        nextJ >= 0 && nextJ < n &&
                        unionFind.isIsland(nextIdx) &&      //是岛
                        !unionFind.find(idx, nextIdx)       //不属于同一块陆地
                ) {
                    unionFind.union(idx, nextIdx);
                }
            }
            res.add(unionFind.size);
        }
        return res;
    }

    private class UnionFind {
        int[] cellParent;
        int[] cellSize;
        int row;
        int col;
        int size;               //整个2D board的陆地个数, 初始是零

        public boolean isIsland(int idx) {
            return cellParent[idx] != -1;   //-1是初始值, 是海洋
        }

        public void addIsland(int idx) {
            if (cellParent[idx] == -1) {
                cellParent[idx] = idx;
                this.size++;
            }
        }

        public UnionFind(int m, int n) {
            this.row = m;
            this.col = n;
            this.size = 0;
            cellParent = new int[row * col];
            cellSize = new int[row * col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    int cur = i * col + j;  //这里是 rowIdx * COL + colIdx
                    cellParent[cur] = -1;
                    cellSize[cur] = 1;
                }
            }
        }

        public boolean find(int idx1, int idx2) {
            return getRoot(idx1) == getRoot(idx2);
        }

        public void union(int idx1, int idx2) {
            int root1 = getRoot(idx1);
            int root2 = getRoot(idx2);
            if (root1 == root2) {
                return;
            }
            if (cellSize[root1] < cellSize[root2]) {
                cellParent[root1] = root2;
                cellSize[root2] += cellSize[root1];
            } else {
                cellParent[root2] = root1;
                cellSize[root1] += cellSize[root2];
            }
            this.size--;//每发生一次Union, 2D board的size就减少1
        }

        private int getRoot(int curIdx) {
            int cur = curIdx;
            while (cellParent[cur] != cur) {
                cellParent[cur] = cellParent[cellParent[cur]]; // 压缩路径
                cur = cellParent[cur];
            }
            cellParent[curIdx] = cur;
            return cur;
        }
    }
    public static void main(String[] args){
        L305 l305 = new L305();
        int m = 8;
        int n = 2;
        int[][] positions = {{7,0}};
        System.out.println(l305.numIslands2(m, n, positions));
    }
}
