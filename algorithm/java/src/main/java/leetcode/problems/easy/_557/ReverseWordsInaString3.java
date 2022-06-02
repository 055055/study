package leetcode.problems.easy._557;

public class ReverseWordsInaString3 {
    public static void main(String[] args) {

        String s = "Let's take LeetCode contest";
//        String s =  "God Ding";
        System.out.println("solutions = " + betterSolution(s));
    }

    public static String solution(String s) {
        StringBuilder sb = new StringBuilder();
        String[] splitedStrings = s.split(" ");

        for (int i = 0; i < splitedStrings.length; i++) {
            char[] chars = splitedStrings[i].toCharArray();
            for (int j = chars.length - 1; j >= 0; j--) {
                sb.append(chars[j]);
                sb.append(" ");
            }
        }

        return sb.toString().trim();
    }


    public static String betterSolution(String s){
        int i=0;
        char[] str = s.toCharArray();
        for(int j=0;j<str.length;j++){
            if(str[j] == ' '){
                reverse(i,j-1,str);
                i = j+1;
            }
        }
        reverse(i,str.length-1,str);
        return new String(str);
    }

    public static void reverse(int i, int j, char[] str){
        while(i<j){
            char temp = str[i];
            str[i] = str[j];
            str[j] = temp;
            i++;
            j--;
        }
    }

}
