package lecture.basic._1_string._1_chatAt;

public class ChatAt {
    public static void main(String[] args) {
        String num1 = "123";
        String num2 = "888";

        String response = solve(num1, num2);
        System.out.println("response = " + response);
    }

    public static String solve(String num1, String num2) {
        //1
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        int num1Length = num1.length() - 1;
        int num2Length = num2.length() - 1;

        //2
        while (num1Length >= 0 && num2Length >= 0) {
            int n1 = 0, n2 = 0;
            if (num1Length >= 0) {
                n1 = num1.charAt(num1Length) - '0'; // String => int
            }

            if (num2Length >= 0) {
                n2 = num2.charAt(num1Length) - '0'; // String => int
            }
            int sum = n1 + n2 + carry;
            carry = sum / 10;
            sb.append(sum % 10);
            num1Length--;
            num2Length--;
        }

        if (carry != 0) {
            sb.append(carry);
        }
        return sb.reverse().toString();
    }
}
