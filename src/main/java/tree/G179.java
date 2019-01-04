package tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
*
* 坑 1: 0-2 Tree: 这里要求有 0 或者 2 个 child leaves 的节点, 只有一个的不在范围内
* 坑 2: 画图, 把 base case 画出来: n = 0, 1, 2
* 坑 3: 这里的base case只考虑 n=1 的情况
* 坑 4: follow up:
*           如果问 total number 的话, 该怎么分析? 为什么用 DP 来解 total number?
*           为什么用 DFS 解第一问? 用 DP 解第二问? 因为要判断是否有overlap, 当搜索的过程中有overlap的话, 用DP
* */
@SuppressWarnings("Duplicates")
public class G179 {
    public List<TreeNode> generateTrees(int n) {
        if (n == 1) {
            return Arrays.asList(new TreeNode(1));
        }
        List<TreeNode> res = new ArrayList<>();
        for (int len = 1; len < n; len++) {
            List<TreeNode> left = generateTrees(len);
            List<TreeNode> right = generateTrees(n - len);
            for (int i = 0; i < left.size(); i++) {
                for (int j = 0; j < right.size(); j++) {
                    TreeNode newRoot = new TreeNode(n);
                    newRoot.left = left.get(i);
                    newRoot.right = right.get(j);
                    res.add(newRoot);
                }
            }
        }
        return res;
    }
}
