package aoa;

import java.util.*;

@SuppressWarnings("Duplicates")
public class MS {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    //加 comment************************************************************
    /*
     * Time complexity: O(n), we at most traverse LinkedList for one time
     * Space complexity is O(1), we do it in-place
     */
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode cur = head;
        while (cur != null) {
            if (cur.val == val) {
                prev.next = cur.next;
            } else {
                prev = cur;
            }
            cur = cur.next;
        }
        return dummy.next;
    }
    //加 comment*************************************************************
    /*
    * strategy:
    * 1. find the first decreasing number from right to left
    * 2. after this number, find the smallest one element but bigger than this number
    * 3. swap them
    * 4. let every element after this number became increasing
    * Time complexity: O(n),  we at most traverse array for 3 times
    * Space complexity is O(1), we do it in-place
    * */
    public void nextPermutation(int[] nums) {
        if(nums == null || nums.length == 0) return;
        int pointer = nums.length;
        int i;
        boolean flag = false;
        // find the first decreasing number from right to left;
        for(i = nums.length - 1; i > 0; i--){
            if(nums[i] > nums[i - 1]){
                flag = true;
                break;
            }
        }
        // if flag == false -> all of numbers are increasing(it's min), we need to change it to max one;
        if(!flag) {
            shift(nums, 0, nums.length - 1);
            return;
        }
        int val = nums[i  - 1];
        // find the smallest one bigger than val;
        int j = i;
        while(j < nums.length && nums[j] > val){
            j++;
        }
        j--;
        swap(nums, i - 1, j);
        // after i, make the array is increasing;
        shift(nums, i, nums.length - 1);

    }
    private void shift(int[] nums, int left, int right){
        while(left < right) {
            swap(nums, left, right);
            left += 1;
            right -= 1;
        }
    }
    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    //加 comment*************************************************************
    /*
     * Time complexity: O(n), have to traverse allPoints, and its size is n, k can be ignored compared with n
     * Space complexity is O(k), use PriorityQueue, and its max size is K
     */
    public class Point {
        public int x;
        public int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public Set<Point> FindClosestPoints(Set<Point> allPoints, Point referencePoint, int k) {
        Set<Point> res = new HashSet<>();
        Point ori = referencePoint;
        PriorityQueue<Point> maxHeap = new PriorityQueue<>(
                (a, b) -> getDist(b, ori) - getDist(a, ori));
        for (Point curPoint : allPoints) {
            if (maxHeap.size() < k) {
                maxHeap.offer(curPoint);
            } else {
                Point top = maxHeap.peek();
                if(getDist(curPoint, ori) - getDist(top, ori) < 0){
                    maxHeap.poll();
                    maxHeap.offer(curPoint);
                }
            }
        }
        while (!maxHeap.isEmpty()) {
            res.add(maxHeap.poll());
        }
        return res;
    }
    private int getDist(Point pointA, Point pointB){
        return (pointA.y - pointB.y)*(pointA.y - pointB.y)
                    + (pointA.x - pointB.x)*(pointA.x - pointB.x);
    }
    //加 comment*************************************************************
    public class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }

    public int findMinNum(String[][] meetTime) {
        //corner case
        if (meetTime == null || meetTime.length == 0 || meetTime[0] == null || meetTime[0].length == 0) return 0;
        //create intervals based on meetTime
        List<Interval> intervalList = new ArrayList<>();
        int idx = 0;
        //traver all meetTime and add into list
        for (String[] meet : meetTime) {
            String start, end;
            if (meet[0].indexOf(':') != -1) {
                start = meet[0].substring(0, meet[0].indexOf(':'))
                        +
                        meet[0].substring(meet[0].indexOf(':') + 1, meet[0].length() - 1);
            } else {
                start = meet[0].substring(0, meet[0].length() - 1) + "00";
            }
            if (meet[1].indexOf(':') != -1) {
                end = meet[1].substring(0, meet[1].indexOf(':'))
                        +
                        meet[1].substring(meet[1].indexOf(':') + 1, meet[1].length() - 1);
            } else {
                end = meet[1].substring(0, meet[1].length() - 1) + "00";
            }
            intervalList.add(new Interval(Integer.parseInt(start), Integer.parseInt(end)));
        }
        return meetingRoom(intervalList);
    }


    public class EndPoint implements Comparable<EndPoint> {
        int val;
        boolean isStart;
        @Override
        public int compareTo(EndPoint that){
            if(this.val != that.val){
                return this.val - that.val;
            }else {
                return this.isStart ? 1 : -1;
            }
        }
        public EndPoint(int val, boolean isStart){
            this.val = val;
            this.isStart = isStart;
        }
    }
    int meetingRoom(List<Interval> meetings){
        //corner case
        List<EndPoint> eps = new ArrayList<>();
        for(Interval m : meetings){
            eps.add(new EndPoint(m.start, true));
            eps.add(new EndPoint(m.end, false));
        }
        //time complexity: O(n log n)
        Collections.sort(eps);
        int poolSize = 0;
        int max = 0;
        for(EndPoint ep: eps){
            if(ep.isStart) poolSize++;
            else poolSize--;
            max = Math.max(max, poolSize);
        }
        return max;
    }

    //加 comment*************************************************************

    public static void main(String[] args){
        System.out.println("HELLO");
    }

}
