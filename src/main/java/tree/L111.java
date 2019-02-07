package tree;

/*
* 坑 1: 需要根据左右子树的高度是否为零, 来分别讨论
*       左子树高度为零, 则 高度是 右子树高度+1, 否则会变成 0+1
*       右子树高度为零, 则 高度是 左子树高度+1, 否则会变成 0+1
*       左右子树都不为零, 则 取较小的那个
* */
@SuppressWarnings("Duplicates")
public class L111 {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = minDepth(root.left);
        int rightDepth = minDepth(root.right);
        if (leftDepth == 0) {
            return rightDepth + 1;
        } else if (rightDepth == 0) {
            return leftDepth + 1;
        } else {
            return Math.min(leftDepth, rightDepth) + 1;
        }
    }
}
