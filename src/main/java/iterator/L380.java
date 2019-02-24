package iterator;

import java.util.*;

@SuppressWarnings("Duplicates")
public class L380 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: insert的逻辑:
    *           首先判断是否已经存在, 通过hashMap的key来判断
    *           然后把该 val 加到 hashMap<Value, Set<Index>> 和 List<Value> 里面
    * 坑 2: remove() 的逻辑不清楚:
    *           首先判断该删除的val是否存在于map的key里面
    *           从map里面得到该删除的val在list里面的index, 这里用iterator().next()来得到
    *           从map里面remove掉该删除的val的index
    *           判断要删除的val的index在list里面的位置:
    *               如果是最后一个的话, 则直接从list里面remove掉即可
    *               如果是中间位置的某一个的话, 则把最末尾的元素移动到中间, 同时更新map里面删除的val和最后一个val的index(add, remove)
    *           把list的末尾元素删除掉
    *           最后, check该删除的val的Set<Index>是否为空, 如果为空的话, 从map里面移除
    * */
    class RandomizedSet {
        private Map<Integer, Set<Integer>> map;
        private List<Integer> list;
        private Random random;
        /**
         * Initialize your data structure here.
         */
        public RandomizedSet() {
            map = new HashMap<>();
            list = new ArrayList<>();
            random = new java.util.Random();
        }
        /**
         * Inserts a value to the set. Returns true if the set did not already contain the specified element.
         */
        public boolean insert(int val) {
            boolean isExist = map.containsKey(val);

            if (!isExist) {
                map.put(val, new HashSet<>());
            }
            map.get(val).add(list.size());
            list.add(val);

            return !isExist;
        }
        /**
         * Removes a value from the set. Returns true if the set contained the specified element.
         */
        public boolean remove(int val) {
            if (!map.containsKey(val)) {
                return false;
            }
            //先更新map, 再更新list
            int delValIdx = map.get(val).iterator().next();
            map.get(val).remove(delValIdx);

            //看是否需要交换
            if (delValIdx < list.size() - 1) {
                int lastVal = list.get(list.size() - 1);
                list.set(delValIdx, lastVal);
                map.get(lastVal).remove(list.size() - 1);
                map.get(lastVal).add(delValIdx);
            }
            //下面这句忘记了!
            list.remove(list.size() - 1);
            if (map.get(val).isEmpty()) {
                map.remove(val);
            }
            return true;
        }
        /**
         * Get a random element from the set.
         */
        public int getRandom() {
            return list.get(random.nextInt(list.size()));
        }
    }
}
