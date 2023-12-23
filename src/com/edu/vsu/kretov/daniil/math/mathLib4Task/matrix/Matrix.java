package com.edu.vsu.kretov.daniil.math.mathLib4Task.matrix;

/**
 * A mutable square matrix.
 *
 * @author Alexander Laptev
 */
public interface Matrix<T extends Matrix<T>> {
    /**
     * @return A copy of this matrix.
     */
    T cpy();

    /**
     * Fills this matrix with zeroes.
     *
     * @return This matrix for chaining.
     */
    T setZero();

    /**
     * Sets this matrix to the identity matrix.
     *
     * @return This matrix for chaining.
     */
    T setIdentity();

    /**
     * Sets a matrix from the given array of floats. The values are expected to be in column-major order.
     * The array must have at least 16 values.
     *
     * @param values The array of floats.
     * @throws ArrayIndexOutOfBoundsException if there are not enough values in the array.
     */
    T set(float[] values);

    /**
     * Sets this matrix to the given matrix.
     *
     * @param m The matrix.
     * @return This matrix for chaining.
     */
    T set(T m);

    /**
     * Fills this matrix with the specified scalar value.
     *
     * @param scalar The scalar.
     * @return This matrix for chaining.
     */
    T fill(float scalar);

    /**
     * Transposes this matrix.
     *
     * @return This matrix for chaining.
     */
    T tra();

    /**
     * @return The determinant of this matrix.
     */
    float det();

    /**
     * Inverts this matrix.
     *
     * @return This matrix for chaining.
     * @throws ArithmeticException if the matrix is degenerate (det(this) == 0).
     */
    T inv();

    /**
     * Adds the given matrix to this matrix.
     *
     * @param m The matrix to add.
     * @return This matrix for chaining.
     */
    T add(T m);

    /**
     * Subtracts the given matrix from this matrix.
     *
     * @param m The matrix to subtract.
     * @return This matrix for chaining.
     */
    T sub(T m);

    /**
     * Multiplies this matrix by the given scalar.
     *
     * @param scalar The scalar.
     * @return This matrix for chaining.
     */
    T mul(float scalar);

    /**
     * Divides this matrix by the given scalar.
     *
     * @param scalar The scalar.
     * @return This matrix for chaining.
     */
    T div(float scalar);

    /**
     * Multiplies this matrix by the given matrix. Calling <code>A.mul(B)</code> results in the following:
     * <pre>A <- AB</pre>
     *
     * @param m The other matrix.
     * @return This matrix for chaining.
     */
    T mul(T m);

    /**
     * Performs a component-wise multiplication (Hadamard product) of the two matrices.
     *
     * @param m The other matrix.
     * @return This matrix for chaining.
     */
    T comMul(T m);

    /**
     * Performs a component-wise division of the two matrices.
     *
     * @param m The other matrix.
     * @return This matrix for chaining.
     */
    T comDiv(T m);
}
