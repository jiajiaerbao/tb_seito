package peilian;

/*
* 坑 1: 这道题是用的 recursion 做的, 看的discuss
* 坑 2: 利用系统栈, 从head开始, 把所有的node都要到系统栈里面,
*       从系统栈最上面的node开始加 1, 然后把carry传到前一个节点
* */
@SuppressWarnings("Duplicates")
public class L369 {
    public ListNode plusOne(ListNode head) {
        if (head == null) {
            return head;
        }
        int carry = helper(head);
        if (carry == 1) {
            ListNode newHead = new ListNode(1);
            newHead.next = head;
            head = newHead;
        }
        return head;
    }

    private int helper(ListNode head) {
        if (head.next == null) {
            //先无脑加上carry
            head.val = head.val + 1;
            //再判断当前的head.val是否等于10
            if (head.val == 10) {
                head.val = 0;
                return 1;
            }
            return 0;
        }
        int carry = helper(head.next);
        //先无脑加上carry
        head.val += carry;
        //再判断当前的head.val是否等于10
        if (head.val == 10) {
            head.val = 0;
            return 1;
        }
        return 0;
    }
}
