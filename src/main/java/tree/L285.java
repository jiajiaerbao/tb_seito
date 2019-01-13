package tree;


import java.util.Stack;

/*
* 第一种: 专门针对BST的解法
* 坑 1: 如果 cur.val == p.val的话, 一定是向右子树查找
* 坑 2: 何时更新 res? 当cur.val大于p.val的时候, 更新res, 同时向cur的左子树移动
* 第二种: 对普通的binary tree 也适用
* 坑 3: 坑BOSS:
*           分两种情况考虑, 具体参考everNote:
*               第一种简单: 找到 p 的右子树的最左面的节点即可;
*               第二种较难: 用 stack 来保存 p 的所有 parent 节点, stack 保存 p 的 parent 的逻辑是:
*                              先把 parent 压栈 (从 root 开始), 接着继续 进入 下一层(左子树),
*                              等走到最下一层左子树的时候, 如果找到的话, 直接返回tree, 如果没有找到的话, 返回到上一层(当前最左面节点的紧挨着的parent),
*                              然后向右走(遍历紧挨着的parent的右子树), 如果找到p返回, 如果没找到, 把紧挨着的parent和其下面的所有节点都弹栈,
*                              接着又走到上一层的parent
*           具体见everNode的画图
* 坑 4: stack 只有一种情况返回true, 那就是找到了 p 了, 但是 p 没有被压栈, stack里面都是 p 的 parent, 最先压入栈的是 root
* 坑 5: 对处理之后的stack进行操作, 每次对stack进行peek或者pop操作之前, 一定要判断stack是否为空, 否则会报exception
* 坑 6: 对stack里面的每个元素进行判断, 找到第一个upper_parent.left = current_parent的是, 返回upper_parent即可
* */
@SuppressWarnings("Duplicates")
public class L285 {
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null || p == null) {
            return null;
        }
        TreeNode res = null;
        TreeNode cur = root;
        while (cur != null) {
            if (cur.val > p.val) {
                res = cur;
                cur = cur.left;
            } else {                //cur.val <= p.val
                cur = cur.right;
            }
        }
        return res;
    }
    /************************************************************************************************/
    public TreeNode inorderSuccessor_2(TreeNode root, TreeNode p) {
        if (root == null || p == null) {
            return null;
        }
        //第一种情况: p 如果有右子树的话, 则找到 p 的右子树的最小节点, 即大于 p 的所有元素中, 最小的那个元素
        if (p.right != null) {
            return leftMostNode(p.right);
        }

        Stack<TreeNode> stack = new Stack<>();
        //stack里面保存的是 p 的所有 parent 节点, 但是不包括 p 本身
        if (!findNode(root, p, stack) || stack.isEmpty()) {
            return null;
        }

        TreeNode prev = stack.pop();
        //如果 p 是 root 的直接左子树, 并且 p 没有右子树的话, 这时stack里面只有一个元素, 就是root
        if (prev.left == p) {
            return prev;
        }
        //stack里面, p 的 parent node 的存放顺序是:
        // 栈顶 是 p的直接parent
        // 栈底 是 root
        TreeNode cur = null;
        //stack类似于queue, 这里while loop 的结束条件是 stack 是否为空
        while (!stack.isEmpty()) {
            cur = stack.pop();
            if (cur.left == prev) {
                return cur;
            }
            prev = cur;
        }
        return null;
    }

    private boolean findNode(TreeNode root, TreeNode p, Stack<TreeNode> stack) {
        if (root == null) {
            return false;
        }
        if (root == p) {
            return true;
        }

        stack.push(root);
        if (findNode(root.left, p, stack)) {
            return true;
        }
        if (findNode(root.right, p, stack)) {
            return true;
        }
        stack.pop();

        return false;
    }

    private TreeNode leftMostNode(TreeNode root) {
        TreeNode cur = root;
        TreeNode res = cur;
        while (cur != null) {
            res = cur;
            cur = cur.left;
        }
        return res;
    }
    /************************************************************************************************/
    //时间复杂度是O(n), 暴力解法
    //注意 1: 这是中序遍历的变形, 把 左-中-右 变成了 右-中-左
    //注意 2: 在每一层DFS当中, 当需要从 中(root) 向左遍历 到左子树的时候, 需要update successor[0] 为当前层的 root, 然后再进入到下一层 (root 的左子树)
    //注意 3: 当找到了 p 的时候, 更新 res 即可, 如果没有找到, 比如 p 是 BST 的最后一个, 则返回 null
    public TreeNode inorderSuccessor_3(TreeNode root, TreeNode p) {
        TreeNode[] res = new TreeNode[1];
        TreeNode[] successor = new TreeNode[1];
        helper(root, p, res, successor);
        return res[0];
    }
    private void helper(TreeNode root, TreeNode p, TreeNode[] res, TreeNode[] successor) {
        if (root == null) {
            return;
        }
        helper(root.right, p, res, successor);
        if (root == p) {
            res[0] = successor[0];
        }
        successor[0] = root;
        helper(root.left, p, res, successor);
    }
}
