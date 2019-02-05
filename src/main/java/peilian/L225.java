package peilian;

import java.util.LinkedList;
import java.util.Queue;

/*
* 坑 1: 需要事先跟面试官clarify哪个API用的多, 没法做到两个都是 O(1)
*       这里假设push多
* 坑 2: 在 push 操作, 里面 rotate queue 的话, 比较简单, 因为涉及到 insert 的只有 push
*       如果在 pop 里面 rotate queue 的话, 还需要考虑 pop的时候, 也要做同样的操作, 比较麻烦
* */
@SuppressWarnings("Duplicates")
public class L225 {
    Queue<Integer> queue;

    /**
     * Initialize your data structure here.
     */
    public L225() {
        queue = new LinkedList<>();
    }

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        queue.offer(x);
        for (int i = 0; i < queue.size() - 1; i++) {
            queue.offer(queue.poll());
        }
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
        return queue.poll();
    }

    /**
     * Get the top element.
     */
    public int top() {
        return queue.peek();
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return queue.isEmpty();
    }
}
