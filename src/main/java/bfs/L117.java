package bfs;

import java.util.LinkedList;
import java.util.Queue;

public class L117 {
    public class TreeLinkNode {
        int val;
        TreeLinkNode left, right, next;
        TreeLinkNode(int x) { val = x; }
    }

    /**
     * 跟 L116 是同一个题目: 目标是当前节点 cur node, 如何指向 cur node, 则需要知道 prev node
     * 在每一层的遍历结束的时候, size-- > 0 的 while loop 结束的时候, reset prev == null
     */
    public void connect(TreeLinkNode root) {
        //corner case
        if (root == null) {
            return;
        }
        Queue<TreeLinkNode> que = new LinkedList<>();
        que.offer(root);
        TreeLinkNode cur = null;
        TreeLinkNode prev = null;
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
