package peilian;

import tree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/*
* validate a complete binary tree
* */
@SuppressWarnings("Duplicates")
public class LValComlTr {
    /******************************第一遍********************************************/
    /*
    * 坑 1: complete binary tree的性质是 从根节点开始, 所有的节点必须非空, 直到遇到第一个空节点为止
    *       并且 遇到了 空节点之后, 后面的所有节点必须是空节点
    * 坑 2: 设置一个 flag 用来表示是否遇到了空节点, 如果遇到了空节点, 但是又一次遇到了非空节点的话, 则不满足条件, 返回false
    *
    * */
    public boolean validateCompleteBinaryTree(TreeNode root) {
        if (root == null) {
            return false;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean flag = false;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode cur = queue.poll();
                if (cur.left != null) {
                    if (flag) {
                        return false;
                    } else {
                        queue.offer(cur.left);
                    }
                }
                if (cur.right != null) {
                    if (flag) {
                        return false;
                    } else {
                        queue.offer(cur.right);
                    }
                }
            }
        }
        return true;
    }
}
