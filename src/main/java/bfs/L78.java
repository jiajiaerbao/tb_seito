package bfs;

import java.util.ArrayList;
import java.util.List;

public class L78 {
    /*
    * 定长搜索的题目:
    *   模板大概记住了, 但是还是没有理解透彻
    *   对于输入的参数, nums的每一位进行遍历
    *       取出结果集中的每一个结果, 注意: 这里仅仅是取出每一个结果, 不删除, 因为这里没有限制结果集的长度
    *       当取出一个结果之后, 生成一个新的arrayList: new ArrayList<>(res.get(k))
    * */

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        if (nums == null || nums.length == 0) {
            return res;
        }
        for (int i = 0; i < nums.length; i++) {
            int size = res.size();
            for (int k = 0; k < size; k++) {
                /*List<Integer> subRes = res.get(k);*/
                List<Integer> subRes = new ArrayList<>(res.get(k));
                System.out.println(subRes);
                subRes.add(nums[i]);
                res.add(subRes);
            }
        }
        return res;
    }
}
