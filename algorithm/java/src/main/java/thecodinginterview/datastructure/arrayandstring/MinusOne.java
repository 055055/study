package thecodinginterview.datastructure.arrayandstring;

public class MinusOne {
    //1.5 하나 빼기; 문자열을 편집하는 방법에는 세 가지 종류가 있다. 문자 삽입, 문자 삭제, 문자 교체, 문자열 두 개가 주어졌을 때,
    //문자열을 같게 만들기 위한 편집 횟수가 1회 이내인지 확인하는 함수를 작성하라.

    //ex)
    //pale, ple -> true
    //pales, pale -> true
    //pale, bale -> true
    //pale, bake -> false

    //교체: 문자 한개를 교체해서 두 문자열을 같게 만들 수 있는 bale과 pale 같은 경우를 생각해 보자. 문자열 한개를 교체함으로써
    //bale을 pale로 만들 수 있다.

    //삽입: apple은 aple에서 문자 한 개를 삽입하면 같게 만들 수 있다. 즉, 어떤 문자열에서 특정한 위치를 빈 공간으로 남겨 두면
    //그 부분을 제외한 나머지 부분이 동일하다.

    //삭제: apple에서 문자 한 개를 삭제하면 aple과 같게 만들 수 있다. 여기서 삭제의 반대는 삽입이라는 사실을 알 수 있다.


    public static void main(String[] args) {

        System.out.println("solution() = " + solution("pale", "ple"));
    }

    public static boolean solution(String s1, String s2) {
        if (s1.length() == s2.length()) {
            return replaceOneEdit(s1, s2);
        } else if ((s1.length() + 1 == s2.length())) {
            return insertOneEdit(s1, s2);
        } else if (s1.length() == s2.length() + 1) {
            return insertOneEdit(s2, s1);
        }

        return false;
    }

    private static boolean insertOneEdit(String s1, String s2) {
        int index1 = 0;
        int index2 = 0;

        while (index1 < s1.length() && index2 < s2.length()) {
            if (s1.charAt(index1) != s2.charAt(index2)) {
                if (index1 != index2) {
                    return false;
                }
            } else {
                index1++;
            }
            index2++;
        }
        return true;
    }

    private static boolean replaceOneEdit(String s1, String s2) {
        boolean foundDifference = false;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (foundDifference) {
                    return false;
                }
                foundDifference = true;
            }
        }
        return true;
    }
}
