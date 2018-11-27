package bfs;

import java.util.LinkedList;
import java.util.Queue;

public class L513 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        /*
         *  坑 1: result 的初始值是 root.val
         *  坑 2: 用 flag 来check 每一层到底有没有访问到一个节点
         * */
        public int findBottomLeftValue(TreeNode root) {
            //corner case
            if (root == null) {
                return -1;//throw IllegalArgumentException("Invalid input!");
            }

            //result 的初始值是 root.val
            int res = root.val;

            Queue<TreeNode> que = new LinkedList<>();
            que.offer(root);

            while (!que.isEmpty()) {
                int size = que.size();
                boolean getFirstLeftChild = false;
                while (size-- > 0) {
                    TreeNode cur = que.poll();
                    if (cur.left != null) {
                        que.offer(cur.left);
                    }
                    if (cur.right != null) {
                        que.offer(cur.right);
                    }
                    if (!getFirstLeftChild && (cur.left != null || cur.right != null)) {
                        res = getFirstChildVal(cur);
                        getFirstLeftChild = true;
                    }
                }
            }
            return res;
        }

        private int getFirstChildVal(TreeNode node) {
            if (node.left != null) {
                return node.left.val;
            }
            return node.right.val;
        }
    }

}
