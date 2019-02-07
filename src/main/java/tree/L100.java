package tree;

/*
* 坑 1: 这道题按照算法哥的答案的套路做就可以了
* */
@SuppressWarnings("Duplicates")
public class L100 {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        } else {
            return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }
}
