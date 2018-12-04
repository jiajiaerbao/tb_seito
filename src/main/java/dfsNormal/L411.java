package dfsNormal;

import java.util.*;

public class L411 {
    /*
    * 坑 1:
    *
    *
    * */
    private class DictionaryPool {
        public int[] masks;
        public int size;

        public DictionaryPool(int size) {
            this.masks = new int[size];
            this.size = 0;
        }

        public void add(int mask) {
            this.masks[size++] = mask;
        }

        public void remove(int idx) {
            int temp = masks[size - 1];
            masks[size - 1] = masks[idx];
            masks[idx] = temp;
            size--;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }
    }

    private int minLen;
    private int res;

    public String minAbbreviation(String target, String[] dictionary) {
        if (target == null || target.length() == 0) {
            return "";
        }
        if (dictionary == null || dictionary.length == 0) {
            return String.valueOf(target.length());
        }
        int len = target.length();
        int dicSize = dictionary.length;
        DictionaryPool pool = new DictionaryPool(dicSize);

        for (int i = 0; i < dictionary.length; i++) {
            String s = dictionary[i];
            if (s.length() == len) {
                pool.add(getMask(target, s));
            }
        }
        minLen = len;
        res = (1 << len) - 1;
        helper(target, 0, 0, 0, pool);
        return convertMask(target, res);

    }

    private void helper(String target, int idx, int path, int curLen, DictionaryPool pool) {
        if (curLen >= minLen) {
            return;
        }
        int len = target.length();
        if (pool.isEmpty()) {
            if (idx < len) curLen++;
            if (curLen < minLen) {
                minLen = curLen;
                path = path << (len - idx);
                res = path;
            }
            return;
        }
        if (idx >= len) return;
        //add a number
        for (int i = idx; i < len; i++) {
            if (curLen == 0 || (path & 1) == 1) {
                helper(target, i + 1, path << (i + 1 - idx), curLen + 1, pool);

            }
        }
        //add a char
        int i = 0, curSize = pool.size;
        while (i < pool.size) {
            int mask = pool.masks[i];
            int offset = len - 1 - idx;
            if ((mask & (1 << offset)) == 0) {
                pool.remove(i);
            } else {
                i++;
            }
        }
        helper(target, idx + 1, (path << 1) | 1, curLen + 1, pool);
        pool.setSize(curSize);
    }

    private int getMask(String target, String s) {
        int mask = 0, len = target.length();
        for (int i = 0; i < len; i++) {
            mask <<= 1;
            if (s.charAt(i) == target.charAt(i)) {
                mask |= 1;
            }
        }
        return mask;
    }

    private String convertMask(String target, int mask) {
        StringBuilder sb = new StringBuilder();
        int len = target.length();
        int m = 1 << (len - 1), count = 0;
        for (int i = 0; i < len; i++, m = m >> 1) {
            if ((mask & m) == 0) {
                count++;
            } else {
                if (count > 0) sb.append(count);
                count = 0;
                sb.append(target.charAt(i));
            }
        }
        if (count > 0) sb.append(count);
        return sb.toString();
    }

    /*
    * 下面这个解法超时了
    * */
    /*public String minAbbreviation(String target, String[] dictionary) {
        if (target == null || target.length() == 0) {
            return "";
        }
        if (dictionary == null || dictionary.length == 0) {
            return String.valueOf(target.length());
        }
        Set<String> pool = new HashSet<>();
        for (String str : dictionary) {
            helper(str, pool, 0, new StringBuilder(), 0);
        }
        List<String> list = new ArrayList<>();

        helper(target, list, 0, new StringBuilder(), 0);
        Collections.sort(list, (String a, String b) -> a.length() - b.length());

        for(String str: list){
            if(!pool.contains(str)){
                return str;
            }
        }
        return null;
    }

    private void helper(String s, Set<String> set, int idx, StringBuilder path, int lastVal) {
        if (idx == s.length()) {
            int size = path.length();
            if(lastVal > 0){
                path.append(lastVal);
            }
            System.out.println("subRes:" + path.toString());
            set.add(path.toString());
            path.setLength(size);
            return;
        }
        int size = path.length();
        //change
        helper(s, set, idx + 1, path, lastVal + 1);
        //从这道题的角度来讲, 下面这句话可以不用写, 因为上面一句根本没有改变path
        path.setLength(size);
        //keep
        if (lastVal > 0) {
            path.append(lastVal);
        }
        path.append(s.charAt(idx));
        helper(s, set, idx + 1, path, 0);
        path.setLength(size);
    }

    private void helper(String s, List<String> list, int idx, StringBuilder path, int lastVal) {
        if (idx == s.length()) {
            int size = path.length();
            if(lastVal > 0){
                path.append(lastVal);
            }
            System.out.println("subRes:" + path.toString());
            list.add(path.toString());
            path.setLength(size);
            return;
        }
        int size = path.length();
        //change
        helper(s, list, idx + 1, path, lastVal + 1);
        //从这道题的角度来讲, 下面这句话可以不用写, 因为上面一句根本没有改变path
        path.setLength(size);
        //keep
        if (lastVal > 0) {
            path.append(lastVal);
        }
        path.append(s.charAt(idx));
        helper(s, list, idx + 1, path, 0);
        path.setLength(size);
    }*/
}
