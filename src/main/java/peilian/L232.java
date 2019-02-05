package peilian;

import java.util.Stack;

/*
* 坑 1: 这道题的关键点是 mergeTwoStack , 具体的逻辑不懂得话, 画出图形来就清楚了
* 坑 2: input 进入到 stackIn 的时候, 是倒序的
*       从 stackIn 到 stackOut 的时候, 又倒了一遍, 变成了 跟input相同的顺序
* 坑 3: leetcode 没有要求, 如果说考虑 stackIn和stackOut为空的话, 需要返回null
* */
@SuppressWarnings("Duplicates")
public class L232 {
    private Stack<Integer> stackIn;
    private Stack<Integer> stackOut;

    /**
     * Initialize your data structure here.
     */
    public L232() {
        this.stackIn = new Stack<>();
        this.stackOut = new Stack<>();
    }

    /**
     * Push element x to the back of queue.
     */
    public void push(int x) {
        stackIn.push(x);
    }

    /**
     * Removes the element from in front of queue and returns that element.
     */
    public int pop() {
        mergeTwoStack();
        return stackOut.pop();
    }

    /**
     * Get the front element.
     */
    public int peek() {
        mergeTwoStack();
        return stackOut.peek();
    }

    private void mergeTwoStack() {
        if (stackOut.isEmpty()) {
            while (!stackIn.isEmpty()) {
                stackOut.push(stackIn.pop());
            }
        }
    }

    /**
     * Returns whether the queue is empty.
     */
    public boolean empty() {
        return stackIn.isEmpty() && stackOut.isEmpty();
    }
}
