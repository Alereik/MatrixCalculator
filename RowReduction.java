package MatrixCalculator;

/**
 * This class performs row reduction through row operations on matrices in order to row 
 * reduce them into Reduced Row Echelon Form (RREF).
 * 
 * @author AOsterndorff
 *
 */
public class RowReduction {
    
    /**
     * This method outputs each step in the row reduction process to the user if the user sets the
     * boolean parameter 'steps' to be true. row numbers are incremented to their vernacular 
     * numbering convention rather than the number of their index (Row1 is at index 0, Row2 is at
     * index 1, etc.).
     * 
     * @param newMatrix       The matrix being row operations are being conducted on.
     * @param leftMatrixWidth The number of columns that will be converted to RREF.
     * @param operation       The type of row operation being conducted.
     * @param row1            A row involved in an elementary row operation.
     * @param row2            A possible second row that may be involved in an elementary row 
     *                        operation, depending on which operation is being conducted.
     * @param scalar          The scalar that a row is scaled by if the operation calls for it.
     */
    public static void showSteps(String[][] newMatrix, int leftMatrixWidth, String operation, 
                                 int row1, int row2, String scalar) {
        ++row1;
        ++row2;
        //if denominator of scalar is one, turn scalar into whole number
        if (scalar != null && scalar.endsWith("/1")) {
            scalar = scalar.substring(0, scalar.indexOf("/"));
        }
        if (operation.equals("scale")) {
            System.out.println("Scale row" + row1 + " by " + scalar);
        }
        else if (operation.equals("addScaled")) {
            System.out.println("Add " + scalar + " times Row" + row1 + " to Row" + row2);
        }
        else {
            System.out.println("Swap Row" + row1 + " and Row" + row2);
        }
        MatrixString.printMatrix(newMatrix, leftMatrixWidth);
    }
    
    /**
     * This method returns the reciprocal of an element. If element is "0", or the numerator of
     * element is 0, then "0" is returned. Does not return whole numbers if the parameter numerator
     * is 1. Instead, the fraction version is returned (1/5 does not return 5, instead returns 5/1).
     * 
     * @param element     The element for which a reciprocal is returned.
     * @return reciprocal The multiplicative inverse of the element.
     */
    public static String getReciprocal(String element) {
        final int NUMERATOR = 0;
        final int DENOMINATOR = 1;
        if (element.equals("0")) {
            return "0";
        }
        //check if element is negative
        boolean isNegative = element.contains("-") ? true : false;
        int[] splitFraction;
        String reciprocal = "";
        //split into array if fraction
        if (element.contains("/")) {
            splitFraction = RowOperations.splitFraction(element);
            if (splitFraction[NUMERATOR] == 0) {
                return "0";
            }
            int tempVal = splitFraction[NUMERATOR];
            splitFraction[NUMERATOR] = splitFraction[DENOMINATOR];
            splitFraction[DENOMINATOR] = tempVal;
            reciprocal += splitFraction[NUMERATOR] + "/" + splitFraction[DENOMINATOR];
        }
        else {
            reciprocal += 1 + "/" + element;
        }
        //move '-' to numerator if element was negative
        if (isNegative) {
            reciprocal = "-" + reciprocal.replace("-", "");
        }
        return reciprocal;
    }
    
    /**
     * This method performs row operations along a column of a matrix, starting at a specified row
     * in a specified column. 
     * 1) If a row element that is not "0" is found, firstNonzeroRow is set to false in order to 
     *    prevent further iterations of the for loop that searches for the first non zero row.
     * 2) The first non zero element in the column is scaled to 1 by scalar multiplication with its 
     *    reciprocal (if it is not already at 1).
     * 3) The first non zero row is swapped with the starting row to become the pivot row (if it is 
     *    not already at the starting row).
     * 4) The pivot row is scaled to the negative of all other non zero values in the column and 
     *    is then added to those rows until it is the only non zero value remaining in that column.
     *    
     * - If no non zero value is found from the starting row index to the end of the column, then 
     *   the method returns null to indicate that no row operations took place.
     *       
     * - If steps is true, every elementary row operation performed during the row reduction 
     *   process will be output to the user.
     * 
     * @param newMatrix       The matrix copied from the original matrix. Will be altered.
     * @param startRow        The index of the row to start from. The pivot row.
     * @param col             The column containing the elements that will dictate row operations.
     * @param leftMatrixWidth The number of columns the row reduction process will be performed in.
     *                        This parameter is only meant to be passed along for use in the 
     *                        showSteps method when steps is true.
     * @param steps           Calls the showSteps method after every row operation if true.
     * @return newMatrix      The altered parameter matrix.
     */
    public static String[][] rowReductionInColumn(String[][] newMatrix, int startRow, int col, 
                                                  int leftMatrixWidth, boolean steps) {
        boolean firstNonzeroRow = true;
        for (int i = startRow; i < newMatrix.length && firstNonzeroRow; ++i) {
            //record first non zero row for later swapping to pivot row
            if (!newMatrix[i][col].equals("0")) {
                firstNonzeroRow = false;
                //scale the first non zero row to 1 if not already at 1
                if (!newMatrix[i][col].equals("1")) {
                    String reciprocal = getReciprocal(newMatrix[i][col]);
                    newMatrix[i] = RowOperations.scaleRow(newMatrix, i, reciprocal);
                    if (steps) {
                        showSteps(newMatrix, leftMatrixWidth, "scale", i, 0, reciprocal);
                    }
                }
            }   
            //swap first non zero row to pivot row
            if (startRow != i) {
                newMatrix = RowOperations.swapRows(newMatrix, startRow, i);
                if (steps) {
                    showSteps(newMatrix, leftMatrixWidth, "swap", startRow, i, null);
                }
            }
        }
        //subtract multiple of first non zero row from all other non zero rows
        for (int i = 0; i < newMatrix.length && !firstNonzeroRow; ++i) {
            if (i != startRow && !newMatrix[i][col].equals("0")) {
                String negativeElement;
                if (newMatrix[i][col].contains("-")) {
                    negativeElement = newMatrix[i][col].replace("-", "");
                }
                else {
                    negativeElement = "-" + newMatrix[i][col];
                }
                newMatrix = RowOperations.addScaledRow(newMatrix, i, startRow, negativeElement);
                if (steps) {
                    showSteps(newMatrix, leftMatrixWidth, "addScaled", startRow, i,
                              negativeElement);                
                }                     
            }
        }
        if (firstNonzeroRow) {
            return null;
        }
        return newMatrix;
    } 
    
    /**
     * This method copies the original matrix into a newMatrix that row reduction will be
     * performed on in order to reduce the matrix to Reduced Row Echelon Form (RREF). It calls the 
     * rowReductionInColumn method to conduct the row reduction and then increments the specified 
     * column and starting row. If no row operations took place (as indicated by a null return of 
     * rowReductionInColumn), then the row is not incremented for the next iteration.
     * 
     * @param matrix          The original matrix/augmented matrix input by the user.
     * @param leftMatrixWidth The width of the portion of the matrix to be row reduced. In an 
     *                        augmented matrix, it is the matrix left of the augmentation border. In
     *                        a regular, non augmented matrix, it is the width of the matrix itself.
     * @param steps           Shows the matrix before row reduction if true, and also is passed on 
     *                        to the rowReductionInColumn method so that each row operation is 
     *                        output to the used.
     * @return newMatrix      The copy of the original matrix that is row reduced to RREF.
     */
    public static String[][] rowReduce(String[][] matrix, int leftMatrixWidth, boolean steps) {
        String[][] newMatrix = new String[matrix.length][matrix[0].length];
        //copy matrix into newMatrix
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        if (steps) {
            MatrixString.printMatrix(newMatrix, leftMatrixWidth);
        }
        //iteration by both row and column
        for (int i = 0,j = 0; i < matrix.length && j < leftMatrixWidth; ++i, ++j) {            
            String[][] tempMatrix = rowReductionInColumn(newMatrix, i, j, leftMatrixWidth, steps);
            //row does not increment if non zero value not found in last iteration
            if (tempMatrix == null) {
                --i;
                continue;
            }
            else {
                newMatrix = tempMatrix;
            }
        }
        return newMatrix;
    }
}
