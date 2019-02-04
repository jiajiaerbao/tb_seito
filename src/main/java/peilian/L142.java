package peilian;

/*
* 坑 1: 这道题是抄的答案, 答案写的比你自己的要好, 可以借鉴
* 坑 2: 逻辑分块, 代码也要分块
*           1. 用 快慢指针 找出相遇点 ---> getMeetPoint
*           2. 分情况考虑:
*                   如果 相遇点 为 null, 则直接返回
*                   如果 相遇点 不为 null, 则两个Pointer分别从相遇点和起始点一起走
* 坑 3: 要能分析清楚, 现场给面试官写出来
* */
@SuppressWarnings("Duplicates")
public class L142 {
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode meetPoint = getMeetPoint(head);
        if (meetPoint == null) {
            return meetPoint;
        }
        ListNode slow = head;
        ListNode fast = meetPoint;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    private ListNode getMeetPoint(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return slow;
            }
        }
        return null;
    }
}
