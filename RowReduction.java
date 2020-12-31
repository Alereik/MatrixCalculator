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
     * @param scalar          The scalar a row is scaled by if the operation calls for a scalar.
     */
    public static void showSteps(String[][] newMatrix, int leftMatrixWidth, String operation, 
                                 int row1, int row2, String scalar) {
        ++row1;
        ++row2;
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
     * element is '0' then "0" is returned.
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
     * 1) If a row element that is not "0" is found, the first row with such an element is saved to
     *    the rowToBePivot variable so that row may be swapped into the pivot row (startRow).
     * 2) All non zero elements in the column  are scaled to 1 by scalar multiplication with their 
     *    reciprocals.
     * 3) The first non zero row found is swapped with the starting row to become the pivot row.
     * 4) The pivot row is scaled to the negative of all other non zero values in the column and 
     *    is then added to those rows so that the pivot row has the only non zero value remaining in 
     *    that column.
     * 5) If no non zero value is found from the starting row index to the end of the column, then 
     *    the method returns null to indicate that no row operations took place.
     *    
     * - If steps is true, all steps in the row reduction process will be output to the user.
     * 
     * @param newMatrix       The matrix copied from the original matrix. Will be altered.
     * @param startRow        The index of the row to start from. The pivot row.
     * @param col             The column containing the elements that will dictate row operations.
     * @param leftMatrixWidth The number of columns the row reduction process will be performed in.
     *                        This parameter is used only to pass along to the showSteps method if
     *                        that method is called.
     * @param steps           Calls the showSteps method after every row operation if true.
     * @return newMatrix      The altered parameter matrix.
     */
    public static String[][] rowReductionInColumn(String[][] newMatrix, int startRow, int col, 
                                                  int leftMatrixWidth, boolean steps) {
        boolean firstNonzeroRow = true;
        int rowToBePivot = startRow;
        for (int i = startRow; i < newMatrix.length; ++i) {
            //record first non zero row for later swapping to pivot row
            if (!newMatrix[i][col].equals("0") && firstNonzeroRow) {
                rowToBePivot = i;
                firstNonzeroRow = false;
            }
            //scale all non zero rows to 1 if not already at 1
            if (!newMatrix[i][col].equals("0") && !newMatrix[i][col].equals("1")) {
                String reciprocal = getReciprocal(newMatrix[i][col]);
                newMatrix[i] = RowOperations.scaleRow(newMatrix, i, reciprocal);
                if (steps) {
                    showSteps(newMatrix, leftMatrixWidth, "scale", i, 0, reciprocal);
                }
            }           
        }
        //swap first non zero row to pivot row
        newMatrix = RowOperations.swapRows(newMatrix, startRow, rowToBePivot);
        if (steps) {
            showSteps(newMatrix, leftMatrixWidth, "swap", startRow, rowToBePivot, null);
        }
        //subtract multiple of first non zero row from all other non zero rows
        for (int i = 0; i < newMatrix.length; ++i) {
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
        if (firstNonzeroRow) return null;
        return newMatrix;
    } 
    
    /**
     * This method copies the original matrix into a newMatrix that row reduction will be
     * performed on to reduce the matrix to Reduced Row Echelon Form (RREF). It calls the 
     * rowReductionInColumn method to conduct the row reduction by incrementing column and rows. If
     * no row operations took place as indicated by a null return of rowReductionInColumn, then the
     * row is not incremented while the column continues to be.
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
