package bfs;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/*
* 这道题关键是话题, 把每一层进入到下一层的时候, sum 的计算过程是如何变化的弄清楚了, 就明白了
* 具体见bfs文档
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
}
