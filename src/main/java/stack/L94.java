package stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
* 坑 1: else的部分, 要注意, 用的是 cur = top.right
*
* */
@SuppressWarnings("Duplicates")
public class L94 {
    public List<Integer> inorderTraversal(TreeNode root) {
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
