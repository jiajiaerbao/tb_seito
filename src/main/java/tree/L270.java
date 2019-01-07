package tree;

/*
* 坑 1: 首先, 这是一道easy题目, 你做了30分钟, 而且逻辑思维很差, 有好多小的逻辑错误以及变量调用的错误, 就是写得太少了
* 坑 2: 对于当前层 root, 主要是比较 |root-target| 和 |res[0]-target| 之间的大小关系, 如果 root 更贴近 target, 则更新 res[0]
* 坑 3: 需要把 res[0] 初始化成一个合理的值, 比如 root.val
* */
@SuppressWarnings("Duplicates")
public class L270 {
    public int closestValue(TreeNode root, double target) {
        if (root == null) {
            return -1;
        }
        int[] res = new int[1];
        res[0] = root.val;
        dfs(root, target, res);
        return res[0];
    }

    private void dfs(TreeNode root, double target, int[] res) {
        if (root == null) {
            return;
        }
        if (Math.abs(root.val - target) < Math.abs(res[0] - target)) {
            res[0] = root.val;
        }
        if (target < root.val && root.left != null) {
            dfs(root.left, target, res);
        }
        if (target > root.val && root.right != null) {
            dfs(root.right, target, res);
        }
    }
}
