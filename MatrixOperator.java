package matrixCalculator;

/**
 * Performs operations on matrices.
 * 
 * @author AOsterndorff
 *
 */
public class MatrixOperator extends RowOperator{
    
    /**
     * Returns a sub matrix of a matrix consisting of all the rows and column of the original matrix
     * minus the row and column of the indicated element's position.
     * 
     * @param matrix     The matrix to have one of its rows and columns removed.
     * @param row        The row to be excluded from the sub matrix.
     * @param column     The column to be excluded from the sub matrix;
     * @return subMatrix The resulting matrix with out the indicated row and column from the 
     *                   previous matrix
     */
    private String[][] getSubMatrix(String[][] matrix, int row, int column) {
        String[][] subMatrix = new String[matrix.length - 1][matrix[0].length - 1];
        for (int i = 0; i < matrix.length - 1; ++i) {
            for (int j = 0; j < matrix[0].length - 1; ++j) {
                int oldRow = i < row ? i : i + 1;
                int oldColumn = j < column ? j : j + 1;
                subMatrix[i][j] = matrix[oldRow][oldColumn];
            }
        }
        return subMatrix;
     }
    
    /**
     * Returns the string representation of the cofactor of an element. Row and column are 
     * incremented by one, as the correct computation of the sign requires the vernacular numbering 
     * of row and column numbers.
     * 
     * @param matrix
     * @param row
     * @param column
     * @return 
     */
    public String getCofactor(String[][] matrix, int row, int column) {
        String[][] minor = getSubMatrix(matrix, row, column);
        ++row;//increment row and column for correct computation of sign
        ++column;
        String sign = String.valueOf((int) Math.pow(-1, row + column));
        String subDeterminant = getUpperTriangularForm(minor)[1][0][0];
        String cofactor = fractionMultiplication(sign, subDeterminant);
        return cofactor;
    }
}//TODO matrix multiplication and addition/subtraction
