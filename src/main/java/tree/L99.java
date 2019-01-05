package tree;


/*
* 坑 1: 按照 L98 的思路来写, 通过对 BST 进行 中序遍历 应该是递增序列的特性, 进行递归的中序遍历
* 坑 2: 当第一次发现不满足条件的 TreeNode 的时候, 一定是在prev, 所以
*       第一次碰到的时候, 把 prev 设置到 mistakeNodes里面
*       第二次碰到的时候, 把 cur(root) 设置到 mistakeNodes里面
* 坑 3: 设置 cur(root) 的时候, 无条件地设置到 mistakeNodes[1] 里面, 而不能用if else
*       如果两个node恰好相邻的话, 用了if else只能设置一个点, 因为第一次只能设置prev
* */
@SuppressWarnings("Duplicates")
public class L99 {
    public void recoverTree(TreeNode root) {
        TreeNode[] mistakeNodes = new TreeNode[2];
        TreeNode[] prev = new TreeNode[1];
        recover(root, mistakeNodes, prev);
        if (mistakeNodes[0] != null && mistakeNodes[1] != null) {
            int temp = mistakeNodes[0].val;
            mistakeNodes[0].val = mistakeNodes[1].val;
            mistakeNodes[1].val = temp;
        }
    }

    private void recover(TreeNode root, TreeNode[] mistakeNodes, TreeNode[] prev) {
        if (root == null) {
            return;
        }
        recover(root.left, mistakeNodes, prev);
        //这里处理记录validate node 的逻辑
        if (prev[0] != null && prev[0].val > root.val) {
            if (mistakeNodes[0] == null) {
                mistakeNodes[0] = prev[0];
            }
            //这里cover了两个invalidate nodes 恰好相邻的情况
            mistakeNodes[1] = root;
        }
        prev[0] = root;
        recover(root.right, mistakeNodes, prev);
    }
}
