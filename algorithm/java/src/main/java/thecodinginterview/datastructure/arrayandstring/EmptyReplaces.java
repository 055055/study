package thecodinginterview.datastructure.arrayandstring;

public class EmptyReplaces {

    public static void main(String[] args) {
        System.out.println("replaces = " + replaces("'Mr John Smith'", 13));
    }

    public static String replaces(String s, int trueLength) {
        char[] chars = s.toCharArray();
        int emptyCount = 0;

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                emptyCount++;
            }
        }
        int index = (emptyCount * 3) + trueLength;

        char[] solution = new char[index];

        for (int j = chars.length - 1; j >= 0; j--) {
            if (chars[j] == ' ') {
                System.out.println("chars[j]2 = " + chars[j]);
                solution[index - 1] = '0';
                solution[index - 2] = '2';
                solution[index - 3] = '%';
                index = index - 3;
            } else {
                System.out.println("chars[j] = " + chars[j]);
                solution[index - 1] = chars[j];
                index--;
            }
        }
        return new String(solution);
    }
}
