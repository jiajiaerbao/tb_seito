package iterator;

import java.util.Stack;


@SuppressWarnings("Duplicates")
public class L173 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: BST的话, 只有inOrder traverse才是有序的, 用stack来解
    *       在constructor里面, 先一路走到底, 走到最小的那个node
    *       每次pop出来一个, 然后跳到pop出来那个node的右子树, 继续向左走
    * */
    class secondTime{
        class BSTIterator {
            private Stack<TreeNode> stack;

            public BSTIterator(TreeNode root) {
                stack = new Stack<>();
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
                TreeNode temp = stack.pop();
                if (temp.right != null) {
                    TreeNode cur = temp.right;
                    while (cur != null) {
                        stack.push(cur);
                        cur = cur.left;
                    }
                }
                return temp.val;
            }

            /**
             * @return whether we have a next smallest number
             */
            public boolean hasNext() {
                return !stack.isEmpty();
            }
        }
    }

    /******************************第一遍********************************************/
    /*
     * 坑 1: stack不要忘了初始化: this.stack = new Stack<>();
     * 坑 2: 坑 BOSS
     *       写 next() 的逻辑要清晰:
     *       取出栈顶元素
     *       cur 指向 取出的栈顶元素的 右子树
     *       沿着左方向, 全部压到stack里面
     * */
    class firstTime{
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
}
