package linAlgCalc;

import java.io.IOException;
import java.util.Scanner;

public class CalcMain {
	
	/**
	 * 
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean done = false;
		System.out.println("Welcome to the Linear Algebra Calculator.\n");
		while (!done) {
			System.out.println("                   Please select from these two options.\n\n"
					  + "          [Enter 1]                                    [Enter 2]\n"
					  + "   Build a matrix to compute it's:           Build two matrices in order "
					  + "to:\n"
					  + "       - Inverse                                 - Multiply Matrices\n"
					  + "       - Reduced Row Echelon Form                - Add Matrices\n"
					  + "       - Adjoint                                 - Subtract Matrices\n"
					  + "       - Upper Triangular Form\n"
					  + "       - Transpose\n"
					  + "       - Determinant\n"
					  + "       - Null Space                                    [Enter 0]\n"
					  + "       - Scalar Multiple                                - Quit");
			int userChoice = 99;
			while (userChoice != 1 && userChoice != 2 && userChoice != 0) {
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
			case 0:
				done = true;
				break;
			}
		}
		System.out.println("Goodbye.");
    }
	
	/**
	 * 
	 */
	private static void enterToContinue() {
		System.out.println("Press ENTER to continue.\n");
		try {
			System.in.read();
		} catch (IOException e) {
		}
	}
	
	/**
	 * 
	 * 
	 * @param input
	 */
	private static void oneMatrix(Scanner input) {
		Matrix oneMatrix = new Matrix();
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
				oneMatrix.getScaledMatrix();
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
	 * 
	 * 
	 * @param input
	 * @param oneMatrix
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
	 * 
	 * 
	 * @param input
	 */
	private static void twoMatrix(Scanner input) {
		MatrixOperator operator = new MatrixOperator();
		Matrix oneMatrix = new Matrix();
		Matrix twoMatrix = new Matrix();
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
}
