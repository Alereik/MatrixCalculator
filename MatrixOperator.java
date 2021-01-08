package linAlgCalc;

import java.util.ArrayList;

/**
 * Performs operations on matrices.
 * 
 * @author AOsterndorff
 *
 */
public class MatrixOperator extends RowOperator{
    
	/**
	 * Returns a matrix scaled by a specified scalar.
	 * 
	 * @param matrix	 The matrix to be scaled.
	 * @param scalar	 The scalar to multiply every element of matrix by.
	 * @return newMatrix The resulting scaled matrix.
	 */
	public String[][] scaleMatrix(String[][] matrix, String scalar) {
		String[][]  newMatrix = new String[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; ++i) {
			newMatrix[i] = scaleRow(matrix, i, scalar);
		}
		return newMatrix;
	}
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
     * @param matrix    The matrix from which a cofactor is being determined.
     * @param row       The row of the cofactor.
     * @param column    The column of the cofactor.
     * @return cofactor The product of the minor's determinant and the computed sign. The sign is
     *                  computed as follows: (-1)^(rowNumber + columnNumber).
     */
    private String getCofactor(String[][] matrix, int row, int column) {
        String[][] minor = getSubMatrix(matrix, row, column);
        ++row;//increment row and column for correct computation of sign
        ++column;
        String sign = String.valueOf((int) Math.pow(-1, row + column));
        String subDeterminant = getUpperTriangularForm(minor)[1][0][0];
        String cofactor = fractionMultiplication(sign, subDeterminant);
        return cofactor;
    }
    
    /**
     * Multiplies two matrices together, with matrix one being on the left and matrix two being on 
     * the right of the multiplication order.
     * 
     * @param matrix1 The matrix on the left of the multiplication.
     * @param matrix2 The matrix on the right of the multiplication.
     */
    public void multiplyMatrices(String[][] matrix1, String[][] matrix2) {
        String[][] newMatrix = new String[matrix1.length][matrix2[0].length];
        if (matrix1[0].length != matrix2.length) {//make sure matrix1 numRows == matrix2 numColumns
            System.out.println("These matrices cannot be multiplied in this order\n");
            return;
        }
        for (int i = 0; i < matrix1.length; ++i) {
            for (int j = 0; j < matrix2[0].length; ++j) {
                String dotProduct = "0";
                for (int k = 0; k < matrix2.length; ++k) {
                    String elementProduct = fractionMultiplication(matrix1[i][k], matrix2[k][j]);
                    dotProduct = fractionAddition(dotProduct, elementProduct);
                }
                newMatrix[i][j] = dotProduct;
            }            
        }
        System.out.println("The product of the two matrices multiplied in this order is:");
        printer.printMatrix(newMatrix, matrix2[0].length);
    }
    
    /**
     * Adds or subtracts two matrices, outputting the resulting matrix to the user.
     * 
     * @param matrix1 The first matrix in the operation.
     * @param matrix2 The matrix to be added to or subtracted from the first matrix.
     * @param add     Performs addition on the matrices if true, subtraction if false.
     */
    public void addSubtractMatrices(String[][] matrix1, String[][] matrix2, boolean add) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            System.out.println("Both matrices must be the same size\n");
            return;
        }
        String newMatrix[][] = new String[matrix1.length][matrix1[0].length];
        String[][] tempMatrix2 = new String[matrix2.length][matrix2[0].length];
        if (add) {//addition of matrices
            for (int i = 0; i < matrix2.length; ++i) {
                tempMatrix2[i] = matrix2[i];
            }
            System.out.println("The sum of these two matrices is:");
        }
        else {//scale second matrix by -1 for subtraction of matrices
            for (int i = 0; i < matrix2.length; ++i) {
                tempMatrix2[i] = scaleRow(matrix2, i, "-1");
            }
            System.out.println("The difference of these two matrices is:");
        }
        for (int i = 0; i < matrix1.length; ++i) {//then add negative of second matrix to first
            for (int j = 0; j < matrix1[0].length; ++j) {
                newMatrix[i][j] = fractionAddition(matrix1[i][j], tempMatrix2[i][j]);
            }
        }
        printer.printMatrix(newMatrix, matrix1[0].length);
    }
    
    /**
     * Creates an augmented matrix by appended the identity matrix to the right of the parameter
     * matrix.
     * 
     * @param matrix           The matrix to be augmented with the identity matrix.
     * @return augmentedMatrix The resulting augmented matrix.
     */
    public String[][] augmentIdentity(String[][] matrix) {
        if (matrix.length != matrix[0].length) {
            System.out.println("Square matrix required");
            return null;
        }
        String[][] augmentedMatrix = new String[matrix.length][matrix[0].length * 2];
        for (int i = 0; i < augmentedMatrix.length; ++i) {
            for (int j = 0; j < augmentedMatrix[0].length; ++j) {
                if (j < matrix.length) {
                    augmentedMatrix[i][j] = matrix[i][j];
                }
                else if ((j - matrix.length) == i) {
                    augmentedMatrix[i][j] = "1";
                }
                else {
                    augmentedMatrix[i][j] = "0";
                }
            }
        }
        return augmentedMatrix;
    }
    
    /**
     * Determines whether a matrix is invertible.
     * 
     * @param matrix      The matrix being evaluated as to whether or not it is invertible.
     * @return true/false If the matrix is invertible, returns true.
     */
    public boolean checkInvertibility(String[][] matrix) {
        if (matrix.length != matrix[0].length) {
            return false;
        }
        else {//check if invertible
            String[][] checkMatrix = getUpperTriangularForm(matrix)[0];
            for (int k = 0; k < checkMatrix.length; ++k) {
                if (matrix.length != matrix[0].length || checkMatrix[k][k].equals("0")) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Returns the inverse of a matrix, computed through row reduction, to the user . Requires a 
     * square matrix.
     * 
     * @param matrix The matrix to be inverted if possible.
     * @param steps  Shows each row reduction step applied to the augmented matrix if true, or only 
     *               the inverse if false.
     */
    public String[][] getRowReducedInverse(String[][] matrix) {
        boolean invertible = checkInvertibility(matrix);
        if (!invertible) {
            return null;
        }
        //augment with identity matrix
        String[][] augmentedMatrix = augmentIdentity(matrix);
        augmentedMatrix = rowReduce(augmentedMatrix, matrix[0].length, true);
        //extract and show inverse from augmented matrix
        String[][] inverse = new String[augmentedMatrix.length][augmentedMatrix[0].length / 2];
        for (int i = 0; i < augmentedMatrix.length; ++i) {
            for (int j = augmentedMatrix[0].length / 2; j < augmentedMatrix[0].length; ++j) {
                inverse[i][j - (augmentedMatrix[0].length / 2)] = augmentedMatrix[i][j];
            }
        }
        return inverse;
    }
    
    /**
     * Returns the transpose of a matrix.
     * 
     * @param matrix The matrix to be transposed.
     */
    public String[][] getTranspose(String[][] matrix) {
        String[][] transpose = new String[matrix[0].length][matrix.length];
        for (int i = 0; i < transpose.length; ++i) {
            for (int j = 0; j < transpose[0].length; ++j) {
                transpose[i][j] = matrix[j][i];
            }
        }
        return transpose;
    }
    
    /**
     * Returns the adjoint of the matrix to the user. Requires a square matrix.
     */
    public String[][] getAdjoint(String[][] matrix) {
        if (matrix.length != matrix[0].length) {
            System.out.println("Square matrix required");
            return null;
        }//get cofactor matrix
        String[][] cofactorMatrix = new String[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                cofactorMatrix[i][j] = getCofactor(matrix, i, j);
            }
        }//transpose cofactor matrix into adjoint
        String[][] adjoint = getTranspose(cofactorMatrix);
        return adjoint;
    }
    
    
    /**
     * Returns the inverse of the matrix, computed through the adjoint method, to the user. Requires
     * a square matrix.
     * 1) The determinant of the matrix is computed, and it's reciprocal is obtained.
     * 2) The adjoint of the matrix is computed.
     * 3) The reciprocal of the determinant is multiplied by each entry in the adjoint, resulting in
     *    the inverse of the matrix.
     */
    public String[][] getAdjointInverse(String[][] matrix) {
        boolean invertible = checkInvertibility(matrix);
        if (!invertible) {
            return null;
        }//getUpperTriangularForm(matrix)[1][0][0] is the determinant of the original matrix
        String detReciprocal = getReciprocal(getUpperTriangularForm(matrix)[1][0][0]);
        String[][] adjoint = getAdjoint(matrix);
        String[][] inverse = new String[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                inverse[i][j] = fractionMultiplication(adjoint[i][j], detReciprocal);
            }
        }
        return inverse;
    }
    
    /**
     * Adds zero rows to the bottom of a matrix if the matrix has more columns than rows.
     * 
     * @param rowReduced  The matrix with more columns than rows.
     * @return tempMatrix The matrix with zero rows added to make it a square matrix.
     */
    private String[][] tempSquareMatrix(String[][] rowReduced) {
    	String[][] tempMatrix = new String[rowReduced[0].length][rowReduced[0].length];
    	for (int i = 0; i < rowReduced[0].length; ++i) {
    		for (int j = 0; j < rowReduced[0].length; ++j) {
    			tempMatrix[i][j] = "0";
    		}
    	}
    	for (int i = 0; i < rowReduced.length; ++i) {
    		tempMatrix[i] = rowReduced[i];
    	}
    	return tempMatrix;
    }
    
    /**
     * Returns the basis vectors of the nullity/null space of of a matrix as columns of a matrix.
     * 1) The original matrix is row reduced to RREF.
     * 2) The RREF matrix is checked for leading 1's, with the rows being recorded as having a free
     *    variable in the freeVar ArrayList or not having a free variable in the dependVar 
     *    ArrayList. If the matrix has more columns than rows, the tempSquareMatrix method is called
     *    to add zero rows to the bottom of the matrix. This ensures that every column is entered 
     *    into one of the ArrayLists.
     * 3) The null space matrix is created, with each column being a basis vector for the null 
     *    space. The non zero elements from the columns without leading 1's are placed in the 
     *    of the vectors that are scaled by their respective variables. The free variables are then
		  placed in the rows in the vectors in their respective positions. All other elements in the
		  array are given the value of "0".
     * 
     * @param matrix The matrix for which the nullity/null space of the matrix is being computed.
     */
    public String[][] getNullSpace(String[][] matrix) {
        ArrayList<Integer> freeVar = new ArrayList<>();
        ArrayList<Integer> dependVar= new ArrayList<>();
        String[][] rowReduced = rowReduce(matrix, matrix[0].length, false); 
        if (rowReduced[0].length > rowReduced.length) {
        	rowReduced = tempSquareMatrix(rowReduced);
        }
        for (int i = 0,j = 0; i < rowReduced.length && j < rowReduced[0].length; ++i, ++j) {
            if (!rowReduced[i][j].equals("1")) {
                freeVar.add(j);//record free variables in ArrayList
                --i;
            }
            else {
                dependVar.add(j);//record dependent variables in ArrayList
            }
        }                
        String[][] nullSpace = new String[rowReduced[0].length][freeVar.size()];
        for (int j = 0; j < freeVar.size(); ++j) {//columns in nullSpace are the vectors of nullity
            for (int i = 0, k = 0; i < rowReduced[0].length; ++i, ++k) {
                if (k < dependVar.size()) {//place non zero elements from free variable columns
                    nullSpace[dependVar.get(k)][j] = 
                                        fractionMultiplication(rowReduced[i][freeVar.get(j)], "-1");
                }
                if (i == freeVar.get(j)) {//place free variables
                    nullSpace[i][j] = "1";
                }
                if (nullSpace[i][j] == null) {//place "0" for all other elements
                    nullSpace[i][j] = "0";
                }
            }
        }
        printer.printNullSpace(nullSpace, freeVar);
        return nullSpace;
    }
}
