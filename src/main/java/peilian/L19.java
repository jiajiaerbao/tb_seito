package peilian;

/*
* 坑 1: 这道题是抄的答案, 其中让 firstNode 先走 n 个 的写法要注意, 自己用 while 写的, 不直观
*       这里  for (int i = 1; i <= n + 1; i++) 很明显的能说明是 先走 n 步
* 坑 2: 用一个 dummy 来绑定 head, 防止删除的 head 的情况, 找不到 head.next 了
* */
@SuppressWarnings("Duplicates")
public class L19 {
    //[a --> b --> c --> d]
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        //这里注意
        ListNode firstNode = dummy;
        //这里用 for i = 1...n+1 的写法好
        for (int i = 1; i <= n + 1; i++) {
            firstNode = firstNode.next;
        }
        //这里注意
        ListNode secondNode = dummy;
        while (firstNode != null) {
            firstNode = firstNode.next;
            secondNode = secondNode.next;
        }

        secondNode.next = secondNode.next.next;
        return dummy.next;
    }
}
