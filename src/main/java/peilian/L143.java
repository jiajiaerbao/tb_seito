package peilian;

/*
* 坑 1: merge两个LinkedList到一起, 写的不对, 要用dummyNode
* 坑 2: 因为只是merge pair 一半, 所以只把后半段的LinkedList在原来的位置上面 reverse 即可: 找中点(分奇偶), 从中点处断开(否则可能出环), reverse reference, 然后以头和中点为起点, merge pair
* 坑 3: 思路分块:
*           找 中点, 从中点处断开, 把后半段reverse, 然后前后两个半段进行merge
* */
@SuppressWarnings("Duplicates")
public class L143 {
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        ListNode preMid = findMid(head);
        ListNode mid = preMid.next;
        preMid.next = null;
        ListNode secondPartHead = reverseList(mid);
        merge(head, secondPartHead);
    }

    private ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (head1 != null && head2 != null) {
            cur.next = head1;
            head1 = head1.next;
            cur = cur.next;

            cur.next = head2;
            head2 = head2.next;
            cur = cur.next;
        }
        //head1 和 head2 的两个 list,
        //要么左右相等(l1==null), 要么左边比右边多一个(l1剩下一个node, 因为rightHead = mid.next)
        cur.next = head1;
        return dummy.next;
    }

    //这里不用搞什么 preMid, 直接 取 slow 即可, 如果不是偶数的话 ,左边会多一个
    private ListNode findMid(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        ListNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
}
