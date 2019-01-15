package graph;


import java.util.HashSet;
import java.util.Set;

/*
* 坑 1: 模块化非常重要, 千万不要拘泥于细节, 首先要把思路转换为程序逻辑, 之后再考虑各个细节实现
*       建图 ----> 拓扑排序 ----> 返回一个拓扑排序序列
* 坑 2: 坑BOSS, 这道题 建图 是 难点:
*           建图: 需要跟面试官确认编码方式, 关键点 是 确认 所有字符是否在一定的范围内 并且 连续变化的, 比如 都在ASCII范围内
*           graph ----> 用 Array 或者 ArrayList 来建图, 关键点是 Array或者ArrayList的 index == id - 'a'
*           用 Array: Vertex[] 建图简单些, 因为可以通过 graph[id-'a'] ?= null 来建立并assign一个新节点,
*           用ArrayList的话, 需要事先初始化256个元素, 然后判断id-'a'是否存在, 不存在的话, 用arrayList.set(id-'a', 新节点)来assign新节点
* 坑 3: graph的neighbor, 用Set比较好, 可以防止相同的点被重复加入到neighbor当中
* 坑 4: 因为是用 Array[Vertex] 来建图 graph, 开辟了 连续的 一块空间, 这时 有的点 可能不存在
*       在通过 graph[idx] 访问点 的时候, 需要事先 check graph[idx] 是否为 null
* 坑 5: 反转字符串的写法: new StringBuilder(words[0])).reverse().toString()
* */
@SuppressWarnings("Duplicates")
public class L269 {
    private enum Status {
        INITIAL, PROCESSING, DONE
    }

    private class Vertex {
        private Character id;
        private Set<Character> neighbors;
        private Status status;

        public Vertex(Character id) {
            this.id = id;
            this.neighbors = new HashSet<>();
            this.status = Status.INITIAL;
        }
    }

    public String alienOrder(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }
        if (words.length == 1) {
            return words[0];
        }
        //construct graph
        Vertex[] graph = createGraph(words);
        //get one valid order
        StringBuilder sb = new StringBuilder();
        for (Vertex curV : graph) {
            if (curV != null && hasCycle(curV, graph, sb)) {
                return "";
            }
        }
        return sb.reverse().toString();
    }

    private Vertex[] createGraph(String[] words) {
        char idxBaseChar = 'a';
        Vertex[] graph = new Vertex[256];

        String prevStr = words[0];
        for (int i = 1; i < words.length; i++) {
            String curStr = words[i];
            int prevIdx = 0, curIdx = 0;
            while (prevIdx < prevStr.length() && curIdx < curStr.length()) {
                char prevChar = prevStr.charAt(prevIdx++);
                char curChar = curStr.charAt(curIdx++);
                if (graph[prevChar - idxBaseChar] == null) {
                    graph[prevChar - idxBaseChar] = new Vertex(prevChar);
                }
                if (graph[curChar - idxBaseChar] == null) {
                    graph[curChar - idxBaseChar] = new Vertex(curChar);
                }
                if (prevChar != curChar) {
                    graph[prevChar - idxBaseChar].neighbors.add(curChar);
                    //题目的要求 是 只要在相邻的两个word当中, 找到了 第一对 不同的字母 即可
                    break;
                }
            }
            while (prevIdx < prevStr.length()) {
                char prevChar = prevStr.charAt(prevIdx++);
                if (graph[prevChar - idxBaseChar] == null) {
                    graph[prevChar - idxBaseChar] = new Vertex(prevChar);
                }
            }
            while (curIdx < curStr.length()) {
                char curChar = curStr.charAt(curIdx++);
                if (graph[curChar - idxBaseChar] == null) {
                    graph[curChar - idxBaseChar] = new Vertex(curChar);
                }
            }
            //这里不要忘记了: prevStr 和 curStr 相当于滚动数组, 循环利用, 两行两行地比较
            prevStr = curStr;
        }
        return graph;
    }

    private boolean hasCycle(Vertex curVertexID, Vertex[] graph, StringBuilder sb) {
        char idxBaseChar = 'a';
        Vertex curVertex = graph[curVertexID.id - idxBaseChar];
        if (curVertex.status == Status.PROCESSING) {
            return true;
        }
        if (curVertex.status == Status.DONE) {
            return false;
        }
        curVertex.status = Status.PROCESSING;
        for (Character neighborID : curVertex.neighbors) {
            if (hasCycle(graph[neighborID - idxBaseChar], graph, sb)) {
                return true;
            }
        }
        curVertex.status = Status.DONE;
        sb.append(curVertexID.id);
        return false;
    }

    public static void main(String[] args) {
        String[] words = {"wrt", "wrf", "er", "ett", "rftt"};
        L269 l269 = new L269();
        System.out.println(l269.alienOrder(words));
    }
}
