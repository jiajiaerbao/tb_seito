package peilian;

import java.util.Stack;

/*
* 坑 1: 压入到 minValStack 的 value:
*       minValStack 为空 或者 x小于minValStack.peek() 则 压入 x
*       minValStack.peek() 小于 x, 则压入 minValStack.peek()
* */
@SuppressWarnings("Duplicates")
public class L155 {
    private Stack<Integer> oriValStack;
    private Stack<Integer> minValStack;

    /**
     * initialize your data structure here.
     */
    public L155() {
        oriValStack = new Stack<>();
        minValStack = new Stack<>();
    }
    //这里是坑, 压入到 minValStack 里面的值 是什么
    public void push(int x) {
        oriValStack.push(x);
        if (minValStack.isEmpty() || minValStack.peek() > x) {
            minValStack.push(x);
        } else {
            minValStack.push(minValStack.peek());
        }
    }

    public void pop() {
        minValStack.pop();
        oriValStack.pop();
    }

    public int top() {
        return oriValStack.peek();
    }

    public int getMin() {
        return minValStack.peek();
    }
}
