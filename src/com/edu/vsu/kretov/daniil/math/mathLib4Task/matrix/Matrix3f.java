package com.edu.vsu.kretov.daniil.math.mathLib4Task.matrix;

import com.edu.vsu.kretov.daniil.math.mathLib4Task.vector.Vector3f;

import java.util.Arrays;

/**
 * A mutable column-major 3x3 square matrix of floats.
 */
public class Matrix3f implements Matrix<Matrix3f> {
    // Indices for the matrix values.
    public static final int M11 = 0;
    public static final int M12 = 3;
    public static final int M13 = 6;
    public static final int M21 = 1;
    public static final int M22 = 4;
    public static final int M23 = 7;
    public static final int M31 = 2;
    public static final int M32 = 5;
    public static final int M33 = 8;
    public static final int LEN = 9;

    /**
     * The one-dimensional column-major array of scalars stored in this matrix.
     */
    public float[] val = new float[LEN];

    /**
     * Creates a new identity matrix.
     *
     * @return The identity matrix.
     */
    public static Matrix3f identity() {
        return new Matrix3f();
    }

    /**
     * Creates a new zero matrix.
     *
     * @return The zero matrix.
     */
    public static Matrix3f zero() {
        return new Matrix3f().setZero();
    }

    /**
     * Constructs an identity matrix.
     */
    public Matrix3f() {
        setIdentity();
    }

    /**
     * Constructs a matrix from the given array of floats. The values are expected to be in column-major order.
     * The array must have at least 9 values.
     *
     * @param values The array of floats.
     * @throws ArrayIndexOutOfBoundsException if there are not enough values in the array.
     */
    public Matrix3f(float[] values) {
        System.arraycopy(values, 0, this.val, 0, LEN);
    }

    @SuppressWarnings("CopyConstructorMissesField")
    public Matrix3f(Matrix3f m) {
        set(m);
    }

    /**
     * Constructs a column-major matrix from the given vectors.
     *
     * @param i The vector in the first column.
     * @param j The vector in the second column.
     * @param k The vector in the third column.
     */
    public Matrix3f(Vector3f i, Vector3f j, Vector3f k) {
        set(i, j, k);
    }

    @Override
    public Matrix3f cpy() {
        return new Matrix3f(this);
    }

    @Override
    public Matrix3f setZero() {
        for (int i = 0; i < LEN; i++) val[i] = 0.0f;
        return this;
    }

    @Override
    public Matrix3f setIdentity() {
        val[M11] = 1.0f;
        val[M12] = 0.0f;
        val[M13] = 0.0f;
        val[M21] = 0.0f;
        val[M22] = 1.0f;
        val[M23] = 0.0f;
        val[M31] = 0.0f;
        val[M32] = 0.0f;
        val[M33] = 1.0f;
        return this;
    }

    @Override
    public Matrix3f set(float[] values) {
        System.arraycopy(values, 0, this.val, 0, LEN);
        return this;
    }

    @Override
    public Matrix3f set(Matrix3f m) {
        System.arraycopy(m.val, 0, this.val, 0, LEN);
        return this;
    }

    /**
     * Sets this matrix from the given vectors.
     *
     * @param i The vector in the first column.
     * @param j The vector in the second column.
     * @param k The vector in the third column.
     * @return This matrix for chaining.
     */
    public Matrix3f set(Vector3f i, Vector3f j, Vector3f k) {
        val[M11] = i.x;
        val[M21] = i.y;
        val[M31] = i.z;
        val[M12] = j.x;
        val[M22] = j.y;
        val[M32] = j.z;
        val[M13] = k.x;
        val[M23] = k.y;
        val[M33] = k.z;
        return this;
    }

    @Override
    public Matrix3f fill(float scalar) {
        for (int i = 0; i < LEN; i++) val[i] = scalar;
        return this;
    }

    @Override
    public Matrix3f tra() {
        float m12 = val[M12];
        float m13 = val[M13];
        float m23 = val[M23];

        val[M12] = val[M21];
        val[M13] = val[M31];
        val[M21] = m12;
        val[M23] = val[M32];
        val[M31] = m13;
        val[M32] = m23;
        return this;
    }

    @Override
    public float det() {
        return val[M11] * val[M22] * val[M33] + val[M12] * val[M23] * val[M31] + val[M13] * val[M21] * val[M32]
                - val[M11] * val[M23] * val[M32] - val[M12] * val[M21] * val[M33] - val[M13] * val[M22] * val[M31];
    }

    @Override
    public Matrix3f inv() {
        float det = det();
        if (det == 0.0f) throw new ArithmeticException("Degenerate matrix.");

        // Build the adjoint matrix.
        float m11 = val[M22] * val[M33] - val[M32] * val[M23];
        float m21 = val[M31] * val[M23] - val[M21] * val[M33];
        float m31 = val[M21] * val[M32] - val[M31] * val[M22];
        float m12 = val[M32] * val[M13] - val[M12] * val[M33];
        float m22 = val[M11] * val[M33] - val[M31] * val[M13];
        float m32 = val[M31] * val[M12] - val[M11] * val[M32];
        float m13 = val[M12] * val[M23] - val[M22] * val[M13];
        float m23 = val[M21] * val[M13] - val[M11] * val[M23];
        float m33 = val[M11] * val[M22] - val[M21] * val[M12];
        float invDet = 1.0f / det;

        val[M11] = m11 * invDet;
        val[M21] = m21 * invDet;
        val[M31] = m31 * invDet;
        val[M12] = m12 * invDet;
        val[M22] = m22 * invDet;
        val[M32] = m32 * invDet;
        val[M13] = m13 * invDet;
        val[M23] = m23 * invDet;
        val[M33] = m33 * invDet;
        return this;
    }

    @Override
    public Matrix3f add(Matrix3f m) {
        for (int i = 0; i < LEN; i++) this.val[i] += m.val[i];
        return this;
    }

    @Override
    public Matrix3f sub(Matrix3f m) {
        for (int i = 0; i < LEN; i++) this.val[i] -= m.val[i];
        return this;
    }

    @Override
    public Matrix3f mul(float scalar) {
        for (int i = 0; i < LEN; i++) this.val[i] *= scalar;
        return this;
    }

    @Override
    public Matrix3f div(float scalar) {
        for (int i = 0; i < LEN; i++) this.val[i] /= scalar;
        return this;
    }

    @Override
    public Matrix3f mul(Matrix3f m) {
        float m11 = val[M11] * m.val[M11] + val[M12] * m.val[M21] + val[M13] * m.val[M31];
        float m12 = val[M11] * m.val[M12] + val[M12] * m.val[M22] + val[M13] * m.val[M32];
        float m13 = val[M11] * m.val[M13] + val[M12] * m.val[M23] + val[M13] * m.val[M33];
        float m21 = val[M21] * m.val[M11] + val[M22] * m.val[M21] + val[M23] * m.val[M31];
        float m22 = val[M21] * m.val[M12] + val[M22] * m.val[M22] + val[M23] * m.val[M32];
        float m23 = val[M21] * m.val[M13] + val[M22] * m.val[M23] + val[M23] * m.val[M33];
        float m31 = val[M31] * m.val[M11] + val[M32] * m.val[M21] + val[M33] * m.val[M31];
        float m32 = val[M31] * m.val[M12] + val[M32] * m.val[M22] + val[M33] * m.val[M32];
        float m33 = val[M31] * m.val[M13] + val[M32] * m.val[M23] + val[M33] * m.val[M33];

        val[M11] = m11;
        val[M21] = m21;
        val[M31] = m31;
        val[M12] = m12;
        val[M22] = m22;
        val[M32] = m32;
        val[M13] = m13;
        val[M23] = m23;
        val[M33] = m33;
        return this;
    }

    @Override
    public Matrix3f comMul(Matrix3f m) {
        for (int i = 0; i < LEN; i++) this.val[i] *= m.val[i];
        return this;
    }

    @Override
    public Matrix3f comDiv(Matrix3f m) {
        for (int i = 0; i < LEN; i++) this.val[i] /= m.val[i];
        return this;
    }

    /**
     * Multiplies this matrix by the given 3D column vector.
     *
     * @param v The given vector.
     * @return The changed given vector.
     */
    public Vector3f mul(Vector3f v) {
        v.x = val[M11] * v.x + val[M12] * v.y + val[M13] * v.z;
        v.y = val[M21] * v.x + val[M22] * v.y + val[M23] * v.z;
        v.z = val[M31] * v.x + val[M32] * v.y + val[M33] * v.z;
        return v;
    }

    /**
     * Multiplies the given row vector by the given matrix.
     *
     * @param v The vector.
     * @param m The matrix.
     * @return The changed vector.
     */
    public static Vector3f mul(Vector3f v, Matrix3f m) {
        v.x = m.val[M11] * v.x + m.val[M21] * v.y + m.val[M31] * v.z;
        v.y = m.val[M12] * v.x + m.val[M22] * v.y + m.val[M32] * v.z;
        v.z = m.val[M13] * v.x + m.val[M23] * v.y + m.val[M33] * v.z;
        return v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix3f m = (Matrix3f) o;
        return Arrays.equals(val, m.val);
    }

    /**
     * Checks if the given matrices are nearly equal using the provided epsilon value.
     *
     * @param epsilon The epsilon value.
     * @return Whether the two matrices are nearly equal.
     */
    public boolean epsEquals(Matrix3f m, float epsilon) {
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
