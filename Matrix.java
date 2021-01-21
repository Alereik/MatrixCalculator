package linAlgCalc;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Matrix objects for which linear algebra computations can be performed on and/or with.
 * 
 * @author AOsterndorff
 *
 */
public class Matrix {

    private String[][] matrix;
    private int numRows;
    private int numColumns;
    private MatrixOperator operator = new MatrixOperator();
    private PrintStringBuilder printer = new PrintStringBuilder();

    public Matrix(Scanner input) {
        setDimensions(input);
        matrix = setMatrixArray(input);
    }

    public Matrix(String[][] matrix) {
        this.numRows = matrix.length;
        this.numColumns = matrix[0].length;
        this.matrix = matrix;
    }

    /**
     * Prompts the user to enter in the number of rows and columns that the new matrix will have. If
     * the input is not a number, the user is prompted again until a valid input is received. If the
     * user enters a valid number, the absolute value of that number is taken.
     * 
     * @param input The scanner that obtains the user's input.
     */
    private void setDimensions(Scanner input) {
        System.out.println("Enter the number of rows the matrix will have");
        while (!input.hasNextInt()) {
            input.next();
            System.out.println("Please enter a positive integer");
        }
        numRows = Math.abs(input.nextInt());
        numRows = numRows < 1 ? numRows + 1 : numRows;
        System.out.println("Enter the number of columns the matrix will have");
        while (!input.hasNextInt()) {
            input.next();
            System.out.println("Please enter a positive integer");
        }
        numColumns = Math.abs(input.nextInt());
        numColumns = numColumns < 1 ? numColumns + 1 : numColumns;
        input.nextLine();
    }

    /**
     * Prompts the user to enter each element of the matrix to be used in computations. Each element
     * is checked for validity by calling the checkElementValidity method. If the user enters an 
     * invalid entry, the entry is not accepted and the user is re-prompted to enter that position's
     * element until a valid entry is entered. 
     * 
     * @param input   The scanner that obtains the user's input.
     * @return matrix The matrix consisting of elements entered by the user.
     */
    private String[][] setMatrixArray(Scanner input) {
        String[][] matrix = new String[numRows][numColumns];
        for (int i = 0; i < matrix.length; ++i) {
            Arrays.fill(matrix[i], "_");
        }
        boolean isValidEntry = true;
        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numColumns; ++j) {
                System.out.println("Enter the element for Row" + (i + 1) + " , Column" + (j + 1));
                matrix[i][j] = input.nextLine().trim();
                isValidEntry = operator.checkElementValidity(matrix[i][j]);
                if (!isValidEntry) {
                    System.out.println("Invalid: Element must be a whole number or a fraction");
                    --j;
                }
                else {
                    printer.printMatrix(matrix, numColumns);
                }
            }
        }
        return matrix;
    }

    /**
     * Returns the number of rows in the matrix.
     * 
     * @return numRows The number of rows in the matrix.
     */
    public int getNumRows() {
    	System.out.println("This matrix has " + numRows + " rows.");
        return numRows;
    }

    /**
     * Returns the number of columns in the matrix.
     * 
     * @return numColumns The number of columns in the matrix.
     */
    public int getNumColumns() {
    	System.out.println("This matrix has " + numColumns + " columns.");
        return numColumns;
    }

    /**
     * Returns the matrix's two dimensional array.
     * 
     * @return matrix The two dimensional array housing the matrix's values.
     */
    public String[][] getMatrixArray() {
        return matrix;
    }

    /**
     * Outputs the matrix scaled by a specified scalar.
     */
    public void getScaledMatrix(Scanner input) {
    	String entry;
    	boolean validEntry = false;
    	do {
    		System.out.println("Please enter a scalar");
    		entry = input.nextLine().trim();
    		validEntry = operator.checkElementValidity(entry);
    		if (!validEntry) {
    			System.out.println("Invalid: Element must be a whole number or a fraction");
    		}
    	} while (!validEntry);
    	String[][] scaledMatrix = operator.scaleMatrix(matrix, entry);
    	printer.printMatrix(scaledMatrix, numColumns);
    }

    /**
     * Outputs the inverse of a matrix, computed through row reduction, to the user . Requires a
     * square matrix.
     * 
     * @param matrix The matrix to be inverted if possible.
     * @param steps  Shows each row reduction step applied to the augmented matrix if true, or only
     *               the inverse if false.
     */
    public void getRowReducedInverse() {
        boolean invertible = operator.checkInvertibility(matrix);
        if (!invertible) {
            System.out.println("This matrix is not invertible\n");
            return;
        }
        String[][] inverse = operator.getRowReducedInverse(matrix);
        System.out.println("The inverse of this matrix is:");
        printer.printMatrix(inverse, inverse[0].length);
    }

    /**
     * Outputs the inverse of the matrix, computed through the adjoint method, to the user. Requires
     * a square matrix.
     */
    public void getAdjointInverse() {
        boolean invertible = operator.checkInvertibility(matrix);
        if (!invertible) {
            System.out.println("This matrix is not invertible\n");
            return;
        }
        getAdjoint();
        getDeterminant();
        String reciprocal = 
              operator.getReciprocal(operator.getUpperTriangularForm(matrix)[1][0][0]);
        System.out.println("The adjoint, scaled by reciprocal of the determinant" 
              + "(" +  reciprocal + ") is the inverse\n");
        String[][] inverse = operator.getAdjointInverse(matrix);
        System.out.println("The inverse of this matrix is:");
        printer.printMatrix(inverse, numColumns);
    }

    /**
     * Outputs the Reduced Row Echelon Form of a matrix to the user, showing each step of the 
     * row reduction process.
     * 
     * @param matrix The matrix to be row reduced to RREF.
     */
    public void getRREF() {
        operator.rowReduce(matrix, numColumns, true);
    }

    /**
     * Outputs the transpose of a matrix.
     * 
     */
    public void getTranspose() {
        String[][] transpose = operator.getTranspose(matrix);
        System.out.println("The transpose of this matrix is:");
        printer.printMatrix(transpose, transpose[0].length);
    }

    /**
     * Outputs an upper triangular form of the matrix. Requires a square matrix. This method calls
     * the getUpperTriangularForm method in the RowOperator class. The first two dimensional element
     * of the three dimensional array returned from that method call is the upper triangular matrix 
     * form resulting from the row operations performed on the original matrix.
     */
    public void getUpperTriangularForm() {
        if (numRows != numColumns) {
            System.out.println("Square matrix required\n");
            return;
        }
        String[][][] returned = operator.getUpperTriangularForm(matrix);
        System.out.println("An upper triangular form of this matrix is:");
        printer.printMatrix(returned[0], numColumns);
    }

    /**
     * Outputs the determinant of the matrix. Requires a square matrix. This method calls the 
     * getUpperTriangularForm method in the RowOperator class. The the second two dimension element 
     * in the three dimensional array that is returned is the determinant of the original matrix.
     */
    public void getDeterminant() {
        if (numRows != numColumns) {
            System.out.println("Square matrix required\n");
            return;            
        }
        String[][][] returned = operator.getUpperTriangularForm(matrix);
        System.out.println("The determinant of this matrix is " + returned[1][0][0] + "\n");
    }

    /**
     * Outputs the adjoint of the matrix to the user. Requires a square matrix.
     */
    public void getAdjoint() {
        if (numRows != numColumns) {
            System.out.println("Square matrix required\n");
            return;
        }
        System.out.println("The adjoint of this matrix is:");
        String[][] adjoint = operator.getAdjoint(matrix);
        printer.printMatrix(adjoint, numColumns);
    }

    /**
     * Outputs the kernel/nullity/null space of a matrix to the user.
     */
    public void getNullSpace() {
        if (operator.getNullSpace(matrix, false) != null) {
            System.out.println("The null space of this matrix is:");
            operator.getNullSpace(matrix, true);
        }
        else {
        	System.out.println("The null space of this matrix is {0}\n");
        }
    }

    /**
     * Prints the matrix to the console.
     */
    public void print() {
    	System.out.println("Your matrix:");
        printer.printMatrix(matrix, numColumns);
    }
}
