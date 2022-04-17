package leetcode.problems.easy._14;

import java.util.Arrays;

public class LongestCommonPrefix {

    public static void main(String[] args) {
        String[] strs = new String[]{"ab", "a"};
        String longestCommonPrefi = getLongestCommonPrefix(strs);
        System.out.println("longestCommonPrefi = " + longestCommonPrefi);
    }

    public static String getLongestCommonPrefix(String[] strs) {
        Arrays.sort(strs);
        String firstWord = strs[0];
        String lastWord = strs[strs.length - 1];
        int len = Math.min(firstWord.length(), lastWord.length());
        int i = 0;
        for (; i < len; i++) {
            if (firstWord.charAt(i) != lastWord.charAt(i)) {
                break;
            }
        }
        return firstWord.substring(0, i);
    }

    public static String getLongestCommonPrefixBestSolution(String[] strs) {
        String s1 = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (!(strs[i].startsWith(s1))) {
                s1 = s1.substring(0, s1.length() - 1);
            }
        }
        return s1;
    }
}
