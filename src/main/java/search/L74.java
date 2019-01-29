package search;

/*
* 坑 1: 如果用 left + 1 < right 的模板的话, 那么退出 while loop的时候, 最后需要判断 left 和 right 是否等于 target
*
* */
@SuppressWarnings("Duplicates")
public class L74 {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return false;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int left = 0;
        int right = row * col - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (matrix[mid / col][mid % col] < target) {
                left = mid;
            } else if (matrix[mid / col][mid % col] > target) {
                right = mid;
            } else {
                return true;
            }
        }
        if (matrix[left / col][left % col] == target || matrix[right / col][right % col] == target) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        L74 l74 = new L74();
        l74.searchMatrix(new int[][]{{1}}, 1);
    }
}
