package search;

import java.util.*;


@SuppressWarnings("Duplicates")
public class L373 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 这里有两个坑:
    *           一: 一定要设置一个visited数组, 否则会出现循环访问的情况
    *           二: while loop的退出条件, k>0 && minHeap is not empty
    * */
    public class SecondTime {
        class Cell {
            int x;
            int y;
            int val;
            public Cell(int x, int y, int val) {
                this.x = x;
                this.y = y;
                this.val = val;
            }
            @Override
            public int hashCode() {
                return x * 31 + y;
            }
            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Cell) {
                    Cell that = (Cell) obj;
                    return that.x == this.x && that.y == this.y;
                } else {
                    return false;
                }
            }
        }
        public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            List<int[]> res = new ArrayList<>();
            if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
                return res;
            }
            Set<Cell> visited = new HashSet<>();
            PriorityQueue<Cell> minHeap = new PriorityQueue<>((c1, c2) -> c1.val - c2.val);

            Cell initCell = new Cell(0, 0, nums1[0] + nums2[0]);
            minHeap.offer(initCell);
            visited.add(initCell);
            int cnt = k;
            while (cnt > 0 && !minHeap.isEmpty()) {
                Cell curCell = minHeap.poll();
                int curI = curCell.x;
                int curJ = curCell.y;
                res.add(new int[]{nums1[curI], nums2[curJ]});
                if (curI + 1 < nums1.length) {
                    Cell newCell = new Cell(curI + 1, curJ, nums1[curI + 1] + nums2[curJ]);
                    if (!visited.contains(newCell)) {
                        minHeap.offer(newCell);
                        visited.add(newCell);
                    }
                }
                if (curJ + 1 < nums2.length) {
                    Cell newCell = new Cell(curI, curJ + 1, nums1[curI] + nums2[curJ + 1]);
                    if (!visited.contains(newCell)) {
                        minHeap.offer(newCell);
                        visited.add(newCell);
                    }
                }
                cnt--;
            }
            return res;
        }
    }
    /******************************第一遍********************************************/
    public class FirstTime{
        /*这里是针对第一种解法
         * 坑 1: while loop 的条件是 cnt-->0 并且 minHeap不是空
         * 坑 2: 这是最优解, 是从左上角的第一个元素开始,
         *           每次取出[i, j]位置的元素之后,
         *           下一步是把 [i+1, j] 和 [i, j+1] 的元素放入到 minHeap 里面
         *           放入之前注意check i+1 和 j+1 是否越界
         * 坑 3: 细节注意一下, 比如构造函数的参数顺序
         * 坑 4: 这里是暴力解法, 把所有的元素都放到了minHeap里面, 然后取出前 k
         * */
        public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            List<int[]> res = new ArrayList<>();
            //corner case
            if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
                return res;
            }
            PriorityQueue<Cell_1> minHeap = new PriorityQueue<>((cell1, cell2) -> cell1.val - cell2.val);
            Set<Cell_1> visited = new HashSet<>();

            Cell_1 cell = new Cell_1(0, 0, nums1[0] + nums2[0]);
            minHeap.offer(cell);
            visited.add(cell);

            int cnt = k;
            while (cnt-- > 0 && !minHeap.isEmpty()) {
                Cell_1 cur = minHeap.poll();
                res.add(new int[]{nums1[cur.x], nums2[cur.y]});
                if (cur.x + 1 < nums1.length) {
                    Cell_1 newCell = new Cell_1(cur.x + 1, cur.y, nums1[cur.x + 1] + nums2[cur.y]);
                    if (!visited.contains(newCell)) {
                        minHeap.offer(newCell);
                        visited.add(newCell);
                    }
                }
                if (cur.y + 1 < nums2.length) {
                    Cell_1 newCell = new Cell_1(cur.x, cur.y + 1, nums1[cur.x] + nums2[cur.y + 1]);
                    if (!visited.contains(newCell)) {
                        minHeap.offer(newCell);
                        visited.add(newCell);
                    }
                }
            }
            return res;

        }

        private class Cell_1 {
            int x;
            int y;
            int val;

            public Cell_1(int x, int y, int val) {
                this.x = x;
                this.y = y;
                this.val = val;
            }

            @Override
            public int hashCode() {
                return x * 31 + y;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Cell_1) {
                    Cell_1 cell = (Cell_1) obj;
                    return this.x == cell.x && this.y == cell.y;
                } else {
                    return false;
                }
            }
        }

        /*这里是针对第二种解法
         * 坑 1: hashCode 和 equals 写的还不够熟练
         * 坑 2: 这里的hashCode直接用乘以31的方法即可
         * 坑 3: 坑BOSS, 比较大小的话,
         *           可以在 Cell_2 implements Comparable<Cell_2>,
         *                   然后 @Override
         *                        public int compareTo(Cell_2 cell1)
         *           也可以在 PriorityQueue 里面改写 Comparator
         *               new PriorityQueue<Cell_2>(cell1, cell2) -> cell1.val - cell2.val);
         *       也就是说, Java比较对象有两种方式:
         *           第一种是把Object的 Class implements Comparable<Cell>, 然后 override compareTo, 把Object变成可比较的
         *           第二种是定义一个comparator
         * */
        public List<int[]> kSmallestPairs_2(int[] nums1, int[] nums2, int k) {
            List<int[]> res = new ArrayList<>();
            //corner case
            if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
                return res;
            }
            Cell_2[][] numsAll = new Cell_2[nums1.length][nums2.length];
            for (int i = 0; i < nums1.length; i++) {
                for (int j = 0; j < nums2.length; j++) {
                    numsAll[i][j] = new Cell_2(i, j, nums1[i] + nums2[j]);
                }
            }
            PriorityQueue<Cell_2> minHeap = new PriorityQueue<>();
            Set<Cell_2> visited = new HashSet<>();
            for (int i = 0; i < numsAll.length; i++) {
                for (int j = 0; j < numsAll[0].length; j++) {
                    if (visited.contains(numsAll[i][j])) {
                        continue;
                    }
                    visited.add(numsAll[i][j]);
                    minHeap.offer(numsAll[i][j]);
                }
            }
            int cnt = k;
            while (cnt-- > 0) {
                Cell_2 cur = minHeap.poll();
                if (cur != null) {
                    res.add(new int[]{nums1[cur.x], nums2[cur.y]});
                }
            }
            return res;
        }

        private class Cell_2 implements Comparable<Cell_2> {
            int x;
            int y;
            int val;

            public Cell_2(int x, int y, int val) {
                this.x = x;
                this.y = y;
                this.val = val;
            }

            @Override
            public int compareTo(Cell_2 cell21) {
                return this.val - cell21.val;
            }

            @Override
            public int hashCode() {
                //这里用简单的乘以31即可
                return x * 31 + y;
            }

            @Override
            public boolean equals(Object obj) {
                //这里直接用instanceof就行, 不用判断null, 如果obj是null, 则instanceof返回false
                if (obj instanceof Cell_2) {
                    Cell_2 cell2 = (Cell_2) obj;
                    return this.x == cell2.x && this.y == cell2.y;
                } else {
                    return false;
                }
            }
        }
    }
}
