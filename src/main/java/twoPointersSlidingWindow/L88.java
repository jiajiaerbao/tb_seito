package twoPointersSlidingWindow;

@SuppressWarnings("Duplicates")
public class L88 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: 定义三个index, 分别指向了nums1, nums2和最后结果集
    *       所有的这三个index, 都是从最右面, len-1开始, 然后一直走到头
    *       如果nums2走到头了, 最后只剩下num1的话, 就不用再sort了
    * */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int idxSum = m + n - 1;
        int idx2 = n - 1;
        int idx1 = m - 1;
        while (idx1 >= 0 && idx2 >= 0) {
            if (nums1[idx1] > nums2[idx2]) {
                nums1[idxSum--] = nums1[idx1--];
            } else {
                nums1[idxSum--] = nums2[idx2--];
            }
        }
        /*while (idx1 >= 0) {
            nums1[idxSum--] = nums1[idx1--];
        }*/
        while (idx2 >= 0) {
            nums1[idxSum--] = nums2[idx2--];
        }
    }
}
