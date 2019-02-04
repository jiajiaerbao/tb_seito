package peilian;


/*
* 坑 1: merge sort LinkedList 把思路分成几大块, 然后分别实现:
*       1. 找到中间的node, 然后断开, 分成左右两半
*       2. 对左右两半分别进行 recursion 的 merge
*
* */
@SuppressWarnings("Duplicates")
public class L148 {
    //这里是分治思想, 大化小
    public ListNode sortList_1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode preMid = findMid_1(head);
        ListNode secondPart = preMid.next;
        preMid.next = null;
        ListNode head1 = sortList_1(head);
        ListNode head2 = sortList_1(secondPart);
        return merge_1(head1, head2);
    }

    //这里非常巧妙: 充分利用了recursion的性质, 当前层只是比较 head1 和 head2 的关系, 剩下的全部丢到下一层
    private ListNode merge_1(ListNode head1, ListNode head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }
        if (head1.val < head2.val) {
            head1.next = merge_1(head1.next, head2);
            return head1;
        } else {
            head2.next = merge_1(head1, head2.next);
            return head2;
        }
    }

    //这里要找 mid 的前一个, 所以需要一个 prev
    private ListNode findMid_1(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        return prev;
    }
    /**************************************第二遍**********************************************/
    //头脑里面要想着 merge sort 的图, 然后写代码, 最好能当场画出来
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode preMid = findMid(head);
        ListNode mid = preMid.next;
        preMid.next = null;
        ListNode head1 = sortList(head);
        ListNode head2 = sortList(mid);
        return merge(head1, head2);
    }

    //当前层, 只看 head1 和 head2
    private ListNode merge(ListNode head1, ListNode head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }
        if (head1.val < head2.val) {
            head1.next = merge(head1.next, head2);
            return head1;
        } else {
            head2.next = merge(head1, head2.next);
            return head2;
        }
    }

    //找到 mid 的前一个
    private ListNode findMid(ListNode head) {
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        return prev;
    }
}




























