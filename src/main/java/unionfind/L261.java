package unionfind;

/*
* 坑 1: 这道题的难点在于如何用Union-Find来: 1.查环; 2:联通区间唯一;
* 坑 2: 前面的corner case注意一下, 就是如果没有边的话, 有一个点的情况下也是树
* 坑 3: 对于每一个村民, 需要三个元素: id, size, parent
* */
@SuppressWarnings("Duplicates")
public class L261 {
    public boolean validTree(int n, int[][] edges) {
        //corner case
        if((edges == null || edges.length == 0) && n <= 1){
            return true;
        }
        if (n <= 0 || edges == null || edges.length == 0 || edges[0] == null || edges[0].length == 0) {
            return false;
        }
        UnionFind unionFind = new UnionFind(n);
        for (int[] edge : edges) {
            if (unionFind.find(edge[0], edge[1])) {
                return false;
            } else {
                unionFind.union(edge[0], edge[1]);
            }
        }
        return unionFind.totalSize == 1;
    }

    class UnionFind {
        int[] vertex;
        int[] parent;
        int[] size;
        int totalSize;

        public UnionFind(int n) {
            this.totalSize = n;
            this.vertex = new int[n];
            this.parent = new int[n];
            this.size = new int[n];
            for (int i = 0; i < n; i++) {
                vertex[i] = i;
                parent[i] = i;
                size[i] = 1;
            }
        }

        private int getRoot(int ver) {
            int cur = ver;
            while (parent[cur] != cur) {
                parent[cur] = parent[parent[cur]];
                cur = parent[cur];
            }
            parent[ver] = cur;
            return cur;
        }

        public boolean find(int ver1, int ver2) {
            return getRoot(ver1) == getRoot(ver2);
        }

        public void union(int ver1, int ver2) {
            int root1 = getRoot(ver1);
            int root2 = getRoot(ver2);
            if (size[root1] > size[root2]) {
                parent[root2] = root1;
                size[root1] += size[root2];
            } else {
                parent[root1] = root2;
                size[root2] += root1;
            }
            totalSize--;
        }
    }
}
