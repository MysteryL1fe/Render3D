package com.edu.vsu.kretov.daniil.mathLib4Task.matrix;

import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector4f;

import java.util.Arrays;

/**
 * A mutable column-major 4x4 square matrix of floats.
 */
public class Matrix4f implements Matrix<Matrix4f> {
    // Indices for the matrix values.
    public static final int M11 = 0;
    public static final int M12 = 4;
    public static final int M13 = 8;
    public static final int M14 = 12;
    public static final int M21 = 1;
    public static final int M22 = 5;
    public static final int M23 = 9;
    public static final int M24 = 13;
    public static final int M31 = 2;
    public static final int M32 = 6;
    public static final int M33 = 10;
    public static final int M34 = 14;
    public static final int M41 = 3;
    public static final int M42 = 7;
    public static final int M43 = 11;
    public static final int M44 = 15;
    public static final int LEN = 16;

    /**
     * The one-dimensional column-major array of scalars stored in this matrix.
     */
    public float[] val = new float[LEN];

    /**
     * Creates a new identity matrix.
     *
     * @return The identity matrix.
     */
    public static Matrix4f identity() {
        return new Matrix4f();
    }

    /**
     * Creates a new zero matrix.
     *
     * @return The zero matrix.
     */
    public static Matrix4f zero() {
        return new Matrix4f().setZero();
    }

    /**
     * Constructs an identity matrix.
     */
    public Matrix4f() {
        setIdentity();
    }

    /**
     * Constructs a matrix from the given array of floats. The values are expected to be in column-major order.
     * The array must have at least 16 values.
     *
     * @param values The array of floats.
     * @throws ArrayIndexOutOfBoundsException if there are not enough values in the array.
     */
    public Matrix4f(float[] values) {
        System.arraycopy(values, 0, this.val, 0, LEN);
    }

    @SuppressWarnings("CopyConstructorMissesField")
    public Matrix4f(Matrix4f m) {
        set(m);
    }

    /**
     * Constructs a column-major matrix from the given vectors.
     *
     * @param i The vector in the first column.
     * @param j The vector in the second column.
     * @param k The vector in the third column.
     * @param l The vector in the fourth column.
     */
    public Matrix4f(Vector4f i, Vector4f j, Vector4f k, Vector4f l) {
        set(i, j, k, l);
    }

    @Override
    public Matrix4f cpy() {
        return new Matrix4f(this);
    }

    @Override
    public Matrix4f setZero() {
        for (int i = 0; i < LEN; i++) val[i] = 0.0f;
        return this;
    }

    @Override
    public Matrix4f setIdentity() {
        val[M11] = 1.0f;
        val[M12] = 0.0f;
        val[M13] = 0.0f;
        val[M14] = 0.0f;
        val[M21] = 0.0f;
        val[M22] = 1.0f;
        val[M23] = 0.0f;
        val[M24] = 0.0f;
        val[M31] = 0.0f;
        val[M32] = 0.0f;
        val[M33] = 1.0f;
        val[M34] = 0.0f;
        val[M41] = 0.0f;
        val[M42] = 0.0f;
        val[M43] = 0.0f;
        val[M44] = 1.0f;
        return this;
    }

    @Override
    public Matrix4f set(float[] values) {
        System.arraycopy(values, 0, this.val, 0, LEN);
        return this;
    }

    @Override
    public Matrix4f set(Matrix4f m) {
        System.arraycopy(m.val, 0, this.val, 0, LEN);
        return this;
    }

    /**
     * Sets this matrix from the given vectors.
     *
     * @param i The vector in the first column.
     * @param j The vector in the second column.
     * @param k The vector in the third column.
     * @param l The vector in the fourth column.
     * @return This matrix for chaining.
     */
    public Matrix4f set(Vector4f i, Vector4f j, Vector4f k, Vector4f l) {
        val[M11] = i.x;
        val[M21] = i.y;
        val[M31] = i.z;
        val[M41] = i.w;
        val[M12] = j.x;
        val[M22] = j.y;
        val[M32] = j.z;
        val[M42] = j.w;
        val[M13] = k.x;
        val[M23] = k.y;
        val[M33] = k.z;
        val[M43] = k.w;
        val[M14] = l.x;
        val[M24] = l.y;
        val[M34] = l.z;
        val[M44] = l.w;
        return this;
    }

    @Override
    public Matrix4f fill(float scalar) {
        for (int i = 0; i < LEN; i++) val[i] = scalar;
        return this;
    }

    @Override
    public Matrix4f tra() {
        float m12 = val[M12];
        float m13 = val[M13];
        float m14 = val[M14];
        float m23 = val[M23];
        float m24 = val[M24];
        float m34 = val[M34];

        val[M12] = val[M21];
        val[M13] = val[M31];
        val[M14] = val[M41];
        val[M21] = m12;
        val[M23] = val[M32];
        val[M24] = val[M42];
        val[M31] = m13;
        val[M32] = m23;
        val[M34] = val[M43];
        val[M41] = m14;
        val[M42] = m24;
        val[M43] = m34;
        return this;
    }

    @Override
    public float det() {
        return val[M41] * val[M32] * val[M23] * val[M14] - val[M31] * val[M42] * val[M23] * val[M14]
                - val[M41] * val[M22] * val[M33] * val[M14] + val[M21] * val[M42] * val[M33] * val[M14]
                + val[M31] * val[M22] * val[M43] * val[M14] - val[M21] * val[M32] * val[M43] * val[M14]
                - val[M41] * val[M32] * val[M13] * val[M24] + val[M31] * val[M42] * val[M13] * val[M24]
                + val[M41] * val[M12] * val[M33] * val[M24] - val[M11] * val[M42] * val[M33] * val[M24]
                - val[M31] * val[M12] * val[M43] * val[M24] + val[M11] * val[M32] * val[M43] * val[M24]
                + val[M41] * val[M22] * val[M13] * val[M34] - val[M21] * val[M42] * val[M13] * val[M34]
                - val[M41] * val[M12] * val[M23] * val[M34] + val[M11] * val[M42] * val[M23] * val[M34]
                + val[M21] * val[M12] * val[M43] * val[M34] - val[M11] * val[M22] * val[M43] * val[M34]
                - val[M31] * val[M22] * val[M13] * val[M44] + val[M21] * val[M32] * val[M13] * val[M44]
                + val[M31] * val[M12] * val[M23] * val[M44] - val[M11] * val[M32] * val[M23] * val[M44]
                - val[M21] * val[M12] * val[M33] * val[M44] + val[M11] * val[M22] * val[M33] * val[M44];
    }

    @Override
    public Matrix4f inv() {
        float det = det();
        if (det == 0.0f) throw new ArithmeticException("Degenerate matrix.");

        // Build the adjoint matrix.
        float m11 = val[M23] * val[M34] * val[M42] - val[M24] * val[M33] * val[M42] + val[M24] * val[M32] * val[M43]
                - val[M22] * val[M34] * val[M43] - val[M23] * val[M32] * val[M44] + val[M22] * val[M33] * val[M44];
        float m12 = val[M14] * val[M33] * val[M42] - val[M13] * val[M34] * val[M42] - val[M14] * val[M32] * val[M43]
                + val[M12] * val[M34] * val[M43] + val[M13] * val[M32] * val[M44] - val[M12] * val[M33] * val[M44];
        float m13 = val[M13] * val[M24] * val[M42] - val[M14] * val[M23] * val[M42] + val[M14] * val[M22] * val[M43]
                - val[M12] * val[M24] * val[M43] - val[M13] * val[M22] * val[M44] + val[M12] * val[M23] * val[M44];
        float m14 = val[M14] * val[M23] * val[M32] - val[M13] * val[M24] * val[M32] - val[M14] * val[M22] * val[M33]
                + val[M12] * val[M24] * val[M33] + val[M13] * val[M22] * val[M34] - val[M12] * val[M23] * val[M34];
        float m21 = val[M24] * val[M33] * val[M41] - val[M23] * val[M34] * val[M41] - val[M24] * val[M31] * val[M43]
                + val[M21] * val[M34] * val[M43] + val[M23] * val[M31] * val[M44] - val[M21] * val[M33] * val[M44];
        float m22 = val[M13] * val[M34] * val[M41] - val[M14] * val[M33] * val[M41] + val[M14] * val[M31] * val[M43]
                - val[M11] * val[M34] * val[M43] - val[M13] * val[M31] * val[M44] + val[M11] * val[M33] * val[M44];
        float m23 = val[M14] * val[M23] * val[M41] - val[M13] * val[M24] * val[M41] - val[M14] * val[M21] * val[M43]
                + val[M11] * val[M24] * val[M43] + val[M13] * val[M21] * val[M44] - val[M11] * val[M23] * val[M44];
        float m24 = val[M13] * val[M24] * val[M31] - val[M14] * val[M23] * val[M31] + val[M14] * val[M21] * val[M33]
                - val[M11] * val[M24] * val[M33] - val[M13] * val[M21] * val[M34] + val[M11] * val[M23] * val[M34];
        float m31 = val[M22] * val[M34] * val[M41] - val[M24] * val[M32] * val[M41] + val[M24] * val[M31] * val[M42]
                - val[M21] * val[M34] * val[M42] - val[M22] * val[M31] * val[M44] + val[M21] * val[M32] * val[M44];
        float m32 = val[M14] * val[M32] * val[M41] - val[M12] * val[M34] * val[M41] - val[M14] * val[M31] * val[M42]
                + val[M11] * val[M34] * val[M42] + val[M12] * val[M31] * val[M44] - val[M11] * val[M32] * val[M44];
        float m33 = val[M12] * val[M24] * val[M41] - val[M14] * val[M22] * val[M41] + val[M14] * val[M21] * val[M42]
                - val[M11] * val[M24] * val[M42] - val[M12] * val[M21] * val[M44] + val[M11] * val[M22] * val[M44];
        float m34 = val[M14] * val[M22] * val[M31] - val[M12] * val[M24] * val[M31] - val[M14] * val[M21] * val[M32]
                + val[M11] * val[M24] * val[M32] + val[M12] * val[M21] * val[M34] - val[M11] * val[M22] * val[M34];
        float m41 = val[M23] * val[M32] * val[M41] - val[M22] * val[M33] * val[M41] - val[M23] * val[M31] * val[M42]
                + val[M21] * val[M33] * val[M42] + val[M22] * val[M31] * val[M43] - val[M21] * val[M32] * val[M43];
        float m42 = val[M12] * val[M33] * val[M41] - val[M13] * val[M32] * val[M41] + val[M13] * val[M31] * val[M42]
                - val[M11] * val[M33] * val[M42] - val[M12] * val[M31] * val[M43] + val[M11] * val[M32] * val[M43];
        float m43 = val[M13] * val[M22] * val[M41] - val[M12] * val[M23] * val[M41] - val[M13] * val[M21] * val[M42]
                + val[M11] * val[M23] * val[M42] + val[M12] * val[M21] * val[M43] - val[M11] * val[M22] * val[M43];
        float m44 = val[M12] * val[M23] * val[M31] - val[M13] * val[M22] * val[M31] + val[M13] * val[M21] * val[M32]
                - val[M11] * val[M23] * val[M32] - val[M12] * val[M21] * val[M33] + val[M11] * val[M22] * val[M33];
        float invDet = 1.0f / det;

        val[M11] = m11 * invDet;
        val[M21] = m21 * invDet;
        val[M31] = m31 * invDet;
        val[M41] = m41 * invDet;
        val[M12] = m12 * invDet;
        val[M22] = m22 * invDet;
        val[M32] = m32 * invDet;
        val[M42] = m42 * invDet;
        val[M13] = m13 * invDet;
        val[M23] = m23 * invDet;
        val[M33] = m33 * invDet;
        val[M43] = m43 * invDet;
        val[M14] = m14 * invDet;
        val[M24] = m24 * invDet;
        val[M34] = m34 * invDet;
        val[M44] = m44 * invDet;
        return this;
    }

    @Override
    public Matrix4f add(Matrix4f m) {
        for (int i = 0; i < LEN; i++) this.val[i] += m.val[i];
        return this;
    }

    @Override
    public Matrix4f sub(Matrix4f m) {
        for (int i = 0; i < LEN; i++) this.val[i] -= m.val[i];
        return this;
    }

    @Override
    public Matrix4f mul(float scalar) {
        for (int i = 0; i < LEN; i++) this.val[i] *= scalar;
        return this;
    }

    @Override
    public Matrix4f div(float scalar) {
        for (int i = 0; i < LEN; i++) this.val[i] /= scalar;
        return this;
    }

    @Override
    public Matrix4f mul(Matrix4f m) {
        float m11 = val[M11] * m.val[M11] + val[M12] * m.val[M21] + val[M13] * m.val[M31] + val[M14] * m.val[M41];
        float m12 = val[M11] * m.val[M12] + val[M12] * m.val[M22] + val[M13] * m.val[M32] + val[M14] * m.val[M42];
        float m13 = val[M11] * m.val[M13] + val[M12] * m.val[M23] + val[M13] * m.val[M33] + val[M14] * m.val[M43];
        float m14 = val[M11] * m.val[M14] + val[M12] * m.val[M24] + val[M13] * m.val[M34] + val[M14] * m.val[M44];
        float m21 = val[M21] * m.val[M11] + val[M22] * m.val[M21] + val[M23] * m.val[M31] + val[M24] * m.val[M41];
        float m22 = val[M21] * m.val[M12] + val[M22] * m.val[M22] + val[M23] * m.val[M32] + val[M24] * m.val[M42];
        float m23 = val[M21] * m.val[M13] + val[M22] * m.val[M23] + val[M23] * m.val[M33] + val[M24] * m.val[M43];
        float m24 = val[M21] * m.val[M14] + val[M22] * m.val[M24] + val[M23] * m.val[M34] + val[M24] * m.val[M44];
        float m31 = val[M31] * m.val[M11] + val[M32] * m.val[M21] + val[M33] * m.val[M31] + val[M34] * m.val[M41];
        float m32 = val[M31] * m.val[M12] + val[M32] * m.val[M22] + val[M33] * m.val[M32] + val[M34] * m.val[M42];
        float m33 = val[M31] * m.val[M13] + val[M32] * m.val[M23] + val[M33] * m.val[M33] + val[M34] * m.val[M43];
        float m34 = val[M31] * m.val[M14] + val[M32] * m.val[M24] + val[M33] * m.val[M34] + val[M34] * m.val[M44];
        float m41 = val[M41] * m.val[M11] + val[M42] * m.val[M21] + val[M43] * m.val[M31] + val[M44] * m.val[M41];
        float m42 = val[M41] * m.val[M12] + val[M42] * m.val[M22] + val[M43] * m.val[M32] + val[M44] * m.val[M42];
        float m43 = val[M41] * m.val[M13] + val[M42] * m.val[M23] + val[M43] * m.val[M33] + val[M44] * m.val[M43];
        float m44 = val[M41] * m.val[M14] + val[M42] * m.val[M24] + val[M43] * m.val[M34] + val[M44] * m.val[M44];

        val[M11] = m11;
        val[M21] = m21;
        val[M31] = m31;
        val[M41] = m41;
        val[M12] = m12;
        val[M22] = m22;
        val[M32] = m32;
        val[M42] = m42;
        val[M13] = m13;
        val[M23] = m23;
        val[M33] = m33;
        val[M43] = m43;
        val[M14] = m14;
        val[M24] = m24;
        val[M34] = m34;
        val[M44] = m44;
        return this;
    }

    @Override
    public Matrix4f comMul(Matrix4f m) {
        for (int i = 0; i < LEN; i++) this.val[i] *= m.val[i];
        return this;
    }

    @Override
    public Matrix4f comDiv(Matrix4f m) {
        for (int i = 0; i < LEN; i++) this.val[i] /= m.val[i];
        return this;
    }

    /**
     * Multiplies this matrix by the given 4D column vector.
     *
     * @param v The given vector.
     * @return The changed given vector.
     */
    public Vector4f mul(Vector4f v) {
        v.x = val[M11] * v.x + val[M12] * v.y + val[M13] * v.z + val[M14] * v.w;
        v.y = val[M21] * v.x + val[M22] * v.y + val[M23] * v.z + val[M24] * v.w;
        v.z = val[M31] * v.x + val[M32] * v.y + val[M33] * v.z + val[M34] * v.w;
        v.w = val[M41] * v.x + val[M42] * v.y + val[M43] * v.z + val[M44] * v.w;
        return v;
    }

    /**
     * Multiplies the given row vector by the given matrix.
     *
     * @param v The vector.
     * @param m The matrix.
     * @return The changed vector.
     */
    public static Vector4f mul(Vector4f v, Matrix4f m) {
        v.x = m.val[M11] * v.x + m.val[M21] * v.y + m.val[M31] * v.z + m.val[M41] * v.w;
        v.y = m.val[M12] * v.x + m.val[M22] * v.y + m.val[M32] * v.z + m.val[M42] * v.w;
        v.z = m.val[M13] * v.x + m.val[M23] * v.y + m.val[M33] * v.z + m.val[M43] * v.w;
        v.w = m.val[M14] * v.x + m.val[M24] * v.y + m.val[M34] * v.z + m.val[M44] * v.w;
        return v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix4f matrix4f = (Matrix4f) o;
        return Arrays.equals(val, matrix4f.val);
    }

    /**
     * Checks if the given matrices are nearly equal using the provided epsilon value.
     *
     * @param epsilon The epsilon value.
     * @return Whether the two matrices are nearly equal.
     */
    public boolean epsEquals(Matrix4f m, float epsilon) {
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
