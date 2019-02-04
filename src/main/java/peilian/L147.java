package peilian;

/*
* 坑 1: 这道题是原原本本地抄的, 没有一点自己的思维
* 坑 2: 这里不是很理解:
*           prev = dummy;
*           while (prev.next != null && cur.val > prev.next.val) {
*               prev = prev.next;
*           }
*       注意, 这里是 cur.val > prev.next.val的时候, prev向后移动一个
* 坑 3: 相当于从 dummy 开始, 重新建立了一个升序的序列
* */
@SuppressWarnings("Duplicates")
public class L147 {
    public ListNode insertionSortList_1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        //建立一个节点, 用来连接排好序的 LinkedList
        ListNode dummy = new ListNode(0);
        ListNode prev = dummy;

        //遍历所有node的时候用的
        ListNode cur = head;
        //要插入到其他位置的node
        ListNode insertNode = null;
        while (cur != null) {
            prev = dummy;
            while (prev.next != null && cur.val > prev.next.val) {
                prev = prev.next;
            }
            //每一次找到一个不满足要求的节点, 都插入到 prev....prev.next 之间
            insertNode = cur;
            //cur 从 head 遍历到 最后一个节点
            cur = cur.next;
            insertNode.next = prev.next;
            prev.next = insertNode;
        }
        return dummy.next;
    }
    /*****************************第二遍*************************************/
    /*
    * 坑 1: 这道题的思路要清晰:
    *           建立一个 dummy node, 以该 dummy node 作为起点, 建立一个升序序列
    *           每次都把 prev 设置在 dummy 的位置 (以 dummy 作为起点的升序序列)
    *           比较 cur 和 prev.next的值, 然后 顺次移动 prev
    *           碰到了不满足要求的值以后, 就插入到 prev .... prev.next 之间
    * */
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        ListNode prev = dummy;
        ListNode cur = head;
        ListNode insertNode = null;
        while (cur != null) {
            prev = dummy;
            while (prev.next != null && cur.val > prev.next.val) {
                prev = prev.next;
            }
            insertNode = cur;
            cur = cur.next;
            insertNode.next = prev.next;
            prev.next = insertNode;
        }
        return dummy.next;
    }
}















































