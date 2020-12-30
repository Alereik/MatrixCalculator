package MatrixCalculator;

import java.util.Arrays;

/**
 * This class tests the methods in the RowReduction class.
 * 
 * @author AOsterndorff
 *
 */
public class TestRowReduction {
    
    /**
     * This main method calls the test methods.
     * 
     * @param args Unused.
     */
    public static void main(String[] args) {
        testGetReciprocal();
        testRowReduce();
        testRowReductionInColumn();
    }
    
    /**
     * This method tests the getReciprocal method.
     */
    public static void testGetReciprocal() {
        boolean allPassed = true;
        {//test 1: positive fraction
            String element = "2/7";
            String expected = "7/2";
            String actual = RowReduction.getReciprocal(element);
            if (!expected.equals(actual)) {
                System.out.println("testGetReciprocal: Test 1 failed");
            }
        }
        {//test 2: whole number
            String element = "3";
            String expected = "1/3";
            String actual = RowReduction.getReciprocal(element);
            if (!expected.equals(actual)) {
                System.out.println("testGetReciprocal: Test 2 failed");
            }
        }
        {//test 3: negative fraction
            String element = "-2/7";
            String expected = "-7/2";
            String actual = RowReduction.getReciprocal(element);
            if (!expected.equals(actual)) {
                System.out.println("testGetReciprocal: Test 3 failed");
            }
        }
        {//test 4: negative whole number
            String element = "-2";
            String expected = "-1/2";
            String actual = RowReduction.getReciprocal(element);
            if (!expected.equals(actual)) {
                System.out.println("testGetReciprocal: Test 4 failed");

            }
        }
        if (allPassed) {
            System.out.println("All testGetReciprocal tests passed");
        }
    }
    
    /**
     * This method tests the rowReductionInColumn method
     */
    public static void testRowReductionInColumn() {
        boolean allPassed = true;
        {//test 1: 
            String[][] matrix = new String[][] {{"0", "1/2", "3"}, 
                                                {"1/3", "0", "5"}, 
                                                {"2", "4", "1"}};
            String[][] expected = new String[][] {{"1", "0", "15"}, 
                                                  {"0", "1/2", "3"}, 
                                                  {"0", "2", "-29/2"}};
            String[][] actual = RowReduction.rowReductionInColumn(matrix, 0, 0);
            if (!Arrays.deepEquals(expected, actual)) {
                System.out.println("testRowReductionInColumn: Test 1 failed");
                allPassed = false;
            }
        }
        {//test 2: 
            String[][] matrix = new String[][] {{"1", "2", "3", "1"}, 
                                                {"1", "1", "2", "1"}, 
                                                {"1", "2", "3", "1"}};
            String[][] expected = new String[][] {{"1", "2", "3", "1"}, 
                                                  {"0", "-1", "-1", "0"}, 
                                                  {"0", "0", "0", "0"}};
            String[][] actual = RowReduction.rowReductionInColumn(matrix, 0, 0);
            if (!Arrays.deepEquals(expected, actual)) {
                System.out.println("testRowReductionInColumn: Test 2 failed");
                allPassed = false;
            }
        } 
        {//test 3:
            String[][] matrix = new String[][] {{"1", "-1", "-1", "2"}, 
                                                {"2", "-2", "-1", "3"}, 
                                                {"-1", "1", "4", "0"}};
            String[][] expected = new String[][] {{"1", "-1", "-1", "2"}, 
                                                  {"0", "0", "1/2", "-1/2"}, 
                                                  {"0", "0", "-3", "-2"}};
            String[][] actual = RowReduction.rowReductionInColumn(matrix, 0, 0);
            if (!Arrays.deepEquals(expected, actual)) {
                System.out.println("testRowReductionInColumn: Test 3 failed");
                allPassed = false;
            }
        }
        //TODO augmented matrix tests
        if (allPassed) {
            System.out.println("All testRowReduceInColumn tests passed");
        }
    }
    
    /**
     * This method tests the rowReduce method
     */
    public static void testRowReduce() {
        boolean allPassed = true;
        {//test 1: RREF is I
            String[][] matrix = new String[][] {{"0", "1/2", "3"}, 
                                                {"1/3", "0", "5"}, 
                                                {"2", "4", "1"}};
            String[][] expected = new String[][] {{"1", "0", "0"}, 
                                                  {"0", "1", "0"}, 
                                                  {"0", "0", "1"}};
            String[][] actual = RowReduction.rowReduce(matrix, matrix[0].length);
            if (!Arrays.deepEquals(expected, actual)) {
                System.out.println("testRowReduce: Test 1 failed");
                allPassed = false;
            }
        }
        {//test 2: RREF with zero row
            String[][] matrix = new String[][] {{"1", "2", "3", "1"}, 
                                                {"1", "1", "2", "1"}, 
                                                {"1", "2", "3", "1"}};
            String[][] expected = new String[][] {{"1", "0", "1", "1"}, 
                                                  {"0", "1", "1", "0"}, 
                                                  {"0", "0", "0", "0"}};
            String[][] actual = RowReduction.rowReduce(matrix, matrix[0].length);
            if (!Arrays.deepEquals(expected, actual)) {
                System.out.println("testRowReduce: Test 2 failed");
                allPassed = false;
            }
        }
        {//test 3: RREF with non pivot column preceding pivot column, more columns than rows
            String[][] matrix = new String[][] {{"1", "-1", "-1", "2"}, 
                                                {"2", "-2", "-1", "3"}, 
                                                {"-1", "1", "4", "0"}};
            String[][] expected = new String[][] {{"1", "-1", "0", "0"}, 
                                                  {"0", "0", "1", "0"}, 
                                                  {"0", "0", "0", "1"}};
            String[][] actual = RowReduction.rowReduce(matrix, matrix[0].length);
            if (!Arrays.deepEquals(expected, actual)) {
                System.out.println("testRowReduce: Test 3 failed");
                allPassed = false;
            }
        }
        {//test 4: more rows than columns
            String[][] matrix = new String[][] {{"1", "-1", "-1"}, 
                                                {"2", "-2", "-1"}, 
                                                {"-1", "1", "4"},
                                                {"4", "1", "2"},
                                                {"3", "3", "2"}};
            String[][] expected = new String[][] {{"1", "0", "0"}, 
                                                  {"0", "1", "0"}, 
                                                  {"0", "0", "1"},
                                                  {"0", "0", "0"},
                                                  {"0", "0", "0"}};
            String[][] actual = RowReduction.rowReduce(matrix, matrix[0].length);
            if (!Arrays.deepEquals(expected, actual)) {
                System.out.println("testRowReduce: Test 4 failed");
                allPassed = false;
            }  
        }
        if (allPassed) {
            System.out.println("All testRowReduce tests passed");
        }
    }
}
