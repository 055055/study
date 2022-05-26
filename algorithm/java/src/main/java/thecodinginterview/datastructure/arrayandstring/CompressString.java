package thecodinginterview.datastructure.arrayandstring;
//1.6 문자열 압축:
// 반복되는 무자의 개수를 세는 방식의 기보적인 문자열 압축 메서드를 작성하라. 예를 들어 문자열 aabccccaaa를 압축하면
// a2b1c4a3이 된다. 만약 압축된 문자열의 길이가 기존 문자열의 길이보다 길다면 기존 문자열을 반환해야 한다. 문자열은 대소문자 알파벳 (a~z)로만 이루어져 잇따.

public class CompressString {

    public static void main(String[] args) {
        String s = "aabccccaaa";

        System.out.println("solution() = " + solution(s));
    }

    public static String solution(String s) {

        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            count++;
            if (i + 1 >= s.length() || s.charAt(i) != s.charAt(i + 1)) {
                sb.append(s.charAt(i));
                sb.append(count);
                count = 0;
            }
        }

        return sb.length() < s.length() ? sb.toString() : s;
    }
}
