package leetcode.problems.medium._8;

public class StringToInteger {

    public static void main(String[] args) {
//        String s = " -56342sss";
//        String s = "words and 987";
//        String s = "0032";
//        String s = "42";
        String s = "";
        System.out.println(myAtoi(s));

    }

    public static int myAtoi(String s) {
        int number = 0;
        int sign = 1;
        int i = 0;

        s = s.trim();

        if (s.isEmpty()) {
            return 0;
        } else if (s.charAt(i) == '-') {
            i++;
            sign = -1;
        } else if (s.charAt(i) == '+') {
            i++;
        }

        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            int toInt = s.charAt(i) - '0';
            if (number > (Integer.MAX_VALUE - toInt) / 10)
                return sign > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            number = number * 10 + toInt;
            System.out.println("toInt = " + toInt);
            i++;
        }


        return number * sign;
    }
}
