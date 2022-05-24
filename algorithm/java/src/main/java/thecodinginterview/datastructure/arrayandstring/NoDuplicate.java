package thecodinginterview.datastructure.arrayandstring;


//1.1 중복이 없는가 : 문자열이 주어졌을 때, 이 문자열에 같은 문자가 중복되어 등장하는지 확인하는 알고리즘을 작성하라.
//
//설명: 아스키 문자열인지 유니코드 문자열인지 확인해야 하지만, 여기서는 간단하게 아스키문자열이라고 가정하자. 이 가정이 없다면 저장 공간의 크기를 늘려야 할 수도 있다.
//
//이 문제를 푸는 한가지 방법은 문자 집합에서 i번째 문자가 배열 내에 존재하는지 표시하는 불린(boolean) 배열을 사용하는 것이다. 같은 원소에 두 번 접근하면,
//        바로 false를 반환한다.
//
//또한 문자열의 길이가 문자 집합의 크기보다 클 경우 바로 false를 반환해도 된다. 결국 256가지 문자를 한 번씩만 사용해서 길이가 280인 문자열을 만들 수는 없으니 말이다.
//


public class NoDuplicate {
    public static void main(String[] args) {
        System.out.println("solution = " + solution("abc"));
    }

    public static boolean solution(String s) {
//이 코드의 시간 복잡도는 O(n)이다 (n은 문자열의 길이). 공간 복잡도는 O(1)이다.
        boolean[] char_array = new boolean[128];

        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);
            System.out.println("c = " + c);
            if (char_array[c]) {
                return false;
            }
            char_array[c] = true;
        }

        return true;
    }
}
