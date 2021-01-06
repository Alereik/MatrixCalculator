package matrixCalculator;

import java.util.Scanner;

public class MatrixCalculator {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Matrix Calculator.\nWhat would you like to do?");
        Matrix matrix = new Matrix();
        matrix.getUpperTriangularForm();
        matrix.getDeterminant();
        matrix.getRowReducedInverse();
        matrix.getAdjointInverse();
        matrix.getRREF();
        input.close();
    }
}
