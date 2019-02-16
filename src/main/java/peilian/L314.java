package peilian;

import tree.TreeNode;

import java.util.*;

/*
* 坑 1: 用两个Queue:
*               Queue<TreeNode>: 当前节点
*               Queue<Integer>:  当前节点的col index
*       一定要保证对这两个Queue要同时操作, 要么一起offer, 要么一起poll
*
*       别忘了定义一个map, 用来存储<列Index, 该列所有节点值>
* 坑 2: 用Map, 以col index作为key, 该col index的所有val组成的list作为 value, 建立map
*       最后用map来输出结果, 这时用 leftMin, rightMax 来 确定map里面key的range
*
* 坑 3: 这里不需要层级搜索,
* */
@SuppressWarnings("Duplicates")
public class L314 {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> colIdxQueue = new LinkedList<>();

        Map<Integer, List<Integer>> map = new HashMap<>();

        int minCol = Integer.MAX_VALUE;
        int maxCol = Integer.MIN_VALUE;
        nodeQueue.offer(root);
        colIdxQueue.offer(0);
        while (!nodeQueue.isEmpty()) {
            TreeNode curNode = nodeQueue.poll();
            int curIdx = colIdxQueue.poll();

            List<Integer> list = map.getOrDefault(curIdx, new ArrayList<>());
            list.add(curNode.val);
            map.put(curIdx, list);

            if (curNode.left != null) {
                nodeQueue.offer(curNode.left);
                colIdxQueue.offer(curIdx - 1);
            }
            if (curNode.right != null) {
                nodeQueue.offer(curNode.right);
                colIdxQueue.offer(curIdx + 1);
            }

            minCol = Math.min(minCol, curIdx);
            maxCol = Math.max(maxCol, curIdx);
        }
        for (int i = minCol; i <= maxCol; i++) {
            res.add(map.get(i));
        }
        return res;
    }
}
