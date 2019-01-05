package tree;

/*
* 坑 1: 求左子树的高度(getHeight)的写法要注意: 是以 root 作为判断条件, root!=null
* 坑 2: 位运算的操作, 还是不熟悉, 如何进行位移?
* 坑 3: 计算高度的时候:
*       root的高度(1) +
*       full tree那一侧的子树高度(2^h-1:通过位运算计算) +
*       complete tree那一侧的子树的高度(recursion调用 dfs)
* */
@SuppressWarnings("Duplicates")
public class L222 {
    public int countNodes(TreeNode root) {
        return dfs(root);
    }

    private int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftMostLeft = getHeight(root.left);
        int leftMostRight = getHeight(root.right);
        if (leftMostLeft == leftMostRight) { //这时右子树是complete tree
            return (1 << leftMostLeft) - 1 + dfs(root.right) + 1;
        } else if (leftMostLeft > leftMostRight) { //这时左子树是complete tree
            return dfs(root.left) + (1 << leftMostRight) - 1 + 1;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private int getHeight(TreeNode root) {
        int cnt = 0;
        while (root != null) {
            cnt++;
            root = root.left;
        }
        return cnt;
    }
}
