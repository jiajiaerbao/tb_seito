package twoPointersSlidingWindow;

import java.util.HashMap;
import java.util.Map;

/*
* 坑 1: 坑BOSS: 这道题是完完全全地抄的, 跟LRU一样, 你还需要练习
*       Class MyQueue{
*           Class Node{
*               char, lastIndex, prev, next
*           }
*           head, tail, start, maxSize;
*           Map<char, Node>
*           constructor: connect head and tail, create map
*           3 APIs:
*           ****add(char, index): //这个是核心
*                   new ---> add OR
*                   existing ---> update OR
*                   map.size == maxSize ---> delete head node (have to update start index)
*           remove(node)
*           removeFromHead(): head.next.next-->new 1st node
*           getSize(index): current sliding window size, in doubleLinkedList only keep start index of the window
*       }
* 坑 2: 涉及到LinkedList的操作的时候, 两层指针的时候, 尽量事先用变量把第一层存起来, 比如
*       Node prev = n.prev
*       Node next = n.next
* */
@SuppressWarnings("Duplicates")
public class L340 {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        //corner case
        if (s == null || s.length() == 0 || k == 0) {
            return 0;
        }
        int res = 0;
        MyQueue myQueue = new MyQueue(k);
        for (int i = 0; i < s.length(); i++) {
            char curCh = s.charAt(i);
            myQueue.add(curCh, i);
            res = Math.max(res, myQueue.getSize(i));
        }
        return res;
    }

    private class MyQueue {
        private class Node {
            Character id;
            int lastIdx;
            Node prev;
            Node next;

            public Node(Character id, int lastIdx) {
                this.id = id;
                this.lastIdx = lastIdx;
                this.prev = null;
                this.next = null;
            }
        }

        Node head;
        Node tail;
        int maxSize;
        int start;
        Map<Character, Node> map;

        public MyQueue(int maxSize) {
            this.maxSize = maxSize;
            this.start = 0;
            head = new Node('\0', 0);
            tail = new Node('\0', 0);
            head.next = tail;
            tail.prev = head;
            map = new HashMap<>();
        }

        public void add(Character curCh, int curIdx) {
            Node newNode = new Node(curCh, curIdx);
            if (map.containsKey(curCh)) {
                remove(curCh);
            } else if (map.size() == maxSize) {
                int prevLeastIdx = removeFromHead();
                start = prevLeastIdx + 1;
            }
            map.put(curCh, newNode);
            addToTail(newNode);
        }

        private void remove(Character curCh) {
            Node node = map.get(curCh);
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
            map.remove(curCh);
        }

        private int removeFromHead() {
            map.remove(head.next.id);   //先从map里面remove掉, 跟接下来的从DoubleLinkedList里面删除 不发生冲突

            int res = head.next.lastIdx;    //这里注意: 这里取得的是 删除之前的 index
            Node newFirstNode = head.next.next;

            head.next = newFirstNode;
            newFirstNode.prev = head;
            return res;
        }

        private void addToTail(Node node) {
            Node prevLastNode = tail.prev;
            /*map.put(node.id, node);*/
            prevLastNode.next = node;
            node.prev = prevLastNode;
            node.next = tail;
            tail.prev = node;
        }

        public int getSize(int idx) {
            return idx - start + 1;
        }
    }
}
