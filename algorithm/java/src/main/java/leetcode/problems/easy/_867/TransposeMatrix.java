package leetcode.problems.easy._867;

import java.util.Arrays;
import java.util.HashMap;

public class TransposeMatrix {
    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        System.out.println("solution = " + Arrays.deepToString(solution(matrix)));

    }

    public static int[][] solution(int[][] matrix) {
        int R = matrix.length, C = matrix[0].length;
        int[][] ans = new int[C][R];
        for (int r = 0; r < R; ++r)
            for (int c = 0; c < C; ++c) {
                ans[c][r] = matrix[r][c];
            }
        return ans;
    }
}
