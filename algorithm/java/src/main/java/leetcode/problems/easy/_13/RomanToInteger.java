package leetcode.problems.easy._13;

import java.util.HashMap;

public class RomanToInteger {

    public static void main(String[] args) {
        String s = "III";

        int romanToIntFirstAnswer = getRomanToIntFirstAnswer(s);
        System.out.println("romanToIntFirstAnswer = " + romanToIntFirstAnswer);
    }

    public static int getRomanToIntFirstAnswer(String s) {
        HashMap<Character,Integer> hm = new HashMap<>();
        hm.put('I',1);
        hm.put('V',5);
        hm.put('X',10);
        hm.put('L',50);
        hm.put('C',100);
        hm.put('D',500);
        hm.put('M',1000);

        int result = hm.get(s.charAt(0));

        for (int i = 1; i < s.length(); i++){
            int previous = hm.get(s.charAt(i-1));
            int current = hm.get(s.charAt(i));

            if (previous >= current){
                result += current;
            }else{
                result = result - previous + current - previous;
            }
        }

        return result;
    }
}
