package iterator;

import java.util.*;

@SuppressWarnings("Duplicates")
public class L341 {


    /******************************第一遍********************************************/
    /*
    * 坑 1: 这道题是抄的答案:
    *           关键点是 NestedInteger 里面可能是 list 也可能是 integer, 而且是递归地循环嵌套方式
    * 坑 2: 坑 BOSS:
    *           你的大方向就错了, 这道题应该用stack
    *           而且是从list的末尾开始, 从 len-1 向 0 反向地插入到stack中, 而stack是后进先出, 这样就保证了正序
    * 坑 3: 因为List可能存在递归嵌套, 比如 List<NestedInteger<List<NestedInteger<List<NestedInteger>>>>>,
    *       需要把这个转化成只含有 List 和 Integer 的形式, 这是在constructor里面直接进行flat的,
    *               缺点是stack的size过大, 消耗内存过多
    *               优点是在call next 的时候, 因为stack里面只含有 List<Integer> 和 Integer两种数据形式, 进行输出的时候, 时间效率会很高
    * */
    class firstTime{
        public class NestedIterator implements Iterator<Integer> {
            private Stack<NestedInteger> stack;

            public NestedIterator(List<NestedInteger> nestedList) {
                this.stack = new Stack<>();
                pushInStack(stack, nestedList);
            }

            private void pushInStack(Stack<NestedInteger> stack, List<NestedInteger> list) {
                if (list == null) {
                    return;
                }
                for (int i = list.size() - 1; i >= 0; i--) {
                    if (list.get(i).isInteger()) {
                        stack.push(list.get(i));
                    } else {
                        pushInStack(stack, list.get(i).getList());
                    }
                }
            }

            @Override
            public Integer next() {
                while (!stack.isEmpty() && !stack.peek().isInteger()) {
                    NestedInteger cur = stack.pop();
                    for (int i = cur.getList().size() - 1; i >= 0; i--) {
                        stack.push(cur.getList().get(i));
                    }
                }
                return stack.pop().getInteger();
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }
        }
    }

    public interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }
}
