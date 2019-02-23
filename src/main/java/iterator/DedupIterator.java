package iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/*
* 题目要求:
*       把连续相等的数字 deduplicate (去掉重复)
*       连续数据的大小关系是无序的
*
* */
@SuppressWarnings("Duplicates")
public class DedupIterator {
    /******************************第一遍********************************************/
    /*
    * 坑 1: 完完全全抄的答案, 还不是很理解
    * 坑 2: 坑 BOSS:
    *           check后面的:
    *               每一次都是拿当前的元素(cur)和后一个的元素(.next())进行比较, 如果不同则输出, 相同则跳过后面的相同的元素
    * 坑 3: next()的逻辑:
    *           得到下一个并放入cur, 如果next里面有, 则从next里取, 否则从.next()里面取
    *           更新next, 如果.next()和cur相同, 则跳过, 直到找到第一个跟cur不同的元素, 把该元素放入next中
    * */
    public class FirstTime{
        public class MyDedupIteratorIterator<Integer> implements Iterator<Integer>{
            private Iterator<Integer> iterator;
            private Integer next;
            public MyDedupIteratorIterator(List<Integer> list){
                this.iterator = list.iterator();
                this.next = null;
            }
            @Override
            public boolean hasNext(){
                return iterator.hasNext() || next != null;
            }
            @Override
            public Integer next(){
                //把要输出的值存放到cur里面: 如果next里面有, 则直接从next里面取; 如果next里面没有, 则直接从.next()里面取
                Integer cur = null;
                if(next != null){
                    cur = next;
                    next = null;
                }else {
                    cur = iterator.next();
                }
                //更新next, 这里把.next()跟cur进行比较
                while (iterator.hasNext()){
                    Integer val = iterator.next();
                    if(val != cur){
                        next = val;
                        break;
                    }
                }
                return cur;
            }
        }
    }
}
