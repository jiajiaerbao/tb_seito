package peilian;

/*
* 坑 1: check 是否 含有 k 个 元素 的 逻辑:
            int cnt = 1;
            ListNode cur = head;
            while (cnt < k && cur != null) {
                cnt++;
                cur = cur.next;
            }
        cnt==k的话, 则等于k
* 坑 2: reverseLinkedListByK 的逻辑
*           head...cur: 是 前 k 个 元素
*           cur.next 是 第 k+1 个 元素
*       cur.next = null;是把 前 k 个元素断开
* */
@SuppressWarnings("Duplicates")
public class L25 {
    /*
    * 1-->2-->3-->4  (1, 3)
    * cr cr  cr
    *       1  --> 2   -->3-->4  (1, 3)
    * prv  cur    next
    * */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null || k <= 1) {
            return head;
        }
        int cnt = 1;
        ListNode cur = head;
        while (cnt < k && cur != null) {
            cnt++;
            cur = cur.next;
        }
        //System.out.println("Head:" + head.val + ";  cur:" + cur.val);
        if (cnt < k) {
            return head;
        }
        return reverseLinkedListByK(head, k);
    }

    private ListNode reverseLinkedListByK(ListNode head, int k) {
        //System.out.println("reverseHead:" + head.val);
        int cnt = 1;
        ListNode cur = head;
        while (cnt < k && cur != null) {
            cur = cur.next;
            cnt++;
        }
        //System.out.println("reverseCur:" + cur.val);
        //这里是坑: 这里是判断是否走到了末尾
        if (cur == null) {
            return head;
        }
        ListNode subHead = reverseKGroup(cur.next, k);

        cur.next = null;
        ListNode newHead = reverseList(head);

        //这里是坑
        head.next = subHead;
        return newHead;
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
