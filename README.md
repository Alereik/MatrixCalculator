# MatrixCalculator
A matrix calculator for solving linear algebra problems primarily through the use of elementary row operations.

The matrix elements are whole number and fraction string literals, not doubles or floats.

The operations performed on these matrices are not limited by the size of a given matrix (Although computation time increases the larger they become).

The following can be computed for a given matrix of appropriate dimensions.
  - Reduced Row Echelon Form
  - Adjoint
  - Inverse through row reduction
  - Inverse through adjoint method
  - Transpose
  - Upper Triangular Form
  - Determinant
  - Null Space

MatrixStringBuilder - Creates strings that visually represents matrices, augmeneted matrices, and null space displays that can be printed to the console.

ElementOperator - Performs basic arithmetic operations on elements of a matrix.

RowOperator - Performs elementary row operations on matrices. Extends ElementOperator.

MatrixOperator - Performs operations on a matrix beyond the row level. Extends RowOperator.

Matrix - The matrix object that many functions can be performed on.

MatrixCalculator - The location of menus and user input.
