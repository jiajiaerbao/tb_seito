package bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
* Created by Ryu 11/24/18
* */
@SuppressWarnings("Duplicates")
public class L107 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    /******************************第二遍********************************************/
    public List<List<Integer>> levelOrderBottom_2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        while (!que.isEmpty()) {
            int size = que.size();
            List<Integer> list = new ArrayList<>();
            while (size-- > 0) {
                TreeNode cur = que.poll();
                list.add(cur.val);
                if (cur.left != null) {
                    que.offer(cur.left);
                }
                if (cur.right != null) {
                    que.offer(cur.right);
                }
            }
            res.add(0, list);
        }
        return res;
    }
    /******************************第一遍********************************************/
    /*
    * 这道题的关键:
    *   把每一层的结果放到 list 的顺序
    *   最后得到的结果放到最上面的一层, 也是应用了list.add(0, val) --> list.add(0, subList)
    * */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        //corner case
        if (root == null) {
            return res;
        }
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        TreeNode cur = null;
        while (!que.isEmpty()) {
            int size = que.size();
            List<Integer> list = new ArrayList<>();
            while (size-- > 0) {
                cur = que.poll();
                list.add(cur.val);
                if (cur.left != null) {
                    que.offer(cur.left);
                }
                if (cur.right != null) {
                    que.offer(cur.right);
                }
            }
            res.add(0, list);
        }
        return res;
    }
}
