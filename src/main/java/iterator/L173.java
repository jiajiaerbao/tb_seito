package iterator;

import java.util.Stack;


/*
* 坑 1: stack不要忘了初始化: this.stack = new Stack<>();
* 坑 2: 坑 BOSS
*       写 next() 的逻辑要清晰:
*       取出栈顶元素
*       cur 指向 取出的栈顶元素的 右子树
*       沿着左方向, 全部压到stack里面
* */
@SuppressWarnings("Duplicates")
public class L173 {
    private Stack<TreeNode> stack;

    public void BSTIterator(TreeNode root) {
        this.stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
    }

    /**
     * @return the next smallest number
     */
    public int next() {
        TreeNode top = stack.pop();
        TreeNode cur = top.right;
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
        return top.val;
    }

    /**
     * @return whether we have a next smallest number
     */
    public boolean hasNext() {
        return !stack.isEmpty();
    }
}
