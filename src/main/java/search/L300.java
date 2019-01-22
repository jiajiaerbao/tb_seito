package search;


import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class L300 {
    public int lengthOfLIS_2(int[] nums) {
        //1.正常解法:
        //大方向: 分叉 --> DFS --> pruning: n^2

        //2.偏门 greedy: n log n
        //找到每一层的最小元素, 一定不会丢解; 对于每一层的备选元素, 这些不同的备选元素之间是竞争关系, 选取最小的不会丢解
        //替换: A --> B --> C, C的后面是D, 如果D > C 的话, 则直接拼接上去, 如果D < C 的话, 无法直接拼接, 可以把C 替换成 D, 这样不影响解的长度
        if (nums == null || nums.length == 0) {
            return 0;
        }
        List<Integer> buffer = new ArrayList<>();
        //比当前数大的最小的, 如果是相等的话, 不用换, 因为是sorted, 所以可以用binary search
        //这里的逻辑参考leetcode的solution的第三种解法的说明
        //当替换了比当前数n大的最小的那个数之后, 不影响解的长度
        //2, 3, 7 --> 1 ,3 ,7: 2被1替换了之后, 并不影响解的长度
        for (int n : nums) {
            if (buffer.size() == 0) {
                buffer.add(n);
            } else {
                int index = findIndex(buffer, n);
                if (index >= buffer.size()) {
                    buffer.add(n);
                } else {
                    buffer.set(index, n);
                }
            }
        }
        return buffer.size();
    }
    private int findIndex(List<Integer> list, int n) {
        int left = 0;
        int right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) == n) {
                return mid;
            } else if (list.get(mid) > n) {
                //mid要比n大, 与n相等的只能出现在mid的左边
                right = mid - 1;
            } else {
                //mid要比n小, 与n相等的只能出现在mid的右边
                left = mid + 1;
            }
        }
        //如果找不到相等的, 则把比 n 大的那个返回
        return left;
    }

    /*
    * 坑 1: binary search针对的是 结果集 中的元素:
    *           在已知的结果集序列res里面, 对于新来的元素target(n), 跟结果集中已知的元素进行比较, 如果找到了比target(n)大的元素,
    *           则定位到比target(n)大的所有元素里面最小的那个, 然后进行进行替换
    *           如果没有比target(n)大的元素, 则把target(n)直接add到res的最后面
    * 坑 2: 在进行binary search的时候, 最后定位到了两个元素, [left, right], 这时
    *       如果 left 大于等于 target(n) 的话, 则 替换 left
    *       如果 right 大于等于 target(n) 的话, 则 替换 right
    *       找不到的话, 则返回 size()+1
    * */
    /******************************************第二遍************************************************/
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        List<Integer> res = new ArrayList<>();
        for (int n : nums) {
            int idx = getMaxValInAllLessThanN(res, n);
            if (idx < res.size()) {
                res.set(idx, n);
            } else {
                res.add(n);
            }
            System.out.println(res);
        }
        return res.size();
    }

    //这里找的是 在已经 排好的 解序列 里面 找比 target大的 最小值
    private int getMaxValInAllLessThanN(List<Integer> list, int target) {
        if (list.size() == 0) {
            return list.size() + 1;
        }
        int left = 0, right = list.size() - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < target) {
                left = mid;
            } else if (list.get(mid) > target) {
                right = mid;
            } else {
                right = mid;
            }
        }
        if (list.get(left) >= target) {
            return left;
        } else if (list.get(right) >= target) {
            return right;
        } else {
            return list.size() + 1;
        }
    }

    public static void main(String[] args){
        L300 l300 = new L300();
        int[] arr = new int[] {4,10,4,3,8,9};
        /*int[] arr = new int[] {10,9,2,5,3,7,101,18};*/
        /*int[] arr = new int[] {-2, -1};*/
        l300.lengthOfLIS(arr);
    }
































}
