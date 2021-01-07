package linearAlgebraCalculator;

import java.util.Scanner;

public class MatrixCalculator {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Matrix Calculator.\nWhat would you like to do?");
        String[][] matrixArr = new String[][] {{"1", "1", "4", "1", "2"}, {"0", "1", "2", "1", "1"},
                                               {"0", "0", "0", "1", "2"}, {"1", "-1", "0", "0", "2"},
                                               {"2", "1", "6", "0", "1"}};
        String[][] matrixArr1 =new String[][] {{"-3", "6", "-1", "1", "-7"},
                                               {"1", "-2", "2", "3", "-1"},  
                                               {"2", "-4", "5", "8", "-4"}};
        Matrix matrix = new Matrix(matrixArr1);
        matrix.getRREF();
        matrix.getNullSpace();
        input.close();
  
    }
}
