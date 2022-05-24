package thecodinginterview.datastructure.arrayandstring;

import java.util.Arrays;

public class PermutationCheck {
//
//    1.2 순열 확인: 문자열 두 개가 주어졌을 때 이 둘이 서로 순열 관계에 있는지 확인하는 메서드를 작성하라.
//
//    설명:




    public static void main(String[] args) {
//        System.out.println("solution = " + solution("dog", "god"));
        System.out.println("bestSolution() = " + bestSolution("dog", "god"));
    }

    
    //풀이 1. 정렬하라
//    만일 두 문자열이 서로 순열 관계에 있다면, 이 둘은 같은 문자로 구성되어 있고 순서만 다를 것이다. 문자열을 따라서 정렬하면 둘 다 같은 결과가 나와야한다.
//    그 뒤에는 정렬된 무낮열이 같은지만 비교해 보면 된다.
//
//    이 알고리즘은 어떤 면에서 최적은 아니다. 하지만 깔끔하고 단순하며 이해하기 쉽다는 측면에서 보면 괜찮은 알고리즘이다. 실용성 면에서 보면, 아주 훌륭한 풀이 법이다.
//    그럼에도 효율성이 아주 중요한 상황이라면 다른 식으로 구현할 수 있다.
    public static boolean solution(String s, String t) {
        if (s.length() != t.length()) return false;

        return sort(s).equals(sort(t));
    }

    public static String sort(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

//    풀이 2. 문자열에 포함된 문자의 출현 횟수가 같은지 검사하라.
//
//    이번에도 순열의 정의, 즉 두 문자열이 동일한 문자 개수를 갖고 있다는 점을 이용해서 알고리즘을 구현할 것이다. 배열을 두 개 사용해서 각 문자열 내의 문자 출현 횟수를 기록한 다음, 두 배열을 비교하자.
//    실제로는 면접관에게 문자 집합 크기를 확인해야 한다. 여기서는 문자 집합으로 아스키 코드를 사용한다고 가정한다.

    public static boolean bestSolution(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] letters = new int[128];

        for (int i = 0; i < s.length(); i++) {
            letters[s.charAt(i)]++;
        }

        for (int j = 0; j < t.length(); j++) {
            letters[t.charAt(j)]--;
            if (letters[t.charAt(j)] < 0) return false;
        }

        return true;
    }
}
