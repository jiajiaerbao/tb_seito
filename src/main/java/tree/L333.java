package tree;

/*
* 坑 1: 对于 root 来说, 左子树 和 右子树 可有可无:
*       root.left == null 表示没有左子树
*       root.left == null || left.max < root.val
*       对于 右子树 也同样
* 坑 2: 根据从 左右子树得到的信息, 跟当前 root 的信息进行判断的时候
*       a.是 root == null 的节点的话, 自动 + 1, 也就是通过 size == 0 来判断
*       b.如果 root 不是 null 的话, 根据左右子树返回的 min 和 max 来判断
* 坑 3: 生成新的 Wrapper class 的时候, 注意 max 和 min 的先后顺序
* */
@SuppressWarnings("Duplicates")
public class L333 {
    public int largestBSTSubtree(TreeNode root) {
        int[] res = new int[1];
        dfs(root, res);
        return res[0];
    }

    private Wrapper dfs(TreeNode root, int[] cnt) {
        if (root == null) {
            return new Wrapper(0, 0, 0);
        }
        Wrapper left = dfs(root.left, cnt);
        Wrapper right = dfs(root.right, cnt);
        //左子树 或者 右子树 不是BST
        if (left == null || right == null) {
            return null;
        }
        //left.size == 0 和 right.size == 0 说明 左子树 或者 右子树 是 null
        if ((left.size == 0 || left.max < root.val) &&
                (right.size == 0 || root.val < right.min)) {
            cnt[0] = Math.max(cnt[0], left.size + right.size + 1);
            return new Wrapper(
                    right.size == 0 ? root.val : right.max,
                    left.size == 0 ? root.val : left.min,
                    left.size + right.size + 1);
        }
        return null;
    }

    private class Wrapper {
        public int max;
        public int min;
        public int size;

        public Wrapper(int max, int min, int size) {
            this.max = max;
            this.min = min;
            this.size = size;
        }
    }
}
