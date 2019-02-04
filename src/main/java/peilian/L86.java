package peilian;

/*
* 坑 1: LinkedList就是一个熟练工种, 把题目拿出来, 不看答案, 自己写, 写完以后跟上课讲的思路对比一下,
*       思路练熟, 写题的手感练熟
* 坑 2: 跟 328 类似
* */
@SuppressWarnings("Duplicates")
public class L86 {
    public ListNode partition(ListNode head, int x) {
        if (head == null) {
            return head;
        }
        ListNode dummyLarger = new ListNode(0);
        ListNode curLarger = dummyLarger;
        ListNode dummyLess = new ListNode(0);
        ListNode curLess = dummyLess;
        ListNode cur = head;
        while (cur != null) {
            if (cur.val < x) {
                curLarger.next = cur;
                curLarger = curLarger.next;
            } else {
                curLess.next = cur;
                curLess = curLess.next;
            }
            cur = cur.next;
        }
        curLarger.next = dummyLess.next;
        curLess.next = null;
        return dummyLarger.next;
    }
}
