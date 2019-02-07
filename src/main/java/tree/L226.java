package tree;

/*
* 坑 1: 一定要先把 root.left 和 root.right 暂时保存下来
*      不要在 recursion 里面用 root.left 和 root.right
*      因为 root.left 和 root.right 都是通过 root 来连接的
*      recursion之后相对位置就发生变化了
*      直接用两个临时变量 left 和 right 来直接指向 左右子树
* */
@SuppressWarnings("Duplicates")
public class L226 {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.right = invertTree(left);
        root.left = invertTree(right);
        return root;
    }
}
