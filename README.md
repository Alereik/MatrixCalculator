# MatrixCalculator
A matrix calculator for performing row operations on matrices and using the results to solve linear algebra problems.

MatrixString - Creates string that visually represents matrices and augmeneted matrices.

RowOperations - Performs elementary row operations on matrices.



12/28/2020: - The MatrixString class, while functioning properly, needs to be rewritten in order to reduce repeated code.
            - All exception handling in RowOperations class removed, as it resulted in too much unnecessary code and if the code for building the intitial matrix is done                         properly, none of the currently handled exceptions should ever occur in this class.
