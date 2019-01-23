package search;

@SuppressWarnings("Duplicates")
public class L302 {
    public int minArea_1(char[][] image, int x, int y) {
        //corner case
        if(image == null || image.length == 0 || image[0] == null || image[0].length == 0){
            return 0;
        }
        if(x < 0 || x > image.length - 1 || y < 0 || y > image[0].length - 1){
            return 0;
        }

        /*
         * 这里的二分法, 解是该元素为'1', 非解释该元素为'0'
         * */

        //get left boundary: find the most left col which is zero
        int left = 0;
        int right = y;
        //这里的二分法是基于该列是否等于零来判断的, 因为是在基准点的左边: 如果等于零, 则移动左列, 如果不等于零, 则移动右列
        while(left + 1 < right){
            int mid = left + (right - left) / 2;
            if(isColYAllZero(image, mid)){
                left = mid;
            }else{
                right = mid;
            }
        }
        //最后的左边界是以左面的第一个全是零的列为边界
        int leftBoundary = !isColYAllZero(image, left) ? left : right;
        System.out.println("left: " + left + "; right" + right);
        System.out.println("leftBoundary: " + leftBoundary);

        // get rigth boundary: find the most right col which is zero
        left = y;
        right = image[0].length - 1;
        //这里的二分法是基于该列是否等于零来判断的, 因为是在基准点的右边: 如果等于零, 则移动右列, 如果不等于零, 则移动左列
        while(left + 1 < right){
            int mid = left + (right - left) / 2;
            if(isColYAllZero(image, mid)){
                right = mid;
            }else{
                left = mid;
            }
        }
        //最后的右边界是以右面的第一个全是零的列为边界
        int rightBoundary = !isColYAllZero(image, right) ? right : left;
        System.out.println("left: " + left + "; right" + right);
        System.out.println("rightBoundary: " + rightBoundary);

        //get up boundary: find the most up row which is zero
        int up = 0;
        int low = x;
        //这里的二分法是基于该行是否等于零来判断的, 因为是在基准点的上边: 如果等于零, 则移动上边界的行, 如果不等于零, 则移动下边界的行
        while(up+1<low){
            int mid = up + (low - up) / 2;
            if(isRowXAllZero(image, mid)){
                up = mid;
            }else {
                low = mid;
            }
        }
        //最后的上边界是以上边的第一个全是零的行为边界
        int upBoundary = !isRowXAllZero(image, up) ? up: low;
        System.out.println("up: " + up + "; low" + low);
        System.out.println("upBoundary: " + upBoundary);

        //get dow boundary: find the most down row which is zero
        up = x;
        low = image.length - 1;
        //这里的二分法是基于该行是否等于零来判断的, 因为是在基准点的下边: 如果等于零, 则移动下边界的行, 如果不等于零, 则移动上边界的行
        while (up + 1 < low){
            int mid = up + (low - up) / 2;
            if(isRowXAllZero(image, mid)){
                low = mid;
            }else{
                up = mid;
            }
        }
        //最后的上边界是以下边的第一个全是零的行为边界
        int downBoundary = !isRowXAllZero(image, low) ? low: up;
        System.out.println("up: " + up + "; low" + low);
        System.out.println("downBoundary: " + downBoundary);

        return (rightBoundary - leftBoundary + 1) * (downBoundary - upBoundary + 1);
    }

    private boolean isColYAllZero(char[][] image, int y){
        for(int i = 0; i < image.length; i++){
            //这里要注意, 要用'1', 而不是单纯的1
            if(image[i][y] == '1'){
                return false;
            }
        }
        return true;
    }
    private boolean isRowXAllZero(char[][] image, int x){
        for(int i = 0; i < image[0].length; i++){
            //这里要注意, 要用'1', 而不是单纯的1
            if(image[x][i] == '1'){
                return false;
            }
        }
        return true;
    }

    /*
    * 坑 1: 这道题非常简单, 就是调试错误太费时间, 思路清晰的话, 没有问题
    * 坑 2: 这道题 求的就是 [x, y] 的上下左右边界,
    *       对于 左边界和上边界, 是 left most
    *       对于 右边界和下边界, 是 right most
    * 坑 3: 最后求出来的 上下左右边界, 相互相减的时候, 不要忘记加 1 之后再相减
    * 坑 4: 判断 某一行  某一列 是否 含有 1 的逻辑 可以单独提出来
    * 坑 5: 整体框架就是
    *               对于 x, 向左边看 含有 1 的最左边的列, 和含有 1 的最右边的列
    *               对于 y, 向上边看 含有 1 的最上边的行, 和含有 1 的最下边的行
    *
    * */

    /****************************************************************************************************/

    public int minArea(char[][] image, int x, int y) {
        //corner case
        if (image == null || image.length == 0 || image[0] == null || image[0].length == 0) {
            return 0;
        }
        int xLeft = getLowerCol(image, x, y);
        int xRight = getHigherCol(image, x, y);
        int yUp = getLowerRow(image, x, y);
        int yDown = getHigherRow(image, x, y);
        /*System.out.println("xLeft:" + xLeft);
        System.out.println("xRight:" + xRight);
        System.out.println("yUp:" + yUp);
        System.out.println("yDown:" + yDown);*/
        return (xRight + 1 - xLeft) * (yDown + 1 - yUp);
    }

    //最右边的Col: most right
    private int getHigherCol(char[][] image, int x, int y) {
        int left = y, right = image[0].length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            boolean hasPix = colHasPixal(image, mid);
            if (hasPix) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return colHasPixal(image, right) ? right : left;
    }

    //最下边的Rou: most right
    private int getHigherRow(char[][] image, int x, int y) {
        int left = x, right = image.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            boolean hasPix = rowHasPixal(image, mid);
            if (hasPix) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return rowHasPixal(image, right) ? right : left;
    }

    //最左边的Col: most left
    private int getLowerCol(char[][] image, int x, int y) {
        int left = 0, right = y;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            boolean hasPix = colHasPixal(image, mid);
            if (hasPix) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return colHasPixal(image, left) ? left : right;
    }

    //最上边的Row: most left
    private int getLowerRow(char[][] image, int x, int y) {
        int left = 0, right = x;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            boolean hasPix = rowHasPixal(image, mid);
            if (hasPix) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return rowHasPixal(image, left) ? left : right;
    }

    private boolean colHasPixal(char[][] image, int y) {
        for (int i = 0; i < image.length; i++) {
            if (image[i][y] == '1') {
                return true;
            }
        }
        return false;
    }

    private boolean rowHasPixal(char[][] image, int x) {
        for (int i = 0; i < image[0].length; i++) {
            if (image[x][i] == '1') {
                return true;
            }
        }
        return false;
    }
}
