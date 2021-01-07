package linearAlgebraCalculator;

/**
 * Performs row operations on a matrix.
 * 
 * @author AOsterndorff
 *
 */
public class RowOperator extends ElementOperator{
        
    /**
     * Multiplies a row in the matrix by a scalar.
     * 
     * @param matrix     The matrix containing the row to be scaled.
     * @param rowIndex   The index of the row to be scaled.
     * @param scalar     The scalar to multiply all elements of the specified row by.
     * @return scaledRow The scaled row.
     */
    public String[] scaleRow(String[][] matrix, int rowIndex, String scalar) {
        String[] scaledRow = new String[matrix[0].length];
        for (int j = 0; j < matrix[0].length; ++j) {
            scaledRow[j] = fractionMultiplication(matrix[rowIndex][j], scalar);
        }
        return scaledRow;
    } 

    /**
     * Multiplies a row in the matrix by a scalar and adds it to another row in the matrix.
     *
     * @param matrix     The matrix containing the rows to be added to and scaled.
     * @param rowAddedTo The index of the row to add the scaled row to.
     * @param rowScaled  The index of the row that is copied with the copy of this row being scaled 
     *                   and added to rowAddedTo. 
     * @param scalar     The scalar all elements in the copy of rowScaled are multiplied by.
     * @return newMatrix The resulting matrix after the row operation (adding a multiple of one row
     *                   to another) has completed.
     */
    public String[][] addScaledRow(String[][] matrix, int rowAddedTo, int rowScaled, 
                                          String scalar) {
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
     * Returns a matrix with two rows that were swapped relative to their positions in the original 
     * matrix.
     * 
     * @param matrix     The original matrix containing the two rows to have their positions 
     *                   swapped.
     * @param row1       The row to be swapped into row2's position.
     * @param row2       The row to be swapped into row1's position.
     * @return newMatrix The resulting matrix with the swapped rows.
     */
    public String[][] swapRows(String[][] matrix, int row1, int row2) {
        String[][] newMatrix = new String[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; ++i) {
            if (i == row1) {
                newMatrix[i] = matrix[row2];
            }
            else if (i == row2) {
                newMatrix[i] = matrix[row1];
            }
            else {
                newMatrix[i] = matrix[i];
            }
        }
        return newMatrix;
    }
    
    /**
     * Outputs the matrix that has been converted to an upper triangular form. Returns a string form
     * of the number to scale the triangular matrix's determinant by in order to compute the 
     * determinant of the parameter matrix.
     * 1) If the entry in the diagonal is zero, the first row with a non zero entry in that column 
     *    is swapped with it.
     * 2) All other rows with non zero entries in the column are scaled to one before being scaled
     *    to the value of the element in the diagonal. The reciprocal of these two scalings are 
     *    multiplied by the value of determinantScalar.
     * 3) The negative of the row with the diagonal entry is added to the scaled row, making the 
     *    scaled row's element in that column "0".
     * 4) The process is repeated along each column starting at the rows of the diagonal entries.
     * 
     * @param matrix                The matrix to be converted to upper triangular form.
     * @return matrixAndDeterminant The three dimensional array containing both the resulting upper
     *                              triangular matrix, as well as the determinant of the original
     *                              matrix.
     */
    public String[][][] getUpperTriangularForm(String[][] matrix) {
        String[][] tempMatrix = new String[matrix.length][matrix[0].length];
        String determinantScalar = "1";
        for (int i = 0; i < matrix.length; ++i) {//copy matrix
            tempMatrix[i] = matrix[i];
        }
        for (int j = 0; j < matrix.length; ++j) {
            for (int i = j; i < matrix.length; ++i) {//iterations start on the diagonal
                if (!tempMatrix[i][j].equals("0") && i != j) {//nonzero rows not on diagonal
                    if (tempMatrix[j][j].equals("0")) {//if diagonal entry is zero swap rows
                        tempMatrix = swapRows(tempMatrix, i, j);
                        determinantScalar = fractionMultiplication(determinantScalar, "-1");
                    }
                    else {//scale non diagonal row to 1, then multiple by diagonal row
                        String pivotReciprocal = getReciprocal(tempMatrix[j][j]);
                        String reciprocalOfScalar = fractionMultiplication(pivotReciprocal, 
                                                                           tempMatrix[i][j]);
                        String reciprocal = getReciprocal(tempMatrix[i][j]);
                        tempMatrix[i] = scaleRow(tempMatrix, i, reciprocal);//scale to 1
                        tempMatrix[i] = scaleRow(tempMatrix, i, tempMatrix[j][j]);//scale by pivot
                        determinantScalar = fractionMultiplication(determinantScalar, 
                                                                   reciprocalOfScalar);
                        tempMatrix = addScaledRow(tempMatrix, i, j, "-1");//add -pivot to row
                    }                    
                }
            }
        }
        String productOfDiagonals = "1";
        for (int i = 0; i < tempMatrix.length; ++i) {
            productOfDiagonals = fractionMultiplication(productOfDiagonals, tempMatrix[i][i]);
        }
        String determinant = fractionMultiplication(determinantScalar, productOfDiagonals);
        String[][][] matrixAndDeterminant = new String[][][] {tempMatrix,{{determinant}}};
        return matrixAndDeterminant;
    }
    
    /**
     * Outputs each step in the row reduction process to the user if the user sets the
     * boolean parameter 'steps' to be true. Row numbers are incremented to their vernacular 
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
    private void showSteps(String[][] newMatrix, int leftMatrixWidth, String operation, 
                                 int row1, int row2, String scalar) {
        ++row1;
        ++row2;
        //if denominator of scalar is one, turn scalar into whole number
        if (scalar != null && scalar.endsWith("/1")) {
            scalar = scalar.substring(0, scalar.indexOf("/"));
        }
        if (operation.equals("scale")) {
            System.out.println("Scale Row" + row1 + " by " + scalar);
        }
        else if (operation.equals("addScaled")) {
            System.out.println("Add " + scalar + " times Row" + row1 + " to Row" + row2);
        }
        else {
            System.out.println("Swap Row" + row1 + " and Row" + row2);
        }
        printer.printMatrix(newMatrix, leftMatrixWidth);
    }
    
    /**
     * Performs row operations along a column of a matrix, starting at a specified row in a 
     * specified column. 
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
     * @param newMatrix       The matrix copied from the original matrix. Will be altered.
     * @param startRow        The index of the row to start from. The pivot row.
     * @param col             The column containing the elements that will dictate row operations.
     * @param leftMatrixWidth The number of columns the row reduction process will be performed in.
     *                        This parameter is only meant to be passed along for use in the 
     *                        showSteps method when steps is true.
     * @return newMatrix      The altered parameter matrix.
     */
    private String[][] rowReductionInColumn(String[][] newMatrix, int startRow, int col, 
                                            int leftMatrixWidth, boolean showSteps) {
        boolean firstNonzeroRow = true;
        for (int i = startRow; i < newMatrix.length && firstNonzeroRow; ++i) {
            //record first non zero row for later swapping to pivot row
            if (!newMatrix[i][col].equals("0")) {
                firstNonzeroRow = false;
                //scale the first non zero row to 1 if not already at 1
                if (!newMatrix[i][col].equals("1")) {
                    String reciprocal = getReciprocal(newMatrix[i][col]);
                    newMatrix[i] = scaleRow(newMatrix, i, reciprocal);
                    if (showSteps) {
                        showSteps(newMatrix, leftMatrixWidth, "scale", i, 0, reciprocal);
                    }
                }
                //swap first non zero row to pivot row
                if (startRow != i) {
                    newMatrix = swapRows(newMatrix, startRow, i);
                    if (showSteps) {
                        showSteps(newMatrix, leftMatrixWidth, "swap", startRow, i, null);
                    }
                }
            }   
        }
        if (firstNonzeroRow) {
            return null;
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
                newMatrix = addScaledRow(newMatrix, i, startRow, negativeElement);
                if (showSteps) {
                    showSteps(newMatrix, leftMatrixWidth, "addScaled", startRow, i, 
                              negativeElement);
                }
            }
        }
        return newMatrix;
    } 
    
    /**
     * Copies the original matrix into a newMatrix that row reduction will be performed on in order 
     * to reduce the matrix to Reduced Row Echelon Form (RREF). It calls the rowReductionInColumn 
     * method to conduct the row reduction and then increments the specified column and starting 
     * row. If no row operations took place (as indicated by a null return of rowReductionInColumn),
     * then the row is not incremented for the next iteration.
     * 
     * @param matrix          The original matrix/augmented matrix input by the user.
     * @param leftMatrixWidth The width of the portion of the matrix to be row reduced. In an 
     *                        augmented matrix, it is the matrix left of the augmentation border. In
     *                        a regular, non augmented matrix, it is the width of the matrix itself.
     * @return newMatrix      The copy of the original matrix that is row reduced to RREF.
     */
    public String[][] rowReduce(String[][] matrix, int leftMatrixWidth, boolean showSteps) {
        String[][] newMatrix = new String[matrix.length][matrix[0].length];
        //copy matrix into newMatrix
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        //iteration by both row and column
        for (int i = 0,j = 0; i < matrix.length && j < leftMatrixWidth; ++i, ++j) {            
            String[][] tempMatrix = rowReductionInColumn(newMatrix, i, j, leftMatrixWidth, 
                                                         showSteps);
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

