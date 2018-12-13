package bfs;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/*
* 坑 1: 这道题关键是画图, 把每一层进入到下一层的时候, sum 的计算过程是如何变化的弄清楚了, 就明白了
*       具体见bfs文档
* 坑 2: 每当进入到下一层的时候, 把之前所有遇到过的Integer都加一遍即可
* */
public class L364 {
    // This is the interface that allows for creating nested lists.
// You should not implement it, or speculate about its implementation
    public interface NestedInteger {
        // Constructor initializes an empty nested list.
        //public NestedInteger();

        // Constructor initializes a single integer.
        //public NestedInteger(int value);

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // Set this NestedInteger to hold a single integer.
        public void setInteger(int value);

        // Set this NestedInteger to hold a nested list and adds a nested integer to it.
        public void add(NestedInteger ni);

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int sum = 0;
        //corner case
        if (nestedList == null || nestedList.isEmpty()) {
            return sum;
        }
        Queue<List<NestedInteger>> que = new LinkedList<>();
        que.offer(nestedList);
        int subSum = 0;
        while (!que.isEmpty()) {
            int size = que.size();
            while (size-- > 0) {
                List<NestedInteger> nestedIntegerList = que.poll();
                for (NestedInteger nestedInteger : nestedIntegerList) {
                    if (nestedInteger.isInteger()) {
                        subSum += nestedInteger.getInteger();
                    } else {
                        que.offer(nestedInteger.getList());
                    }
                }
            }
            sum = sum + subSum;
        }
        return sum;
    }

    /****************************************************************/
    /*
    * 2 + (2+4)
    * 1 + (1+4) + (1+4+6)
    * */
    public int depthSumInverse2(List<NestedInteger> nestedList) {
        if (nestedList == null || nestedList.size() == 0) {
            return 0;
        }
        Queue<NestedInteger> que = new LinkedList<>();
        for (NestedInteger cur : nestedList) {
            que.offer(cur);
        }
        int sum = 0;
        int eachIntegerSum = 0;
        while (!que.isEmpty()) {
            int size = que.size();
            while (size-- > 0) {
                NestedInteger cur = que.poll();
                if (cur.isInteger()) {
                    eachIntegerSum += cur.getInteger();
                } else {
                    for (NestedInteger next : cur.getList()) {
                        que.offer(next);
                    }
                }
            }
            sum += eachIntegerSum;
        }
        return sum;
    }
}






































