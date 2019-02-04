package peilian;

/*
* 坑 1: 跳出 while 的时候, 不要忘记把最后一个cur的val赋值给prev
*           prev.val = cur.val;
*           prev.next = null;
* */
@SuppressWarnings("Duplicates")
public class L237 {
    public void deleteNode(ListNode node) {
        ListNode prev = node;
        ListNode cur = node.next;
        while (cur.next != null) {
            prev.val = cur.val;
            prev = cur;
            cur = cur.next;
        }
        prev.val = cur.val;
        prev.next = null;
    }
}
