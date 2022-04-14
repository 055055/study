package leetcode.problems.easy._9;

public class PalindromeNumber {
    public static void main(String[] args) {
        int x = -121;

        boolean palindromeMyFirstAnswer = isPalindromeMyFirstAnswer(x);
        boolean palindrome = isPalindrome(x);

        System.out.println("palindromeMyFirstAnswer = " + palindromeMyFirstAnswer);
        System.out.println("palindrome = " + palindrome);
    }

    public static boolean isPalindromeMyFirstAnswer(int x) {
        StringBuilder sb = new StringBuilder().append(x);
        return sb.toString().equals(sb.reverse().toString());
    }

    public static boolean isPalindrome(int x) {

        if (x < 0) return false;

        int calcu = 0;
        int reverse = 0;
        int origin = x;

        while (origin != 0) {
            calcu = origin % 10;
            reverse = reverse * 10 + calcu;
            origin = origin / 10;

        }
        return reverse == x;
    }
}
