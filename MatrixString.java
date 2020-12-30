package MatrixCalculator;

public class MatrixString {
    
    /**
     * This method equalizes the length of the element strings from the parameter vector array, then
     * returns a new array containing white space padded elements from the parameter array so that
     * the element strings are of equal length.
     * 
     * @param vector                The input parameter vector array.
     * @return equalizedVectorArr   The new vector array with elements of equal length.
     */
    public static String[] equalizeElementWidth(String[] vector) {
        String[] equalizedVectorArr = new String[vector.length];
        //find length of longest element
        int greatestLength = 0;
        for (int i = 0; i < vector.length; ++i) {
            if (vector[i].length() > greatestLength) {
                greatestLength = vector[i].length();
            }
        }
        //pad shorter elements with whitespace to equalize their length with longest element
        for (int i = 0; i < vector.length; ++i) {
            String padLeft = "";
            String padRight = "";
            int difference = greatestLength - vector[i].length();
            //even number of spaces to pad element
            if (difference % 2 ==0) {
                for (int j = 0; j < difference / 2; ++j) {
                    padLeft += " ";
                    padRight += " ";                    
                }
                equalizedVectorArr[i] = padLeft + vector[i] + padRight;
            }
            //odd number of spaces to pad element puts one more padding space on right side
            else {
                for (int j = 0; j < difference / 2; ++j) {
                    padLeft += " ";
                    padRight += " ";                    
                }
                padRight += " ";
                equalizedVectorArr[i] = padLeft + vector[i] + padRight;
            }
        }
        return equalizedVectorArr;

    }
    /**
     * This method equalizes the length of the element strings from the parameter matrix array, then
     * returns a new array containing white space padded elements from the parameter array so that 
     * the element strings are of equal length.
     * 
     * @param matrix                The input parameter matrix array.
     * @return equalizedMatrixArr   The new matrix array with elements of equal length.
     */
    public static String[][] equalizeElementWidth(String[][] matrix) {
        String[][] equalizedMatrixArr = new String[matrix.length][matrix[0].length];
        //find length of longest element
        int greatestLength = 0;
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                if (matrix[i][j].length() > greatestLength) {
                    greatestLength = matrix[i][j].length();
                }
            }
        }
        //add whitespace padding to shorter elements to match longest element
        for (int i = 0; i < matrix.length; ++i) {
            int difference = 0;
            for (int j = 0; j < matrix[i].length; ++j) {
                String padLeft = "";
                String padRight = "";
                difference = greatestLength - matrix[i][j].length();
                //even number of spaces to pad element
                if (difference % 2 == 0) {
                    for (int k = 0; k < difference / 2; ++k) {
                        padLeft += " ";
                        padRight += " ";
                    }
                    equalizedMatrixArr[i][j] = padLeft + matrix[i][j] + padRight;
                }
                //odd number of spaces to pad element puts one more padding space on right side
                else {
                    for (int k = 0; k < difference / 2; ++k) {
                        padLeft += " ";
                        padRight += " ";
                    }
                    padRight += " ";
                    equalizedMatrixArr[i][j] = padLeft + matrix[i][j] + padRight;
                }
            }
        }
        return equalizedMatrixArr;
    }

    /**
     * This method returns a string equal to the sum of the lengths of a row of elements in a matrix 
     * after having padded each element of that row with a whitespace on either side. This 
     * facilitates correct spacing between the parenthesis of the matrix, as well as correct spacing 
     * between parenthesis and the augmentation border of augmented matrices.
     * 
     * @param matrix       The matrix whose width needs to be determined. Should have been equalized
     *                     prior to the calling of this method.
     * @return matrixWidth The string of length equal to the matrix's width.
     */
    public static String getMatrixWidth(String[][] matrix) {
        String matrixWidth = "";
        int elementWidth = 0;
        //sum the lengths of all elements
        for (int j = 0; j < matrix[0].length; ++j) {
            elementWidth += matrix[0][j].length();
            //each element padded with 2 spaces, one on either side
            matrixWidth += "  ";
        }
        //concatenate spaces, elementWidth times
        for (int i = 0; i < elementWidth; ++i) {
            matrixWidth += " ";
        }
        return matrixWidth;
    }
    /**
     * This method outputs a matrix from a two-dimensional array. 
     * 
     * - First the lengths of the strings in the matrix are made to be of equal length, with the 
     *   longest length element determining the length all elements will be.
     *   
     * - Then that length is multiplied by the number of columns in the matrix to determine the 
     *   total length of the elements in a row.
     * 
     * - Next, the number of spaces padding the elements of the rows are added to that length. This 
     *   determines the amount of space between elements of the matrix parenthesis that are not 
     *   populated with the matrix elements.
     *   
     * - Finally, a string constituting a visual representation of the matrix is generated and
     *   returned.
     * 
     * @param matrix             The two-dimensional array.
     * @return matrixPrintString The visual representation of the matrix in the form of a string.
     */
    public static String getMatrixString(String[][] matrix) {
        //call equalizeElementWidth to create matrix with element strings of equal length
        String[][] equalMatrixArr = equalizeElementWidth(matrix);
        //get width of matrix to build rows without elements
        String matrixWidth = getMatrixWidth(equalMatrixArr);
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
     * This method returns a string that visually represents an augmented matrix consisting of a
     * a matrix and a vector.
     * 
     * @param matrix             The matrix on the left side of the augmented matrix.
     * @param vector             The vector on the right side of the augmented matrix.
     * @return matrixPrintString The string that visually represents the augmented matrix.
     */
    public static String getAugmentedMatrixString(String[][] matrix, String[] vector) {
        //call equalizeElementWidth to create arrays with element strings of equal length
        String[][] equalMatrixArr = equalizeElementWidth(matrix);
        String[] equalVectorArr = equalizeElementWidth(vector);
        //get widths of matrix and vector to build rows without elements
        String leftMatrixWidth = getMatrixWidth(equalMatrixArr);
        String rightVectorWidth = "  ";//two padding spaces, one on each side of the element
        for (int i = 0; i < equalVectorArr[0].length(); ++i) {
            rightVectorWidth += " ";
        }
        String augMatrixWidth = leftMatrixWidth + "|" + rightVectorWidth;
        //generate a string representation of the augmented matrix
        String matrixPrintString = "/" + augMatrixWidth + "\\\n";        
        for (int i = 0; i < equalMatrixArr.length; ++i) {
            matrixPrintString += "|";
            for (int j = 0; j < equalMatrixArr[i].length; ++j) {
                matrixPrintString += " " + equalMatrixArr[i][j] + " ";
            }
            matrixPrintString += "| " + equalVectorArr[i] + " |\n";
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
     * This method returns a string that visually represents an augmented matrix consisting of two
     * matrices.
     * 
     * @param matrix             The matrix on the left side of the augmented matrix.
     * @param rightMatrix        The matrix on the right side of the augmented matrix.
     * @return matrixPrintString The string that visually represents the augmented matrix.
     */
    public static String getAugmentedMatrixString(String[][] matrix, String[][] rightMatrix) {
        //call equalizeElementWidth to create arrays with element strings of equal length
        String[][] equalMatrixArr = equalizeElementWidth(matrix);
        String[][] equalAugMatrix = equalizeElementWidth(rightMatrix);
        //get widths of matrices to build rows without elements
        String leftMatrixWidth = getMatrixWidth(equalMatrixArr);
        String rightMatrixWidth = getMatrixWidth(equalAugMatrix);
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
     * This method prints out a string representation of a matrix or augmented matrix depending on
     * the difference between the width of the parameter matrix and the leftMatrixWidth parameter.
     * If the two are the same, then the matrix is not an augmented matrix and is simply printed.
     * If leftMatrix width is less than the width of the parameter matrix, then the matrix is split 
     * into its constituent parts before printing.
     * 
     * @param matrix          The entire matrix. May be an augmented matrix.
     * @param leftMatrixWidth The width of the matrix to the left of an augmentation border, if any.
     */
    public static void printMatrix(String[][] matrix, int leftMatrixWidth) {
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
}
