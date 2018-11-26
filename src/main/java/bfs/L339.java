package bfs;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class L339 {

    /*
    * 这道题的本质是level order traverse
    * easy 题目, 没什么可说的
    * */

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
    public int depthSum(List<NestedInteger> nestedList) {
        int sum = 0;
        //corner case
        if (nestedList == null || nestedList.size() == 0) {
            return sum;
        }
        Queue<List<NestedInteger>> que = new LinkedList<>();
        que.offer(nestedList);
        int level = 1;
        while (!que.isEmpty()) {
            int size = que.size();
            while (size-- > 0) {
                List<NestedInteger> nestedIntegerList = que.poll();
                for (NestedInteger nestedInteger : nestedIntegerList) {
                    if (nestedInteger.isInteger()) {
                        sum += level * nestedInteger.getInteger();
                    } else {
                        que.offer(nestedInteger.getList());
                    }
                }
            }
            level++;
        }
        return sum;
    }
}
