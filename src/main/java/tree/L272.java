package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/*
* 坑 1: 这里的关键点是放入Stack里面的不仅仅是单独一个TreeNode, 而是该TreeNode所代表的左/右子树
* 坑 2: 以target为中心, node.val(left stack) < target <= node.val(right stack)
* 坑 3: 坑BOSS: 这里的逻辑是从中心向两边, 跟everNote上面的求 BST twoSUM 刚好相反
*       当从stackLeft里面得到一个nodeLeft, 并放入到了结果集res之后, 更新stackLeft, 确保stackLeft的栈顶元素是第一个小于nodeLeft的元素, 即nodeLeft先取左子树, 然后再recursively取右子树
*       当从stackRight里面得到一个nodeRight, 并放入到了结果集res之后, 更新stackRight, 确保stackRight的栈顶元素是第一个大于nodeRight的元素, 即nodeRight先取右子树, 然后再recursively取左子树
*       把BST投射到sorted array上了之后, 节点的相互关系如下:
*       ******(next nodeLeft)******(current nodeLeft)*********(target)***********(current nodeRight)************(next nodeRight)
* */
@SuppressWarnings("Duplicates")
public class L272 {
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        //corner case
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stackLeft = new Stack<>();
        Stack<TreeNode> stackRight = new Stack<>();

        TreeNode cur = root;
        while (cur != null) {
            if (cur.val < target) {
                stackLeft.push(cur);
                cur = cur.right;
            } else {
                stackRight.push(cur);
                cur = cur.left;
            }
        }
        while (k-- > 0) {
            if (!stackRight.isEmpty() && !stackLeft.isEmpty()) {
                if (Math.abs(stackRight.peek().val - target) < Math.abs(stackLeft.peek().val - target)) {
                    res.add(getNextRight(stackRight).val);
                } else {
                    res.add(getNextLeft(stackLeft).val);
                }
            } else if (!stackRight.isEmpty()) {
                res.add(getNextRight(stackRight).val);
            } else if (!stackLeft.isEmpty()) {
                res.add(getNextLeft(stackLeft).val);
            }
        }
        return res;
    }

    private TreeNode getNextLeft(Stack<TreeNode> stackLeft) {
        TreeNode top = stackLeft.pop();
        TreeNode cur = top.left;
        while (cur != null) {
            stackLeft.push(cur);
            cur = cur.right;
        }
        return top;
    }

    private TreeNode getNextRight(Stack<TreeNode> stackRight) {
        TreeNode top = stackRight.pop();
        TreeNode cur = top.right;
        while (cur != null) {
            stackRight.push(cur);
            cur = cur.left;
        }
        return top;
    }
}
