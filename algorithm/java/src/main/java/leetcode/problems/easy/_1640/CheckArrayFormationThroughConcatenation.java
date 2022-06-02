package leetcode.problems.easy._1640;

import java.util.HashMap;
import java.util.Map;

public class CheckArrayFormationThroughConcatenation {

    public static void main(String[] args) {
//        int[] arr = {15, 88};
//        int[][] pieces = {{88}, {15}};

//        int[] arr = {91,4,64,78};
//        int[][] pieces = {{78},{4,64},{91}};

        int[] arr = {49, 18, 16};
        int[][] pieces = {{16, 18, 49}};

        System.out.println("solution(arr, pieces) = " + solution(arr, pieces));
    }

    public static boolean solution(int[] arr, int[][] pieces) {

        Map<Integer, Integer> positionMap = new HashMap<>();
        //#Store positions of all the elements of arr
        for (int i = 0; i < arr.length; i++)
            positionMap.put(arr[i], i);

        for (int[] piece : pieces) {
            //#First element does not have any position in original arr
            if (!positionMap.containsKey(piece[0])) return false;
            for (int i = 1; i < piece.length; i++) {
                if (!positionMap.containsKey(piece[i])) return false;
                if (positionMap.get(piece[i]) - positionMap.get(piece[i - 1]) != 1) return false;
            }
        }

        return true;
    }

    public static boolean bestSolution(int[] arr, int[][] pieces) {
        StringBuilder sb = new StringBuilder();
        for (int x : arr) {
            sb.append("#");
            sb.append(x);
            sb.append("#");
        }
        for (int i = 0; i < pieces.length; i++) {
            StringBuilder res = new StringBuilder();
            for (int j = 0; j < pieces[i].length; j++) {
                res.append("#");
                res.append(pieces[i][j]);
                res.append("#");
            }
            if (!sb.toString().contains(res.toString())) {
                return false;
            }
        }
        return true;
    }
}
