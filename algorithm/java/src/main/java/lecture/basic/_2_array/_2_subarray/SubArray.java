package lecture.basic._2_array._2_subarray;

import java.util.HashMap;
import java.util.Map;

public class SubArray {
    public static void main(String[] args) {
        int[] nums = {3, 4, 7, 2, -3, 1, 4, 2};
        int k = 7;
        System.out.println(usingSum(nums, k));
        System.out.println(usingMap(nums, k));
    }

    public static int usingSum(int[] nums, int k) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum = sum + nums[j];
                if (sum == k) {
                    System.out.println("sum = " + sum + " : nums[" + j + "]" + nums[j]);
                    count++;
                }
            }
        }
        return count;
    }

    public static int usingMap(int[] nums, int k) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0;

        //2
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i]; // sum = sum + nums[i];
            System.out.println("sum = " + sum);
            if (map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }

            map.put(sum, map.getOrDefault(sum, 0) + 1);

        }
        return count;
    }

}
