package tree;

import java.util.ArrayList;
import java.util.List;

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
        getRange(root.left, k1, k2, res);
        if (k1 < root.val && root.val < k2) {
            res.add(root.val);
        }
        getRange(root.right, k1, k2, res);
    }
}
