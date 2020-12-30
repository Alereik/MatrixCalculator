package MatrixCalculator;

import java.util.Arrays;

/**
 * This class tests the methods in the RowOperation class.
 * 
 * @author AOsterndorff
 *
 */
public class TestRowOperations {
    
    /**
     * This main method calls the test methods.
     * 
     * @param args Unused.
     */
    public static void main(String[] args) {
        testIntegerMultiplication();
        testReduceFraction();
        testSplitFraction();
        testFractionAddition();        
        testFractionIntScaling();
        testFractionMultiplication();
        testScaleRow();
        testAddScaledRow();
        testSwapRows();
    }
    
    /**
     * This method tests the integerMultiplication method.
     */
    public static void testIntegerMultiplication() {
        boolean allPassed = true;
        {//test 1: two valid parameters
            String factor1 = "4";
            String factor2 = "5";
            String expected = "20";
            String actual = RowOperations.integerMultiplication(factor1, factor2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual  
                                   + "\ntestIntegerMultiplication: Test 1 failed");
                allPassed = false;
            }
        }
        {//test 2: two valid parameters, one negative
            String factor1 = "4";
            String factor2 = "-5";
            String expected = "-20";
            String actual = RowOperations.integerMultiplication(factor1, factor2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual  
                                   + "\ntestIntegerMultiplication: Test 2 failed");
                allPassed = false;
            }
        }
        {//test 3: two valid parameters, both negative
            String factor1 = "-4";
            String factor2 = "-5";
            String expected = "20";
            String actual = RowOperations.integerMultiplication(factor1, factor2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual  
                                   + "\ntestIntegerMultiplication: Test 3 failed");
                allPassed = false;
            }
        }
        if (allPassed) {
            System.out.println("All testIntegerMultiplication tests passed");
        }
    }
    
    /**
     * This method tests the reduceFraction method.
     */
    public static void testReduceFraction() {
        boolean allPassed = true;
        {//test 1
            int[] fractionArr = new int[] {10,20};
            int[] expected = new int[] {1,2};
            int[] actual = RowOperations.reduceFraction(fractionArr);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n" 
                      + Arrays.toString(actual) + "\ntestReduceFraction: Test 1 failed");
                allPassed = false;
            }
        }
        {//test 2
            int[] fractionArr = new int[] {15000,60000};
            int[] expected = new int[] {1,4};
            int[] actual = RowOperations.reduceFraction(fractionArr);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n" 
                      + Arrays.toString(actual) + "\ntestReduceFraction: Test 2 failed");
                allPassed = false;
            }
        }
        {//test 3 double negative
            int[] fractionArr = new int[] {-6,-9};
            int[] expected = new int[] {2,3};
            int[] actual = RowOperations.reduceFraction(fractionArr);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n" 
                      + Arrays.toString(actual) + "\ntestReduceFraction: Test 3 failed");
                allPassed = false;
            }
        }
        {//test 4 negative numerator
            int[] fractionArr = new int[] {-6,9};
            int[] expected = new int[] {-2,3};
            int[] actual = RowOperations.reduceFraction(fractionArr);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n" 
                      + Arrays.toString(actual) + "\ntestReduceFraction: Test 4 failed");
                allPassed = false;
            }
        }
        {//test 5 negative denominator
            int[] fractionArr = new int[] {6,-9};
            int[] expected = new int[] {-2,3};
            int[] actual = RowOperations.reduceFraction(fractionArr);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n" 
                      + Arrays.toString(actual) + "\ntestReduceFraction: Test 5 failed");
                allPassed = false;
            }
        }
        if (allPassed) {
            System.out.println("All testReduceFraction tests passed");
        }
    }
    
    /**
     * This method tests the splitFraction method.
     */
    public static void testSplitFraction() {
        boolean allPassed = true;
        {//test 1: valid parameter
            String fraction = "3/4";
            int[] expected = new int[] {3, 4};
            int[] actual = RowOperations.splitFraction(fraction);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n" 
                      + Arrays.toString(actual) + "\ntestSplitFraction: Test 1 failed");
                allPassed = false;
            }
        }
        {//test 2: reducible fraction
            String fraction = "12/18";
            int[] expected = new int[] {2, 3};
            int[] actual = RowOperations.splitFraction(fraction);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n"  
                      + Arrays.toString(actual) + "\ntestSplitFraction: Test 2 failed");
                allPassed = false;
            }
        }
        {//test 3: zero denominator
            String fraction = "3/0";
            int[] expected = null;
            int[] actual = RowOperations.splitFraction(fraction);
            if (expected != actual) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n"  
                      + Arrays.toString(actual) + "\ntestSplitFraction: Test 3 failed");
                allPassed = false;
            }
        }
        {//test 4: valid parameter with negative numerator
            String fraction = "-3/4";
            int[] expected = new int[] {-3, 4};
            int[] actual = RowOperations.splitFraction(fraction);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n"  
                      + Arrays.toString(actual) + "\ntestSplitFraction: Test 4 failed");
                allPassed = false;
            }
        }
        {//test 5: valid parameter with negative denominator
            String fraction = "3/-4";
            int[] expected = new int[] {-3, 4};
            int[] actual = RowOperations.splitFraction(fraction);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n"  
                      + Arrays.toString(actual) + "\ntestSplitFraction: Test 5 failed");
                allPassed = false;
            }
        }
        {//test 6: valid parameter with negative numerator and denominator (double negative)
            String fraction = "-3/-4";
            int[] expected = new int[] {3, 4};
            int[] actual = RowOperations.splitFraction(fraction);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n"  
                      + Arrays.toString(actual) + "\ntestSplitFraction: Test 6 failed");
                allPassed = false;
            }
        }
        if (allPassed) {
            System.out.println("All testSplitFraction tests passed");
        }
    }
    
    /**
     * This method tests the fractionAddition method.
     */
    public static void testFractionAddition() {
        boolean allPassed = true;
        {//test 1: same denominator, no reduction required
            String fraction1 = "1/5";
            String fraction2 = "2/5";
            String expected = "3/5";
            String actual = RowOperations.fractionAddition(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual  
                                   + "\ntestFractionAddition: Test 1 failed");
                allPassed = false;
            }
        }
        {//test 2: same denominator, reduction required
            String fraction1 = "2/9";
            String fraction2 = "4/9";
            String expected = "2/3";
            String actual = RowOperations.fractionAddition(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual  
                                   + "\ntestFractionAddition: Test 2 failed");
                allPassed = false;
            }
        }
        {//test 3: different denominator no reduction required
            String fraction1 = "3/5";
            String fraction2 = "1/2";
            String expected = "11/10";
            String actual = RowOperations.fractionAddition(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual  
                                   + "\ntestFractionAddition: Test 3 failed");
                allPassed = false;
            }
        }
        {//test 4: different denominator reduction required
            String fraction1 = "3/4";
            String fraction2 = "1/6";
            String expected = "11/12";
            String actual = RowOperations.fractionAddition(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual  
                                   + "\ntestFractionAddition: Test 4 failed");
                allPassed = false;
            }
        }
        {//test 5: larger negative fraction
            String fraction1 = "-3/8";
            String fraction2 = "1/8";
            String expected = "-1/4";
            String actual = RowOperations.fractionAddition(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual  
                                   + "\ntestFractionAddition: Test 5 failed");
                allPassed = false;
            }
        }
        {//test 6: smaller negative fraction
            String fraction1 = "3/8";
            String fraction2 = "-1/8";
            String expected = "1/4";
            String actual = RowOperations.fractionAddition(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual  
                                   + "\ntestFractionAddition: Test 6 failed");
                allPassed = false;
            }
        }
        {//test 7: two negative fractions
            String fraction1 = "-3/8";
            String fraction2 = "-1/8";
            String expected = "-1/2";
            String actual = RowOperations.fractionAddition(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual  
                                   + "\ntestFractionAddition: Test 7 failed");
                allPassed = false;
            }
        }
        {//test 8: a denominator of 1, no reduction required
            String fraction1 = "1/5";
            String fraction2 = "2/1";
            String expected = "11/5";
            String actual = RowOperations.fractionAddition(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual  
                                   + "\ntestFractionAddition: Test 8 failed");
                allPassed = false;
            }
        }
        if (allPassed) {
            System.out.println("All testFractionAddition tests passed");
        }
    }
    
    /**
     * This method tests the fractionIntScaling method.
     */
    public static void testFractionIntScaling() {
        boolean allPassed = true;
        {//test 1: valid parameters
            String fraction = "3/4";
            String scalar = "5";
            String expected = "15/4";
            String actual = RowOperations.fractionIntScaling(fraction, scalar);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual  
                                   + "\ntestfractionIntScaling: Test 1 failed");
                allPassed = false;
            }
        }
        {//test 2: reducible fraction
            String fraction = "12/18";
            String scalar = "5";
            String expected = "10/3";
            String actual = RowOperations.fractionIntScaling(fraction, scalar);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\ntestfractionIntScaling: Test 2 failed");
                allPassed = false;
            }
        }
        {//test 3: numerator equal to denominator
            String fraction = "3/15";
            String scalar = "5";
            String expected = "1";
            String actual = RowOperations.fractionIntScaling(fraction, scalar);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\ntestfractionIntScaling: Test 3 failed");
                allPassed = false;
            }
        }
        {//test 4: denominator equal to 1
            String fraction = "3/1";
            String scalar = "5";
            String expected = "15";
            String actual = RowOperations.fractionIntScaling(fraction, scalar);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\ntestfractionIntScaling: Test 4 failed");
                allPassed = false;
            }
        }
        {//test 5: reducible scaled fraction should be reduced
            String fraction = "1/20";
            String scalar = "5";
            String expected = "1/4";
            String actual = RowOperations.fractionIntScaling(fraction, scalar);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\ntestfractionIntScaling: Test 5 failed");
                allPassed = false;
            }
        }
        {//test 6: zero factor
            String fraction = "3/4";
            String scalar = "0";
            String expected = "0";
            String actual = RowOperations.fractionIntScaling(fraction, scalar);
            if (expected != actual) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\ntestfractionIntScaling: Test 6 failed");
                allPassed = false;
            }
        }
        if (allPassed) {
            System.out.println("All testFractionIntScaling tests passed");
        }
    }
    
    /**
     * This method tests the fractionMultiplication method.
     */
    public static void testFractionMultiplication() {
        boolean allPassed = true;
        {//test 1: valid parameters
            String fraction1 = "3/4";
            String fraction2 = "7/8";
            String expected = "21/32";
            String actual = RowOperations.fractionMultiplication(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\nestFractionMultiplication: Test 1 failed");
                allPassed= false;
            }
        }
        {//test 2: reducible product fraction should be reduced
            String fraction1 = "3/4";
            String fraction2 = "4/5";
            String expected = "3/5";
            String actual = RowOperations.fractionMultiplication(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\nestFractionMultiplication: Test 2 failed");
                allPassed= false;
            }
        }
        {//test 3: product fraction numerator equal to product fraction denominator
            String fraction1 = "3/4";
            String fraction2 = "4/3";
            String expected = "1";
            String actual = RowOperations.fractionMultiplication(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\nestFractionMultiplication: Test 3 failed");
                allPassed= false;
            }
        }
        {//test 4: product fraction denominator equal to 1
            String fraction1 = "3/1";
            String fraction2 = "4/1";
            String expected = "12";
            String actual = RowOperations.fractionMultiplication(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\nestFractionMultiplication: Test 4 failed");
                allPassed= false;
            }
        }
        {//test 5: one negative factor
            String fraction1 = "-3/4";
            String fraction2 = "2/5";
            String expected = "-3/10";
            String actual = RowOperations.fractionMultiplication(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\nestFractionMultiplication: Test 5 failed");
                allPassed= false;
            }
        }
        {//test 6: two negative factors
            String fraction1 = "-3/4";
            String fraction2 = "-2/5";
            String expected = "3/10";
            String actual = RowOperations.fractionMultiplication(fraction1, fraction2);
            if (!expected.equals(actual)) {
                System.out.println("expected:\n" + expected + "\nactual:\n" + actual 
                                   + "\nestFractionMultiplication: Test 6 failed");
                allPassed= false;
            }
        }
        if (allPassed) {
            System.out.println("All testFractionMultiplication tests passed");
        }
    }
    
    /**
     * This method tests the multiplyRow method.
     */
    public static void testScaleRow() {
        boolean allPassed = true;
        {//test 1: positive integer scalar
            String[][] matrix = new String[][] {{"1", "0", "3/4"}, {"3/4", "1", "1/2"}, 
                                                {"3/4", "1/2", "1"}};
            String scalar = "3";
            int rowIndex = 0;
            String[] expected = new String[] {"3", "0", "9/4"};
            String[] actual = RowOperations.scaleRow(matrix, rowIndex, scalar);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n"  
                      + Arrays.toString(actual) + "\ntestScaleRow: Test 1 failed");
                allPassed = false;
            }
        }
        {//test 2: negative integer scalar
            String[][] matrix = new String[][] {{"1", "1/2", "3/4"}, {"-3/4", "1", "0"}, 
                                                {"3/4", "1/2", "1"}};
            String scalar = "-3";
            int rowIndex = 1;
            String[] expected = new String[] {"9/4", "-3", "0"};
            String[] actual = RowOperations.scaleRow(matrix, rowIndex, scalar);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n"  
                      + Arrays.toString(actual) + "\ntestScaleRow: Test 2 failed");
                allPassed = false;
            }
        }
        {//test 3: positive fractional scalar
            String[][] matrix = new String[][] {{"1", "1/2", "3/4"}, {"3/4", "1", "1/2"}, 
                                                {"0", "1/2", "1"}};
            String scalar = "3/5";
            int rowIndex = 2;
            String[] expected = new String[] {"0", "3/10", "3/5"};
            String[] actual = RowOperations.scaleRow(matrix, rowIndex, scalar);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n"  
                      + Arrays.toString(actual) + "\ntestScaleRow: Test 3 failed");
                allPassed = false;
            }
        }
        {//test 4: negative fractional scalar
            String[][] matrix = new String[][] {{"1", "1/2", "3/4"}, {"3/4", "1", "1/2"}, 
                                                {"3/4", "-1/2", "1"}};
            String scalar = "-3/5";
            int rowIndex = 2;
            String[] expected = new String[] {"-9/20", "3/10", "-3/5"};
            String[] actual = RowOperations.scaleRow(matrix, rowIndex, scalar);
            if (!Arrays.equals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.toString(expected) + "\nactual:\n"  
                      + Arrays.toString(actual) + "\ntestScaleRow: Test 4 failed");
                allPassed = false;
            }
        }
        if (allPassed) {
            System.out.println("All testScaleRow tests passed");
        }
    }
    
    /**
     * This method tests the AddScaledRow method.
     */
    public static void testAddScaledRow() {
        boolean allPassed = true;
        {//test 1 fractional scalar
            String[][] matrix = new String[][] {{"1", "0", "3/4"}, {"3/4", "1", "1/2"}, 
                                                {"3/4", "1/2", "1"}};
            int rowAddedTo = 1;
            int rowScaled = 0;
            String scalar = "2/5";
            String[][] expected = new String[][] {{"1", "0", "3/4"}, {"23/20", "1", "4/5"}, 
                                              {"3/4", "1/2", "1"}};
            String[][] actual = RowOperations.addScaledRow(matrix, rowAddedTo, rowScaled, scalar);
            if (!Arrays.deepEquals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.deepToString(expected) + "\nactual:\n"  
                      + Arrays.deepToString(actual) + "\ntestAddScaledRow: Test 1 failed");
                allPassed = false;
            }
        }
        {//test 2 whole number scalar
            String[][] matrix = new String[][] {{"1", "0", "0"}, {"0", "1", "0"}, {"0", "1", "1"}};
            int rowAddedTo = 2;
            int rowScaled = 1;
            String scalar = "-1";
            String[][] expected = new String[][] {{"1", "0", "0"}, {"0", "1", "0"},
                                                  {"0", "0", "1"}};
            String[][] actual = RowOperations.addScaledRow(matrix, rowAddedTo, rowScaled, scalar);
            if (!Arrays.deepEquals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.deepToString(expected) + "\nactual:\n"  
                      + Arrays.deepToString(actual) + "\ntestAddScaledRow: Test 2 failed");
                allPassed = false;
            }
        }
        {//test 3 negative inverse scalar
            String[][] matrix = new String[][] {{"1", "0", "0"}, {"0", "4/5", "0"}, 
                                                {"0", "1", "1"}};
            int rowAddedTo = 2;
            int rowScaled = 1;
            String scalar = "-5/4";
            String[][] expected = new String[][] {{"1", "0", "0"}, {"0", "4/5", "0"},
                                                  {"0", "0", "1"}};
            String[][] actual = RowOperations.addScaledRow(matrix, rowAddedTo, rowScaled, scalar);
            if (!Arrays.deepEquals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.deepToString(expected) + "\nactual:\n"  
                      + Arrays.deepToString(actual) + "\ntestAddScaledRow: Test 3 failed");
                allPassed = false;
            }
        }
        if (allPassed) {
            System.out.println("All testAddScaledRow tests passed");
        }
    }
    
    /**
     * This method tests the swapRows method.
     */
    public static void testSwapRows() {
        boolean allPassed = true;
        {//test 1 3x3 matrix
            String[][] matrix = new String[][] {{"1", "0", "0"}, {"0", "0", "1"}, {"0", "1", "0"}};
            int row1 = 1;
            int row2 = 2;
            String[][] expected = new String[][] {{"1", "0", "0"}, {"0", "1", "0"},
                                                  {"0", "0", "1"}};
            String[][] actual = RowOperations.swapRows(matrix, row1, row2);
            if (!Arrays.deepEquals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.deepToString(expected) + "\nactual:\n"  
                      + Arrays.deepToString(actual) + "\ntestSwapRows: Test 1 failed");
                allPassed = false;
            }
        }
        {//test 2 5x5 matrix, rows swapped not adjacent
            String[][] matrix = new String[][] {{"1", "0", "0", "0", "0"}, 
                                                {"0", "0", "0", "1", "0"}, 
                                                {"0", "0", "1", "0", "0"},
                                                {"0", "1", "0", "0", "0"},
                                                {"0", "0", "0", "0", "1"}};
            int row1 = 3;
            int row2 = 1;
            String[][] expected = new String[][] {{"1", "0", "0", "0", "0"}, 
                                                  {"0", "1", "0", "0", "0"}, 
                                                  {"0", "0", "1", "0", "0"},
                                                  {"0", "0", "0", "1", "0"},
                                                  {"0", "0", "0", "0", "1"}};
            String[][] actual = RowOperations.swapRows(matrix, row1, row2);
            if (!Arrays.deepEquals(expected, actual)) {
                System.out.println("expected:\n" + Arrays.deepToString(expected) + "\nactual:\n"  
                      + Arrays.deepToString(actual) + "\ntestSwapRows: Test 2 failed");
                allPassed = false;
            }
        }
        if (allPassed) {
            System.out.println("All testSwapRows tests passed");
        }
    }
}
