package dfsPruning;

import java.util.*;

@SuppressWarnings("Duplicates")
public class L464 {
    /*
    *   back tracking一定要注意, 如果没有在dfs的紧接着的下一行写back track的话, 那么就会出错误, 而且很容易出现逻辑错误
    *   先不要考虑pruning, 考虑如何用dfs来解出来
    *   卡在怎么表示已经访问过的元素: 用 HashSet 表示已经访问过的元素, 这个 HashSet 单独存储
    *       这里错在了back track的地方:
    *       下面的写法是正确的, 如果在if里面没有写usedNum.remove(i);的话, 则就是错误的了
                usedNum.add(i);
                if (!dfs(maxVal, target, curSum + i, usedNum)) {
                    usedNum.remove(i);
                    return true;
                }
                usedNum.remove(i);
        但是这种写法超时了, 因为没有做pruning: 如何保存已经判断过的序列的状态? 因为小于32个数, 所以可以用Integer来表示
    *   坑 1: 要明确sum的含义是什么? 上一层dfs的玩家已经握在手里的sum
    *           所以在进入下一层dfs之后, 没有base case
    *   坑 2: 根据题意, 在corner case里面严格控制住 input 的范围
    *   坑 3: 在做pruning的时候, 用位运算来确定数组的最大长度: men[(1<<maxChoosableInteger)-1]
    *   坑 4: 计算mask的时候, 1 移动 i 位的逻辑是  1 << (i - 1), 因为是从0开始计算的
    *   坑 5: 要理解status的含义:
    *           每一个bit代表一个数字, 1的含义是这个数字黑没有被选过, 还在pool里面
    *           记录 status 的初始状态是 (1 << maxChoosableInteger) - 1, 也就是所有的bit位上面都是 1
    *           也就是 status 的最大值
    *           而记录所有status的Boolean值的array是 status的最大值+1, 也就是 (1 << maxChoosableInteger)
    * */
    public boolean canIWin2(int maxChoosableInteger, int desiredTotal) {
        //corner case
        if (maxChoosableInteger <= 0 || maxChoosableInteger > 20 || desiredTotal < 0 || desiredTotal > 300) {
            throw new IllegalArgumentException("Invalid Input");
        }
        int sum = (1 + maxChoosableInteger) * maxChoosableInteger / 2;
        if (sum < desiredTotal) {
            return false;
        }
        return dfs2(maxChoosableInteger, desiredTotal, (1 << maxChoosableInteger) - 1, 0, new Boolean[(1 << maxChoosableInteger)]);
    }

    private boolean dfs2(int maxVal, int target, int status, int curSum, Boolean[] map) {
        if (map[status] != null) {
            return map[status];
        }
        for (int i = 1; i <= maxVal; i++) {
            int mask = 1 << (i - 1);
            if ((status & mask) != 0) {
                int newStatus = status - mask;
                if ((curSum + i) >= target) {
                    map[status] = true;
                    return true;
                }
                if (!dfs2(maxVal, target, newStatus, curSum + i, map)) {
                    map[status] = true;
                    return true;
                }
            }
        }
        map[status] = false;
        return false;
    }







































    /********************************************抄的答案**************************************************/

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if (maxChoosableInteger <= 0 || maxChoosableInteger > 20 || desiredTotal < 0 || desiredTotal > 300) {
            throw new IllegalArgumentException("Invalid Input");
        }
        int sum = (1 + maxChoosableInteger) * maxChoosableInteger / 2;
        if (sum < desiredTotal) {
            return false;
        }
        int len = (1 << maxChoosableInteger) - 1;
        return dfs(len, 0, desiredTotal, maxChoosableInteger, new Boolean[len + 1]);
    }

    private boolean dfs(int map, int curTotal, int desiredTotal, int maxChoosableInteger, Boolean[] men) {
        if (men[map] != null) {
            return men[map];
        }
        for (int i = 1; i <= maxChoosableInteger; i++) {
            int mask = 1 << (i - 1);
            if ((mask & map) != 0) {
                if (curTotal + i >= desiredTotal) {
                    return true;
                }
                int newMap = map - mask;
                if (!dfs(newMap, curTotal + i, desiredTotal, maxChoosableInteger, men)) {
                    men[map] = true;
                    return true;
                }
            }
        }
        men[map] = false;
        return false;
    }
}