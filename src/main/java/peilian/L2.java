package peilian;

/*
* 坑 1: 思路是 merge two sorted LinkedList, val 是 两个list 的 加和, 注意要向下传递一个carry,
*       而且 最后还要判断一下, 最后的carry 是否需要单独建立一个node
* 坑 2: dummyNode 用来固定新的 LinkedList 的头结点, 然后 三个 cur 来 分别指向 三个 list (包括新建的list)
*       因为两个已知的list的head是个位, 所以是 node by node 第齐步走 merge 两个list
*       然后 把 curL1 或者 curL2 剩下的元素也 merge 到新的 list
*       最后看一下 carry 是否还有元素
* */
@SuppressWarnings("Duplicates")
public class L2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        ListNode curL1 = l1;
        ListNode curL2 = l2;

        int val = 0;
        int carry = 0;
        while (curL1 != null && curL2 != null) {
            val = curL1.val + curL2.val + carry;
            carry = 0;
            if (val >= 10) {
                carry = 1;
                val -= 10;
            }

            ListNode newNode = new ListNode(val);
            cur.next = newNode;
            cur = cur.next;

            curL1 = curL1.next;
            curL2 = curL2.next;
        }
        while (curL1 != null) {
            val = curL1.val + carry;
            carry = 0;
            if (val >= 10) {
                carry = 1;
                val -= 10;
            }
            ListNode newNode = new ListNode(val);
            cur.next = newNode;
            cur = cur.next;
            curL1 = curL1.next;
        }
        while (curL2 != null) {
            val = curL2.val + carry;
            carry = 0;
            if (val >= 10) {
                carry = 1;
                val -= 10;
            }
            ListNode newNode = new ListNode(val);
            cur.next = newNode;
            cur = cur.next;
            curL2 = curL2.next;
        }
        //最后不要忘了加上最后一个Carry
        if (carry == 1) {
            ListNode newNode = new ListNode(carry);
            cur.next = newNode;
        }
        return dummy.next;
    }
}
