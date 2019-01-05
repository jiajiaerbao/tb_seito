package tree;

/*
* 坑 1: 坑 BOSS: 这道题虽然做出来了, 但是自顶向下和自底向上的思路不是很明确
* 坑 2: 这里是一种自顶向下的思想:
*       判断 root.val==root.left.val 和 root.val==root.right.val 是否成立 (前提是左右子树存在)
* 坑 3: 因为是调用dfs(左子树), dfs(右子树), 所以实际上还是从最底层开始一层一层地判断
* 坑 4: 跟算法哥的答案的区别在于: 算法哥的答案是在 左右子树的dfs 里面 各自判断;
*       而你是在 root 的 dfs 里面判断
* */
@SuppressWarnings("Duplicates")
public class L250 {
    public int countUnivalSubtrees(TreeNode root) {
        int[] res = new int[1];
        dfs(root, res);
        return res[0];
    }

    private boolean dfs(TreeNode root, int[] cnt) {
        if (root == null) {
            return true;
        }
        boolean left = dfs(root.left, cnt);
        boolean right = dfs(root.right, cnt);
        if (!left || !right) {
            return false;
        }
        if (root.left != null && root.val != root.left.val) {
            return false;
        }
        if (root.right != null && root.val != root.right.val) {
            return false;
        }
        cnt[0]++;
        return true;
    }
}
