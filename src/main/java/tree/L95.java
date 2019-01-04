package tree;

import java.util.ArrayList;
import java.util.List;

/*
* 坑 1: 一共有 n 个数, 从 1 到 n 任意一个数都能作为 root,
*       当 i 在 [1, n] 之间作为 root 的时候, 又划分成了[1, i-1]和[i+1,n]两个相同的子问题
*       以 i 作为 root 的 所有左子树的集合 和 所有右子树的集合 的 任意组合 都是 题目的一个解
* 坑 2: 每一个组合都需要一个新的 root 来保存 对应的 树的形态
* */
@SuppressWarnings("Duplicates")
public class L95 {
    public List<TreeNode> generateTrees(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }
        return getTreeNodes(1, n);
    }

    private List<TreeNode> getTreeNodes(int start, int end) {
        List<TreeNode> res = new ArrayList<>();
        if (start > end) {
            res.add(null);
            return res;
        }

        for (int curRoot = start; curRoot <= end; curRoot++) {
            List<TreeNode> leftSet = getTreeNodes(start, curRoot - 1);
            List<TreeNode> rightSet = getTreeNodes(curRoot + 1, end);
            for (int leftChild = 0; leftChild < leftSet.size(); leftChild++) {
                for (int rightChild = 0; rightChild < rightSet.size(); rightChild++) {
                    TreeNode newRoot = new TreeNode(curRoot);
                    newRoot.left = leftSet.get(leftChild);
                    newRoot.right = rightSet.get(rightChild);
                    res.add(newRoot);
                }
            }
        }
        return res;
    }
}
