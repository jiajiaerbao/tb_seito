package search;

import java.util.PriorityQueue;

@SuppressWarnings("Duplicates")
public class L215 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 这是用 quickSelection 写的, 算法哥的答案也是这样给的, 老刘推荐用minHeap来做(见第一遍)
    * */
    class SecondTime{
        public int findKthLargest(int[] nums, int k) {
            return search(nums, 0, nums.length - 1, k);
        }

        private int search(int[] nums, int left, int right, int k) {
            if (left == right) {
                return nums[left];
            }
            int idxPivot = partition(nums, left, right);
            int rankPivot = idxPivot - left + 1;
            if (rankPivot == k) {
                return nums[idxPivot];
            } else if (rankPivot < k) {
                //这里各个参数的变化要注意: 当pivot的排名小于k的时候, 减掉pivot的左边, left变成 idxPivot+1, k变成 k-rankPivot
                return search(nums, idxPivot + 1, right, k - rankPivot);
            } else {
                //这里各个参数的变化要注意: 当pivot的排名大于k的时候, 减掉pivot的右边, right变成 idxPivot-1, k不变
                return search(nums, left, idxPivot - 1, k);
            }
        }

        //这里是 [left, right] 的 左闭右闭 区间, pivot取最右面那个即nums[right], 具体看everNote的算法哥findKth的代码
        private int partition(int[] nums, int left, int right) {
            int pivotVal = nums[right];
            int start = left - 1;
            //这里 i 的起始点是 left, 而不是 0
            for (int i = left; i < right; i++) {
                //如果大于pivotVal的话, 交换
                if (nums[i] > pivotVal) {
                    swap(nums, ++start, i);
                }
            }
            swap(nums, ++start, right);
            return start;
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    /******************************第一遍********************************************/
    class FirstTime{
        /*
         * 坑 1: 这道题用minHeap来做, 因为只要保存到目前为止的最大的 k 个 即可
         *       这样的话, 在后面的follow up的时候, 空间复杂度只是跟 k 有关系
         * */
        public int findKthLargest(int[] nums, int k) {
            PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> a - b);
            for (int num : nums) {
                if (minHeap.size() < k) {
                    minHeap.offer(num);
                } else {
                    int top = minHeap.peek();
                    if (num > top) {
                        minHeap.poll();
                        minHeap.offer(num);
                    }
                }
            }
            return minHeap.peek();
        }
    }

}
