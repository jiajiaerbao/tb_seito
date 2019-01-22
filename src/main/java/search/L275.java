package search;


@SuppressWarnings("Duplicates")
public class L275 {
    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) {
            return 0;
        }
        int left = 0;
        int right = citations.length - 1;
        int len = citations.length;
        //题目本身不难, 读题和理解题意费时间
        //这里用的是第一种模板
        //len - mid 与 citations[mid] 的关系参考截图
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (citations[mid] == len - mid) {
                return len - mid;
            } else if (len - mid > citations[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return len - left;
    }
}
