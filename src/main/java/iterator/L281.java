package iterator;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/*
* 坑 1: Deque 的初始化 注意一下:
*       private Deque<Iterator<Integer>> deque;
*       this.deque = new LinkedList<>();
* 坑 2: 在把各个 list 的 iterator 放到 Deque 之前, 一定要先 check list 是否为空
* */

@SuppressWarnings("Duplicates")
public class L281 {

    private Deque<Iterator<Integer>> deque;

    public void ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        this.deque = new LinkedList<>();
        if (!(v2 == null || v2.isEmpty())) {
            deque.offerFirst(v2.iterator());
        }
        if (!(v1 == null || v1.isEmpty())) {
            deque.offerFirst(v1.iterator());
        }
    }

    public int next() {
        Iterator<Integer> head = deque.pollFirst();
        int ret = head.next();
        if (head.hasNext()) {
            deque.offerLast(head);
        }
        return ret;
    }

    public boolean hasNext() {
        return !deque.isEmpty();
    }
}
