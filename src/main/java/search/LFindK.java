package search;

@SuppressWarnings("Duplicates")
public class LFindK {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 注意这里的 nums array 是 无序的
    * */
    class SecondTime{
        public int findKth(int[] nums, int k){
            return search(nums, 0, nums.length-1, k);
        }
        private int search(int[] nums, int start, int end, int k){
            if(start == end){
                return nums[start];
            }
            int pivotIdx = partition(nums, start, end);
            int pivotRank = pivotIdx - start + 1;
            if(pivotRank == k){
                return nums[pivotIdx];
            }else if(pivotRank > k){
                return search(nums, start, pivotIdx - 1, k);
            }else {
                return search(nums, pivotIdx+1, end, k - pivotRank);
            }
        }
        private int partition(int[] nums, int start, int end){
            int pivot = nums[end];
            int left = start-1;
            for(int i = start; i < end; i++){
                //left是小于pivot的区间, 当碰到了nums[i]的话, ++left
                if(nums[i] < pivot){
                    swap(nums, ++left, i);
                }
            }
            swap(nums, ++left, end);
            return left;
        }
        private void swap(int[] nums, int i, int j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
    /******************************第一遍********************************************/
    /*
    * 坑 1: 这道题你不熟练, 关键就是写熟了, 这个findK和求中位数, 可以用同一个代码
    * 坑 2: partition的过程:
    *           选择最右面的节点(end)作为pivot:
    *               [ start, left ) : 左闭右开, 小于pivot, left的初始值: start-1, 表示空
    *               [ left, i ] : 左闭右闭, 大于pivot
    *               ( i, end ): 左开右开, 未知
    * 坑 3: 对nums进行partition, 找到pivot的idx
    *       判断是否是第k个元素
    *           是 则返回
    *           k 在 idx 左面, 则 继续递归地在 [start, idx-1] 里找 第 k 个
    *           k 在 idx 右面, 则 继续递归地在 [idx+1, end] 里找 第 k-(idxP-start+1) 个
    * */
    class FirstTime{
        private int findKth(int[] nums, int start, int end, int k){
            if(start == end){
                return nums[start];
            }
            int idxP = partition(nums, start, end);
            int rankP = idxP - start + 1;
            if(rankP == k){
                return nums[idxP];
            }else if( rankP < k) {
                return findKth(nums, idxP+1, end, k - rankP);
            }else { //rankP > k
                return findKth(nums, start, idxP-1, k);
            }
        }
        private int partition(int[] nums, int start, int end){
            //取 最右面的元素 作为 pivot
            int pivot = nums[end];
            //[start, start-1]是小于pivot的区间, 初始是空
            int left = start-1;
            for(int i = start; i < end; i++){
                //先把小于pivot的区间增加1, 再交换赋值 (因为初始是空)
                if(nums[i] < pivot){
                    swap(nums, ++left, i);
                }
            }
            //最后把pivot移动到 left+1 的位置
            swap(nums, ++left, end);
            return left;
        }
        private void swap(int[] nums, int i, int j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
