package MatrixCalculator;

/**
 * This class performs Gauss-Jordan reduction through row operations on matrices in order to row 
 * reduce them into Reduced Row Echelon Form (RREF).
 * 
 * @author AOsterndorff
 *
 */
public class RowReduction {
    
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
        if (element.equals("0")) return "0";
        //check if element is negative
        boolean isNegative = element.contains("-") ? true : false;
        int[] splitFraction;
        String reciprocal = "";
        //split into array if fraction
        if (element.contains("/")) {
            splitFraction = RowOperations.splitFraction(element);
            if (splitFraction[NUMERATOR] == 0) return "0";
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
     *    is then add to those rows so that the pivot row has the only non zero value remaining in 
     *    that column.
     * 5) If no non zero value is found from the starting row index to the end of the column, then 
     *    the method returns null to indicate that no row operations took place.
     * 
     * @param newMatrix      The matrix copied from the original matrix. Will be altered.
     * @param startRow       The index of the row to start from. The pivot row.
     * @param col            The column containing the elements that will dictate row operations.
     * @return newMatrix     The altered parameter matrix.
     */
    public static String[][] rowReductionInColumn(String[][] newMatrix, int startRow, int col) {
        //System.out.println(MatrixString.printMatrix(newMatrix));//////////////////////////////////
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
                //System.out.println(MatrixString.printMatrix(newMatrix));//////////////////////////
            }           
        }
        //swap first non zero row to pivot row
        newMatrix = RowOperations.swapRows(newMatrix, startRow, rowToBePivot);
        //System.out.println(MatrixString.printMatrix(newMatrix));//////////////////////////////////
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
                //System.out.println(MatrixString.printMatrix(newMatrix));//////////////////////////
            }
        }
        if (firstNonzeroRow) return null;
        //System.out.println(MatrixString.printMatrix(newMatrix));//////////////////////////////////
        return newMatrix;
    } 
    
    /**
     * This method copies the original matrix into a newMatrix that Gauss-Jordan reduction will be
     * performed on to reduce the matrix to Reduced Row Echelon Form (RREF). It calls the 
     * rowReductionInColumn method to conduct the row reduction by incrementing column and rows. If
     * no row operations took place as indicated by a null return of rowReductionInColumn, then the
     * row is not incremented while the column continues to be.
     * 
     * @param matrix          The original matrix/augmented matrix input by the user.
     * @param leftMatrixWidth The width of the portion of the matrix to be row reduced. In an 
     *                        augmented matrix, it is the matrix left of the augmentation border. In
     *                        a regular matrix, it is the width of the matrix itself.
     * @return newMatrix      The copy of the original matrix that is row reduced to RREF.
     */
    public static String[][] rowReduce(String[][] matrix, int leftMatrixWidth) {
        String[][] newMatrix = new String[matrix.length][matrix[0].length];
        //copy matrix into newMatrix
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        //iteration by both row and column
        for (int i = 0,j = 0; i < matrix.length && j < leftMatrixWidth; ++i, ++j) {            
            String[][] tempMatrix = rowReductionInColumn(newMatrix, i, j); 
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
