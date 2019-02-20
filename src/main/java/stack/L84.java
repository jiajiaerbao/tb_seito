package stack;

import java.util.Stack;

@SuppressWarnings("Duplicates")
public class L84 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: 这道题也是抄的答案, 关键点是如何求size和height
    *          height: 因为是左开右开区间,
    *                       如果 i < height.length的话, 直接取 heights[i],
    *                       如果 i == height.length的话, 相当于最后加了一个虚拟元素, 高度是 零
    *          size: 如果新扫描到的立方体的高度 小于 前一个, 则需要计算size,
    *                       如果stack里面还有元素, 则是 (stack.peek(), i) 的左开右开区间
    *                       如果stack为空的话, 则是 i
    * 坑 2: 看看everNote的微信聊天截屏, 那里说的很清楚:
    *           关键就是如何确定左右边界, 当无法压栈, 也就是无法保证单调栈的时候, 就是右边界确定的时候
    * */
    public int largestRectangleArea(int[] heights) {
        //corner case
        if (heights == null || heights.length == 0) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>();
        int max = 0;
        for (int i = 0; i <= heights.length; i++) {
            int hei = (i == heights.length ? 0 : heights[i]);
            while (!stack.isEmpty() && heights[stack.peek()] > hei) {
                int top = stack.pop();
                int size = heights[top] * (stack.isEmpty() ? i : (i - stack.peek() - 1));
                max = Math.max(size, max);
            }
            stack.push(i);
        }
        return max;
    }
}
