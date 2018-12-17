package dfsPruning;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class L403 {
    /*
    * 坑 1: 题意不理解:
    *           1. 用多少步跳过来的: k
    *           2. 所在石头的数值的大小: stone[idx]
    * [0,1,3,5,6,8,12,17]
    * [1,2,3,4,5,6, 7, 8]
    * [0] --> [1,1] --> [2,3] --> [2,5] --> [3,8] --> [4,12] --> [5,17]
    * 结果[k, stone[idx]]: [1,1] --> [2,3] --> [2,5] --> [3,8] --> [4,12]
    * 分析得出: 也就是下一块石头到上一块石头的距离是 [k-1, k+1] 的范围内
    *           1] --> [2 == 3]
    *           3] --> [2 == 5]
    *           5] --> [3 == 8]
    *           8] --> [4 == 12]
    *          12] --> [5 == 17]
    * 在dfs里面表示的时候: [k, stone[idx]]
    * 坑 2: 通过 dist = stones[i] - stones[idx] 来确定当前 idx 上的 stone 到后面所有的 stone 的距离
    * 坑 3: 如果不加 pruning 的话, 会time limit exceeded, 加 pruning 的数据结构是 Map<Integer, Boolean>[]
    *       对于int[] stones的每一个stone, 都对应一个单独的 map, 而且每个 stone 的 map 的 key 之间没有太大的关联性, 是离散的
    * 坑 4: Map array的初始化要注意写法: 1.先建立一个Map array; 2.再一个一个地建立一个 新map
    * 坑 5: 调用dfs的时候, k 和 idx 的初始值都是 1, 因为默认已经在第一块石头上面了
    * 坑 6: 注意corner case:
    *           1. 如果stones只有一个元素的话, false
    *           2. 如果第零个stone和第一个stone相差大于1的话, false
    * */
    public boolean canCross(int[] stones) {

        //corner case
        if (stones == null || stones.length <= 1) {
            return false;
        }
        if (stones[1] - stones[0] > 1) {
            return false;
        }
        Map<Integer, Boolean>[] maps = new HashMap[stones.length];
        for (int i = 0; i < stones.length; i++) {
            maps[i] = new HashMap<>();
        }
        return dfs(stones, 1, 1, maps);
    }

    private boolean dfs(int[] stones, int k, int idx, Map<Integer, Boolean>[] maps) {
        if (maps[idx].get(k) != null) {
            return maps[idx].get(k);
        }
        int len = stones.length;
        if (idx == len - 1) {
            maps[idx].put(k, true);
            return true;
        }
        for (int i = idx + 1; i < len; i++) {
            int dist = stones[i] - stones[idx];
            if (dist >= (k - 1) && dist <= (k + 1)) {
                if (dfs(stones, dist, i, maps)) {
                    maps[idx].put(k, true);
                    return true;
                }
            }
        }
        maps[idx].put(k, false);
        return false;
    }

}
