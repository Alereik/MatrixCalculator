package MatrixCalculator;

import java.util.Arrays;

/**
 * This class tests the methods in the MatrixString class.
 * 
 * @author AOsterndorff
 *
 */
public class TestMatrixString {
    
    /**
     * This main method calls the test methods.
     * 
     * @param args Unused.
     */
    public static void main(String[] args) {
        testEqualizeElementWidth();
        testprintMatrix();
        testPrintAugmentedMatrix();
    }
    
    /**
     * This method tests both of the EqualizeElementWidth methods.
     */
    public static void testEqualizeElementWidth() {
        boolean allPassed = true;
        {//test 1 one-dimensional array parameter
            String[] vectorArr = new String[] {"0", "12", "123", "1", "1234567890"};
            String[] expected = new String[] 
                  {"    0     ", "    12    ", "   123    ", "    1     ", "1234567890"};
            String[] actual = MatrixString.equalizeElementWidth(vectorArr);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n" 
                    + Arrays.toString(actual) 
                    + "\ntestEqualizeElementWidth: Test 1 failed");
                allPassed = false;
            }
        }
        {//test 2 two-dimensional array parameter
            String[][] matrixArr = new String[][] {{"0", "12", "2"}, 
                                                   {"0", "12345", "12345678"},
                                                   {"1234567890", "1234567890", "1234567890"}};
            String[][] expected = new String[][] {{"    0     ", "    12    ", "    2     "}, 
                                                  {"    0     ", "  12345   ", " 12345678 "},
                                                  {"1234567890", "1234567890", "1234567890"}};
            String[][] actual = MatrixString.equalizeElementWidth(matrixArr);
            if (!Arrays.deepEquals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.deepToString(expected) + "\nactual:\n" 
                                   + Arrays.deepToString(actual) 
                                   + "\ntestEqualizeElementWidth: Test 2 failed");
                allPassed = false;
            }
        }
        if (allPassed) {
            System.out.println("All testEqualizeElementWidth tests passed");
        }
    }
    /**
     * This method tests the generateMatrix and the getMatrixWidth methods.
     */
    public static void testprintMatrix() {
        boolean allPassed = true;
        {//test 1: single character
            String[][] matrixArr = new String[3][3];
            for (int i = 0; i < matrixArr.length; ++i) {
                for (int j = 0; j < matrixArr[i].length; ++j) {
                    matrixArr[i][j] = "0";
                }
            }
            String expected = "/         \\\n"
                            + "| 0  0  0 |\n"
                            + "|         |\n"
                            + "| 0  0  0 |\n"
                            + "|         |\n"
                            + "| 0  0  0 |\n"
                           + "\\         /\n";
            String actual = MatrixString.printMatrix(matrixArr);
            if(!actual.equals(expected)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\ntestPrintMatrix: Test 1 failed");
                allPassed = false;
            }
        }
        {//test 2: multiple characters
            String[][] matrixArr = new String[3][3];
            for (int i = 0; i < matrixArr.length; ++i) {
                for (int j = 0; j < matrixArr[i].length; ++j) {
                    matrixArr[i][j] = "1234567890";
                }
            }
            String expected = "/                                    \\\n"
                            + "| 1234567890  1234567890  1234567890 |\n"
                            + "|                                    |\n"
                            + "| 1234567890  1234567890  1234567890 |\n"
                            + "|                                    |\n"
                            + "| 1234567890  1234567890  1234567890 |\n"
                           + "\\                                    /\n";
            String actual = MatrixString.printMatrix(matrixArr);
            if(!actual.equals(expected)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\ntestPrintMatrix: Test 2 failed");
                allPassed = false;
            }
        }
        {//test 3: differing numbers of characters
            String[][] matrixArr = new String[][] {{"0", "1", "2"}, 
                                                   {"0", "12345", "1234567890"},
                                                   {"1234567890", "1234567890", "1234567890"}};
            String expected = "/                                    \\\n"
                            + "|     0           1           2      |\n"
                            + "|                                    |\n"
                            + "|     0         12345     1234567890 |\n"
                            + "|                                    |\n"
                            + "| 1234567890  1234567890  1234567890 |\n"
                           + "\\                                    /\n";
            String actual = MatrixString.printMatrix(matrixArr);
            if(!actual.equals(expected)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\ntestPrintMatrix: Test 3 failed");
                allPassed = false;
            }
        }
        {//test 4: 2x2 differing numbers of characters
            String[][] matrixArr = new String[][] {{"0", "1"}, 
                                                   {"0", "12345"}};
            String expected = "/              \\\n"
                            + "|   0      1   |\n"
                            + "|              |\n"
                            + "|   0    12345 |\n"
                           + "\\              /\n";
            String actual = MatrixString.printMatrix(matrixArr);
            if(!actual.equals(expected)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual
                                   + "\ntestPrintMatrix: Test 4 failed");
                allPassed = false;
            }
        }
        {//test 5: 2x3 differing numbers of characters
            String[][] matrixArr = new String[][] {{"0", "1"}, 
                                                   {"0", "12345"},
                                                   {"1234567890", "1234567890"}};
            String expected = "/                        \\\n"
                            + "|     0           1      |\n"
                            + "|                        |\n"
                            + "|     0         12345    |\n"
                            + "|                        |\n"
                            + "| 1234567890  1234567890 |\n"
                           + "\\                        /\n";
            String actual = MatrixString.printMatrix(matrixArr);
            if(!actual.equals(expected)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual
                                   + "\ntestPrintMatrix: Test 5 failed");
                allPassed = false;
            }
        }
        {//test 6: 5x5 differing numbers of characters
            String[][] matrixArr = new String[][] {{"0", "1", "2", "1234567890", "1234567890"}, 
                  {"0", "12345", "1234567890", "1234567890", "1234567890"},
                  {"1234567890", "1234567890", "1234567890", "1234567890", "1234567890"},
                  {"1234567890", "1234567890", "1234567890", "1234567890", "1234567890"},
                  {"1234567890", "123456789", "12345678", "1234567", "123456"}};
            String expected = "/                                                            \\\n"
                            + "|     0           1           2       1234567890  1234567890 |\n"
                            + "|                                                            |\n"
                            + "|     0         12345     1234567890  1234567890  1234567890 |\n"
                            + "|                                                            |\n"
                            + "| 1234567890  1234567890  1234567890  1234567890  1234567890 |\n"
                            + "|                                                            |\n"
                            + "| 1234567890  1234567890  1234567890  1234567890  1234567890 |\n"
                            + "|                                                            |\n"
                            + "| 1234567890  123456789    12345678    1234567      123456   |\n"
                           + "\\                                                            /\n";
            String actual = MatrixString.printMatrix(matrixArr);
            if(!actual.equals(expected)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual
                                   + "\ntestPrintMatrix: Test 6 failed");
                allPassed = false;
            }
        }
        if (allPassed) {
            System.out.println("All testPrintMatrix tests passed");
        }       
    }
    
    /**
     *  This method tests both of the printAugmentedMatrix methods and the getMatrixWidth method.
     */
    public static void testPrintAugmentedMatrix() {
        boolean allPassed = true;
        {//test 1: 2x2 matrix and 2x1 vector
            String[][] matrixArr = new String[2][2];
            String[] vectorArr = new String[2];
            for (int i = 0; i < matrixArr.length; ++i) {
                vectorArr[i] = "0";
                for (int j = 0; j < matrixArr[i].length; ++j) {
                    matrixArr[i][j] = "0";
                }
            }
            String expected =  "/      |   \\\n"
                             + "| 0  0 | 0 |\n"
                             + "|      |   |\n"
                             + "| 0  0 | 0 |\n"
                            + "\\      |   /\n";
            String actual = MatrixString.printAugmentedMatrix(matrixArr, vectorArr);
            if(!actual.equals(expected)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual
                                   + "\ntestPrintAugmentedMatrix: Test 1 failed");
                allPassed = false;
            }
        }
        {//test 2: 4x4 matrix and 4x1 vector
            String[][] matrixArr = new String[][] {{"0", "0", "0", "0"},
                                                     {"123", "1", "12", "1234"}, 
                                                     {"0", "0", "1", "2"},
                                                     {"123456", "123456", "123456", "123456"}};
            String[] vectorArr = new String[] {"1", "1/3", "12345", "0"};
            String expected =  "/                                |       \\\n"
                             + "|   0       0       0       0    |   1   |\n"
                             + "|                                |       |\n"
                             + "|  123      1       12     1234  |  1/3  |\n"
                             + "|                                |       |\n" 
                             + "|   0       0       1       2    | 12345 |\n"
                             + "|                                |       |\n" 
                             + "| 123456  123456  123456  123456 |   0   |\n"
                            + "\\                                |       /\n";
            String actual = MatrixString.printAugmentedMatrix(matrixArr, vectorArr);
            if(!actual.equals(expected)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual
                                   + "\ntestPrintAugmentedMatrix: Test 2 failed");
                allPassed = false;
            }
        }
        {//test 3: 2x2 matrix and 2x2 matrix
            String[][] matrixArr = new String[2][2];
            String[][] augMatrixArr = new String[2][2];
            for (int i = 0; i < matrixArr.length; ++i) {
                for (int j = 0; j < matrixArr[i].length; ++j) {
                    matrixArr[i][j] = "0";
                    augMatrixArr[i][j] = "0";
                }
            }
            String expected =  "/      |      \\\n"
                             + "| 0  0 | 0  0 |\n"
                             + "|      |      |\n"
                             + "| 0  0 | 0  0 |\n"
                            + "\\      |      /\n";
            String actual = MatrixString.printAugmentedMatrix(matrixArr, augMatrixArr);
            if(!actual.equals(expected)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual
                                   + "\ntestPrintAugmentedMatrix: Test 3 failed");
                allPassed = false;
            }
        }
        {//test 4: 3x3 matrix and 3x3 matrix
            String[][] matrixArr = new String[][] {{"123", "1", "12"}, {"0", "0", "1"},
                                                     {"123456", "123456", "123456"}};
            String[][] augMatrixArr = new String[][] {{"1234", "1/3", "0"}, {"2", "12345", "0"}, 
                                                      {"12345", "0", "0"}};
            String expected =  "/                        |                     \\\n"
                             + "|  123      1       12   | 1234    1/3     0   |\n"
                             + "|                        |                     |\n" 
                             + "|   0       0       1    |   2    12345    0   |\n"
                             + "|                        |                     |\n" 
                             + "| 123456  123456  123456 | 12345    0      0   |\n"
                            + "\\                        |                     /\n";
            String actual = MatrixString.printAugmentedMatrix(matrixArr, augMatrixArr);
            if(!actual.equals(expected)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual
                                   + "\ntestPrintAugmentedMatrix: Test 4 failed");
                allPassed = false;
            }
        }
        {//test 5: 3x3 matrix and 2x1 vector
            String[][] matrixArr = new String[][] {{"123", "1", "12"}, {"0", "0", "1"},
                                                     {"123456", "123456", "123456"}};
            String[] vectorArr = new String[] {"1234", "1/3"};
            String expected = null;
            String actual = MatrixString.printAugmentedMatrix(matrixArr, vectorArr);
            if(actual != expected) {
                System.out.println("testPrintAugmentedMatrix: Test 5 failed");
                allPassed = false;
            }
        }
        {//test 6: 2x3 matrix and 3x1 vector
            String[][] matrixArr = new String[][] {{"123", "1", "12"}, {"0", "0", "1"}};
            String[] vectorArr = new String[] {"1234", "1/3", "0"};
            String expected = null;
            String actual = MatrixString.printAugmentedMatrix(matrixArr, vectorArr);
            if(actual != expected) {
                System.out.println("testPrintAugmentedMatrix: Test 6 failed");
                allPassed = false;
            }
        }
        {//test 7: 3x3 matrix and 2x3 matrix
            String[][] matrixArr = new String[][] {{"123", "1", "12"}, {"0", "0", "1"},
                                                     {"123456", "123456", "123456"}};
            String[][] augMatrixArr = new String[][] {{"1234", "1/3", "0"}, {"2", "12345", "0"}};
            String expected = null;
            String actual = MatrixString.printAugmentedMatrix(matrixArr, augMatrixArr);
            if(actual != expected) {
                System.out.println("testPrintAugmentedMatrix: Test 7 failed");
                allPassed = false;
            }
        }
        {//test 8: 2x3 matrix and 3x3 matrix
            String[][] matrixArr = new String[][] {{"123", "1", "12"}, {"0", "0", "1"}};
            String[][] augMatrixArr = new String[][] {{"1234", "1/3", "0"}, {"2", "12345", "0"},
                                                      {"12345", "0", "0"}};
            String expected = null;
            String actual = MatrixString.printAugmentedMatrix(matrixArr, augMatrixArr);
            if(actual != expected) {
                System.out.println("testPrintAugmentedMatrix: Test 8 failed");
                allPassed = false;
            }
        }
        if (allPassed) {
            System.out.println("All testPrintAugmentedMatrix tests passed");
        }   
    }
}
