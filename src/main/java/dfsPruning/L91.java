package dfsPruning;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("Duplicates")
public class L91 {


    /*
    * 坑 1: 这道题应该用 dp 来解, 而你勉强用DFS来解, 超时了, 但是又不会写Pruning
    * 坑 2: DFS超时了, Pruning写不出来, 估计Pruning应该是 前面N位数字 所对应的状态的个数
    * */

    /*
    DFS超时了, Pruning写不出来, 估计Pruning应该是 前面N位数字 所对应的状态的个数
    public int numDecodings_failure(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        Map<Integer, Character> map = new HashMap<>();
        int i = 1;
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            map.put(i++, ch);
        }
        int[] res = new int[1];
        dfs_failure(s, 0, new StringBuilder(), map, new HashSet<>(), res);
        return res[0];
    }

    private void dfs_failure(String s, int idx, StringBuilder path, Map<Integer, Character> map, Set<String> set, int[] res) {
        int len = s.length();
        //System.out.println(path.toString());
        if (idx == len) {
            String str = path.toString();
            if (!set.contains(str)) {
                //System.out.println("==" + str);
                set.add(str);
                res[0]++;
            }
            return;
        }
        int size = path.length();
        for (int i = 1; i < 3; i++) {
            if (idx + i <= len) {
                int val = Integer.valueOf(s.substring(idx, idx + i));
                if (val == 0) {
                    break;
                }
                if (val >= 1 && val <= 26) {
                    path.append(map.get(val));
                    dfs_failure(s, idx + i, path, map, set, res);
                    path.setLength(size);
                }
            }
        }
    }
    */
}
