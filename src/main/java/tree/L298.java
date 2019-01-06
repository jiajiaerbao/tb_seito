package tree;

/*
* 坑 1: corner case: root==null 需要单独处理
* 坑 2: 在 dfs 里面, 当前层的高度 len = 1, 也就是起始高度是 1
* 坑 3: 这里的逻辑跟你自己写的 L250 的代码一样,
*       就是站在 root 层的 dfs, 跟 left child 和 right child 进行比较, 然后取较大的长度返回
*       同时更新 global len
* */
@SuppressWarnings("Duplicates")
public class L298 {
    public int longestConsecutive(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int[] res = new int[1];
        dfs(root, res);
        return res[0] + 1;
    }

    private int dfs(TreeNode root, int[] res) {
        int cur = 1;
        if (root == null) {
            return cur;
        }
        int left = dfs(root.left, res);
        int right = dfs(root.right, res);
        if (root.left != null && root.val + 1 == root.left.val) {
            cur = left + 1;
        }
        if (root.right != null && root.val + 1 == root.right.val) {
            cur = Math.max(cur, right + 1);
        }
        res[0] = Math.max(cur, res[0]);
        return cur;
    }
}
