package graph;

import java.util.ArrayList;
import java.util.List;

/*
* 坑 1: 生成拓扑排序的时候, 在 index = 0 的位置加入新的元素
* 坑 2: 这里的corner case不用加, 否则会出错; 如果出现环了的话, 结果集是空, 返回[], 也就是new int[0]
* 坑 3: 最后要求返回的是 int[], 也就是 vertex ID 的一个array, 用 ArrayList<Integer> 很难直接转换成 int[], 直接用for loop逐一赋值算了
* */
@SuppressWarnings("Duplicates")
public class L210 {
    private enum Status {
        INITIAL, PROCESSING, DONE;
    }

    private class Vertex {
        private int id;
        private List<Integer> neighborsID;
        private Status status;

        public Vertex(int id) {
            this.id = id;
            this.neighborsID = new ArrayList<>();
            this.status = Status.INITIAL;
        }
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        /*if (prerequisites == null || prerequisites.length == 0 ||
                prerequisites[0] == null || prerequisites[0].length == 0 ||
                numCourses <= 0) {
            return new int[]{0};
        }*/
        List<Integer> res = new ArrayList<>();
        List<Vertex> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new Vertex(i));
        }
        for (int[] entry : prerequisites) {
            graph.get(entry[1]).neighborsID.add(entry[0]);
        }
        for (int vertexID = 0; vertexID < graph.size(); vertexID++) {
            if (hasCycle(vertexID, graph, res)) {
                return new int[0];
            }
        }
        int[] resArr = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            resArr[i] = res.get(i);
        }
        return resArr;
    }

    private boolean hasCycle(int curVertexID, List<Vertex> graph, List<Integer> res) {
        if (graph.get(curVertexID).status == Status.PROCESSING) {
            return true;
        }
        if (graph.get(curVertexID).status == Status.DONE) {
            return false;
        }
        graph.get(curVertexID).status = Status.PROCESSING;
        for (int neighborID : graph.get(curVertexID).neighborsID) {
            if (hasCycle(neighborID, graph, res)) {
                return true;
            }
        }
        graph.get(curVertexID).status = Status.DONE;
        res.add(0, curVertexID);
        return false;
    }
}
