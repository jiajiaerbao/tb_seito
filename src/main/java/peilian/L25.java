package peilian;


@SuppressWarnings("Duplicates")
public class L25 {
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
    /*
    * 1-->2-->3-->4  (1, 3)
    * cr cr  cr
    *       1  --> 2   -->3-->4  (1, 3)
    * prv  cur    next
    * */
    public ListNode reverseKGroup_1(ListNode head, int k) {
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
        return reverseLinkedListByK_1(head, k);
    }

    private ListNode reverseLinkedListByK_1(ListNode head, int k) {
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
        ListNode subHead = reverseKGroup_1(cur.next, k);

        cur.next = null;
        ListNode newHead = reverseList_1(head);

        //这里是坑
        head.next = subHead;
        return newHead;
    }

    private ListNode reverseList_1(ListNode head) {
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
    /***********************************第二遍*********************************************/
    /*
    * 坑 1: 这个是抄的其他人的答案, 比你自己写的简单多了, 多写几遍加强练习
    * 坑 2: 下面的语句控制着 小于k 的时候, 是否 reverse
    *             if (cnt < k) {
    *                 return head;
    *             }
    *       如果想 reverse 的话, 直接删除上面的话即可
    * */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k <= 1) {
            return head;
        }
        ListNode nextKHead = head;
        int cnt = 0; // 这样 执行完 下面的 while 之后, nexthead 就落到了 k+1 的位置, 也就是下一次 k 的起点了
        while (cnt < k && nextKHead != null) {
            cnt++;
            nextKHead = nextKHead.next;
        }
        if (cnt < k) {
            return head;
        }
        ListNode subHead = reverseKGroup(nextKHead, k);

        ListNode prev = null;
        ListNode next = null;
        ListNode cur = head;
        while (cur != nextKHead) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }

        head.next = subHead;
        return prev;
    }









































}
