package iterator;

import java.util.Iterator;

@SuppressWarnings("Duplicates")
public class L284 {

    /******************************第一遍********************************************/
    /*
    * 坑 1: 这个答案跟算法哥给的答案不一致,
    *           你是用的缓存的思想:
    *               也就是 cur 里面存的是下一个将要输出的元素
    *               而每次 call .next() 的时候, 都是先输出cur, 把下一个元素转移到cur里面
    * */
    class firstTime{
        class PeekingIterator implements Iterator<Integer> {
            private Integer cur;
            private Iterator<Integer> iterator;

            public PeekingIterator(Iterator<Integer> iterator) {
                // initialize any member here.
                this.iterator = iterator;
                if (this.iterator.hasNext()) {
                    this.cur = iterator.next();
                } else {
                    //不要忘记考虑hashNext() == null的情况
                    this.cur = null;
                }
            }

            // Returns the next element in the iteration without advancing the iterator.
            public Integer peek() {
                return cur;
            }

            // hasNext() and next() should behave the same as in the Iterator interface.
            // Override them if needed.
            @Override
            public Integer next() {
                Integer temp = cur;
                cur = null;
                if (this.iterator.hasNext()) {
                    cur = iterator.next();
                }
                return temp;
            }

            @Override
            public boolean hasNext() {
                return cur != null || this.iterator.hasNext();
            }
        }
    }
}
