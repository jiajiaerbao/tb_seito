package dfsNormal;

import java.util.HashMap;

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
}
