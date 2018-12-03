package dfsNormal;

import java.util.ArrayList;
import java.util.List;

public class L257 {
    /*
    * 坑 1: 判断叶子节点的条件是 左子树和右子树 都为空
    * 坑 2: 在生成结果之前, 先把 当前节点 node.val 的值 加入到结果集当中
    * 坑 3: 在从左子树的dfs出来之后, 进入右子树的dfs之前, 记得setback
    * */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        helper(res, root, new StringBuilder());
        return res;
    }

    private void helper(List<String> res, TreeNode node, StringBuilder curPath) {
        //System.out.println("TEST:" + curPath.toString());
        if (curPath.length() == 0) {
            curPath.append(node.val);
        } else {
            curPath.append("->");
            curPath.append(node.val);
        }
        if (node.left == null && node.right == null) {
            res.add(curPath.toString());
            return;
        }
        int size = curPath.length();
        if (node.left != null) {
            helper(res, node.left, curPath);
        }
        curPath.setLength(size);
        if (node.right != null) {
            helper(res, node.right, curPath);
        }
    }
}
