package interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

@SuppressWarnings("Duplicates")
public class L218 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 这道题基本上就是抄的
    * 坑 2: 具体的分析和解释看everNote, 这里只是记录写code时候, 容易出现的错误
    *       把 interval 打断, 拆分成 起点 和 终点
    *       在compareTo里面加入break tie的逻辑:
    *           都是起点, 高的在前
    *           都是终点, 低的在前
    *           一个是起点一个是终点, 起点在前
    *       建立一个maxHeap, 用来保存当前的最高距离
    *       对于把interval拆分之后的所有起点和终点, 遍历所有的2n个点, 要分别考虑
    *           cur是起点: 加入到maxHeap, 如果该起点是第一个起点或者目前最高的起点, 则画(加入result list),
    *           cur是终点: 从maxHeap移除, 如果移除的是最高点, 画图(如果是最后一个点, 也画图)
    * */
    class SecondTime{
        private class MyEndPoint implements Comparable<MyEndPoint> {
            public int val;
            public int height;
            public boolean isEnd;

            public MyEndPoint(int val, int height, boolean isEnd) {
                this.val = val;
                this.height = height;
                this.isEnd = isEnd;
            }

            @Override
            public int compareTo(MyEndPoint that) {
                if (this.val != that.val) {
                    return this.val - that.val;
                } else {
                    //下面的逻辑是通过画图分析出来的
                    if (!this.isEnd && !that.isEnd) {
                        return that.height - this.height;
                    } else if (this.isEnd && that.isEnd) {
                        return this.height - that.height;
                    } else {
                        if (!this.isEnd) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                }
            }
        }

        public List<int[]> getSkyline(int[][] buildings) {
            List<int[]> res = new ArrayList<>();
            //corner case

            List<MyEndPoint> list = new ArrayList<>();

            for (int[] building : buildings) {
                list.add(new MyEndPoint(building[0], building[2], false));
                list.add(new MyEndPoint(building[1], building[2], true));
            }
            //这里容易忘记
            Collections.sort(list);
            //这里容易忘记
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>((i1, i2) -> i2 - i1);

            for (MyEndPoint myEndPoint : list) {
                //这里是分情况考虑
                if (!myEndPoint.isEnd) {
                    if (maxHeap.isEmpty() || myEndPoint.height > maxHeap.peek()) {
                        res.add(new int[]{myEndPoint.val, myEndPoint.height});
                    }
                    maxHeap.offer(myEndPoint.height);
                } else {
                    maxHeap.remove(myEndPoint.height);
                    if (maxHeap.isEmpty() || maxHeap.peek() < myEndPoint.height) {
                        res.add(new int[]{myEndPoint.val, maxHeap.isEmpty() ? 0 : maxHeap.peek()});
                    }
                }
            }
            return res;
        }
    }
    /******************************第一遍********************************************/
    class FirstTime{
        private class EndPoint implements Comparable<EndPoint> {
            int val;
            int height;
            boolean isEnd;
            public EndPoint(int val, int height, boolean isEnd) {
                this.val = val;
                this.height = height;
                this.isEnd = isEnd;
            }
            @Override
            public int compareTo(EndPoint that) {
                if (this.val != that.val) {
                    return this.val - that.val;
                } else {
                    if (!this.isEnd && !that.isEnd) {         //当都是左边界的时候, 高的come first
                        return that.height - this.height;
                    } else if (this.isEnd && that.isEnd) {    //当都是右边界的时候, 低的come first
                        return this.height - that.height;
                    } else {                                 //当一个是左边界, 一个是右边界的时候, 左边界的come first
                        if (!this.isEnd) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                }
            }
        }
        public List<int[]> getSkyline(int[][] buildings) {
            List<int[]> res = new ArrayList<>();
            if (buildings == null || buildings.length == 0) {
                return res;
            }
            List<EndPoint> list = new ArrayList<>();
            for (int[] building : buildings) {
                list.add(new EndPoint(building[0], building[2], false));
                list.add(new EndPoint(building[1], building[2], true));
            }
            Collections.sort(list);
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>((i1, i2) -> i2 - i1);
            for (EndPoint endPoint : list) {
                if (!endPoint.isEnd) {
                    if (maxHeap.isEmpty() || endPoint.height > maxHeap.peek()) {
                        res.add(new int[]{endPoint.val, endPoint.height});
                    }
                    maxHeap.offer(endPoint.height);
                } else {
                    maxHeap.remove(endPoint.height);
                    if (maxHeap.isEmpty() || maxHeap.peek() < endPoint.height) {
                        res.add(new int[]{endPoint.val, maxHeap.isEmpty() ? 0 : maxHeap.peek()});
                    }
                }
            }
            return res;
        }
    }
}
