package tree;

/*
* 坑 1: 相对于右子树来讲, root 就是右子树的 prev
* 坑 2: 因为 BST 是按照 in-order traverse 来升序的, 所以判断的时候, 选判断左子树, 再判断root节点, 最后判断右子树
* 坑 3: 如何用 prev[] 来表示没有赋过值? 通过 Integer prev[] 来表示
* 坑 4: 这道题的特点是严格递增, 所以两个元素相等也返回false
* */
@SuppressWarnings("Duplicates")
public class L98 {
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        Integer[] prev = {null};
        return dfs(root, prev);
    }

    private boolean dfs(TreeNode root, Integer[] prev) {
        if (root == null) {
            return true;
        }
        if (!dfs(root.left, prev)) {
            return false;
        }
        if (prev[0] != null && prev[0] >= root.val) {
            return false;
        }
        prev[0] = root.val;
        return dfs(root.right, prev);
    }
}
