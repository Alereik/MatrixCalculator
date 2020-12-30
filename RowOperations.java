package MatrixCalculator;

/**
 * This class performs elementary row operations on matrices.
 * 
 * @author AOsterndorff
 *
 */
public class RowOperations {

    /*
     * Integer arrays will have a length of 2, with the first element holding the value of the
     * numerator of a fraction and the second element holding the value of the denominator.
     */
    final static int NUMERATOR = 0;
    final static int DENOMINATOR = 1;

    /**
     * This method multiplies an integer by another integer by converting numerical characters in a
     * string to integers, multiplying them, and converting them back into string literals.
     * 
     * @param factor1  The first string from which numerical characters are parsed and multiplied.
     * @param factor2  The second string from which numerical characters are parsed and multiplied.
     * @return product The string containing the numerical characters of the product.
     */
    public static String integerMultiplication(String factor1, String factor2) {
        String product;
        int firstFactor = Integer.parseInt(factor1);
        int secondFactor = Integer.parseInt(factor2);
        product = String.valueOf(firstFactor * secondFactor);
        return product;
    }

    /**
     * This method reduces fractions split into an int array of length 2, where the first index of 
     * the array is the numerator and the second index of the array is the denominator.
     * 
     * @param fractionArr The array containing the numerator and denominator to be reduced.
     * @return reducedArr The array containing the reduced numerator and denominator.
     */
    public static int[] reduceFraction(int[] fractionArr) {
        boolean negativeFraction = false;
        int[] reduced = new int[] {fractionArr[NUMERATOR], fractionArr[DENOMINATOR]};
        //remove double negative in fraction if necessary
        if (fractionArr[NUMERATOR] < 0 && fractionArr[DENOMINATOR] < 0) {
            fractionArr[NUMERATOR] *= -1;
            fractionArr[DENOMINATOR] *= -1;
        }
        //temporarily turn fraction positive for reduction
        for (int i = 0; i < fractionArr.length; ++i) {
            if (fractionArr[i] < 0) {
                fractionArr[i] *= -1;
                negativeFraction = true;
            }
        }
        int smallerNum = (fractionArr[NUMERATOR] > fractionArr[DENOMINATOR]) ? 
            fractionArr[DENOMINATOR] : fractionArr[NUMERATOR];
            //smaller number is greatest POSSIBLE common factor
            for (int i = 1; i <= smallerNum; ++i) {
                if ((fractionArr[NUMERATOR] % i == 0) && (fractionArr[DENOMINATOR] % i == 0)) {
                    reduced[NUMERATOR] = fractionArr[NUMERATOR] / i;
                    reduced[DENOMINATOR] = fractionArr[DENOMINATOR] / i;
                }
            }
            if (negativeFraction) {
                reduced[NUMERATOR] *= -1;
            }
            return reduced;
    }

    /**
     * This method takes a fraction in string form and splits it along the '/' character, parsing 
     * the integers and putting them into an array.
     * 
     * The first element of the resulting array contains the numerator of the fraction, and the 
     * second element contains the denominator.
     * 
     * @param fraction       The string literal of a fraction to be split and parsed.
     * @return splitFraction The array containing the numerator and denominator of the fraction.
     */
    public static int[] splitFraction(String fraction) {
        String[] split = fraction.split("/");
        int[] splitFraction = new int[2];
        splitFraction[NUMERATOR] = Integer.parseInt(split[0]);//splitFraction[0]
        splitFraction[DENOMINATOR] = Integer.parseInt(split[1]);//splitFraction[1]
        //reduce fraction if possible
        splitFraction = reduceFraction(splitFraction);
        //if zero denominator
        if (splitFraction[DENOMINATOR] == 0) {
            return null;
        }
        return splitFraction;
    }

    /**
     * This method adds two fractions together. Any parameters not in fraction form are converted
     * to fractions at the start of the method.
     * 
     * @param fraction1 The first fraction to be added.
     * @param fraction2 The second fraction to be added.
     * @return sum      The fraction that is the sum of the parameter fractions.
     */
    public static String fractionAddition(String fraction1, String fraction2) {
        //convert non fractions to fractions
        if (!fraction1.contains("/")) {
            fraction1 += "/1";
        }
        if (!fraction2.contains("/")) {
            fraction2 += "/1";
        }
        int[] firstFraction = splitFraction(fraction1);
        int[] secondFraction = splitFraction(fraction2);
        int[] sumFraction = new int[2];
        String sum = "";
        //same denominator
        if (firstFraction[DENOMINATOR] == secondFraction[DENOMINATOR]) {
            sumFraction[NUMERATOR] = firstFraction[NUMERATOR] + secondFraction[NUMERATOR];
            sumFraction[DENOMINATOR] = firstFraction[DENOMINATOR];
        }
        //different denominators
        else {
            sumFraction[NUMERATOR] = 
                (firstFraction[NUMERATOR] * secondFraction[DENOMINATOR])
                + (secondFraction[NUMERATOR] * firstFraction[DENOMINATOR]);

            sumFraction[DENOMINATOR] = 
                (firstFraction[DENOMINATOR] * secondFraction[DENOMINATOR]);
        }
        //reduce resulting fraction if possible
        sumFraction = reduceFraction(sumFraction);
        //numerator equal to denominator
        if (sumFraction[NUMERATOR] == sumFraction[DENOMINATOR]) {
            sum = "1";
        }
        else if (sumFraction[NUMERATOR] == 0) {
            sum = "0";
        }
        //denominator of 1
        else if (sumFraction[DENOMINATOR] == 1) {
            sum = String.valueOf(sumFraction[NUMERATOR]);
        }
        else {
            sum += sumFraction[NUMERATOR] + "/" + sumFraction[DENOMINATOR];
        }
        return sum;
    }
    
    /**
     * This method multiplies a fraction by an integer by calling the splitFraction method to 
     * convert the numerical characters in the fraction string to integers, multiplying the 
     * numerator by the intFactor, and converting the result into a string.
     * 
     * @param fraction  The string representation of the fraction to be scaled.
     * @param scalar The string representation of the integer to scale the fraction by.
     * @return product  The resultant scaled fraction.
     */
    public static String fractionIntScaling(String fraction, String scalar) {
        int[] splitFraction = splitFraction(fraction);
        String product;
        //zero scalar
        if (scalar == "0") {
            return "0";
        }            
        splitFraction[NUMERATOR] *= Integer.parseInt(scalar);
        //reduce fraction is possible
        splitFraction = reduceFraction(splitFraction);
        //numerator equal to denominator
        if (splitFraction[NUMERATOR] == splitFraction[DENOMINATOR]) {
            if (splitFraction[NUMERATOR] == 0) {
                product = "0";
            }
            else {
                product = "1";
            }    
        }
        //denominator equal to 1
        else if (splitFraction[DENOMINATOR] == 1) {
            product = String.valueOf(splitFraction[NUMERATOR]);
        }
        else {
            product = splitFraction[NUMERATOR] + "/" 
                + splitFraction[DENOMINATOR];
        }
        return product;
    }
    
    /**
     * This method multiplies a fraction by another fraction by calling the splitFraction method to
     * convert the numerical characters in the fractions string to integers, multiplying the 
     * numerators and denominators by each other, and converting the result into a string.
     * 
     * @param fraction1 The string representation of a fraction to multiply with another.
     * @param fraction2 The string representation of the other fraction to be multiplied with.
     * @return product  The resultant product of the two multiplied fractions.
     */
    public static String fractionMultiplication(String fraction1, String fraction2) {
        int[] firstFraction = splitFraction(fraction1);
        int[] secondFraction = splitFraction(fraction2);
        String product;
        int[] productFraction = new int[] {firstFraction[NUMERATOR] * secondFraction[NUMERATOR],
            firstFraction[DENOMINATOR] * secondFraction[DENOMINATOR]};
        //reduce fraction if possible
        productFraction = reduceFraction(productFraction);
        //numerator equal to denominator
        if (productFraction[NUMERATOR] == productFraction[DENOMINATOR]) {
            if (productFraction[NUMERATOR] == 0) {
                product = "0";
            }
            else {
                product = "1";
            } 
        }
        //denominator equal to 1
        else if (productFraction[DENOMINATOR] == 1) {
            product = String.valueOf(productFraction[NUMERATOR]);
        }
        else {
            product = productFraction[NUMERATOR] + "/" 
                + productFraction[DENOMINATOR];
        }
        return product;
    }
    
    /**
     * This method multiplies a row in the matrix by a scalar.
     * 
     * @param matrix     The matrix containing the row to be scaled.
     * @param rowIndex   The index of the row to be scaled.
     * @param scalar     The scalar to multiply all elements of the specified row by.
     * @return scaledRow The scaled row.
     */
    public static String[] scaleRow(String[][] matrix, int rowIndex, String scalar) {
        if (matrix == null || scalar == null || scalar.equals("")) {
            return null;
        }
        String[] scaledRow = new String[matrix[0].length];
        for (int j = 0; j < matrix[0].length; ++j) {
            //both factors are fractions
            if (matrix[rowIndex][j].contains("/") && scalar.contains("/")) {
                scaledRow[j] = fractionMultiplication(matrix[rowIndex][j], scalar);
            }
            //only the row element factor is a fraction
            else if (matrix[rowIndex][j].contains("/") && !scalar.contains("/")) {
                scaledRow[j] = fractionIntScaling(matrix[rowIndex][j], scalar);
            }
            //only the scalar is a fraction
            else if (!matrix[rowIndex][j].contains("/") && scalar.contains("/")) {
                scaledRow[j] = fractionIntScaling(scalar, matrix[rowIndex][j]);
            }
            //both factors are integers
            else {
                scaledRow[j] = integerMultiplication(matrix[rowIndex][j], scalar);
            }            
        }
        return scaledRow;
    } 

    /**
     * This method multiplies a row in the matrix by a scalar and adds it to another row in the
     * matrix.
     *
     * @param matrix     The matrix containing the rows to be added to and scaled.
     * @param rowAddedTo The index of the row to add the scaled row to.
     * @param rowScaled  The index of the row that is copied with the copy of this row being scaled 
     *                   and added to rowAddedTo. 
     * @param scalar     The scalar all elements in the copy of rowScaled are multiplied by.
     * @return newMatrix The resulting matrix after the row operation (adding a multiple of one row
     *                   to another) has completed.
     */
    public static String[][] addScaledRow(String[][] matrix, int rowAddedTo, int rowScaled, 
                                          String scalar) {
        if (matrix == null || scalar == null || scalar.equals("")) {
            return null;
        }
        String[][] newMatrix = new String[matrix.length][matrix[0].length];
        //scale row to be added to specified row
        String[] scaledRow = scaleRow(matrix, rowScaled, scalar);
        //build new matrix
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                //added scaled elements from scaled row to elements from specified row
                if (i == rowAddedTo) {
                    newMatrix[i][j] = fractionAddition(matrix[i][j], scaledRow[j]);
                }
                //copy all other elements from all other rows
                else {
                    newMatrix[i][j] = matrix[i][j];
                }
            }
        }
        return newMatrix;
    }
    
    /**
     * This method returns a matrix with two rows that were swapped relative to their positions in
     * the original matrix.
     * 
     * @param matrix     The original matrix containing the two rows to have their positions 
     *                   swapped.
     * @param row1       The row to be swapped into row2's position.
     * @param row2       The row to be swapped into row1's position.
     * @return newMatrix The resulting matrix with the swapped rows.
     */
    public static String[][] swapRows(String[][] matrix, int row1, int row2) {
        String[][] newMatrix = new String[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                if (i == row1) {
                    newMatrix[i][j] = matrix[row2][j];
                }
                else if (i == row2) {
                    newMatrix[i][j] = matrix[row1][j];
                }
                else {
                    newMatrix[i][j] = matrix[i][j];
                }
            }
        }
        return newMatrix;
    }
}
