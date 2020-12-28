# MatrixCalculator
A matrix calculator for performing row operations on matrices and using the results to solve linear algebra problems.

MatrixString - Creates strings that visually represents matrices and augmeneted matrices and can be printed to the console.

RowOperations - Performs elementary row operations on matrices.



12/28/2020: - In MatrixString class: Created getMatrixWidth method, then rewrote printMatrix and both printAugmentedMatrix methods to call this method in order to reduce repeated               code. Removed exception handling, as that should be unnecessary with proper code concerning the initialization of the matrices and vectors.
            - All exception handling in RowOperations class removed, as it resulted in too much unnecessary code and if the code for building the intitial matrix is done                         properly, none of the currently handled exceptions should ever occur in this class.
