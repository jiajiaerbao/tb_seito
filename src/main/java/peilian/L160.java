package peilian;

/*
* 坑 1: 抄的答案, 答案很精巧, 关键是能解释清楚
* 坑 2: 这道题的关键点就是这两句:
*           curA = (curA == null ? headB : curA.next);
*           curB = (curB == null ? headA : curB.next);
*       当 curA 是 null 的时候, 等于 headB, 而不是 curB
*       当 curB 是 null 的时候, 等于 headA, 而不是 curA
* */
@SuppressWarnings("Duplicates")
public class L160 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode curA = headA;
        ListNode curB = headB;
        while (curA != curB) {
            curA = (curA == null ? headB : curA.next);
            curB = (curB == null ? headA : curB.next);
        }
        return curA;
    }
}
