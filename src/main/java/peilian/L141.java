package peilian;

/*
* 坑 1: 注意最后的判断条件是 slow == fast
* */
@SuppressWarnings("Duplicates")
public class L141 {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null && slow != fast) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow == fast;
    }
}
