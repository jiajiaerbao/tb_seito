package aoa;

import tree.TreeNode;

import java.lang.reflect.Array;
import java.util.*;

@SuppressWarnings("Duplicates")
public class Amazon {
    /*
     * Explain:
     * The logical of my solutions is to use PriorityQueue and keep its size no more than numSteakhouses, numSteakhouses is the size from requirement.
     * In order to keep nearest numSteakhouses locations, the comparator of PriorityQueue was overridden, and used as a maxHeap.
     * Iterating all locations from allLocations and insert it into maxHeap, if there are more than numSteakhouses elements, then the largest(furthest) location will be removed.
     * At last, populate all elements at maxHeap from top and insert it into the beginning of result list in order to keep ascending order.
     * Time complexity: O(n log k), where n is size of allLocations, and k is numSteakhouses
     * Space complexity: O(k), where k is numSteakhouses used by maxHeap
     * */
    /*
    * Created by Patel on 01/11/2019
    * @Return nearest numSteakhouses locations
    * */
    List<List<Integer>> nearestXsteakHouses(
            int totalSteakhouses,
            List<List<Integer>> allLocations,
            int numSteakhouses) {
        //corner case
        if (totalSteakhouses < 0 || numSteakhouses < 0 || totalSteakhouses < numSteakhouses
                || allLocations == null || allLocations.size() == 0) {
            throw new IllegalArgumentException("nearestXsteakHouses: invalid input");
        }
        //maxHeap to save all locations based on their distances
        PriorityQueue<List<Integer>> pq = new PriorityQueue<>((o1, o2) ->
                o2.get(0) * o2.get(0) + o2.get(1) * o2.get(1) - o1.get(0) * o1.get(0) - o1.get(1) * o1.get(1)
        );

        List<List<Integer>> res = new ArrayList<>();

        //keep the maxHeap size no more than numSteakhouses,
        //and always keep nearest numSteakhouses locations in the maxHeap
        for (List<Integer> location : allLocations) {
            //corner case: check whether each location has 2 elements
            if (location == null || location.size() != 2) {
                throw new IllegalArgumentException("invalid input: allLocations");
            }
            pq.offer(location);
            //keep maxHeap size no more than numSteakhouses
            if (pq.size() > numSteakhouses) {
                pq.poll();
            }
        }
        //populate into result
        while (!pq.isEmpty()) {
            res.add(0, pq.poll());
        }
        return res;
    }

    /****************************************************************************************************************************************************************************************************************************/

    /*
    * Explain:
    * The logical of my solution is using treeMap to store the list with less elements in foregroundAppList and backgroundAppList, assuming the two lists have different counts of Apps.
    * Traverse the list with less elements and populate it into treeMap, key is memory usage of each APP, value is ID of each APP: <AppMemory, AppID>. The time complexity of this process is O(m log m), space complexity is O(m)
    * Traverse the list with more elements, for current memory usage of each APP in the list with more elements, calculating the remain memory which is the difference between deviceCapacity and current memory usage.
    * Utilizing the floorKey function in treeMap to get the maximum available App memory in the treeMap which is less than or equal to remain memory.
    * If maximum available App memory exists in the treeMap, then compare with current maximumUsage, if large than current maximumUsage, then update maximumUsage using the current combination and also update result set.
    * The total time complexity is Max(O(m log n), O(n log n)), which n is size with less elements in the two lists, and m is the size with more elements in the two lists
    * The total space complexity is O(n), which n is size with less elements in the two lists
    * */
    /*
     * Created by Patel on 01/11/2019
     * @Return maximum usage combination
     * */
    List<List<Integer>> optimalUtilization(
            int deviceCapacity,
            List<List<Integer>> foregroundAppList,
            List<List<Integer>> backgroundAppList
    ) {
        //corner case
        if (deviceCapacity < 0 || foregroundAppList == null || backgroundAppList == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        List<List<Integer>> res = new ArrayList<>();
        //assign two reference to list with more elements in two lists, and list with less elements in two lists
        List<List<Integer>> listOfMoreItems;
        List<List<Integer>> listWithLessItems;
        if (foregroundAppList.size() < backgroundAppList.size()) {
            listOfMoreItems = backgroundAppList;
            listWithLessItems = foregroundAppList;
        } else {
            listOfMoreItems = foregroundAppList;
            listWithLessItems = backgroundAppList;
        }
        //populate list with lees elements into treeMap, key is capacity of each APP, value is its ID.
        TreeMap<Integer, List<Integer>> treeMapOfLessItems = new TreeMap<>();
        for (List<Integer> list : listWithLessItems) {
            List<Integer> curList = treeMapOfLessItems.getOrDefault(list.get(1), new ArrayList<>());
            curList.add(list.get(0));
            treeMapOfLessItems.put(list.get(1), curList);
        }

        int maximumUsage = -1;
        //iterate list with more elements, for the difference between deviceCapacity and each App memory in the list with more elements
        //if the current combination consume more memory than current maximumUsage and less or equal to deviceCapacity, then update maximumUsage and result set
        for (List<Integer> list : listOfMoreItems) {
            int curMemory = list.get(1);
            Integer restMemory = treeMapOfLessItems.floorKey(deviceCapacity - curMemory);
            if (restMemory == null) {
                continue;
            }
            if (curMemory + restMemory > maximumUsage) {
                maximumUsage = curMemory + restMemory;
                res.clear();
                for (int cur : treeMapOfLessItems.get(restMemory)) {
                    res.add(Arrays.asList(cur, list.get(0)));
                }
            }
        }
        return res;
    }

    /****************************************************************************************************************************************************************************************************************************/

    public static int[] sortArray(int arr[]) {
        int len = arr.length;
        int temp;
        for (int i = 0; i <= len - 1; i++) {
            for (int j = i; j < len; j++) {
                if (arr[i] < arr[j]) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    /****************************************************************************************************************************************************************************************************************************/

    public static int countOccurrence(int arr[], int value) {
        int i = 0, count = 0, len = arr.length;
        while (i < len) {
            if (arr[i] == value) {
                count += 1;
            }
            i++;
        }
        return count;
    }

    /****************************************************************************************************************************************************************************************************************************/

    public static void printPattern(int n) {
        int i, j, print = 1;
        for (i = 1; i <= n; i++) {
            for (j = 1; j <= 2 * i; j++) {
                System.out.print(print);
            }
            System.out.println();
        }
    }

    /****************************************************************************************************************************************************************************************************************************/
    /*
     * Explain:
     * The logic of my solution is to create two paths to record the trace from root to node 1 and node 2, when constructing BST.
     * The logic of constructing BST is to traverse the left node or right node while comparing the current value in the array to the current node,
     * if lees than current node, go to left subTree, otherwise go to right subTree. If there is no more left subTree or right subTree,
     * then create a new tree node based on current value and add the new tree node to the BST, and also add this node to the path at the same time.
     * After traversing all values in the array, If there is no such node of node1 or node2 in the BST, the size of path1 or path2 will be zero, then return -1;
     * The time complexity is O(n), which n is the size of array.
     * The space complexity is O(n), which n is the size of array.
     * */
    /*
     * Created by Patel on 01/11/2019
     * @Return maximum usage combination
     * */
    public int bstDistance(int[] values, int n, int node1, int node2) {
        if (values == null || values.length == 0 || n == 0 || node1 == node2) {
            return 0;
        }
        List<TreeNode> path1 = new ArrayList<>();
        List<TreeNode> path2 = new ArrayList<>();

        TreeNode root = new TreeNode(values[0]);
        if (root.val == node1) {
            path1.add(root);
        }
        if (root.val == node2) {
            path2.add(root);
        }
        for (int i = 1; i < n; i++) {
            if (values[i] == node1) {
                insertBST(root, node1, path1);
                if (path2.size() != 0) {
                    break;
                }
            } else if (values[i] == node2) {
                insertBST(root, node2, path2);
                if (path1.size() != 0) {
                    break;
                }
            } else {
                insertBST(root, values[i], null);
            }
        }
        if (path1.size() == 0 || path2.size() == 0) {
            return -1;
        }
        int commonPath = Math.min(path1.size(), path2.size()) ;//- 1;

        for (int i = 0; i < Math.min(path1.size(), path2.size()); i++) {
            if (path1.get(i) != path2.get(i)) {
                commonPath = i ;//- 1;
                break;
            }
        }
        return path1.size() + path2.size() - 2 - 2 * (commonPath - 1);//2 * commonPath;
    }

    public static void insertBST(TreeNode root, int val, List<TreeNode> path) {
        addNodeToPath(root, path);
        if (root.val > val) {
            if (root.left != null) {
                insertBST(root.left, val, path);
            } else {
                root.left = new TreeNode(val);
                addNodeToPath(root.left, path);
            }
        } else {
            if (root.right != null) {
                insertBST(root.right, val, path);
            } else {
                root.right = new TreeNode(val);
                addNodeToPath(root.right, path);
            }
        }
    }

    public static void addNodeToPath(TreeNode node, List<TreeNode> path) {
        if (path != null) {
            path.add(node);
        }
    }

    /****************************************************************************************************************************************************************************************************************************/
    /*
     * Explain:
     * As the possible characters of writing string have limited possibilities,
     * so I created a ASCII size integer array to count how many times of each character appearing in the writing String.
     * And using a repeatCnt variable to save total duplicate characters until now, as if there are more than two times of one character appearing in num size subString,
     * the repeatCnt will be more than zero, and this subString is not a qualified subString.
     * First step is to traverse the first num size characters, and save appearing times in the ASCII array, and save total duplicate characters until now to repeatCnt.
     * If repeatCnt is more than zero, then skip current subString, move forward to the next num size subString, using for loop to repeat the process above.
     * The time complexity is O(n).
     * The space complexity is O(1).
     * */
    /*
     * Created by Patel on 01/11/2019
     * @Return maximum usage combination
     * */
    public List<String> subStringsKDist(String inputStr, int num) {
        if (inputStr == null || inputStr.length() == 0 || num < 0 || inputStr.length() < num) {
            return new ArrayList<>();
        }
        Set<String> res = new HashSet<>();
        int[] charsCnt = new int[256];
        int repeatCnt = 0;
        char[] chars = inputStr.toCharArray();
        for (int i = 0; i < num; i++) {
            charsCnt[chars[i] - 'a']++;
            if (charsCnt[chars[i] - 'a'] > 1) {
                repeatCnt++;
            }
        }
        if (repeatCnt == 0) {
            res.add(inputStr.substring(0, num));
        }
        for (int i = num; i < inputStr.length(); i++) {
            charsCnt[chars[i] - 'a']++;
            if (charsCnt[chars[i] - 'a'] > 1) {
                repeatCnt++;
            }
            charsCnt[chars[i - num] - 'a']--;
            if (charsCnt[chars[i - num] - 'a'] >= 1) {
                repeatCnt--;
            }
            if (repeatCnt == 0) {
                res.add(inputStr.substring(i - num + 1, i + 1));
            }
        }
        return new ArrayList<>(res);
    }

    /****************************************************************************************************************************************************************************************************************************/
    /*
     * Explain:
     * As the start point of delivery is from the top left cell, and destination is in the grid. So I chose breadth first search to traverse the whole grid in order to find the destination.
     * Using 2d boolean array to memorize visited cells to avoid re-visit cells which have been visited before.
     * Using the variable miniDistance to count how many steps needed to reach the destination.
     * As there are three types of cells, one is 0 which means accessible, nine is destination and one is obstacle,
     * from the top left point, put all accessible neighbor cells into the queue and increase miniDistance by one.
     * Next step is pop up all cells currently in the queue in on loop and put all accessible neighbor cells into the queue in order to traverse in next loop.
     * After traversed all cells in the grid, there is not destination cell then return -1.
     * The time complexity is O(numRow * numColumns).
     * The space complexity is O(numRow + numColumns).
     * */
    /*
     * Created by Patel on 01/11/2019
     * @Return maximum usage combination
     * */
    int minimumDistance(int numRow, int numColumns, List<List<Integer>> area) {
        //corner case
        if (numRow <= 0 || numColumns <= 0 || area == null || area.size() == 0) {
            return -1;
        }
        int minDistance = 1;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<int[]> queue = new LinkedList<>();
        //Set<int[]> visited = new HashSet<>();
        boolean[][] visited = new boolean[numRow][numColumns];
        int i = 0;
        int j = 0;
        int[] startPt = {area.get(i).get(0), area.get(i).get(1)};
        if (area.get(startPt[0]).get(startPt[1]) == 9) {
            return minDistance;
        }
        queue.offer(new int[]{area.get(i).get(0), area.get(i).get(1)});
        //visited.add(startPt);
        visited[area.get(i).get(0)][area.get(i).get(1)] = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            minDistance++;
            while (size-- > 0) {
                int[] cur = queue.poll();
                for (int[] dir : dirs) {
                    int nextI = cur[0] + dir[0];
                    int nextJ = cur[1] + dir[1];
                    if (nextI >= 0 && nextI < numRow && nextJ >= 0 && nextJ <= numColumns && !visited[nextI][nextJ]) {//&& !visited.contains(new int[]{nextI, nextJ})) {
                        if (area.get(nextI).get(nextJ) == 9) {
                            return minDistance;
                        } else if (area.get(nextI).get(nextJ) == 1) {
                            queue.offer(new int[]{nextI, nextJ});
                            //visited.add(new int[]{nextI, nextJ});
                            visited[nextI][nextJ] = true;
                        }
                    }
                }
            }
        }
        return -1;
    }

    /****************************************************************************************************************************************************************************************************************************/
    /*
     * Explain:
     * I am using sliding window and deque to solve this problem.
     * Using deque to save index of temp arrayList, since using arrayDeque, so the original order of the index will be kept in the deque.
     * Using for loop to traverse each element in the temp arrayList, and within the for loop, there are two while loops:
     * The first while is to check whether the least index (also with the lease value in current sliding window) in the deque is out of range or not,
     * which means if the least index is out of sliding window size, then delete it from the deque.
     * The second while loop is to check whether the new coming value is less than the current last value in the sliding window, if so then delete if from deque.
     * After executing the second while, put it to the last position in the deque, and the first element in the deque is guaranteed the least value's index in the sliding window.
     * Put the index of current least value into result list.
     * The time complexity is amortized O(1), as for each element in the temp arrayList, can only enter into deque and go out of deque once.
     * The space complexity is O(k), k is windowSiz
     * */
    /*
     * Created by Patel on 01/11/2019
     * @Return maximum usage combination
     * */
    public static ArrayList<Integer> calculateWindowMin(int numOfDays, ArrayList<Integer> temp, int windowSize) {
        if (temp == null || temp.size() == 0 || numOfDays <= 0 || windowSize <= 0) {
            return new ArrayList<>();
        }
        ArrayList<Integer> res = new ArrayList<>();
        Deque<Integer> deque = new ArrayDeque<>();
        for(int i = 0; i < temp.size(); i++){
            while (!deque.isEmpty() && deque.peekFirst() < i - windowSize + 1){
                deque.pollFirst();
            }
            while (!deque.isEmpty() && temp.get(deque.peekLast()) > temp.get(i)){
                deque.pollLast();
            }
            deque.offerLast(i);
            if(i >= windowSize - 1){
                res.add(temp.get(deque.peekFirst()));
            }
        }
        return res;
    }

    /****************************************************************************************************************************************************************************************************************************/
    /*
     * Explain:
     * The logic of my solution is using Map<ProductId, PriorityQueue including all scores of this Product>,
     * the key of the map is the product id,
     * the value of the map is the all review scores of this product, and stored in PriorityQueue data structure.
     * The first for loop is to traverse all scores and put each score into the map of the same product id.
     * The second for loop is to get highest five (if less than five then get all scores), then calculate the average score,
     * and put into result map<Product_Id, Average_Score>
     * */
    /*
     * Created by Patel on 01/11/2019
     * @Return maximum usage combination
     * */
    private class Result {
        private int id;
        private double value;
    }

    public Map<Integer, Double> calculateHighest(int scoreCount, ArrayList<Result> reviewScores) {
        Map<Integer, PriorityQueue<Double>> map = new HashMap<>();
        for (Result result : reviewScores) {
            PriorityQueue<Double> priorityQueue = map.getOrDefault(result.id, new PriorityQueue<>(Comparator.reverseOrder()));
            priorityQueue.offer(result.value);
            map.put(result.id, priorityQueue);
        }
        Map<Integer, Double> res = new HashMap<>();
        for (Map.Entry<Integer, PriorityQueue<Double>> entry : map.entrySet()) {
            PriorityQueue<Double> priorityQueue = entry.getValue();
            double sum = 0;
            int i = 0;
            while (i <= 5) {
                if (!priorityQueue.isEmpty()) {
                    sum += priorityQueue.poll();
                    i++;
                } else {
                    break;
                }
            }
            res.put(entry.getKey(), sum / i);
        }
        return res;
    }
    /****************************************************************************************************************************************************************************************************************************/
    /*
     * Explain:
     * The first step is to traverse all toExclude words and put them into Set, as for searching operation Set is more efficient than List
     * The second step is to split the text based on regex splitting based on non-english-letters,
     * using for loop to iterate all words, put them into Map<Word, Times appearing until now>, and memorize the max times among all words.
     * The last step is to traverse all words and put words with maximum times into result list.
     * Time: O(n), n is the max(words in text, toExclude)
     * Space: O(n), n is the max(words in text, toExclude)
     * */
    /*
     * Created by Patel on 01/11/2019
     * @Return maximum usage combination
     * */
    public List<String> findMostFrequentWord(String text, List<String> toExclude){
        if(toExclude == null || toExclude.size() == 0 || text == null || text.length() == 0){
            return new ArrayList<>();
        }
        List<String> res = new ArrayList<>();
        Set<String> setExcludeWords = new HashSet<>(toExclude);
        Map<String, Integer> map = new HashMap<>();
        String[] allWords = text.split("[^a-zA-Z]");
        int curMax = -1;
        for(String word: allWords){
            if(setExcludeWords.contains(word)){
                continue;
            }
            int cur = map.getOrDefault(word.toLowerCase(), 0);
            cur++;
            map.put(word.toLowerCase(), cur);
            curMax = Math.max(curMax, map.get(word.toLowerCase()));
        }
        for(String word: map.keySet()){
            if(map.get(word.toLowerCase()) == curMax){
                res.add(word);
            }
        }
        return res;
    }
    /****************************************************************************************************************************************************************************************************************************/
    /*
     * Explain:
     * 第一步: 区分开 都是字母的 和 都是数字的 log, 分别放到两个list里面
     * 第二步: 对 都是字母的 log 进行排序, overwrite comparator方法
     * (先从identifier后面的单词开始, 逐一比较, 如果能区分出来大小关系, OK
     *  如果除了identifier都一样, 比较identifier, 如果其中一个是另外一个的子序列, 那么把短的放在前面)
     * 第三步: 加回result list里面
     * Time: O(n log n)
     * Space: O(n)
     * */
    /*
     * Created by Patel on 01/11/2019
     * @Return maximum usage combination
     * */
    public List<String> reorder(int longFileSize, List<String> logFile) {
        //corner case
        if (longFileSize <= 0 || logFile == null || logFile.size() == 0) {
            return new ArrayList<>();
        }
        List<String> res = new ArrayList<>();
        String SPACE = " ";
        List<String> digitLog = new ArrayList<>();
        List<String> englishLetterLog = new ArrayList<>();
        for (String log : logFile) {
            char firstLetterOfLog = log.split(SPACE)[1].charAt(0);
            if (firstLetterOfLog >= '0' && firstLetterOfLog <= '9') {
                digitLog.add(log);
            } else {
                englishLetterLog.add(log);
            }
        }
        Collections.sort(englishLetterLog, (log1, log2) -> {
            String[] log1Words = log1.split(SPACE);
            String[] log2Words = log2.split(SPACE);
            int log1Len = log1Words.length;
            int log2Len = log2Words.length;
            int i = 1, j = 1;
            while (i < log1Len && j < log2Len) {
                String curWordLog1 = log1Words[i].toLowerCase();
                String curWordLog2 = log2Words[i].toLowerCase();
                int compare = curWordLog1.compareTo(curWordLog2);
                if (compare != 0) {
                    return compare;
                }
                i++;
                j++;
            }
            if (log1Len == log2Len) {
                return log1Words[0].compareTo(log2Words[0]);
            }
            return log1Len == i ? -1 : 1;
        });
        res.addAll(englishLetterLog);
        res.addAll(digitLog);
        return res;
    }

    /****************************************************************************************************************************************************************************************************************************/
    /*
     * Explain:
     * 一: 遍历一遍input, 用 Map<input的各个字母, 各个字母出现的次数> 所有出现过的字母和相应字母的出现次数
     * 二: 用HashSet来记录任意一个scene中间出现的所有字母, 在字母最后一次出现在scene之后, 从HashSet当做删除,
     *     因为题目要求所有出现在任意一个scene中的字母, 只能出现在当前的scene中,
     *     也就是说, 当到达scene的末尾的时候, set里面不包含任何字母
     * 三: 当set的 size 等于零的时候, 加入到结果集当中
     * */
    /*
     * Created by Patel on 01/11/2019
     * @Return maximum usage combination
     * */
    public List<Integer> lengthEachScene(List<Character> inputList) {
        if (inputList == null || inputList.size() == 0) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        Map<Character, Integer> map = new HashMap<>();
        for (char curLetter : inputList) {
            int curCnt = map.getOrDefault(curLetter, 0);
            curCnt++;
            map.put(curLetter, curCnt);
        }
        int sceneLen = 0;
        Set<Character> allLetterInOneScene = new HashSet<>();
        for (char curLetter : inputList) {
            sceneLen++;
            allLetterInOneScene.add(curLetter);
            map.put(curLetter, map.get(curLetter) - 1);
            if (map.get(curLetter) == 0) {
                allLetterInOneScene.remove(curLetter);
            }
            if (allLetterInOneScene.size() == 0) {
                res.add(sceneLen);
                sceneLen = 0;
            }
        }
        return res;
    }

    /****************************************************************************************************************************************************************************************************************************/
    /*
     * Explain:
     * 一: 记录targetList的 单词总个数, 每个单词出现的次数: 用到了两个 hashMap, 第一个 hashMap 是用来保存<target中的每个单词, 该单词出现的次数>,
     *     第二个 hashMap 仅仅用来保存单词, 其中次数设置为零 <target中的每个单词, 0>
     * 二: 设置两个 index: slow, fast, 每个过程都是先移动 fast, 当 slow 和 fast 之间的单词满足如下条件:
     *     targetList的所有单词都出现在了slow和fast之间
     *     并且每个单词的出现次数都大于等于targetList中相应单词的出现次数
     *     的时候, 向右移动slow指针, 当
     *     targetList的所有单词都出现在了slow和fast之间 并且 出现次数不小于targetList中的出现次数的时候, 继续移动向右移动slow指针
     *     同时更新最短长度
     * 三: 最后输出最短长度, 如果最短长度的起始节点还是-1的话, 就说明没有找到, 直接输出-1即可
     * */
    /*
     * Created by Patel on 01/11/2019
     * @Return maximum usage combination
     * */
    public List<Integer> subSequenceTags(List<String> targetList, List<String> availableTagList) {
        //corner case
        if (targetList == null || targetList.size() == 0 || availableTagList == null || availableTagList.size() == 0) {
            return new ArrayList<>();
        }
        HashMap<String, Integer> targetMap = new HashMap<>();
        HashMap<String, Integer> curMap = new HashMap<>();
        for (String target : targetList) {
            String targetLower = target.toLowerCase();
            int cnt = targetMap.getOrDefault(targetLower, 0);
            targetMap.put(targetLower, cnt + 1);
            curMap.put(targetLower, 0);
        }
        int targetSize = targetList.size();
        int curMinLen = availableTagList.size() + 2;
        int startMinLen = -1;
        int endMinLen = -1;
        int curTargetWordsCnt = 0;
        int slow = 0;
        for (int fast = 0; fast < availableTagList.size(); fast++) {
            String curAvailableWord = availableTagList.get(fast).toLowerCase();
            if (targetMap.containsKey(curAvailableWord)) {
                int targetWordCnt = targetMap.get(curAvailableWord);
                int curAvailWordCnt = curMap.get(curAvailableWord);
                if (curAvailWordCnt < targetWordCnt) {
                    curTargetWordsCnt++;
                }
                curMap.put(curAvailableWord, curAvailWordCnt + 1);
                if (curTargetWordsCnt == targetSize) {
                    while (slow < fast && (!targetMap.containsKey(availableTagList.get(slow).toLowerCase()) ||
                            curMap.get(availableTagList.get(slow).toLowerCase()) > targetMap.get(availableTagList.get(slow).toLowerCase()))) {
                        String wordSlow = availableTagList.get(slow).toLowerCase();
                        if (targetMap.containsKey(wordSlow)) {
                            curMap.put(wordSlow, curMap.get(wordSlow) - 1);
                        }
                        slow++;
                    }
                    if (curMinLen > fast - slow) {
                        curMinLen = fast - slow;
                        startMinLen = slow;
                        endMinLen = fast;
                    }
                }
            }
        }
        return (startMinLen == -1) ? Arrays.asList(-1) : Arrays.asList(startMinLen, endMinLen);
    }
    /****************************************************************************************************************************************************************************************************************************/

        public static void main(String[] args) {
        Amazon amazon = new Amazon();

        /*int totalSteakhouses = 3;
        int numSteakhouses = 2;
        List<List<Integer>> allLocations = new ArrayList<>();
        allLocations.add(Arrays.asList(1, 2));
        allLocations.add(Arrays.asList(3, 4));
        allLocations.add(Arrays.asList(1, -1));
        System.out.println(amazon.nearestXsteakHouses(totalSteakhouses, allLocations, numSteakhouses));*/

        /*int deviceCapacity = 10;
        List<List<Integer>> foregroundAppList = new ArrayList<>();
        List<List<Integer>> backgroundAppList = new ArrayList<>();
        foregroundAppList.add(Arrays.asList(1,3));
        foregroundAppList.add(Arrays.asList(2,5));
        foregroundAppList.add(Arrays.asList(3,7));
        foregroundAppList.add(Arrays.asList(4,10));
        backgroundAppList.add(Arrays.asList(1,2));
        backgroundAppList.add(Arrays.asList(2,3));
        backgroundAppList.add(Arrays.asList(3,4));
        backgroundAppList.add(Arrays.asList(4,5));
        *//*int deviceCapacity = 7;
        List<List<Integer>> foregroundAppList = new ArrayList<>();
        List<List<Integer>> backgroundAppList = new ArrayList<>();
        foregroundAppList.add(Arrays.asList(1,2));
        foregroundAppList.add(Arrays.asList(2,4));
        foregroundAppList.add(Arrays.asList(3,6));
        backgroundAppList.add(Arrays.asList(1,2));*//*
        System.out.println(amazon.optimalUtilization(deviceCapacity, foregroundAppList, backgroundAppList));*/

        /*int[] values = new int[]{5, 3, 6, 1, 2, 4};
        int n = values.length;
        int node1 = 2;
        int node2 = 4;
        System.out.println(amazon.bstDistance(values, n, node1, node2));*//*

        *//*String inputStr = "abcd";
        int num = 3;*//*
        String inputStr = "awaglknagawunagwkwag";
        int num = 4;
        System.out.println(amazon.subStringsKDist(inputStr, num));*/

        /*int numRows = 3;
        int numColumns = 3;
        List<List<Integer>> lot = new ArrayList<>();
        lot.add(Arrays.asList(1, 0, 0));
        lot.add(Arrays.asList(1, 0, 0));
        lot.add(Arrays.asList(1, 9, 1));
        System.out.println(amazon.minimumDistance(numRows, numColumns, lot));*/

        /*int numOfDays = 6;
        List<Integer> temp =  Arrays.asList(6,5,4,3,2,1);
        int windowSize = 3;
        System.out.println(amazon.calculateWindowMin(numOfDays, new ArrayList<>(temp), windowSize));*/

        /*String text = "jack and jill went to the market to buy bread and cheese cheese is jack favorite food";
        List<String> toExclude = Arrays.asList("and","he","the","to","is");
        System.out.println(amazon.findMostFrequentWord(text, toExclude));*/

        /*int logFileSize = 5;
        List<String> logFile = Arrays.asList(
                "a1 9 2 3 1",
                "g1 Act car",
                "zo4 4 7",
                "ab1 off KEY doq",
                "a8 act zoo"
        );
        System.out.println(amazon.reorder(logFileSize, logFile));*/

        /*List<Character> inputList = Arrays.asList('a','b','a','b','c','b','a','c','a','d','e','f','e','g','d','e','h','i','j','h','k','l','i','j');
        System.out.println(amazon.lengthEachScene(inputList));*/

        /*List<String> targetList = Arrays.asList("made","in","Spain");
        List<String> availableTargetList = Arrays.asList("made","weather","forecast","says","that","made","rain","in","spain","stays");
        System.out.println(amazon.subSequenceTags(targetList, availableTargetList));*/

    }
}
