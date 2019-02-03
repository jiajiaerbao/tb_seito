package peilian;

@SuppressWarnings("Duplicates")
public class Lint228 {
    public ListNode middleNode(ListNode head) {
        // write your code here
        if (head == null) {
            return head;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
