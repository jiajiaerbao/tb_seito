package bfs;

import java.util.LinkedList;
import java.util.Queue;

public class L116 {
    public class TreeLinkNode {
        int val;
        TreeLinkNode left, right, next;
        TreeLinkNode(int x) { val = x; }
    }

    /*
     * 思维转换: 用 next 把下一个兄弟节点连接起来, 转化为寻找 prev 节点,
     * 然后用 prev.next 来指向当前 cur node
     * */
    public void connect(TreeLinkNode root) {
        //corner case
        if (root == null) {
            return;
        }
        Queue<TreeLinkNode> que = new LinkedList<>();
        que.offer(root);
        TreeLinkNode prev = null;
        TreeLinkNode cur = null;
        while (!que.isEmpty()) {
            int size = que.size();
            while (size-- > 0) {
                cur = que.poll();
                if (cur.left != null) {
                    que.offer(cur.left);
                }
                if (cur.right != null) {
                    que.offer(cur.right);
                }
                if (prev != null) {
                    prev.next = cur;
                }
                prev = cur;
            }
            prev = null;
        }
    }
}
