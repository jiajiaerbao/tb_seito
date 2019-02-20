package stack;

import java.util.Stack;

@SuppressWarnings("Duplicates")
public class L255 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: 这道题是抄的答案
    *       pre-order: [....root....](分界点)[....左子树<root....](分界点)[....右子树>root....]
    *           在右子树的区域内, 找小于root的点是否存在
    * */
    public boolean verifyPreorder(int[] preorder) {
        if(preorder == null || preorder.length == 0){
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        Integer lowerBound = null;
        for (int num : preorder) {
            if(lowerBound != null && num < lowerBound){
                return false;
            }
            //这里是关键, lowerBound始终指着当前层的root
            while (!stack.isEmpty() && stack.peek() < num) {
                lowerBound = stack.pop();
            }
            stack.push(num);
        }
        return true;
    }
}
