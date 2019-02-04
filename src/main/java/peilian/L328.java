package peilian;

/*
* 坑 1: 这些题考的都是逻辑, 只要思路清晰, 都非常好做
* 坑 2: 大概思路:
*           用 dummyNode 固定住 odd 和 even 的两个头
*           用 curOdd 和 curEven 来逐个添加
*           最后两个连接起来
* 坑 3: 加 curEven之前 注意 cur 是否为空
*       最后别忘了在 curEven 之后加一个 null
* */
@SuppressWarnings("Duplicates")
public class L328 {
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        ListNode dummyOdd = new ListNode(0);
        ListNode curOdd = dummyOdd;
        ListNode dummyEven = new ListNode(0);
        ListNode curEven = dummyEven;
        ListNode cur = head;
        while (cur != null) {
            curOdd.next = cur;
            curOdd = curOdd.next;
            cur = cur.next;

            if (cur != null) {
                curEven.next = cur;
                curEven = curEven.next;
                cur = cur.next;
            }
        }
        curOdd.next = dummyEven.next;
        curEven.next = null;

        return dummyOdd.next;
    }
}
