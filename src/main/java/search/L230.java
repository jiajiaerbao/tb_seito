package search;

import interval.Interval;

@SuppressWarnings("Duplicates")
public class L230 {
    /******************************第二遍********************************************/


    /******************************第一遍********************************************/
    /*
    * 坑 1: 坑BOSS, 因为是BST, 所以中序遍历是单调递增的, 第k大的话, 用中序遍历递归做法即可
    *       helper函数的return type: Integer:
    *               如果是具体数字的话, 表示已当前节点作为根的节点个数
    *               如果是null的话, 表示已经找到了第k大的节点
    * 坑 2: base case是
    *               root==null的时候, 返回 0
    *
    * */
    public int kthSmallest(TreeNode root, int k) {
        int[] res = {0};
        //这里注意, 最后返回的结果是 null 才是得到了正常结果, 返回res[0], 否则返回-1
        return helper(root, k, res) == null ? res[0] : -1;
    }

    //这里的return type包含了两维信息, null表示已经找到了该节点(已经存入了res里面), 非null的话, 表示没有找到节点
    private Integer helper(TreeNode root, int k, int[] res) {
        if (root == null) {
            //到了叶子节点返回 0
            return 0;
        }
        Integer leftSize = helper(root.left, k, res);
        //已经找到
        if (leftSize == null) {
            return null;
        }
        //当前节点是否是第 k 个节点
        if (leftSize + 1 == k) {
            res[0] = root.val;
            return null;
        }
        //右子树是否返回了结果, 以right child为根的话, k 变成了 k - leftSize - 1 了
        Integer rightSize = helper(root.right, k - leftSize - 1, res);
        return rightSize == null ? null : (leftSize + rightSize + 1);
    }
}
