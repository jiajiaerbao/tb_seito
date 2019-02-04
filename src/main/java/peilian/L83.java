package peilian;

/*
* 坑 1: 这种简单题的逻辑思维一定要清晰, while里面分两种情况考虑, 把逻辑都分别封装到 if 和 else 里面
*
* */
@SuppressWarnings("Duplicates")
public class L83 {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = head;
        ListNode cur = head.next;
        while (cur != null) {
            if (prev.val == cur.val) {
                cur = cur.next;
                prev.next = cur;
            } else {
                prev = cur;
                cur = cur.next;
            }
        }
        return head;
    }
}
