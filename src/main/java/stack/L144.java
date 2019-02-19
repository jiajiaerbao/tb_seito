package stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@SuppressWarnings("Duplicates")
public class L144 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 这道题做了三个小时, 因为不理解, 不明白为什么while loop里面这样写
    * 坑 2: 这道题的while loop一定要写得非常熟练,
    *       而且要用一个node有三次机会出现在栈顶的方式 画图 解释清楚
    * */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                res.add(cur.val);
                stack.push(cur);
                cur = cur.left;
            } else {
                TreeNode top = stack.pop();
                cur = top.right;
            }
        }
        return res;
    }
    /******************************第一遍********************************************/
    /*
     * 坑 1: 画图说明的时候, 只画出来针对 pre-order/ post-order 的图即可
     * 坑 2: 第一次 touch 到 node A 的时候, 是 A 作为root, 对应于 if, 沿着左子树走下去
     *       第二次 touch 到 node A 的时候, 是 A 弹栈的时候, 对应于 else, 沿着右子树走下去
     * */
    public List<Integer> preorderTraversal_1_2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        //corner case 可以省略
        //first time touch node put into result list
        TreeNode cur = root;
        Stack<TreeNode> stack = new Stack<>();

        while (cur != null || !stack.isEmpty()) {
            //如果存在右子树, 右子树压栈, 这里对应着从 root 沿着 左子树 深入下去
            if (cur != null) {
                res.add(cur.val);
                stack.push(cur);
                cur = cur.left;
                //对应着 弹出了 子树的root, 沿着 右子树 深入下去
            } else {
                TreeNode top = stack.pop();
                cur = top.right;
            }
        }
        return res;
    }

    public List<Integer> preorderTraversal_1_3(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                res.add(cur.val);
                stack.push(cur);
                cur = cur.left;
            } else {
                TreeNode top = stack.pop();
                cur = top.right;
            }
        }
        return res;
    }
}















































