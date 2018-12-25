package dfsNormal;

import java.util.*;

@SuppressWarnings("Duplicates")
/*
* 代码有问题, 有的测试用例跑步过去, 但是大概思路是对的
* on-site的话, 按照这个代码来写的话, 没有问题, 思路能讲得清楚就没问题
* */

/*
* 坑 1: 对于相同形状的岛, 比如 测试用例2 的四个1组成的岛, 虽然他们的形状相同, 但是通过DFS遍历之后, 得到的节点的起始点不一样
* 第一个岛: [0,0] [1,0] [0,1] [0,2]
* 第二个岛: [2,1] [3,1] [3,2] [3,3]
*       对于通过八种转换之后, 能够得到形状相同的岛, 在最开始通过DFS遍历之后, 得到的list的节点之间的相对顺序不同
*       (因为各个岛不能通过平移实现相同), 这时需要对各个岛进行八种方式的转换, 每转换一次, 找出所有点中的最小值点,
*       然后以该最小值点作为基准, 对岛进行平移, 平移之后, 对岛的所有坐标进行hash, 来比较大小, hash的时候, 转化为String进行Hash简单些
* 坑 2: 与694的不同点, 这里有八种转换
*       通过DFS得到的两个岛, 如果通过八种转换实现形状一样的话, 在DFS后得到的两个ArrayList中,
*       各个node的相对关系不一样, 也就是进入两个岛的起始点可能是任意一个node, 因为起始点可能是不同的node,
*       所以DFS后生成的ArrayList的各个 node 的相对关系 也是不同的
* 坑 3: 对每个岛进行八种转换
*       在每一种转换后的所有node中, 找到最小的那个node, 以最小的那个node作为基准点, 进行平移
*       对平移后的所有点进行排序
*       找出 a.八种转换中; b.以最小值点进行平移; c.所有点进行排序;
*       a.是题目要求
*       b.是去掉相对坐标的干扰
*       c.是去掉以不同点进入相同形状的岛时, 得到的list的点的顺序不一样的干扰
*       得到上面a, b, c之后的 canonical的list(比如最小值的list作为该图形的岛的代表)
*       把各个图形的岛的list存入set用来去重复(可以通过String来序列号list)
* 坑 4: 坐标平移的时候, 不是以list中的某个node作为基准进行平移,
*       而是将 x 和 y 分开对待, 找出所有x中的最小值, 找出所有y中的最小值, 比如平移(9, 66)或者(-100, 6)等
*       也就是说平移的基准点不是list中的某个点
* */
/*
* 测试用例1:
[[1,1,0,0,0],
 [1,0,0,0,0],
 [0,0,0,0,1],
 [0,0,0,1,1]]

[[1,1,0,0,0],[1,0,0,0,0],[0,0,0,0,1],[0,0,0,1,1]]

* 测试用例2:
 [[1,1,1,0,0],
  [1,0,0,0,1],
  [0,1,0,0,1],
  [0,1,1,1,0]]

[[1,1,1,0,0],[1,0,0,0,1],[0,1,0,0,1],[0,1,1,1,0]]

* 测试用例3:
[[0,1,1],
 [1,1,1],
 [0,0,0],
 [1,1,1],
 [1,1,0]]

[[0,1,1],[1,1,1],[0,0,0],[1,1,1],[1,1,0]]
* */
public class L711 {
    public int numDistinctIslands2(int[][] grid) {
        //corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        int row = grid.length;
        int col = grid[0].length;
        boolean[][] visited = new boolean[row][col];
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        Set<String> res = new HashSet<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    ArrayList<int[]> arrayList = new ArrayList<>();
                    helper(grid, visited, directions, i, j, arrayList);
                    String islandStr = getCanonicalHash(arrayList);
                    res.add(islandStr);
                }
            }
        }
        return res.size();
    }

    private void helper(int[][] grid, boolean[][] visited, int[][] directions, int idxI, int idxJ, ArrayList<int[]> arrayList) {
        int row = grid.length;
        int col = grid[0].length;
        if (idxI < 0 || idxI >= row || idxJ < 0 || idxJ >= col || visited[idxI][idxJ] || grid[idxI][idxJ] == 0) {
            return;
        }
        visited[idxI][idxJ] = true;
        arrayList.add(new int[]{idxI, idxJ});
        for (int[] dir : directions) {
            int nextI = idxI + dir[0];
            int nextJ = idxJ + dir[1];
            helper(grid, visited, directions, nextI, nextJ, arrayList);
        }
    }

    private String getCanonicalHash(List<int[]> list) {
        //用于保存序列化之后的某个形状的岛的坐标
        String res = "";
        //八种转换: 注意, 每次转换 都是对 某个岛的 所有坐标 同时进行转换
        for (int i = 0; i < 8; i++) {
            //用于保存每次转换之后的所有点
            List<int[]> transList = new ArrayList<>();
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            //用于保存每次转换之后的某个岛所有点: 保持原始的点和点之间的相互关系
            for (int[] cur : list) {
                int[] next = getTransformPosition(cur[0], cur[1], i);
                //记录每次转换之后最小x和最小y: [minX, minY]就是相对的坐标原点, 即 进行水平和垂直平移的坐标原点
                minX = Math.min(minX, next[0]);
                minY = Math.min(minY, next[1]);
                transList.add(next);
            }
            //坐标平移
            for (int j = 0; j < transList.size(); j++) {
                transList.set(j, new int[]{transList.get(j)[0] - minX, transList.get(j)[1] - minY});
            }
            //对所有点进行排序
            transList.sort((int[] a, int[] b) -> (a[0] * a[0] + a[1] * a[1] - b[0] * b[0] - b[1] * b[1]));
            //通过StringBuilder进行序列化, 便于去除重复
            StringBuilder path = new StringBuilder();
            for (int[] cur : transList) {
                path.append(cur[0]).append(cur[1]);
            }
            String tempRes = path.toString();
            if (res.compareTo(tempRes) < 0) res = tempRes;
        }
        return res;
    }

    private int[] getTransformPosition(int x, int y, int idx) {
        switch (idx) {
            case 0:
                return new int[]{x, y};
            case 1:
                return new int[]{-x, y};
            case 2:
                return new int[]{x, -y};
            case 3:
                return new int[]{-x, -y};
            case 4:
                return new int[]{y, x};
            case 5:
                return new int[]{-y, x};
            case 6:
                return new int[]{y, -x};
            case 7:
                return new int[]{-y, -x};
        }
        return new int[]{x, y};
    }

    public static void main(String args[]) {
        L711 l711 = new L711();
        int[][] input = {{0,1,0,1,1,0,0,0,0,1,0,1,1,0,1},{0,0,0,0,1,1,0,1,0,0,0,1,1,1,0},{1,1,0,0,0,1,0,1,0,0,0,1,1,0,0},{0,1,0,1,0,1,1,0,1,0,1,1,1,0,0},{0,0,1,0,1,0,1,0,1,0,1,1,1,0,1},{1,1,1,0,0,1,0,0,0,1,0,0,0,1,0},{0,0,1,1,1,0,1,0,0,0,1,0,1,0,0},{0,1,0,0,1,1,0,0,1,1,1,0,1,0,0},{1,1,1,0,0,1,0,0,0,1,0,1,1,0,1},{1,0,0,0,0,0,1,0,0,0,0,1,1,1,1}};
        System.out.println(l711.numDistinctIslands2(input));
    }
}
