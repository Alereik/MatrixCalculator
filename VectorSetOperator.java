package linAlgCalc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Performs vector set operations;
 * 
 * @author AOsterndorff
 *
 */
public class VectorSetOperator extends MatrixOperator{

	/**
	 * Prompts the user to enter the elements of a vector.
	 * 
	 * @param input   The scanner that obtains the user's input.
	 * @param length  The length of the vector to be created.
	 * @return vector The vector being created.
	 */
	private String[] setVectorToCheck(Scanner input, int length) {
		PrintStringBuilder printer = new PrintStringBuilder();
		String[] vector = new String[length];
		Arrays.fill(vector, "_");
		boolean isValidEntry = true;
		for (int i = 0; i < length; ++i) {//prompt user for vector
			System.out.println("\n\n\nEnter element" + (i + 1) + " of the vector.");
			vector[i] = input.nextLine().trim();
            isValidEntry = checkElementValidity(vector[i]);
            if (!isValidEntry) {
                System.out.println("Invalid: Element must be a whole number or a fraction");
                --i;
            }
            else {//show vector as it is being built
                printer.printVector(vector);
            }
		}
		return vector;
	}

	/**
	 * Creates an ArrayList containing the indexes of all the zero rows in a matrix comprised of a
	 * set of vectors as its columns.
	 * 
	 * @param augmentedRREF The augmented matrix array comprised of the vector set augmented with
	 * 					    another vector and row reduced to reduced row echelon form (RREF).
	 * @return zeroRows     The ArrayList containing the zeroRows.
	 */
	private ArrayList<Integer> getZeroRows(String[][] augmentedRREF) {
		ArrayList<Integer> zeroRows = new ArrayList<>();
		for (int i = 0; i < augmentedRREF.length; ++i) {
			boolean isZeroRow = true;								//check row for non zero element
			for (int j = 0; j < augmentedRREF[0].length - 1; ++j) {
				if (!augmentedRREF[i][j].equals("0")) {
					isZeroRow = false;
				}
			}
			if (isZeroRow) {//add index of row if no non zero element found in row
				zeroRows.add(i);
			}
		}
		return zeroRows;
	}

	/**
	 * Determines whether or not a matrix, augmented with a vector and row reduced to reduced row
	 * echelon form (RREF) has a solution. The getZeroRows method is called in order to make a list
	 * of the indexes of all zero rows found in the RREF augmented matrix.
	 * 
	 * @param augmentedRREF The matrix reduced to RREF.
	 * @return solution  Returns true is the augmented matrix in REF contains a solution, false if
	 * 					 otherwise.
	 */
	private boolean getIsSolution(String[][] augmentedRREF) {
		boolean solution = true;
		ArrayList<Integer> zeroRows = getZeroRows(augmentedRREF);
		for (int i = 0; i < zeroRows.size(); ++i) {
			if (!augmentedRREF[zeroRows.get(i)][augmentedRREF[0].length - 1].equals("0")) {
				solution = false;
			}
		}
		return solution;
	}

	/**
	 * Augments a vector to a matrix consisting of a set of vectors, and row reduces the resulting
	 * augmented matrix to reduced row echelon form (RREF). The augmented vector is place as the
	 * last column of the augmented matrix.
	 * 
	 * @param vectorSetArr		   The set of vectors to be augmented with the vector.
	 * @param vector			   The vector to be augmented to the set of vectors.
	 * @return augmentedRowReduced The augmented matrix in reduced row echelon form.
	 */
	private String[][] getAugmentedRREF(String[][] vectorSetArr, String[] vector) {
		String[][] augmented = new String[vectorSetArr.length][vectorSetArr[0].length +1];
		for (int j = 0; j < vectorSetArr[0].length + 1; ++j) {
			for (int i = 0; i < vectorSetArr.length; ++i) {
				augmented[i][j] = j < vectorSetArr[0].length ? vectorSetArr[i][j] : vector[i];
			}//augmented columns = vectorSetArr columns until last column, last column  = vector
		}
		String[][] augmentedRowReduced = rowReduce(augmented, vectorSetArr[0].length, true);
		return augmentedRowReduced;
	}

	/**
	 * Determines whether or not a vector is within the span of a set of vectors.
	 * 1) The setVectorToCheck method is called in order to prompt the user to enter the vector that
	 * 	  is being check as to whether or not it falls within the span of the given set of vectors.
	 * 2) The getAugmentedRREF method is called to create an augmented matrix comprised of the given
	 *    set of vectors and the new vector, and then row reduce that augmented matrix to reduced
	 *    row echelon form (RREF).
	 * 3) The getIsSolution method is called to determine whether or not the vector falls within the
	 *    span of the set. If the augmented matrix in RREF has a solution, then the vector falls
	 *    within the span. If there is no solution, then the vector does not fall within the span.
	 * 
	 * @param vectorSetArr The given set of vectors.
	 * @param input		   The scanner that obtains the user's input.
	 * @return withinSpan  Returns true if the vector is within the span of the given set of
	 * 					   vectors, false otherwise.
	 */
	public boolean getIsVectorInSpan(String[][] vectorSetArr, Scanner input) {
		String[] vector = setVectorToCheck(input, vectorSetArr.length);
		String[][] augmentedRREF = getAugmentedRREF(vectorSetArr, vector);
		boolean withinSpan = getIsSolution(augmentedRREF);
		return withinSpan;
	}

	/**
	 * Computes and returns a vector with respect to a given basis set of vectors.
	 * 1) The setVectorToCheck method is called in order to prompt the user to enter the vector that
	 *    will be converted to a vector with respect to the given basis.
	 * 2) The basis set is checked to ensure that it is a basis and that it is comprised of only
	 * 	  it's basis vectors.
	 * 3) The getAugmentedRREF method is called to create an augmented matrix comprised of the given
	 *    set of vectors and the new vector, and then row reduce that augmented matrix to reduced
	 *    row echelon form (RREF).
	 * 4) The vector with respect to the basis set is extracted from the augmented matrix and 
	 *    returned.
	 * 
	 * @param vectorSetArr     The basis set of vectors.
	 * @param input			   The scanner that obtains the user's input.
	 * @return vWithRespectToS The vector with respect to the basis set.
	 */
	public String[] getVRespectS(String[][] vectorSetArr, Scanner input) {
		String[] vWithRespectToS = new String[vectorSetArr.length];
		if (vectorSetArr.length == vectorSetArr[0].length && getIsBasisForRn(vectorSetArr, false)) {
			String[] vector = setVectorToCheck(input, vectorSetArr.length);
			String[][] augmentedRREF = getAugmentedRREF(vectorSetArr, vector);
			for (int i = 0; i < vectorSetArr.length; ++i) {
			    vWithRespectToS[i] = augmentedRREF[i][augmentedRREF[0].length - 1];
			}
			return vWithRespectToS;
		}
		else {
			return null;
		}
	}

	/**
	 * Returns whether or not the set is linearly independent. Outputs the result to the user in
	 * print statements depending on the value of the parameter 'print'.
	 * 
	 * @param vectorSetArr The set of vectors being evaluated.
	 * @param print        Outputs the result to the user if true.
	 * @return true/false  True if the set is linearly independent, false otherwise.
	 */
	public boolean getLinearDependence(String[][] vectorSetArr, boolean print) {
		String[][] answerArr = rowReduce(vectorSetArr, vectorSetArr[0].length, true);
		for (int i = 0; i < answerArr.length; ++i) {//check for > 1 non zero element in a row
			int nonZeroCount = 0;
			for (int j = 0; j < answerArr[0].length; ++j) {
				if (!answerArr[i][j].equals("0")) {
					++nonZeroCount;
				}
				if (nonZeroCount > 1) {
					if (print) {
						System.out.println("This set is not linearly independent.");
					}
					return false;
				}
			}
		}
		if(print ) {
			System.out.println("This set is linearly independent.");
		}
		return true;
	}	

	/**
	 * Determines whether or not a set of vectors forms a basis for R^n, n being the number of
	 * dimensions of the real number space (the number of element in the vectors of the set).
	 * 
	 * @param vectorSetArr  The set of vectors being evaluated.
	 * @param showRREFSteps Shows the row reduction steps if true.
	 * @return isBasis      True if the set forms a basis for R^n, false if otherwise.
	 */
	public boolean getIsBasisForRn(String[][] vectorSetArr, boolean showRREFSteps) {
		boolean isBasis = true;
		String[][] answerArr = rowReduce(vectorSetArr, vectorSetArr[0].length, showRREFSteps);
		if (answerArr.length > answerArr[0].length) {//check for more dimensions than vectors
			isBasis = false;
		}
		else {
			for (int i = 0, j = 0; i < answerArr.length && j < answerArr.length; ++i, ++j) {
				if (!answerArr[i][j].equals("1")) {
					isBasis = false;
				}
			}
		}
		return isBasis;
	}

	/**
	 * Scales a vector by the largest denominator found within its elements in order to remove any
	 * fractions.
	 * 
	 * @param vector  The vector to be scaled.
	 * @return scaled The scaled vector.
	 */
	private String[] scaleByDenominator(String[] vector) {
		String[] scaled = new String[vector.length];
		int denominator = 1;
		String scalar = "1";
		for (int i = 0; i < vector.length; ++i) {//search for largest denominator in the vector
			if (vector[i].contains("/")) {
				int[] splitFraction = splitFraction(vector[i]);
				denominator = splitFraction[1];
				if (denominator > Integer.parseInt(scalar)) {
					scalar = String.valueOf(denominator);
				}
			}
		}
		for (int i = 0; i < vector.length; ++i) {//scale all elements by that largest denominator
			scaled[i] = fractionMultiplication(vector[i], scalar);
		}
		return scaled;
	}

	/**
	 * Adds or subtracts vectors, depending on the specified boolean parameter.
	 * 
	 * @param vector1 The first vector, that has a vector added to it or has a vector subtracted
	 * 				  from it.
	 * @param vector2 The second vector, that is added or subtracted from the first vector.
	 * @param add     Add the second vector to the first if true, subtracts the second vector from
	 * 				  the first if false.
	 * @return result The resulting vector.
	 */
	private String[]  vectorAddSubtract(String[] vector1, String[] vector2, boolean add) {
		String[] result = new String[vector1.length];
		if (!add) {//multiply all vector2 elements by -1 for subtraction
			for (int i = 0; i < vector1.length; ++i) {
				vector2[i] = fractionMultiplication(vector2[i], "-1");
			}
		}
		for (int i = 0; i < vector1.length; ++i) {
			result[i] = fractionAddition(vector1[i], vector2[i]);
		}
		return result;
	}

	/**
	 * Dot multiplies two vectors.
	 * 
	 * @param vector1 	  The first vector to be dot multiplied.
	 * @param vector2 	  The second vector to be dot multiplied.
	 * @return dotProduct The dot product of the two vectors.
	 */
	private String dotProduct(String[] vector1, String[] vector2) {
		String dotProduct = "0";
		for (int i = 0; i < vector1.length; ++i) {
			dotProduct = fractionAddition(dotProduct, 
					                      fractionMultiplication(vector1[i], vector2[i]));
		}
		return dotProduct;
	}

	/**
	 * Applies the Gram-Schmidt algorithm to a basis set of vectors, transforming the set into an
	 * orthogonal basis.
	 * 1) The parameter vectorSetArr is transposed to allow for the first dimension of the two 
	 *    dimensional arrays to represent vectors(simplifying syntax).
	 * 2) The Gram-Schmidt algorithm is applied to the the transposed set of vectors to create an
	 *    orthogonal basis.
	 * 3) The orthogonal basis matrix is transposed to return the vector dimension to the second of
	 *    the two dimensions in the array.
	 *    
	 * 
	 * ex: u4 = v4 - (<v4,u1>/<u1,u1>)u1 - (<v4,u2>/<u2,u2>)u2 - (<v4,u3>/<u3,u3>)u3
	 * 
	 * @param vectorSetArr	   The original basis set of vectors.
	 * @return orthogonalBasis The orthogonal basis.
	 */
	public String[][] applyGramSchmidt(String[][] vectorSetArr) {
		String[][] transpose = getTranspose(vectorSetArr);//transpose vectors to first dimension
		String[][] orthogonalBasis = new String[transpose.length][transpose[0].length];
		orthogonalBasis[0] = transpose[0];
		for (int i = 1; i < transpose.length; ++i) {//each iteration a vector from 2nd to last
			String[] allProjections = new String[transpose[0].length];
			Arrays.fill(allProjections, "0");
			for (int n = 0; n < i; ++n) {//sum all projections to be subtracted
				String numerator = dotProduct(transpose[i], orthogonalBasis[n]);//<vn,u(n-1)>
				String denominator = getReciprocal(dotProduct(orthogonalBasis[n],//1/<u(n-1),u(n-1)>
														      orthogonalBasis[n]));
				String dotProductFraction = fractionMultiplication(numerator, denominator);
				String[] projection = scaleRow(orthogonalBasis, n, dotProductFraction);//*u(n-1)
				allProjections = vectorAddSubtract(allProjections, projection, true);
			}//subtract all projections
			orthogonalBasis[i] = vectorAddSubtract(transpose[i], allProjections, false);
			orthogonalBasis[i] = scaleByDenominator(orthogonalBasis[i]);//scale to remove fractions
		}
		orthogonalBasis = getTranspose(orthogonalBasis);//transpose vectors back to second dimension
		return orthogonalBasis;
	}
}
