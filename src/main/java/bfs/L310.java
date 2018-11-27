package bfs;

import java.util.*;

public class L310 {
    /*
    *   这道题做了四个小时: 遇到难题就开始做一些其他的事情, 逃避
    *   坑 1: 如何保存各个node, 在建立node class的时候, neighbors用set, 这样可以避免在建立node的时候的重复,
    *           因为 set 自身可以去掉重复
    *   坑 2: 如何表示叶子节点? neighbors里面只有一个节点的 node 就是 leaf
    *   坑 3: 如何应用BFS? 主要思想就是从四周的叶子节点开始开始, 利用BFS进行一层一层地缩小, 直到最后Queue里面剩下的都是leaf为止
    *   坑 4: 每个node的值都不同, 也就是可以通过各个node的 val 来区分出不同的 node
    *   坑 5: BOSS 坑, 这里的 BFS 不能用 Queue 来作为终止条件, 因为 Queue 里面始终用 node,
    *           如何判断遍历过所有的 node, 并且已经没有非 leaf 节点了?
    *           简单的办法就是计算 leaf 节点个数, 当leaf节点个数达到了 n 之后, 就没有非 leaf 节点个数了
    * */

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> res = new ArrayList<>();
        if (n <= 1) {
            res.add(0);
            return res;
        }
        int leafCount = n;
        List<TreeNode> nodeList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nodeList.add(new TreeNode(i));
        }
        for (int[] edge : edges) {
            nodeList.get(edge[0]).addNeighbor(edge[1]);
            nodeList.get(edge[1]).addNeighbor(edge[0]);
        }
        Queue<Integer> que = new LinkedList<>();
        for (TreeNode treeNode : nodeList) {
            if (treeNode.isLeaf()) {
                que.add(treeNode.val);
                leafCount--;
            }
        }
        //下面是BFS: 初始状态的Queue里面都是leaf, 一层一层地剥皮, 直到最后剩下的都是leaf为止
        while (leafCount > 0) {
            int size = que.size();
            while (size-- > 0) {
                int cur = que.poll();
                //从 cur 的所有 neighbor 里面删除 cur
                for (int neighbor : nodeList.get(cur).neighbors) {
                    nodeList.get(neighbor).removeNeighbor(cur);
                    //删除了 cur 之后, 如果这个 neighbor 也变成了 leaf, 则加入到 queue 里面
                    if (nodeList.get(neighbor).isLeaf()) {
                        que.offer(nodeList.get(neighbor).getVal());
                        leafCount--;
                    }
                }
            }
        }
        while (!que.isEmpty()) {
            res.add(que.poll());
        }
        return res;
    }

    class TreeNode {
        private int val;
        private Set<Integer> neighbors;

        TreeNode(final int val) {
            this.val = val;
            neighbors = new HashSet<>();
        }

        public Set<Integer> getNeighbors() {
            return this.neighbors;
        }

        public int getVal() {
            return this.val;
        }

        public boolean addNeighbor(final int val) {
            return neighbors.add(val);
        }

        public boolean removeNeighbor(final int val) {
            return neighbors.remove(val);
        }

        public boolean isLeaf() {
            return neighbors.size() == 1;
        }
    }
}



