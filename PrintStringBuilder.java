package linAlgCalc;

import java.util.ArrayList;

/**
 * Converts a matrix or null space solution into a string that represents the matrix visually and 
 * outputs the string to the user.
 * 
 * @author AOsterndorff
 *
 */
public class PrintStringBuilder {
    
    /**
     * Equalizes the length of the element strings from the parameter matrix array, then returns a 
     * new array containing white space padded elements from the parameter array so that the element
     * strings are of equal length.
     * 
     * @param matrix                The input parameter matrix array.
     * @return equalizedMatrixArr   The new matrix array with elements of equal length.
     */
    private String[][] equalizeElementlength(String[][] matrix) {
        String[][] equalizedMatrixArr = new String[matrix.length][matrix[0].length];
        int greatestLength = 0;
        for (int i = 0; i < matrix.length; ++i) {//find length of longest element
            for (int j = 0; j < matrix[i].length; ++j) {
                if (matrix[i][j].length() > greatestLength) {
                    greatestLength = matrix[i][j].length();
                }
            }
        }//add whitespace padding to shorter elements to match longest element
        for (int i = 0; i < matrix.length; ++i) {
            int difference = 0;
            for (int j = 0; j < matrix[i].length; ++j) {
                String padLeft = " ";
                String padRight = " ";
                difference = greatestLength - matrix[i][j].length();               
                if (difference % 2 == 0) {//even number of spaces to pad element
                    padLeft += padLeft.repeat(difference / 2);
                    padRight += padRight.repeat(difference / 2);
                    equalizedMatrixArr[i][j] = padLeft + matrix[i][j] + padRight;
                }
                else {//if odd number of spaces, put one more padding space on right side
                    padLeft += padLeft.repeat(difference / 2);
                    padRight += padRight.repeat((difference / 2) + 1);
                    equalizedMatrixArr[i][j] = padLeft + matrix[i][j] + padRight;
                }
            }
        }
        return equalizedMatrixArr;
    }

    /**
     * Returns a string equal to the sum of the lengths of a row of elements in a matrix after 
     * having padded each element of that row with a whitespace on either side. This facilitates 
     * correct spacing between the parenthesis of the matrix, as well as correct spacing between 
     * parenthesis and the augmentation border of augmented matrices.
     * 
     * @param matrix       The matrix whose width needs to be determined. Should have been equalized
     *                     prior to the calling of this method.
     * @return matrixWidth The string of length equal to the matrix's width.
     */
    private String getMatrixWidthString(String[][] matrix) {
        String matrixWidth = "";
        int elementWidth = 0;
        //sum the lengths of all elements
        for (int j = 0; j < matrix[0].length; ++j) {
            elementWidth += matrix[0][j].length();
            //each element padded with 2 spaces, one on either side
            matrixWidth += "  ";
        }
        //concatenate spaces, elementWidth times
        matrixWidth += " ".repeat(elementWidth);
        return matrixWidth;
    }
    /**
     * Outputs a matrix from a two-dimensional array. 
     * 
     * 1) First the equalizeElementLength method is called to ensure that the strings in each 
     *    element are of equal length, with the longest length element determining the length that 
     *    all of the elements will be.   
     * 2) A string of white space the width of the matrix is created by calling the 
     *    getMatrixWidthString method. This will fill the spaces between parenthesis in the rows not
     *    filled by elements of the array.
     * 3) Finally, a string constituting a visual representation of the matrix is generated and
     *    returned.
     * 
     * @param matrix             The two-dimensional array.
     * @return matrixPrintString The visual representation of the matrix in the form of a string.
     */
    private String getMatrixString(String[][] matrix) {
        //call equalizeElementWidth to create matrix with element strings of equal length
        String[][] equalMatrixArr = equalizeElementlength(matrix);
        //get width of matrix to build rows without elements
        String matrixWidth = getMatrixWidthString(equalMatrixArr);
        //generate a string representation of the matrix
        String matrixPrintString = "/" + matrixWidth + "\\\n";
        for (int i = 0; i < equalMatrixArr.length; ++i) {
            matrixPrintString += "|";
            for (int j = 0; j < equalMatrixArr[i].length; ++j) {
                matrixPrintString += " " + equalMatrixArr[i][j] + " ";
            }
            matrixPrintString += "|\n";
            if (i < equalMatrixArr.length - 1) {
                matrixPrintString += "|" + matrixWidth + "|\n";
            }
            else {
                matrixPrintString += "\\" + matrixWidth + "/\n";
            }
        }
        return matrixPrintString;
    }
    
    /**
     * Returns a string that visually represents an augmented matrix consisting of a matrix and a 
     * vector by converting one of its parameters into a suitable parameter for the other 
     * getAugmentedMatrixString method. The vector parameter is converted from a single dimensional 
     * array of length n to a two dimensional matrix of dimensions n x 1. Both parameters are then 
     * passed on to the other getAugmentedMatrixString method to create the string
     * 
     * @param matrix             The matrix on the left side of the augmented matrix.
     * @param vector             The vector on the right side of the augmented matrix.
     * @return matrixPrintString The string that visually represents the augmented matrix.
     */
    private String getAugmentedMatrixString(String[][] matrix, String[] vector) {
        String[][] vectorMatrix = new String[vector.length][1];
        for (int i = 0; i < vector.length; ++i) {
            vectorMatrix[i][0] = vector[i]; 
        }
        String matrixPrintString = getAugmentedMatrixString(matrix, vectorMatrix);
        return matrixPrintString;
    }
    
    /**
     * Returns a string that visually represents an augmented matrix consisting of two matrices.
     * 
     * @param matrix             The matrix on the left side of the augmented matrix.
     * @param rightMatrix        The matrix on the right side of the augmented matrix.
     * @return matrixPrintString The string that visually represents the augmented matrix.
     */
    private String getAugmentedMatrixString(String[][] matrix, String[][] rightMatrix) {
        //call equalizeElementWidth to create arrays with element strings of equal length
        String[][] equalMatrixArr = equalizeElementlength(matrix);
        String[][] equalAugMatrix = equalizeElementlength(rightMatrix);
        //get widths of matrices to build rows without elements
        String leftMatrixWidth = getMatrixWidthString(equalMatrixArr);
        String rightMatrixWidth = getMatrixWidthString(equalAugMatrix);
        String augMatrixWidth = leftMatrixWidth + "|" + rightMatrixWidth;
        //generate a string representation of the augmented matrix
        String matrixPrintString = "/" + augMatrixWidth + "\\\n";
        for (int i = 0; i < equalMatrixArr.length; ++i) {
            matrixPrintString += "|";
            for (int j = 0; j < equalMatrixArr[i].length; ++j) {
                matrixPrintString += " " + equalMatrixArr[i][j] + " ";
            }
            matrixPrintString += "|";
            for (int j = 0; j < equalAugMatrix[i].length; ++j) {
                matrixPrintString += " " + equalAugMatrix[i][j] + " ";
            }
            matrixPrintString += "|\n";
            if (i < equalMatrixArr.length - 1) {
                matrixPrintString += "|" + augMatrixWidth + "|\n";
            }
            else {
                matrixPrintString += "\\" + augMatrixWidth + "/\n";
            }
        }
        return matrixPrintString;
    } 
    
    /**
     * Prints out a string representation of a matrix or augmented matrix depending on the 
     * difference between the width of the parameter matrix and the leftMatrixWidth parameter. If 
     * the two are the same, then the matrix is not an augmented matrix and is simply printed. If 
     * leftMatrix width is less than the width of the parameter matrix, then the matrix is split 
     * into its constituent parts before printing.
     * 
     * @param matrix          The entire matrix. May be an augmented matrix.
     * @param leftMatrixWidth The width of the matrix to the left of an augmentation border, if any.
     */
    public void printMatrix(String[][] matrix, int leftMatrixWidth) {
        //non augmented matrix
        if (matrix[0].length - leftMatrixWidth == 0) {
            System.out.println(getMatrixString(matrix));
        }
        //matrix augmented with a vector
        else if (matrix[0].length - leftMatrixWidth == 1) {
            String[][] leftMatrix = new String[matrix.length][leftMatrixWidth];
            String[] rightVector = new String[matrix.length];
            for (int i = 0; i < matrix.length; ++i) {
                for (int j = 0; j < matrix[0].length; ++j) {
                    if (j < leftMatrixWidth) {
                        leftMatrix[i][j] = matrix[i][j];
                    }
                    else {
                        rightVector[i] = matrix[i][j];
                    }
                }
            }
            System.out.println(getAugmentedMatrixString(leftMatrix, rightVector));
        }
        //matrix augmented with another matrix
        else if (matrix[0].length - leftMatrixWidth > 1 ) {
            String[][] leftMatrix = new String[matrix.length][leftMatrixWidth];
            String[][] rightMatrix = new String[matrix.length][ matrix[0].length - leftMatrixWidth];
            for (int i = 0; i < matrix.length; ++i) {
                for (int j = 0; j < matrix[0].length; ++j) {
                    if (j < leftMatrixWidth) {
                        leftMatrix[i][j] = matrix[i][j];
                    }
                    else {
                        rightMatrix[i][j - leftMatrixWidth] = matrix[i][j];
                    }
                }
            }
            System.out.println(getAugmentedMatrixString(leftMatrix, rightMatrix));
        }
    }
    
    /**
     * Builds two dimensional string arrays from the Integer elements in the ArrayList freeVar, then
     * calls the equalizeElementLength method in order to equalize the elements lengths. The two
     * dimensional arrays are stored in a three dimensional array.
     * 
     * @param set               The matrix containing the basis vectors of the null space as it's 
     *                          columns.
     * @param freeVar           The column indexes in which free variables were found in the reduced
     *                          row echelon form of the original matrix.
     * @return variablePrintArr The three dimensional array containing all resulting two dimensional
     *                          arrays.
     */
    private String[][][] variableLabelArr(String[][] set, ArrayList<Integer> freeVar) {
        String[][][] variablePrintArr = new String[freeVar.size()][set.length][1];
        String[] variables = new String[freeVar.size()];
        for (int i = 0; i < freeVar.size(); ++i) {//convert freeVar elements to variable names
            variables[i] = "x" + (freeVar.get(i) + 1);
        }
        for (int j = 0; j < freeVar.size(); ++j) {//construct string arrays for variable names
            for (int i = 0; i < set.length; ++i) {
                if (i == set.length / 2) {//put variable name in middle of row height
                    if (j == 0) {
                        variablePrintArr[j][i][0] = variables[j];
                    }
                    else {//plus sign for variable labels following the first
                        variablePrintArr[j][i][0] = " + " + variables[j];
                    }
                }
                else {
                    variablePrintArr[j][i][0] = " ";
                }
            }
        }
        for (int i = 0; i < variablePrintArr.length; ++i) {
            variablePrintArr[i] = equalizeElementlength(variablePrintArr[i]);
        }
        return variablePrintArr;
    }
    
    /**
     * Creates the top and bottom rows of the string representation of the null space display.
     * 
     * @param variablePrintArr    The array of variable label arrays.
     * @param set                 The matrix containing the  basis vectors of the null space as it's 
     *                            columns.
     * @param top                 Creates the top row if true, bottom row if false.
     * @return nullityPrintString The top or bottom row of the null space display string, depending
     *                            on the value of the boolean parameter 'top'.
     */
    private String nullityTopBottom(String[][][] variablePrintArr, String[][] set, boolean top) {
        String nullityPrintString = "";
        if (top) {//top row of string
            for (int j = 0, k = 0, l = 0; j < variablePrintArr.length + set[0].length; ++j) {
                if (j % 2 == 0) {//nullityPrintString column increments on every other iteration
                    nullityPrintString += " ".repeat(variablePrintArr[k][0][0].length());
                    ++k;
                }
                else {//set column increments on every other iteration
                    nullityPrintString += "/" + " ".repeat(set[0][l].length()) + "\\";
                    ++l;
                }
            }
        }
        else {//bottom row of string
            for (int j = 0, k = 0, l = 0; j < variablePrintArr.length + set[0].length; ++j) {
                if (j % 2 == 0) {//nullityPrintString column increments on every other iteration
                    nullityPrintString += " ".repeat(variablePrintArr[k][0][0].length());
                    ++k;
                }
                else {//set column increments on every other iteration
                    nullityPrintString += "\\" + " ".repeat(set[0][l].length()) + "/";
                    ++l;
                }
            }
        }
        nullityPrintString += "\n";
        return nullityPrintString;
    }
    
    /**
     * Outputs a string representation of a null space solution display. 
     * 1) The variableLabelArr method is called to create string arrays of the variable labels, so 
     *    that the elements can space the null space basis vectors evenly.
     * 2) The equalizeElementLength method is called in order to equalize the lengths of the 
     *    elements in 'set'.
     * 3) The nullityBracketEnds method is called to create the top row of the string.
     * 4) The body of the null space display is constructed.
     * 5) The nullityBracketEnds method is called to create the bottom row of the string.
     * 
     * @param set     The matrix containing the  basis vectors of the null space as it's columns.
     * @param freeVar The column indexes in which free variables were found in the reduced row 
     *                echelon form of the original matrix.
     */
    public void printNullSpace(String[][] set, ArrayList<Integer> freeVar) {
        String[][][] variablePrintArr = variableLabelArr(set, freeVar);
        set = equalizeElementlength(set);
        String nullityPrintString = nullityTopBottom(variablePrintArr, set, true);
        for (int i = 0; i < set.length; ++i) {
            for (int j = 0, k = 0, l = 0; j < variablePrintArr.length + set[0].length; ++j) {
                if (j % 2 == 0) {
                    nullityPrintString += variablePrintArr[k][i][0];
                    ++k;
                }
                else {
                    nullityPrintString += "|" + set[i][l] + "|";
                    ++l;
                }
            }
            nullityPrintString += "\n";
        }
        nullityPrintString += nullityTopBottom(variablePrintArr, set, false);
        System.out.println(nullityPrintString);
    }
}
