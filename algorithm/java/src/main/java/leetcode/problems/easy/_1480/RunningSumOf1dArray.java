package leetcode.problems.easy._1480;

import java.util.Arrays;

public class RunningSumOf1dArray {
    public static void main(String[] args) {
//        int[] nums = {1, 2, 3, 4};
//        int[] nums = {1,1,1,1,1};
        int[] nums = {3, 1, 2, 10, 1};
        System.out.println("solution(); = " + Arrays.toString(solution(nums)));
    }

    public static int[] solution(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i-1];
        }

        return nums;
    }
}
