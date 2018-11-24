package bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Ryu 11/24/12
 *
 */
public class L103 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /*
     * 这道题的思维转换点是:
     *      在哪里进行转换方向, 是在queue上面进行转换方向还是在list上面进行转换方向
     *      1. 充分利用list的 add(val) 和 add(0, val)可以从头尾两个方向进行添加
     *      2. 每次只是考虑当前 cur node 的val, 不需要考虑left 和 right 的 val
     * */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        //corner case
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode cur = null;
        boolean leftToRight = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            while (size-- > 0) {
                cur = queue.poll();
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
                if (leftToRight) {
                    list.add(cur.val);
                } else {
                    list.add(0, cur.val);
                }
            }
            leftToRight = !leftToRight;
            res.add(list);
        }
        return res;
    }
}
