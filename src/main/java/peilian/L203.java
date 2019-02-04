package peilian;

/*
* 坑 1: 简单题的逻辑还是不清晰
*
* */
@SuppressWarnings("Duplicates")
public class L203 {
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = head;
        ListNode prev = dummy;
        while (cur != null) {
            //这里的逻辑要清晰: 当cur==val的时候, prev等于cur.next; 当cur!=val的时候, prev才向前挪动一位
            if (cur.val == val) {
                prev.next = cur.next;
            } else {
                prev = cur;
            }
            cur = cur.next;
        }
        return dummy.next;
    }
}
