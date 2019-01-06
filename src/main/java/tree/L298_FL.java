package tree;

/*
* 坑 1: 298 的 follow up: 如果不是单一方向的递增 递减的话, 找出最长的
* 坑 2: 这道题的考点就是 对于 左右子树, 要分别 返回 当前左右子树 的最长的 {递增len, 递减len}
* 坑 3: 同时记录, 以当前节点作为拐点的 最大递增len 最大递减len
* */
@SuppressWarnings("Duplicates")
public class L298_FL {
    public int longestConsecutive(TreeNode root) {
        if (root == null) {
            return 0;
        }
        //保留 以当前节点作为拐点的 最长递增 最长递减 序列的长度
        int[] cnt = new int[1];
        dfs(root, cnt);
        return cnt[0];
    }

    private int[] dfs(TreeNode root, int[] cnt) {
        //保留 当前节点的 最长的 递增序列, 递减序列 的长度
        int[] cur = new int[]{1, 1};
        if (root == null) {
            return new int[]{1, 1};
        }
        int[] left = dfs(root.left, cnt);
        int[] right = dfs(root.right, cnt);
        //更新左子树的 最长递增 最长递减 序列的长度
        if (root.left != null) {
            if (root.val > root.left.val) {
                cur[0] = left[0] + 1;
            } else {
                cur[1] = left[1] + 1;
            }
        }
        //更新右子树的 最长递增 最长递减 序列的长度
        if (root.right != null) {
            if (root.val > root.right.val) {
                cur[0] = Math.max(cur[0], right[0] + 1);
            } else {
                cur[1] = Math.max(cur[1], right[1] + 1);
            }
        }
        if (root.left != null && root.right != null) {
            cnt[0] = Math.max(cur[0] + cur[1] - 1, cnt[0]);
        } else {
            cnt[0] = Math.max(cnt[0], Math.max(cur[0], cur[1]));
        }
        return cur;
    }

    public static void main(String[] args) {
        //[1,null,3,2,4,null,null,null,5]
        TreeNode root = new TreeNode(1);
        TreeNode child3 = new TreeNode(3);
        root.right = child3;
        TreeNode child2 = new TreeNode(2);
        TreeNode child4 = new TreeNode(4);
        child3.left = child2;
        child3.right = child4;
        TreeNode child5 = new TreeNode(5);
        child4.right = child5;

        /*TreeNode root = new TreeNode(1);
        TreeNode child2 = new TreeNode(2);
        TreeNode child6 = new TreeNode(6);
        root.left = child2;
        root.right = child6;
        TreeNode child3 = new TreeNode(3);
        TreeNode child1 = new TreeNode(1);
        child2.left = child3;
        child2.right = child1;
        TreeNode child4 = new TreeNode(4);
        child3.left = child4;
        TreeNode child5 = new TreeNode(5);
        child4.right = child5;
        TreeNode childMinus10 = new TreeNode(-10);
        child1.left = childMinus10;
        TreeNode childMunis15 = new TreeNode(-15);
        childMinus10.right = childMunis15;*/

        L298_FL l298_fl = new L298_FL();
        System.out.println(l298_fl.longestConsecutive(root));
    }
}
