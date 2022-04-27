package lecture.basic._1_string._3_indexOfAndSubString;

public class IndexOf {
    public static void main(String[] args) {
        String str[] = {"test", "teacher"};
        System.out.println(solve(str));

    }

    public static String solve(String[] strs) {
        if (strs.length == 0) return "";

        //1 test
        String firstStr = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(firstStr) != 0) {
                firstStr = firstStr.substring(0, firstStr.length() - 1);
            }
        }
        return firstStr;
    }
}
