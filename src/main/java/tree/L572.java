package tree;

/*
* 坑 1: t 可能是 s 的子树
*       t 或者 跟 s 相等
* 坑 2: 判断 s 和 t 是否相等的时候, 直接用 s 和 t 即可
*       判断 t 是否是 s 的子树 的是, 用 s.left/s.right 和 t 来判断, 这时先判断 s 是否为 null
* */
@SuppressWarnings("Duplicates")
public class L572 {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        return checkAllPossible(s, t);
    }

    private boolean checkAllPossible(TreeNode s, TreeNode t) {

        return exactSame(s, t) || //这里是判断 s 和 t 相等
                (s != null && checkAllPossible(s.left, t)) || //在求左子树 .left 之前, 一定要check s 是否为 null
                (s != null && checkAllPossible(s.right, t)); //在求右子树 .right 之前, 一定要check s 是否为 null
    }

    private boolean exactSame(TreeNode s, TreeNode t) {
        if (s == null && t == null) {
            return true;
        } else if (s == null || t == null) {
            return false;
        } else {
            return s.val == t.val &&
                    exactSame(s.left, t.left) &&
                    exactSame(s.right, t.right);
        }
    }
}
