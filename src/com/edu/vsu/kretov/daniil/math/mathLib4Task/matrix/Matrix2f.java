package com.edu.vsu.kretov.daniil.math.mathLib4Task.matrix;

import com.edu.vsu.kretov.daniil.math.mathLib4Task.vector.Vector2f;

import java.util.Arrays;

/**
 * A mutable column-major 2x2 square matrix of floats.
 */
public class Matrix2f implements Matrix<Matrix2f> {
    // Indices for the matrix values.
    public static final int M11 = 0;
    public static final int M12 = 2;
    public static final int M21 = 1;
    public static final int M22 = 3;
    public static final int LEN = 4;

    /**
     * The one-dimensional column-major array of scalars stored in this matrix.
     */
    public float[] val = new float[LEN];

    /**
     * Creates a new identity matrix.
     *
     * @return The identity matrix.
     */
    public static Matrix2f identity() {
        return new Matrix2f();
    }

    /**
     * Creates a new zero matrix.
     *
     * @return The zero matrix.
     */
    public static Matrix2f zero() {
        return new Matrix2f().setZero();
    }

    /**
     * Constructs an identity matrix.
     */
    public Matrix2f() {
        setIdentity();
    }

    /**
     * Constructs a matrix from the given array of floats. The values are expected to be in column-major order.
     * The array must have at least 4 values.
     *
     * @param values The array of floats.
     * @throws ArrayIndexOutOfBoundsException if there are not enough values in the array.
     */
    public Matrix2f(float[] values) {
        System.arraycopy(values, 0, this.val, 0, LEN);
    }

    @SuppressWarnings("CopyConstructorMissesField")
    public Matrix2f(Matrix2f m) {
        set(m);
    }

    /**
     * Constructs a column-major matrix from the given vectors.
     *
     * @param i The vector in the first column.
     * @param j The vector in the second column.
     */
    public Matrix2f(Vector2f i, Vector2f j) {
        set(i, j);
    }

    @Override
    public Matrix2f cpy() {
        return new Matrix2f(this);
    }

    @Override
    public Matrix2f setZero() {
        for (int i = 0; i < LEN; i++) val[i] = 0.0f;
        return this;
    }

    @Override
    public Matrix2f setIdentity() {
        val[M11] = 1.0f;
        val[M12] = 0.0f;
        val[M21] = 0.0f;
        val[M22] = 1.0f;
        return this;
    }

    @Override
    public Matrix2f set(float[] values) {
        System.arraycopy(values, 0, this.val, 0, LEN);
        return this;
    }

    @Override
    public Matrix2f set(Matrix2f m) {
        System.arraycopy(m.val, 0, this.val, 0, LEN);
        return this;
    }

    /**
     * Sets this matrix from the given vectors.
     *
     * @param i The vector in the first column.
     * @param j The vector in the second column.
     * @return This matrix for chaining.
     */
    public Matrix2f set(Vector2f i, Vector2f j) {
        val[M11] = i.x;
        val[M21] = i.y;
        val[M12] = j.x;
        val[M22] = j.y;
        return this;
    }

    @Override
    public Matrix2f fill(float scalar) {
        for (int i = 0; i < LEN; i++) val[i] = scalar;
        return this;
    }

    @Override
    public Matrix2f tra() {
        float m12 = val[M12];
        val[M12] = val[M21];
        val[M21] = m12;
        return this;
    }

    @Override
    public float det() {
        return val[M11] * val[M22] - val[M12] * val[M21];
    }

    @Override
    public Matrix2f inv() {
        float det = det();
        if (det == 0.0f) throw new ArithmeticException("Degenerate matrix.");

        // Build the adjoint matrix.
        float m11 = val[M22];
        float m21 = -val[M21];
        float m12 = -val[M12];
        float m22 = val[M11];
        float invDet = 1.0f / det;

        val[M11] = m11 * invDet;
        val[M21] = m21 * invDet;
        val[M12] = m12 * invDet;
        val[M22] = m22 * invDet;
        return this;
    }

    @Override
    public Matrix2f add(Matrix2f m) {
        for (int i = 0; i < LEN; i++) this.val[i] += m.val[i];
        return this;
    }

    @Override
    public Matrix2f sub(Matrix2f m) {
        for (int i = 0; i < LEN; i++) this.val[i] -= m.val[i];
        return this;
    }

    @Override
    public Matrix2f mul(float scalar) {
        for (int i = 0; i < LEN; i++) this.val[i] *= scalar;
        return this;
    }

    @Override
    public Matrix2f div(float scalar) {
        for (int i = 0; i < LEN; i++) this.val[i] /= scalar;
        return this;
    }

    @Override
    public Matrix2f mul(Matrix2f m) {
        float m11 = val[M11] * m.val[M11] + val[M12] * m.val[M21];
        float m12 = val[M11] * m.val[M12] + val[M12] * m.val[M22];
        float m21 = val[M21] * m.val[M11] + val[M22] * m.val[M21];
        float m22 = val[M21] * m.val[M12] + val[M22] * m.val[M22];

        val[M11] = m11;
        val[M12] = m12;
        val[M21] = m21;
        val[M22] = m22;
        return this;
    }

    @Override
    public Matrix2f comMul(Matrix2f m) {
        for (int i = 0; i < LEN; i++) this.val[i] *= m.val[i];
        return this;
    }

    @Override
    public Matrix2f comDiv(Matrix2f m) {
        for (int i = 0; i < LEN; i++) this.val[i] /= m.val[i];
        return this;
    }

    /**
     * Multiplies this matrix by the given 2D column vector.
     *
     * @param v The given vector.
     * @return The changed given vector.
     */
    public Vector2f mul(Vector2f v) {
        v.x = val[M11] * v.x + val[M12] * v.y;
        v.y = val[M21] * v.x + val[M22] * v.y;
        return v;
    }

    /**
     * Multiplies the given row vector by the given matrix.
     *
     * @param v The vector.
     * @param m The matrix.
     * @return The changed vector.
     */
    public static Vector2f mul(Vector2f v, Matrix2f m) {
        v.x = m.val[M11] * v.x + m.val[M21] * v.y;
        v.y = m.val[M12] * v.x + m.val[M22] * v.y;
        return v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix2f m = (Matrix2f) o;
        return Arrays.equals(val, m.val);
    }

    /**
     * Checks if the given matrices are nearly equal using the provided epsilon value.
     *
     * @param epsilon The epsilon value.
     * @return Whether the two matrices are nearly equal.
     */
    public boolean epsEquals(Matrix2f m, float epsilon) {
        if (this == m) return true;
        if (m == null) return false;
        for (int i = 0; i < LEN; i++) {
            if (Math.abs(val[i] - m.val[i]) > epsilon) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(val);
    }
}
