package peilian;

/*
* 坑 1: 这道题是抄的 solution, 比你自己想的要简单
* 坑 2: 总体的思路就是
*           先找到 m的前一个node 和 第m个node
*           用两个变量保存起来
*           reverse n-m 个 node
*           把reverse好的 头结点(prev) 和 m的前一个node 相连
*           把reverse好的 尾节点(变化前的第m个node) 和 n-m的后一个结点相连(cur) 相连
*           不满足m个的话, 不reverse; 不满足n-m个的话, reverse
* */
@SuppressWarnings("Duplicates")
public class L92 {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || m < 0 || n < 0 || m > n) {
            return head;
        }
        //以cur为中心去考虑
        ListNode prev = null;
        ListNode cur = head;
        int cnt = 1;
        //退出while的时候, cur已经到达了m的位置, prev是m的前一个, 如果不满足m的话, cur是null, 第二个while不执行
        while (cnt < m && cur != null) {
            prev = cur;
            cur = cur.next;
            cnt++;
        }
        //建立两个变量来记录 变化前的 m 和 m 后一个
        ListNode beforeM = prev;
        ListNode tailM = cur;
        //reverse, 退出下面的while之后, prev是第n个, cur是n的后面那个, 这时n是reverse后的头结点
        ListNode next = null;
        cnt = n - m + 1;                //这里需要加1
        while (cur != null && cnt > 0) {//这里是大于零
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        //跟reverse前的m前一个和n的后一个相连
        if (beforeM != null) {
            beforeM.next = prev;
        } else {
            head = prev;
        }
        tailM.next = cur;
        return head;
    }
}
