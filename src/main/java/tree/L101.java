package tree;

/*
* 坑 1: 充分利用 recursion 的性质:
*           要么 左子树为空 并且 右子树为空
*           要么 左子树的左child == 右子树的右child 并且 左子树的右child == 右子树的左child 并且 左右子树相等
* */
@SuppressWarnings("Duplicates")
public class L101 {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return checkSym(root.left, root.right);
    }

    private boolean checkSym(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        } else if (left == null || right == null) {
            return false;
        } else {
            return left.val == right.val &&
                    checkSym(left.left, right.right) &&
                    checkSym(left.right, right.left);
        }
    }
}
