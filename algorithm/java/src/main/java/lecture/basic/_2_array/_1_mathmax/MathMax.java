package lecture.basic._2_array._1_mathmax;

public class MathMax {
    public static void main(String[] args) {
        int[] prices = {8, 2, 6, 5, 1, 7, 5};
        System.out.println(maxProfit(prices));

    }

    public static int maxProfit(int[] nums) {
        //1
        if(nums.length == 0) return 0;

        //2 담어야할 그릇
        int max = 0;
        int sofaMin = nums[0];

        //3 for while
        for (int i = 0; i <nums.length; i++) {
            if(nums[i] < sofaMin){
                sofaMin = nums[i];
            }else{
                max = Math.max(max, nums[i] - sofaMin);
            }

        }
        return max;
    }
}
