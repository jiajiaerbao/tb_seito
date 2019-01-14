package graph;

import java.util.ArrayList;
import java.util.List;

/*
* 坑 1: 建立一个 enum class, 用来保存 有限的三种状态: 初始, 访问中, 已经访问结束
* 坑 2: 建立 点的vertex class, 把 val, neighbors, status 都 wrap 进vertex class
* 坑 3: 坑BOSS, 因为 点的ID 是连续的, 所以可以用 ArrayList 或者 直接用 Array,
*       这里 ArrayList 或者 Array 的 index 跟 该 index 保存的 vertex.id 是同一个值(ID)
* 坑 4: 最大的问题是, 当你把 neighbors wrap进 vertex 之后, 怎么表示图?
*       这里 vertex.neighbor 只是保存所有 neighbor 的 ID, 通过 graph.get(ID) 来获取实际的 neighbor 点
* 坑 5: 这道题的特点是 Vertex的ID 是连续的 (0~numCourses之间), 所以可以用 Array 或者 ArrayList
* 坑 6: 查环的 hasCycle 里面, 千万不要忘记加上:
*           cur.status = Status.PROCESSING
*           ....(处理neighbors)....
*           cur.status = Status.DON
* */
@SuppressWarnings("Duplicates")
public class L207 {
    private enum Status {
        INITIAL, PROCESSING, DONE;
    }

    private static class Vertex {
        private int id;
        private List<Integer> neighbors; // 这里存的neighbor的ID
        private Status status;

        public Vertex(int id) {
            this.id = id;
            this.status = Status.INITIAL;
            this.neighbors = new ArrayList<>();
        }
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //corner case
        if (prerequisites == null || prerequisites.length == 0 ||
                prerequisites[0] == null || prerequisites[0].length == 0) {
            return true;
        }
        //construct graph
        List<Vertex> graph = new ArrayList<>();
        //这里的 i 就是 每个vertex的ID
        for (int i = 0; i < numCourses; i++) {
            graph.add(new Vertex(i));
        }
        for (int[] entry : prerequisites) {
            graph.get(entry[1]).neighbors.add(entry[0]);
        }
        //check circle
        for (Vertex curVertex : graph) {
            if (hasCycle(curVertex, graph)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasCycle(Vertex cur, List<Vertex> graph) {
        if (cur.status == Status.PROCESSING) {
            return true;
        }
        if (cur.status == Status.DONE) {
            return false;
        }
        cur.status = Status.PROCESSING;
        for (int next : cur.neighbors) {
            if (hasCycle(graph.get(next), graph)) {
                return true;
            }
        }
        cur.status = Status.DONE;
        return false;
    }
}
