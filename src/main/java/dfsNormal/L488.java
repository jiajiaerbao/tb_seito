package dfsNormal;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class L488 {
    /*
    * 坑 1: 坑BOSS: 从头到尾地扫描board: 每一次调用dfs, 也就是以新的newBoard进入下一层dfs的时候, 要从头到尾地扫描board
    * 坑 2: 分叉逻辑: 1.炸掉; 2.保留: 2a.用一个球炸; 2b.用两个球炸
    * 坑 3: 对于hashMap, 用 get 的时候, 如果不存在, 返回的是 null: Integer curCnt = hand.get(ch);
    * 坑 4: 对于 for loop, 以当前 i 作为出发点, 只是向后查找下一个球, 如果两个球的颜色相同, 更新 board, 然后在更新后的基础上, 继续调用下一层的dfs
    * 坑 5: 更新hashMap的时候, 先从hashMap里面remove掉这个key, 然后根据newCnt是否大于零再决定是否添加回去
    * 坑 6: explode的left和right的位置一定要清楚, 也就是
    *           拿掉了两个球(第i, i+1个球)的时候, 对于剩下的球, 包含i-1并向左和包含i+2并向右 的所有的球, 是否有超过三个球的颜色相同的, 并且连在一起的
    *           拿掉了一个球(第i个球)的时候, 对于剩下的球, 包含i-1并向左和包含i+1并向右, 的所有的球, 是否有超过三个球的颜色相同的, 并且连在一起的
    * 坑 7: 小细节: 1.当反复用到某个函数的值, 比如str.length()的时候, 可以用一个变量事先保存下来; 2.变量命名的时候, 尽量弄说明干什么的, 否则到后来很容易出错
    *
    *
    * 坑 8: 初始化hand到map里面的时候: 逻辑要清晰: 首先是判断map里面是否存在这个key, 如果不存在则存入零, 下面就是正常的判断了
    *       if (!map.containsKey(c)) {
                map.put(c, 0);
            }
            map.put(c, map.get(c) + 1);
    * 坑 9: 对于每一层dfs, 都是从最开始的位置 0, 一直判断到结尾 len - 1, 所以不用向下一层传递 idx+1
    * 坑10: 用返回值来处理最小值容易出错, 这里就用int sum, int[] res通过函数参数 来记录各个层dfs的最小值
    *       如果用返回值的话, 需要记录 a.能炸干净; b.不能炸干净
    *           1.base case 分两种:
    *               a.成功: board为空, 本层dfs不需要消耗任何球, 直接返回 0
    *               b.失败: map为空, 但是board不为空, 没办法继续炸了, 返回null
    *           2. recursion body里面:
    *               每一次从下一层dfs得到一个Integer的结果之后, 需要跟本层的已经用掉的球进行合并
    *                   if (res != null) {
                            min = Math.min(res + 1/2, min);
                        }
                 3. 返回值:
                        如果跳出for loop之后, min还是初始值Integer.MAX_VALUE的话, 则证明没有炸干净, 直接返回null即可
    * 坑11: 在for loop里面要用到continue: if(map.get(ch) == null) {continue;}
    * 坑12: base case是board是否为空
    *       如果board为空(board.isEmpty()), 不需要继续炸, 这时计算到目前为止的最小值
    * 坑 13: 每一层的 dfs 都要遍历 board 里面的每一个球
    * 坑 14: 每一层都不要忘记 set back:
    *           Integer curCnt = map.get(ch);
    *           map.remove(ch);
    *           ...(call 下一层 dfs)...
    *           map.put(ch, curCnt;)
    *  坑 15: 每一次炸掉了三个球之后, 都要动态地扫描一遍, 看是否有 能连着炸掉的球 存在
    *           [0, i] ...[j, len-1]
    *           每次以i作为基准, 向左和向右反复扫描
    *           用一个cnt来记录下所有的跟i相同颜色的球
    *           如果cnt大于等于3, 则可以炸掉, 然后以新的i和j作为左右起始点, 继续while遍历
    *           最后用string builder来生成新的board
    * */
    public int findMinStep(String board, String hand) {
        //corner case
        if (board == null || board.length() == 0 || hand == null || hand.length() == 0) {
            return -1;
        }
        int[] min = new int[1];
        min[0] = Integer.MAX_VALUE;
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : hand.toCharArray()) {
            if (!map.containsKey(c)) {
                map.put(c, 0);
            }
            map.put(c, map.get(c) + 1);
        }
        helper(board, map, 0, min);
        return min[0] == Integer.MAX_VALUE ? -1 : min[0];
    }

    private void helper(String board, HashMap<Character, Integer> map, int sum, int[] min) {
        int len = board.length();
        if (len == 0) {
            if (sum < min[0]) {
                min[0] = sum;
            }
            return;
        }
        for (int i = 0; i < len; i++) {
            char ch = board.charAt(i);
            Integer curCnt = map.get(ch);

            if (curCnt != null && i < len - 1 && ch == board.charAt(i + 1)) {
                map.remove(ch);
                int newCnt = curCnt - 1;
                if (newCnt > 0) {
                    map.put(ch, newCnt);
                }
                String newBoard = explode(board, i - 1, i + 2);
                helper(newBoard, map, sum + 1, min);
                map.put(ch, curCnt);
            } else if (curCnt != null && curCnt >= 2) {
                map.remove(ch);
                int newCnt = curCnt - 2;
                if (newCnt > 0) {
                    map.put(ch, newCnt);
                }
                String newBoard = explode(board, i - 1, i + 1);
                helper(newBoard, map, sum + 2, min);
                map.put(ch, curCnt);
            }
        }
    }

    private String explode(String board, int left, int right) {
        int len = board.length();
        while (left >= 0 && right <= len - 1) {
            int cnt = 0;
            int i = left, j = right;
            char ch = board.charAt(left);
            while (i >= 0 && ch == board.charAt(i)) {
                i--;
                cnt++;
            }
            while (j < len && ch == board.charAt(j)) {
                j++;
                cnt++;
            }
            if (cnt >= 3) {    //这里动态改变while loop的condition, 使left和right向左右扩展
                left = i;
                right = j;
            } else {
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int m = 0; m <= left; m++) {
            sb.append(board.charAt(m));
        }
        for (int n = right; n < len; n++) {
            sb.append(board.charAt(n));
        }
        return sb.toString();
    }

    /************************************************第二遍************************************************************/
    public int findMinStep2(String board, String hand) {
        //corner case
        if (board == null || board.length() == 0 || hand == null || hand.length() == 0) {
            return -1;
        }
        //create map for all color balls and its count
        Map<Character, Integer> map = new HashMap<>();
        char[] chars = hand.toCharArray();
        for (char curCh : chars) {
            if (!map.containsKey(curCh)) {
                map.put(curCh, 0);
            }
            map.put(curCh, map.get(curCh) + 1);
        }
        Integer res = dfs(board, map);
        return res == null ? -1 : res;
    }

    private Integer dfs(String board, Map<Character, Integer> map) {
        if (board.isEmpty()) {
            return 0;
        }
        if (map == null) {
            return null;
        }
        int len = board.length();
        Integer min = Integer.MAX_VALUE;
        for (int i = 0; i < len; i++) {
            char ch = board.charAt(i);
            Integer curCnt = map.get(ch);
            if (curCnt == null) {
                continue;
            }
            //need one ball
            if (i < len - 1 && ch == board.charAt(i + 1)) {
                map.remove(ch);
                int newCurCnt = curCnt - 1;
                if (newCurCnt > 0) {
                    map.put(ch, newCurCnt);
                }
                String newBoard = getNewBoard(board, i - 1, i + 2);
                Integer res = dfs(newBoard, map);
                map.put(ch, curCnt);
                if (res != null) {
                    min = Math.min(res + 1, min);
                }
            } else if (curCnt >= 2) {
                map.remove(ch);
                int newCurCnt = curCnt - 2;
                if (newCurCnt > 0) {
                    map.put(ch, newCurCnt);
                }
                String newBoard = getNewBoard(board, i - 1, i + 1);
                Integer res = dfs(newBoard, map);
                map.put(ch, curCnt);
                if (res != null) {
                    min = Math.min(res + 2, min);
                }
            }
        }
        return min == Integer.MAX_VALUE ? null : min;
    }

    private String getNewBoard(String board, int left, int right) {
        int len = board.length();
        while (left >= 0 && right < len) {
            char cur = board.charAt(left);
            int cnt = 0;
            int i = left;
            int j = right;
            while (i >= 0 && cur == board.charAt(i)) {
                i--;
                cnt++;
            }
            while (j < len && cur == board.charAt(j)) {
                j++;
                cnt++;
            }
            if (cnt >= 3) {
                left = i;
                right = j;
            } else {
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(board.substring(0, left + 1));
        sb.append(board.substring(right, len));
        return sb.toString();
    }
}














































