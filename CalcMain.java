package linAlgCalc;

import java.io.IOException;
import java.util.Scanner;

public class CalcMain {

	/**
	 * Main method that displays the main menu to the user.
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean done = false;
		System.out.println("\n\n\n                 Welcome to the Linear Algebra Calculator.\n");
		while (!done) {
			System.out.println("                   Please select from these four options.\n\n"
		          + "          [Enter 1]                                  [Enter 3]\n"
		          + "  Build a matrix to compute it's:          Build a set of vectors in"
		          + " order to: \n"
				  + "       - Inverse                               - Determine if a vector\n"
				  + "       - Reduced Row Echelon Form                is within the set's span\n"
				  + "       - Adjoint                               - Determine if the set\n"
				  + "       - Upper Triangular Form                   is linearly independent\n"
				  + "       - Transpose                             - Determine if the set forms\n"
				  + "       - Determinant                             a basis for R^n\n"
				  + "       - Null Space                            - Get a vector with\n"
				  + "       - Scalar Multiple                         respect to a basis\n"
				  + "                                               - Compute a transition matrix\n"
				  + "          [Enter 2]                              from one basis to another\n"				  
				  + "  Build two matrices in order to:              - Apply the Gram-Schmidt\n"
				  + "      - Multiply Matrices                        process to compute an\n"
				  + "      - Add Matrices                             orthogonal basis\n"
				  + "      - Subtract Matrices                      - Compute an orthogonal\n"
				  + "                                                 complement to an \n"
				  + "                                                 orthogonal basis\n\n"
				  + "                                                     [Enter 0]\n"
				  + "                                                      - Quit\n");
			int userChoice = 99;
			while (userChoice < 0 || userChoice > 3) {
				while(!input.hasNextInt()) {
					input.next();
				}
				userChoice = input.nextInt();
			} 
			switch (userChoice) {
			case 1:
				oneMatrix(input);
				break;
			case 2:
				twoMatrix(input);
				break;
			case 3:
				vectorSet(input);
				break;
			case 0:
				done = true;
				break;
			}
		}
		System.out.println("\n\n\n                                 Goodbye.\n\n\n");
    }

	/**
	 * Pauses the program from returning to a menu. Prompts the user to press enter to continue.
	 */
	private static void enterToContinue() {
		System.out.println("Press ENTER to continue.\n");
		try {
			System.in.read();
		} catch (IOException e) {
		}
	}

	/**
	 * The menu for conducting operations on a single matrix.
	 * 
	 * @param input The scanner to take in user input.
	 */
	private static void oneMatrix(Scanner input) {
		Matrix oneMatrix = new Matrix(input);
		if (oneMatrix.getMatrixArray().length < 2 || oneMatrix.getMatrixArray()[0].length < 2) {
			System.out.println("Vectors are not supported by this option.\n");
			return;
		}
		System.out.println("Your matrix is now created.\n");
		boolean done = false;
		while(!done) {
			System.out.println("       What would you like to compute?\n\n"
					+ "Enter: [1] Inverse                              [9] Show dimensions\n"
					+ "       [2] Row Reduced Echelon Form (RREF)      [0] Show matrix\n"
					+ "       [3] Adjoint\n"
					+ "       [4] An Upper Triangular Form\n"
					+ "       [5] Transpose\n"
					+ "       [6] Determinant\n"
					+ "       [7] Null Space                           [90] Quit\n"
					+ "       [8] Scalar Multiple\n");
			int userChoice = 99;
			while ((userChoice < 0 || userChoice > 9 ) && userChoice != 90) {
				while(!input.hasNextInt()) {
					input.next();
				}
				userChoice = input.nextInt();
			} 
			input.nextLine();
			switch (userChoice) {
			case 1:
				chooseInverseMethod(input, oneMatrix);
				break;
			case 2:
				oneMatrix.getRREF();
				enterToContinue();
				break;
			case 3:
				oneMatrix.getAdjoint();
				enterToContinue();
				break;
			case 4:
				oneMatrix.getUpperTriangularForm();
				enterToContinue();
				break;
			case 5:
				oneMatrix.getTranspose();
				enterToContinue();
				break;
			case 6:
				oneMatrix.getDeterminant();
				enterToContinue();
				break;
			case 7:
				oneMatrix.getNullSpace();
				enterToContinue();
				break;
			case 8:
				oneMatrix.getScaledMatrix(input);
				enterToContinue();
				break;
			case 9:
				oneMatrix.getNumRows();
				oneMatrix.getNumColumns();
				enterToContinue();
				break;
			case 0:
				oneMatrix.print();
				enterToContinue();
				break;
			case 90:
				done = true;
				break;
			}
		}
	}

	/**
	 * The menu for choosing which method to use in computing the inverse of a matrix. The row
	 * reduction method will show each step in the row reduction of the augmented matrix that
	 * contains the matrix and the identity matrix. Choosing the adjoint method will only display
	 * the resulting inverse.
	 * 
	 * @param input     The scanner to take in user input.
	 * @param oneMatrix The matrix to be inverted.
	 */
	private static void chooseInverseMethod(Scanner input, Matrix oneMatrix) {
		MatrixOperator operator = new MatrixOperator();
		boolean invertible = operator.checkInvertibility(oneMatrix.getMatrixArray());
		if (!invertible) {
			System.out.println("This matrix is not invertible. Please select another option.\n");
			enterToContinue();
			return;
		}
		System.out.println("Please choose which method to compute the inverse with.\n\n"
				+ "      [Enter 1]                        [Enter 2]\n\n"
				+ "- Row Reduction Method              - Adjoint Method");
		int userChoice = 99;
		while ((userChoice < 0 || userChoice > 9 ) && userChoice != 90) {
			while(!input.hasNextInt()) {
				input.next();
			}
			userChoice = input.nextInt();
		} 
		switch (userChoice) {
		case 1:
			oneMatrix.getRowReducedInverse();;
			enterToContinue();
			break;
		case 2:
			oneMatrix.getAdjointInverse();
			enterToContinue();
			break;
		}
	}

	/**
	 * The menu for conducting matrix multiplication, addition, or subtraction involving to 
	 * matrices.
	 * 
	 * @param input The scanner to take in user input.
	 */
	private static void twoMatrix(Scanner input) {
		MatrixOperator operator = new MatrixOperator();
		Matrix oneMatrix = new Matrix(input);
		Matrix twoMatrix = new Matrix(input);
		if (oneMatrix.getMatrixArray().length < 2 || oneMatrix.getMatrixArray()[0].length < 2 
		    || twoMatrix.getMatrixArray().length < 2 || twoMatrix.getMatrixArray()[0].length < 2) {
			System.out.println("Vectors are not supported by this option.\n");
			return;
		}
		System.out.println("Your matrices are now created.\n");
		boolean done = false;
		while(!done) {
			System.out.println("       What would you like to compute?\n\n"
					+ "Enter: [1] Multiply                      [5] Show first matrix dimensions\n"
					+ "       [2] Add                           [6] Show first matrix\n"
					+ "       [3] Subtract                      [7] Show second matrix dimensions\n"
					+ "       [4] Swap matrix order             [8] Show second matrix\n\n"
					+ "                                         [90] Quit");
			int userChoice = 99;
			while ((userChoice < 0 || userChoice > 8 ) && userChoice != 90) {
				while(!input.hasNextInt()) {
					input.next();
				}
				userChoice = input.nextInt();
			} 
			switch (userChoice) {
			case 1:
				operator.multiplyMatrices(oneMatrix.getMatrixArray(), twoMatrix.getMatrixArray());
				enterToContinue();
				break;
			case 2:
				operator.addSubtractMatrices(oneMatrix.getMatrixArray(), 
						                     twoMatrix.getMatrixArray(), true);
				enterToContinue();
				break;
			case 3:
				operator.addSubtractMatrices(oneMatrix.getMatrixArray(), 
						                     twoMatrix.getMatrixArray(), false);
				enterToContinue();
				break;
			case 4:
				Matrix tempMatrix = oneMatrix;
				oneMatrix = twoMatrix;
				twoMatrix = tempMatrix;
				System.out.println("The first matrix is now:");
				oneMatrix.print();
				System.out.println("The second matrix is now:");
				twoMatrix.print();
				enterToContinue();
				break;
			case 5:
				oneMatrix.getNumRows();
				oneMatrix.getNumColumns();
				enterToContinue();
				break;
			case 6:
				oneMatrix.print();
				enterToContinue();
				break;
			case 7:
				twoMatrix.getNumRows();
				twoMatrix.getNumColumns();
				enterToContinue();
				break;
			case 8:
				twoMatrix.print();
				enterToContinue();
				break;
			case 90:
				done = true;
				break;
			}
		}
	}

	/**
	 * Prompts the user to choose whether or not they want to compute the orthogonal complement of
	 * the orthogonal basis that was just computed, and outputs it to the user if they chose yes.
	 * 
	 * @param input     The scanner to take in user input.
	 * @param vectorSet The set of vectors the the orthogonal basis was computed from.
	 * @param operator  The vector set operator that computes the orthogonal complement.
	 */
	public static void orthogonalComplement(Scanner input, VectorSet vectorSet, 
			                                VectorSetOperator operator) {
		System.out.println("Would you like to compute the orthogonal complement(W-perp) as well?\n"
				+ "\n      [Enter 1]                        [Enter 2]\n\n"
				+ "        - Yes                            - No");
		int userChoice = 99;
		while ((userChoice < 0 || userChoice > 9 ) && userChoice != 90) {
			while(!input.hasNextInt()) {
				input.next();
			}
			userChoice = input.nextInt();
		} 
		switch (userChoice) {
		case 1:
			vectorSet.getOrthogonalComplement(vectorSet.getVectorSetArr());;
			break;
		case 2:
			break;
		}
	}

	/**
	 * The menu for conducting operations on a set of vectors.
	 * 
	 * @param input The scanner to take in user input.
	 */
	public static void vectorSet(Scanner input) {
		VectorSetOperator operator = new VectorSetOperator();
		VectorSet vectorSet = new VectorSet(input);
		boolean done = false;
		while(!done ) {
			System.out.println("       What would you like to do?\n"
					+ "Enter: [1] Determine if a vector is within the span\n"
					+ "       [2] Determine if the set is linearly independent\n"
					+ "       [3] Determine if the set forms a basis for R^n\n"
					+ "       [4] Transform a vector to a vector with respect to a basis\n"
					+ "       [5] Compute a transition matrix from one basis to another\n"
					+ "       [6] Apply the Gram-Schmidt process to compute an orthogonal basis\n"
					+ "       [7] Show current set of vectors\n\n"
					+ "       [90] Quit");
			int userChoice = 99;
			while ((userChoice < 0 || userChoice > 6 ) && userChoice != 90) {
				while(!input.hasNextInt()) {
					input.next();
				}
				userChoice = input.nextInt();
			}
			input.nextLine();
			switch (userChoice) {
			case 1:
				vectorSet.getVectorWithinSpan(input);
				enterToContinue();
				break;
			case 2:
				vectorSet.getLinearDependence();
				enterToContinue();
				break;
			case 3:
				vectorSet.getIsBasisForRn();
				enterToContinue();
				break;
			case 4:
				vectorSet.getVrespectS(input);
				enterToContinue();
				break;
			case 5:
				vectorSet.getTransitionMatrix(input);
				enterToContinue();
				break;
			case 6:
				vectorSet.getOrthogonalBasis();
				orthogonalComplement(input, vectorSet, operator);
				enterToContinue();
				break;
			case 7:
				vectorSet.print();
				enterToContinue();
				break;
			case 90:
				done = true;
				break;
			}
		}
	}
}
