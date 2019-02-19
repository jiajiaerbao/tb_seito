package stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


@SuppressWarnings("Duplicates")
public class L94 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 坑 BOSS, 按照everNote上面记的笔记,
    *       把preOrder和inOrder放在一起考虑, 用一个模板通吃
    * */
    public List<Integer> inorderTraversal_2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                TreeNode top = stack.pop();
                res.add(top.val);
                cur = top.right;
            }
        }
        return res;
    }

    /******************************第一遍********************************************/
    /*
     * 坑 1: else的部分, 要注意, 用的是 cur = top.right
     *
     * */
    public List<Integer> inorderTraversal_1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode cur = root;
        Stack<TreeNode> stack = new Stack<>();
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                TreeNode top = stack.pop();
                res.add(top.val);
                //这里注意
                cur = top.right;
            }
        }
        return res;
    }
}
