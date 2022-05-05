package leetcode.problems.medium._7;

public class ReverseInteger {
    public static void main(String[] args) {
        int x = 1534236469;
        System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE);
        System.out.println("Integer.MIN_VALUE = " + Integer.MIN_VALUE);

        System.out.println(reverse(x));

    }

    public static int reverse(int x) {
        if (x > Integer.MAX_VALUE) return 0;
        if (x < Integer.MIN_VALUE) return 0;

        int calcu = 0;
        int origin = x;
        int reverse = 0;
        while (origin != 0) {
            calcu = origin % 10;
            origin = origin / 10;
            if (reverse > Integer.MAX_VALUE/10 || (reverse == Integer.MAX_VALUE / 10 && calcu > 7)) return 0;
            if (reverse < Integer.MIN_VALUE/10 || (reverse == Integer.MIN_VALUE / 10 && calcu < -8)) return 0;

            reverse = reverse * 10 + calcu;
        }


        return reverse;
    }
}
