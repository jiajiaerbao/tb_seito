package dfsPruning;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("Duplicates")
public class L291 {
    /*
    * 坑 0: 坑BOSS: 不读题, 对题目要求不理解的情况下, 想当然地去解题:
    *       Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty substring in str
    *       这里已经明确说明了是一个字符对应一个字符串, 并且是一一对应的关系
    * 坑 1: 你的切入点不对, 对于pattern, 你最原始的想法是想找到each individual pattern的string是什么样子的,
    *       而正确的切入点是 找到each char in one individual pattern所对应的字符串
    * 坑 2: base case: 成功: s和p的index都恰好走到尽头; 失败: 如果一个走到头, 另外一个没有走到头
    * 坑 3: HashMap<Character, String>(): pattern的每个字符在str里所对应的字符串
    *           如果在pattern里面碰到了相等的重复的字符
    *               并且str里面的下一个等长的字符串(根据HashMap里存的该字符的字符串长度来比较)
    *               跟HashMap里面的一模一样, 并且后续的判断都通过的话, OK
    * 坑 4: 如果是第一次碰到该字符(HashMap里面没有)
    *           找出该字符所对应的最长可能的长度, 然后for loop的一个一个地试
    *           HashSet的作用是把某个字符所对应的字符串保存起来(不同字符对应的字符串不一样), 防止一模一样的字符串对应pattern里面的而不同字符
    *           最后不要忘记setback
    * 坑 5: maxLength: 求pattern里面某个字符所对应的最大可能长度:
    *           对于pattern里面的某个在位置idx的字符, count它在idx之后总共出现了多少次
    *           如果idx之后出现了其他字符并且已经存在于HashMap里面, 则从str的剩余长度里面减掉
    * 坑 6: 每一次得到一个新的index的时候, 用之前都不要忘记check边界:
    *           if (idxS + lenCurP > s.length())
    *           if (maxLenCurP < 1)
    * 坑 7: 在求pattern的, idxP位置上的陌生字符, 可能对应的, str里面的字符串的最大长度的时候:
    *           依次遍历pattern的idxP和其之后的所有字符
    *           如果已经出现在了map里面, 从str的idxS之后的剩余长度里面减去map里面对应的字符串的长度
    *           如果 map 里面没有出现过, 则减去 1, 也就是默认最短是 1 个字符的长度
    * */

    /*************************************下面是自己写的****************************************************************/
    public boolean wordPatternMatch(String pattern, String str) {
        return dfs(pattern, 0, str, 0, new HashMap<Character, String>());
    }

    private boolean dfs(String p, int idxP, String s, int idxS, Map<Character, String> map) {
        //base case: 只有当 idxP 和 idxS 同时都走到尽头的时候, 才算匹配成功
        if (idxP == p.length() && idxS == s.length()) {
            return true;
        }
        //base case: 如果一个走到尽头, 而另外一个没有走到尽头的话, 匹配失败
        if (idxP >= p.length() || idxS > s.length()) {
            return false;
        }
        //取出pattern里面的当前字符curP
        char curP = p.charAt(idxP);
        if (map.containsKey(curP)) {
            //如果已经匹配过(存在于map), 如果str里面接下来相同长度的字符串跟存在于map里面的匹配好的字符串相同的话, 则继续向后面匹配
            //如果后面的匹配(下一层dfs以及之后的dfs)都成功的话, 返回false
            return map.get(curP).equals(s.substring(idxS, idxS + map.get(curP).length())) &&
                    dfs(p, idxP + 1, s, idxS + map.get(curP).length(), map);
        } else {
            int maxLenCurP = getMaxLength(p, idxP, s, idxS, map);
            //千万不要忘记检查边界
            if (idxS + maxLenCurP > s.length()) {
                return false;
            }
            //对于pattern的idxP上的字符, 在str里面所能对应的最大字符串长度maxLenCurP来说, 要一个一个地用dfs来试
            for (int k = 1; k <= maxLenCurP; k++) {
                String temp = s.substring(idxS, idxS + k);
                //这里是体现了a bijection between a letter in pattern and a non-empty substring in str:
                //一个字符和一个字符串是一一对应的关系:
                //如果map里面已经存在str里面的某个子字符串的话, 则跳过
                if (map.values().contains(temp)) {
                    continue;
                }
                //用dfs一个一个地试
                map.put(curP, temp);
                if (dfs(p, idxP + 1, s, idxS + k, map)) {
                    return true;
                }
                map.remove(curP);
            }
        }
        return false;
    }
    //这个函数还不是太理解: 同时遍历pattern(从idxP开始)和str(idxS开始), 初始的curP的所对应的字符串的最大长度是s.length-idxS
    //          遍历pattern, 如果碰到了相同的curP, count加1(count的初始值是1, 因为需要包括idxP上的curP)
    //          每遍历pattern的一个字符, 需要从s.length-idxS减去所对应的长度, 如果已经匹配好的字符, 则直接减去匹配好的字符串的长度即可(map.get(curP))
    //          如果没有匹配过, 则减去 最小的匹配长度1
    private int getMaxLength(String p, int idxP, String s, int idxS, Map<Character, String> map) {
        char curP = p.charAt(idxP);
        int cntCurP = 1;
        int remainLenS = s.length() - idxS;
        for (int k = idxP + 1; k < p.length(); k++) {
            char temp = p.charAt(k);
            if (temp == curP) {
                cntCurP++;
            } else {
                remainLenS -= map.containsKey(curP) ? map.get(curP).length() : 1;
            }
        }
        return (int) (remainLenS / cntCurP);
    }


    /*************************************下面是默写的第一遍*************************************************************/


    public boolean wordPatternMatch_recite(String pattern, String str) {
        return dfs_recite(pattern, 0, str, 0, new HashMap<Character, String>(), new HashSet<>());
    }

    private boolean dfs_recite(String p, int idxP, String s, int idxS, Map<Character, String> map, Set<String> set) {
        //base case
        if (idxP == p.length() && idxS == s.length()) {
            return true;
        }
        if (idxP >= p.length() || idxS >= s.length()) {
            return false;
        }
        char curP = p.charAt(idxP);
        if (map.containsKey(curP)) {
            int lenCurP = map.get(curP).length();
            //check whether out of bound
            if (idxS + lenCurP > s.length()) {
                return false;
            }
            return map.get(curP).equals(s.substring(idxS, idxS + lenCurP)) && dfs_recite(p, idxP + 1, s, idxS + lenCurP, map, set);
        } else {
            int maxLenCurP = getMaxLength_recite(p, idxP, s, idxS, map);
            //用之前, 不要忘记check边界
            if (maxLenCurP < 1) {
                return false;
            }
            for (int k = 1; k <= maxLenCurP; k++) {
                String subStr = s.substring(idxS, idxS + k);
                //用来保存在当前层dfs之前的, pattern的idxP之前所有的字符, 已经在str里面找到的字符串
                //简单点就是HashMap.ValueSet
                if(set.contains(subStr)){
                    continue;
                }
                map.put(curP, subStr);
                set.add(subStr);
                if (dfs_recite(p, idxP + 1, s, idxS + k, map, set)) {
                    return true;
                }
                map.remove(curP);
                set.remove(subStr);
            }
        }
        return false;
    }

    private int getMaxLength_recite(String p, int idxP, String s, int idxS, Map<Character, String> map) {
        //这里的count的初始值是1, 因为已经在idxP的地方出现过了
        int count = 1;
        char curP = p.charAt(idxP);
        int remainLenS = s.length() - idxS;
        for (int i = idxP + 1; i < p.length(); i++) {
            if (p.charAt(i) == curP) {
                count++;
            } else {
                //这里的逻辑不清晰: 如果已经在map里面存在的话, 减去map里面所对应的字符串的长度, 否则当做patter的陌生字符, 减去1
                remainLenS -= map.containsKey(p.charAt(i)) ? map.get(p.charAt(i)).length() : 1;
            }
        }
        //两面都要加括号
        return (int) (remainLenS / count);
    }

    /*************************************下面是抄的答案*************************************************************/

    public boolean wordPatternMatch_CopiedAnswer(String pattern, String str) {
        return dfs_CopiedAnswer(pattern, 0, str, 0, new HashMap<Character, String>(), new HashSet<String>());
    }

    private boolean dfs_CopiedAnswer(String p, int idxP, String s, int idxS, HashMap<Character, String> map, HashSet<String> set) {
        int lenP = p.length();
        int lenS = s.length();

        if (idxP == lenP && idxS == lenS) {
            return true;
        }
        if (idxP >= lenP || idxS >= lenS) {
            return false;
        }

        char cur = p.charAt(idxP);
        if (map.containsKey(cur)) {
            int curLen = map.get(cur).length();
            if (idxS + curLen > lenS) {
                return false;
            }
            String target = s.substring(idxS, idxS + curLen);
            return target.equals(map.get(cur)) && dfs_CopiedAnswer(p, idxP + 1, s, curLen + idxS, map, set);
        } else {
            int maxLen = maxLength_CopiedAnswer(p, idxP, s, idxS, map);
            if (maxLen < 1) {
                return false;
            }
            for (int k = 1; k <= maxLen; k++) {
                String curStr = s.substring(idxS, idxS + k);
                if (set.contains(curStr)) {
                    continue;
                }
                map.put(cur, curStr);
                set.add(curStr);
                if (dfs_CopiedAnswer(p, idxP + 1, s, idxS + k, map, set)) {
                    return true;
                }
                map.remove(cur);
                set.remove(curStr);
            }
        }
        return false;
    }

    private int maxLength_CopiedAnswer(String p, int idxP, String s, int idxS, HashMap<Character, String> map) {
        int lenP = p.length();
        int lenS = s.length();
        int leftLenS = lenS - idxS;
        int count = 1;
        char thisChar = p.charAt(idxP);
        for (int i = idxP + 1; i < lenP; i++) {
            char c = p.charAt(i);
            if (c == thisChar) {
                count++;
            } else {
                leftLenS -= map.containsKey(c) ? map.get(c).length() : 1;
            }
        }
        return (int) (leftLenS / count);
    }
}
