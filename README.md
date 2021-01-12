# LinearAlgebraCalculator
A calculator for solving linear algebra problems.

The matrix and vector elements are whole number and fraction string literals, not doubles or floats.

The operations performed on these matrices are not limited by the size of a given matrix (Although computation time increases the larger they become).

The following can be computed for a given matrix/ces of appropriate dimensions.
  - Reduced Row Echelon Form
  - Adjoint
  - Inverse through row reduction
  - Inverse through adjoint method
  - Transpose
  - Upper Triangular Form
  - Determinant
  - Null Space
  - Matrix multiplication
  - Matrix scalar multiplication
  - Matrix addition/subtraction
  
The Following can be computed with a set of vectors.
  - Determine if a vector is within the span of a set
  - Determine if a set is linearly independent
  - Determine if a set forms a basis for R^n
  - Get a vector with respect to a basis set
  - Apply the Gram-Schmidt process to a set in order to compute an orthogonal basis
  - Compute an orthogonal complement to an orthogonal basis

PrintStringBuilder - Creates strings that visually represents matrices, augmeneted matrices, and null space displays that can be printed to the console.

ElementOperator - Performs basic arithmetic operations on elements of a matrix.

RowOperator - Performs elementary row operations on matrices.

MatrixOperator - Performs operations on a matrix beyond the row level.

VectorSetOperator - Performs operations on sets of vectors.

Matrix - The matrix object that many functions can be performed on.

VectorSet - A set of vectors from which computations can be performed on, and for which vectors can be assesed as to their relation to.

MainCalc - The location of menus and user input.
