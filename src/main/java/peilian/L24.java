package peilian;


/*
* 坑 1: 画出图形来的话, 会非常好分析
* */
@SuppressWarnings("Duplicates")
public class L24 {
    public ListNode swapPairs(ListNode head) {
        // 1-->2-->   3-->4
        //------>
        // 2-->1-->   4-->3
        if (head == null || head.next == null) {
            return head;
        }
        ListNode subHead = swapPairs(head.next.next);
        //这下面容易出错, 注意下
        ListNode next = head.next;
        next.next = head;
        head.next = subHead;
        return next;
    }
}
