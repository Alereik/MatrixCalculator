package matrixCalculator;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 * 
 * @author AOsterndorff
 *
 */
public class Matrix{
    
    private String[][] matrix;
    private int numRows;
    private int numColumns;
    private MatrixOperator operator = new MatrixOperator();
    private MatrixStringBuilder printer = new MatrixStringBuilder();
    
    public Matrix() {
        Scanner input = new Scanner(System.in);
        setDimensions(input);
        matrix = setMatrixArray(numRows, numColumns, input);
    }
    
    public Matrix(String[][] matrix) {
        this.numRows = matrix.length;
        this.numColumns = matrix[0].length;
        this.matrix = matrix;
    }

    /**
     * Checks if a string entered by the user as an element in a vector or matrix is a valid number 
     * entry. Valid number entries are either string literals of whole numbers such as "-1" or 
     * "148", or string literals of fractions such as "3/4" or "-83/37".
     * 
     * @param element  The user entry being checked for validity.
     * @return isValid Returns true if element is valid.
     */
    private boolean checkElementValidity(String element) {
        boolean isValid = true;
        if (element == null || element.equals("") || element.length() == 0 || element.equals("-")
            || element.equals("/")) {
            isValid = false;
        }
        else if (element.contains("/")) {
            //ensure content exists on both sides of '/'
            String numerator = element.substring(0, element.indexOf('/'));
            String denominator = element.substring(element.indexOf('/'), element.length() - 1);
            if (numerator.length() == 0 || denominator.length() == 0) {
                isValid = false;
            }
            //ensure content on both sides of '/' consists of only integers
            for (int i = 0; i < element.length(); ++i) {                
                if (element.charAt(i) != '/' && element.charAt(i) != '-' 
                    && !Character.isDigit(element.charAt(i))) {
                    isValid = false;
                }
            }
        }
        else {
            for (int i = 0; i < element.length(); ++i) {
                if (element.charAt(i) != '-' && !Character.isDigit(element.charAt(i))) {
                    isValid = false;
                }
            }
        }
        return isValid;
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
        System.out.println("Enter the number of columns the matrix will have");
        while (!input.hasNextInt()) {
            input.next();
            System.out.println("Please enter a positive integer");
        }
        numColumns = Math.abs(input.nextInt());
        input.nextLine();
    }
    
    /**
     * Prompts the user to enter each element of the matrix to be used in computations. Each element
     * is checked for validity by calling the checkElementValidity method. If the user enters an 
     * invalid entry, the entry is not accepted and the user is re-prompted to enter that position's
     * element until a valid entry is entered. 
     * 
     * @param numRows    The number of rows in the matrix.
     * @param numColumns The number of columns in the matrix.
     * @return matrix    The matrix consisting of elements entered by the user.
     */
    private String[][] setMatrixArray(int numRows, int numColumns, Scanner input) {
        String[][] matrix = new String[numRows][numColumns];
        for (int i = 0; i < matrix.length; ++i) {
            Arrays.fill(matrix[i], "_");
        }
        boolean isValidEntry = true;
        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numColumns; ++j) {
                System.out.println("Enter the element for Row" + (i + 1) + " , Column" + (j + 1));
                matrix[i][j] = input.nextLine().trim();
                isValidEntry = checkElementValidity(matrix[i][j]);
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
        return numRows;
    }
    
    /**
     * Returns the number of columns in the matrix.
     * 
     * @return numColumns The number of columns in the matrix.
     */
    public int getNumColumns() {
        return numColumns;
    }
        
    /**
     * Outputs the inverse of a matrix to the user if it is invertible.
     * 
     * @param matrix The matrix to be inverted if possible.
     * @param steps  Shows each row reduction step applied to the augmented matrix if true, or only 
     *               the inverse if false.
     */
    public void getInverse(boolean steps) {
        String[][] checkMatrix = operator.rowReduce(matrix, numRows, false);
        //check if invertible
        for (int k = 0; k < checkMatrix.length; ++k) {
            if (numRows != numColumns || checkMatrix[k][k].equals("0")) {
                System.out.println("Matrix not invertible");
                return;
            }
        }
        //augment with identity matrix
        String[][] augmentedMatrix = new String[numRows][numColumns * 2];
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
        augmentedMatrix = operator.rowReduce(augmentedMatrix, numColumns, steps);
        //extract and show inverse from augmented matrix
        String[][] inverse = new String[augmentedMatrix.length][augmentedMatrix[0].length / 2];
        for (int i = 0; i < augmentedMatrix.length; ++i) {
            for (int j = augmentedMatrix[0].length / 2; j < augmentedMatrix[0].length; ++j) {
                inverse[i][j - (augmentedMatrix[0].length / 2)] = augmentedMatrix[i][j];
            }
        }
        printer.printMatrix(inverse, inverse[0].length);
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
     * Returns the transpose of a matrix.
     * 
     * @param matrix The matrix to be transposed.
     */
    public String[][] getTranspose() {
        String[][] transpose = new String[numRows][numColumns];
        for (int i = 0; i < transpose.length; ++i) {
            for (int j = 0; j < transpose[0].length; ++j) {
                transpose[i][j] = matrix[j][i];
            }
        }
        printer.printMatrix(transpose, transpose[0].length);
        return transpose;
    }
    
    /**
     * Outputs an upper triangular form of the matrix. Requires a square matrix. This method calls
     * the getUpperTriangularForm method in the RowOperator class. The first two dimensional element
     * of the three dimensional array returned from that method call is the upper triangular matrix 
     * form resulting from the row operations performed on the original matrix.
     */
    public void getUpperTriangularForm() {
        if (numRows != numColumns) {
            System.out.println("Square matrix required");
            return;
        }
        String[][][] returned = operator.getUpperTriangularForm(matrix);
        printer.printMatrix(returned[0], numColumns);
    }
    
    /**
     * Outputs the determinant of the matrix. Requires a square matrix. This method calls the 
     * getUpperTriangularForm method in the RowOperator class. The the second two dimension element 
     * in the three dimensional array that is returned is the determinant of the original matrix.
     */
    public void getDeterminant() {
        if (numRows != numColumns) {
            System.out.println("Square matrix required");
            return;            
        }
        String[][][] returned = operator.getUpperTriangularForm(matrix);
        System.out.println("The determinant of this matrix is " + returned[1][0][0]);
    }
    /**
     * Outputs the adjoint of the matrix to the user. Requires a square matrix.
     */
    public String[][] getAdjoint() {
        if (numRows != numColumns) {
            System.out.println("Square matrix required");
            return null;
        }
        String[][] cofactorMatrix = new String[numRows][numColumns];       
        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numColumns; ++j) {
                cofactorMatrix[i][j] = operator.getCofactor(matrix, i, j);
            }
        }
        Matrix transposer = new Matrix(cofactorMatrix);
        String[][] adjoint = transposer.getTranspose();
        return adjoint;
    }
    //TODO
    public void getNullity() {
        
    }
    
    /**
     * Prints the matrix to the console.
     */
    public void print() {
        printer.printMatrix(matrix, numColumns);
    }
}
