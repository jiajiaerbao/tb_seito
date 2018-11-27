package bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class L515 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /*
     *  坑 1: BOSS 坑, 按照层遍历, 取最左面的第一个 child, 或者是取最大值的时候:
     *       需要用 flag 来 check 是否找到了一个这样的值, 这时候一定要用 flag!!!! 已经出现了两次这样的错误了
     * */
    public List<Integer> largestValues(TreeNode root) {
        //corner case
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        res.add(root.val);
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        while (!que.isEmpty()) {
            int size = que.size();
            int max = Integer.MIN_VALUE;
            boolean flag = false;
            while (size-- > 0) {
                TreeNode cur = que.poll();
                if (cur.left != null) {
                    que.offer(cur.left);
                    max = Math.max(max, cur.left.val);
                    flag = true;
                }
                if (cur.right != null) {
                    que.offer(cur.right);
                    max = Math.max(max, cur.right.val);
                    flag = true;
                }
            }
            if (flag) {
                res.add(max);
            }
        }
        return res;
    }
}
