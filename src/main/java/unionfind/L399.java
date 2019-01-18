package unionfind;

import java.util.HashMap;
import java.util.Map;

/*
* 坑 1: 对于点的class V:
*       涉及到了除法, 除法的结果用double来存
*       如果定义点是V, 那么点里面parent也要是相同的class
*           class V {
*               V parent
*           }
*        别忘记 V 的 constructor
* 坑 2: 构建 Union-Find的逻辑
*           根据已知的表达式, 建立点, 并把同一个表达式的两个点 连接起来
*           这里用HashMap来保存 表达式的 参数 和 点之间的联系, 方便将来找到
* 坑 3: 计算表达式的逻辑
*           对于表达式里面的第一个和第二个参数, 分别在map里面找对应的点,
*           如果有任何一个点不存在, 则返回-1, 表示无法计算(最好是throw exception)
*           计算的时候, 调用了divide, 这里要注意的是, divide里面的逻辑是: 在实际计算之前, 一定要对v1和v2call一下find, 确保v1和v2都直接指向root

 * */
@SuppressWarnings("Duplicates")
public class L399 {
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        //corner case

        //UnionFind unionFind = new UnionFind();
        Map<String, V> map = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            String equation1st = equations[i][0];
            String equation2nd = equations[i][1];
            double val = values[i];
            if (!map.containsKey(equation1st)) {
                map.put(equation1st, new V(equation1st));
            }
            if (!map.containsKey(equation2nd)) {
                map.put(equation2nd, new V(equation2nd));
            }
            V v1st = map.get(equation1st);
            V v2nd = map.get(equation2nd);
            if (!find(v1st, v2nd)) {
                union(v1st, v2nd, val);
            }
        }
        double[] res = new double[queries.length];
        for (int i = 0; i < queries.length; i++) {
            String par1 = queries[i][0];
            String part2 = queries[i][1];
            if (!map.containsKey(par1) || !map.containsKey(part2)) {
                res[i] = -1.0;
            } else {
                res[i] = divide(map.get(par1), map.get(part2));
            }
        }
        return res;
    }

    //private class UnionFind {
    private class V {
        String id;
        V parent;
        int size;
        double val;

        public V(String id) {
            this.id = id;
            this.parent = this;             //自己指向自己
            this.val = 1.0;                 //v 到 v.parent 的 val 之间的关系
            this.size = 1;
        }
    }

    public boolean find(V root1, V root2) {
        return getRoot(root1) == getRoot(root2);
    }

    public void union(V ver1, V ver2, double verVal) {
        V root1 = getRoot(ver1);
        V root2 = getRoot(ver2);
        if (root1.size > root2.size) {
            root2.parent = root1;
            root1.size += root2.size;
            root2.val = (ver1.val * verVal) / ver2.val;     //这里看everNote的截图, 到时候现推导
        } else {
            root1.parent = root2;
            root2.size += root1.size;
            root1.val = ver2.val / (ver1.val * verVal);     //这里看everNote的截图, 到时候现推导
        }
    }

    private V getRoot(V ver) {
        V cur = ver;
        double d = 1.0;
        while (cur.parent != cur) {         //这里最好用instance来比较cur.parent是否和cur相同
            cur.val *= cur.parent.val;
            d *= cur.val;                   //保留上一次的计算结果
            cur.parent = cur.parent.parent;
            cur = cur.parent;
        }
        ver.parent = cur;
        ver.val = d;                        //因为ver的parent更新了, 所以更新到parent的值
        return cur;
    }

    public double divide(V v1, V v2) {
        if (!find(v1, v2)) {
            return -1.0;
        }
        return v2.val / v1.val;
    }
    //}
}
