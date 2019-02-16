package bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Ryu 11/23/12
 *
 */

@SuppressWarnings("Duplicates")
public class L102 {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

/******************************第二遍********************************************/
    public List<List<Integer>> levelOrder_2(TreeNode root) {
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
            res.add(list);
        }
        return res;
    }



/******************************第一遍********************************************/
    public List<List<Integer>> levelOrder_1(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        //corner case
        if(root == null){
            return res;
        }
        //push the first level node (root) into the queue
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        //traverse all node of the tree
        TreeNode cur = null;
        while(!queue.isEmpty()){
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            while(size-- > 0){
                cur = queue.poll();
                list.add(cur.val);
                if(cur.left != null){
                    queue.offer(cur.left);
                }
                if(cur.right != null){
                    queue.offer(cur.right);
                }
            }
            res.add(list);
        }
        return res;
    }
}
