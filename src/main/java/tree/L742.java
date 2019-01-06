package tree;

import java.util.*;

/*
* 坑 0: 这个是参照了 leetcode 的答案, 用 dfs 建图, 然后用 bfs 向四周遍历;
*       而算法哥把这道题放在了tree里面, 应该是用 Tree 的 方法来解, 具体用什么方法不知道, 需要确认一下, George哥说用Dijkstra算法
* 坑 1: 建图的时候, 通过dfs, 分别以 parent 和 child 作为key, 把 parent 和 child 放到 map 里面
* 坑 2: 图上面 要查环, 用 visited 来查环, que 的 初始 node 只是 node.val = k 的那个, visited 也相应的是那个node
* 坑 3: 用 map.getOrDefault(root, new LinkedList<>()) 的时候, 最后一定要 put 回 map 里面:
*           List<TreeNode> parentList = map.getOrDefault(parent, new LinkedList<>());
*           parentList.add(root);
*           map.put(parent, parentList)
* 坑 4: 题目一定要clarify清楚, 这里返回的是最后的叶子节点的 val
* 扛 5: 对于 TreeNode 的问题, 一定要先check 该 TreeNode 是否为空, 然后在对其去left, right, val等操作,
*       否则会发生 NullPointerException
* */
@SuppressWarnings("Duplicates")
public class L742 {
    public int findClosestLeaf(TreeNode root, int k) {
        if (root == null) {
            return -1;
        }
        Map<TreeNode, List<TreeNode>> map = new HashMap<>();
        //这里相当于 根节点 root 的 parent 是 null
        dfs(root, map, null);

        Queue<TreeNode> que = new LinkedList<>();
        Set<TreeNode> visited = new HashSet<>();
        for (TreeNode node : map.keySet()) {
            if (node != null && node.val == k) {
                que.offer(node);
                visited.add(node);
            }
        }
        while (!que.isEmpty()) {
            //int size = que.size();
            //while (size-- > 0) {
            TreeNode cur = que.poll();
            //只有一个edge, 就是 连接 parent
            if (cur != null && map.get(cur).size() == 1) {
                return cur.val;
            }
            for (TreeNode next : map.get(cur)) {
                if (!visited.contains(next)) {
                    que.offer(next);
                    visited.add(next);
                }
            }
            //}
        }
        return -1;
    }

    private void dfs(TreeNode root, Map<TreeNode, List<TreeNode>> map, TreeNode parent) {
        if (root != null) {
            List<TreeNode> childList = map.getOrDefault(root, new LinkedList<>());
            childList.add(parent);
            map.put(root, childList);
            List<TreeNode> parentList = map.getOrDefault(parent, new LinkedList<>());
            parentList.add(root);
            map.put(parent, parentList);

            dfs(root.left, map, root);
            dfs(root.right, map, root);
        }
    }
}
