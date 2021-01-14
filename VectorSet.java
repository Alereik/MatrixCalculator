package linAlgCalc;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Sets of vectors that operations are performed on in order to answer linear algebra questions.
 * 
 * @author AOsterndorff
 *
 */
public class VectorSet {

	private String[][] vectorSetArr;
	private int numRows;
	private int numColumns;
    private VectorSetOperator operator = new VectorSetOperator();
    private PrintStringBuilder printer = new PrintStringBuilder();
	
	public VectorSet(Scanner input) {
		setVectorCountAndLength(input);
		vectorSetArr = setVectorSetArr(input);
	}

	/**
	 * Prompts the user to set the number of vectors and the length of those vectors.
	 * 
	 * @param input The scanner that obtains the user's input.
	 */
	private void setVectorCountAndLength(Scanner input) {
        System.out.println("How many vectors do you wish to enter into this set?");
        while (!input.hasNextInt()) {
            input.next();
            System.out.println("Please enter a positive integer");
        }
        numColumns = Math.abs(input.nextInt());
        numColumns = numColumns < 1 ? numColumns + 1 : numColumns;
        System.out.println("How many elements will a vector in this set contain?");
        while (!input.hasNextInt()) {
            input.next();
            System.out.println("Please enter a positive integer");
        }
        numRows = Math.abs(input.nextInt());
        numRows = numRows < 1 ? numRows + 1 : numRows;
        input.nextLine();
	}

	/**
	 * Prompts the user to enter each element of each vector, one vector at a time. The vectors are
	 * entered into an array as columns would be in a matrix, and are generally treated as such.
	 * 
	 * @param input      The scanner that obtains the user's input.
	 * @return vectorArr The two dimensional array containing the vectors.
	 */
	private String[][] setVectorSetArr(Scanner input) {
		String[][] vectorSetArr = new String[numRows][numColumns];
        for (int i = 0; i < vectorSetArr.length; ++i) {
            Arrays.fill(vectorSetArr[i], "_");
        }
        boolean isValidEntry = true;
        for (int j = 0; j < numColumns; ++j) {
        	for (int i = 0; i < numRows; ++i) {
                System.out.println("Enter Element" + (i + 1) + " , of Vector" + (j + 1));
                vectorSetArr[i][j] = input.nextLine().trim();
                isValidEntry = operator.checkElementValidity(vectorSetArr[i][j]);
                if (!isValidEntry) {
                    System.out.println("Invalid: Element must be a whole number or a fraction");
                    --j;
                }
                else {
                    printer.printVectorSet(vectorSetArr);
                }
            }
        }
        System.out.println("The set of vectors is now created.\n");
        return vectorSetArr;
	}

	/**
	 * Returns the two dimensional array containing the vector set.
	 * 
	 * @return
	 */
	public String[][] getVectorSetArr() {
		return vectorSetArr;
	}

	/**
	 * Outputs whether or not a vector falls within the span of a set of vectors.
	 * 
	 * @param input The scanner that obtains the user's input.
	 */
	public void getVectorWithinSpan(Scanner input) {
		boolean withinSpan = operator.getIsVectorInSpan(vectorSetArr, input);
		if (withinSpan) {
			System.out.println("This vector is with the span of the set");
		}
		else {
			System.out.println("This vector is not with the span of the set");
		}
	}

	/**
	 * Outputs a vector with respect to a given basis set of vectors.
	 * 
	 * @param input The scanner that obtains the user's input.
	 */
	public void getVrespectS(Scanner input) {
		String[] vWithRespectToS = operator.getVRespectS(vectorSetArr, input);
		if ( vWithRespectToS != null) {
			System.out.println("The vector with respect to the basis set is:");
			printer.printVector(vWithRespectToS);
		}
		else {
			System.out.println("Error: This option requires a set containing only basis "
					           + "vectors.");
		}
	}

	/**
	 * Outputs whether or not the set is linearly independent to the user.
	 * 
	 * 
	 */
	public void getLinearDependence() {
		operator.getLinearDependence(vectorSetArr, true);
	}

	/**
	 * Outputs whether or not a set of vectors forms a basis for R^n.
	 */
	public void getIsBasisForRn() {
		boolean isBasis = operator.getIsBasisForRn(vectorSetArr, true);

		if (isBasis) {
			System.out.println("This set is a basis for R" + numRows);
		}
		else {
			System.out.println("This set is not a basis for R" + numRows);
		}
	}

	/**
	 * Outputs the set transformed into an orthogonal basis to the user.
	 */
	public void getOrthogonalBasis() {	
		String[][] orthogonalBasis = operator.applyGramSchmidt(vectorSetArr);
		System.out.println("This set was transformed into the orthogonal basis below:");
		printer.printVectorSet(orthogonalBasis);
	}	

	/**
	 * Outputs the orthogonal complement of the span of the set of vectors.
	 * 
	 * @param vectorSetArr The set spanning the subspace the orthogonal complement is of.
	 */
	public void getOrthogonalComplement(String[][] vectorSetArr) {
		String[][] transpose = operator.getTranspose(vectorSetArr);
		String[][] orthogonalComplement = operator.getNullSpace(transpose, false);
        if (orthogonalComplement != null) {
            System.out.println("The orthogonal complement is:");
            operator.getNullSpace(transpose, true);
        }
        else {
        	System.out.println("The orthogonal complement is {0}\n");
        }
	}

	/**
	 * Prints the vector set to the console.
	 */
	public void print() {
		System.out.println("Your current set of vectors:");
		printer.printVectorSet(vectorSetArr);
	}
}
