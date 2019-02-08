package tree;

import java.util.ArrayList;
import java.util.List;

/*
* 坑 1: 你写的是 brute force 的方法, 需要剪枝
*       k1 < root.val 的时候, 才走向左子树
*       root.val > k2 的时候, 才走向右子树
* */
@SuppressWarnings("Duplicates")
public class Lint11 {
    public List<Integer> searchRange(TreeNode root, int k1, int k2) {
        // write your code here
        List<Integer> res = new ArrayList<>();
        getRange(root, k1, k2, res);
        return res;
    }

    private void getRange(TreeNode root, int k1, int k2, List<Integer> res) {
        if (root == null) {
            return;
        }
        if (k1 <= root.val) {
            getRange(root.left, k1, k2, res);
        }
        if (k1 <= root.val && root.val <= k2) {
            res.add(root.val);
        }
        if (root.val <= k2) {
            getRange(root.right, k1, k2, res);
        }
    }
}
