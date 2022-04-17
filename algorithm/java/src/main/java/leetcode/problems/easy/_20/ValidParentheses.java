package leetcode.problems.easy._20;

import java.util.Stack;

public class ValidParentheses {

    public static void main(String[] args) {

        boolean valid = isValid("(]{})[");
        System.out.println("valid = " + valid);
    }

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '{' || c == '[' || c == '(') {
                stack.push(c);
            } else if (stack.empty()) {
                return false;
            } else if (c == '}' && stack.peek() == '{') {
                stack.pop();
            } else if (c == ')' && stack.peek() == '(') {
                stack.pop();
            } else if (c == ']' && stack.peek() == '[') {
                stack.pop();
            } else {
                return false;
            }
        }
        return stack.empty();
    }

    public static boolean isValidSolution(String s) {
        char[] array = new char[s.length()];
        int head = 0;
        for(char c : s.toCharArray()) {
            switch(c) {
                case '{':
                case '[':
                case '(':
                    array[head++] = c;
                    break;
                case '}':
                    if(head == 0 || array[--head] != '{') return false;
                    break;
                case ')':
                    if(head == 0 || array[--head] != '(') return false;
                    break;
                case ']':
                    if(head == 0 || array[--head] != '[') return false;
                    break;
            }
        }
        return head == 0;
    }
}
