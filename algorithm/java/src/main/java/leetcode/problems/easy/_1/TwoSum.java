package leetcode.problems.easy._1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        int[] twoSumMyAnswer = getTwoSumFirstAnswer(nums, target);
        int[] twoSum = getTwoSum(nums, target);
        System.out.println("twoSumMyAnswer = " + Arrays.toString(twoSumMyAnswer));
        System.out.println("twoSum = " + Arrays.toString(twoSum));
    }

    public static int[] getTwoSumFirstAnswer(int[] nums, int target) {
        if (nums.length < 2 || nums.length > 100000) return null;

        int x;
        int[] arr = new int[2];

        for (int i = 0; i < nums.length; i++) {
            x = target - nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (x == nums[j]) {
                    arr[0] = i;
                    arr[1] = j;
                }
            }
        }
        return arr;
    }

    public static int[] getTwoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }

        return null;
    }
}
