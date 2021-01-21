# LinearAlgebraCalculator
A calculator for solving linear algebra problems.

The matrix and vector elements are string literals of whole numbers and fractions, not doubles or floats.

The operations performed on matrices are not limited by the size of a given matrix/matrices (Although computation time increases the larger they become).

The following can be computed for a given matrix/matrices of appropriate dimensions.
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
  - Compute a vector with respect to a basis
  - Compute a transition matrix from one basis set to another
  - Apply the Gram-Schmidt process to a set in order to compute an orthogonal basis
  - Compute an orthogonal complement (W perp) to an orthogonal basis

PrintStringBuilder - Creates strings that visually represent matrices, augmented matrices, and null spaces that can be printed to the console.

ElementOperator - Performs basic arithmetic operations on elements of a matrix.

RowOperator - Performs elementary row operations on matrices.

MatrixOperator - Performs operations on a matrix beyond the row level.

VectorSetOperator - Performs operations on sets of vectors.

Matrix - The matrix object that many functions can be performed on.

VectorSet - A set of vectors on which computations can be performed, and for which vectors can be assesed as to their relation to.

CalcMain - The location of menus and user input.
