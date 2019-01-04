package tree;

/* 坑 1: 对于任意一个 i 在 [1...n] 之间, 都可能成为 root
 * 坑 2: 坑 BOSS: Recursively 考虑 左child 和 右child 各有 多少种BST 的可能性
 * 坑 3: 会有重复计算, 比如 1,2作为左子树, 3作为1,2的根节点的情况,
 *       在 4作为3的根节点, 和 5作为4的根节点而且4作为3的根节点的时候, 会计算两次
 * 坑 4: 因为这里求的是 总共有多少种, 而不是所有的具体形态, 所以能用 pruning, 能DP
 *       是标准的 all possible solution的问题, 用 DFS来做, 分叉的
 *       如果求的是, 所有的形态的集合, 就无法pruning了
 * 坑 5: 这里是左闭右开, 因为需要表示空的逻辑
 * 坑 6: 对于 i 个 node, 任意的 j <= i, 如果是以 j 为 root 的话, 左面有 j-1 个节点, 右面有 i-j 个节点
 */
@SuppressWarnings("Duplicates")
public class L96 {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            int cnt = 0;
            for (int j = 1; j <= i; j++) {
                cnt += dp[j - 1] * dp[i - j];
            }
            dp[i] = cnt;
        }
        return dp[n];
    }
}
