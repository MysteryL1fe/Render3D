package com.edu.vsu.kretov.daniil.mathLib4Task.vector;

import com.edu.vsu.kretov.daniil.mathLib4Task.MathUtils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A mutable three-dimensional float vector.
 *
 * @author Daniil Kretov
 */
public class Vector3f implements Vector<Vector3f> {
    /**
     * The x component of this vector.
     */
    public float x;

    /**
     * The y component of this vector.
     */
    public float y;

    /**
     * The z component of this vector.
     */
    public float z;

    /**
     * @return A zero vector.
     */
    public static Vector3f zero() {
        return new Vector3f();
    }

    /**
     * @return An x unit vector.
     */
    public static Vector3f unitX() {
        return new Vector3f(1.0f, 0.0f, 0.0f);
    }

    /**
     * @return A y unit vector.
     */
    public static Vector3f unitY() {
        return new Vector3f(0.0f, 1.0f, 0.0f);
    }

    /**
     * @return A z unit vector.
     */
    public static Vector3f unitZ() {
        return new Vector3f(0.0f, 0.0f, 1.0f);
    }

    /**
     * Constructs a zero vector.
     */
    public Vector3f() { }

    /**
     * Constructs a vector from the given float array. The array must have at least three values.
     *
     * @param values The array of floats.
     */
    public Vector3f(float[] values) {
        this.x = values[0];
        this.y = values[1];
        this.z = values[2];
    }

    /**
     * Constructs a vector from the given vector.
     *
     * @param v The vector.
     */
    public Vector3f(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * Constructs a vector with the given components.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a vector from the given 2D vector. The z component is set to zero.
     *
     * @param v The vector.
     */
    public Vector3f(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = 0.0f;
    }

    /**
     * Constructs a vector from the given 2D vector with the provided z component.
     *
     * @param v The vector.
     * @param z The z component of the vector.
     */
    public Vector3f(Vector2f v, float z) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
    }

    /**
     * Constructs a vector from the given 4D vector. The w component is discarded.
     *
     * @param v The vector.
     */
    public Vector3f(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3f v = (Vector3f) o;
        return this.idt(v);
    }

    @Override
    public boolean epsEquals(Vector3f v, float epsilon) {
        if (this == v) return true;
        if (v == null) return false;
        if (Math.abs(this.x - v.x) > epsilon) return false;
        if (Math.abs(this.y - v.y) > epsilon) return false;
        return Math.abs(this.z - v.z) <= epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "v3f(x=" + x + ", y=" + y + ", z=" + z + ")";
    }

    @Override
    public boolean idt(Vector3f v) {
        return epsIdt(v, MathUtils.EPSILON);
    }

    @Override
    public boolean epsIdt(Vector3f v, float epsilon) {
        if (Math.abs(this.x - v.x) > epsilon) return false;
        if (Math.abs(this.y - v.y) > epsilon) return false;
        return Math.abs(this.z - v.z) <= epsilon;
    }

    @Override
    public Vector3f cpy() {
        return new Vector3f(this.x, this.y, this.z);
    }

    @Override
    public float[] toArray() {
        float[] result = new float[3];
        result[0] = x;
        result[1] = y;
        result[2] = z;
        return result;
    }

    /**
     * Sets the components of this vector to the specified values.
     *
     * @param x The new x component of this vector.
     * @param y The new y component of this vector.
     * @param z The new z component of this vector.
     * @return This vector for chaining.
     */
    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Sets this vector from the given float array. The array must have at least three values.
     *
     * @param values The array of floats.
     * @return This vector for chaining.
     */
    public Vector3f set(float[] values) {
        this.x = values[0];
        this.y = values[1];
        this.z = values[2];
        return this;
    }

    /**
     * Sets this vector from the given 2D vector. The z component is set to zero.
     *
     * @param v The vector.
     * @return This vector for chaining.
     */
    public Vector3f set(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = 0.0f;
        return this;
    }

    /**
     * Sets this vector from the given 2D vector with the specified z component.
     *
     * @param v The vector.
     * @param z The z component of this vector.
     * @return This vector for chaining.
     */
    public Vector3f set(Vector2f v, float z) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
        return this;
    }

    @Override
    public Vector3f set(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        return this;
    }

    /**
     * Sets this vector from the given 4D vector. The w component is discarded.
     *
     * @param v The vector.
     * @return This vector for chaining.
     */
    public Vector3f set(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        return this;
    }

    @Override
    public Vector3f set(float v) {
        this.x = v;
        this.y = v;
        this.z = v;
        return this;
    }

    @Override
    public Vector3f setZero() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        return this;
    }

    @Override
    public Vector3f nor() {
        if (x == 0.0f && y == 0.0f && z == 0.0f) return this;

        float len = len();
        this.x /= len;
        this.y /= len;
        this.z /= len;
        return this;
    }

    /**
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @return The length of the given vector.
     */
    public static float len(float x, float y, float z) {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @return The square of the length of the given vector.
     */
    public static float len2(float x, float y, float z) {
        return x * x + y * y + z * z;
    }

    @Override
    public float len() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public float len2() {
        return x * x + y * y + z * z;
    }

    @Override
    public Vector3f limit(float limit) {
        float len2 = x * x + y * y + z * z;
        if (len2 > limit * limit) {
            float alpha = limit / (float) Math.sqrt(len2);
            this.x *= alpha;
            this.y *= alpha;
            this.z *= alpha;
        }
        return this;
    }

    @Override
    public Vector3f limit2(float limit2) {
        float len2 = x * x + y * y + z * z;
        if (len2 > limit2) {
            float alpha = (float) Math.sqrt(limit2 / len2);
            this.x *= alpha;
            this.y *= alpha;
            this.z *= alpha;
        }
        return this;
    }

    @Override
    public Vector3f setLength(float length) {
        float oldLen = (float) Math.sqrt(x * x + y * y + z * z);
        float alpha = length / oldLen;
        this.x *= alpha;
        this.y *= alpha;
        this.z *= alpha;
        return this;
    }

    @Override
    public Vector3f setLength2(float length2) {
        float oldLen2 = x * x + y * y + z * z;
        float alpha = (float) Math.sqrt(length2 / oldLen2);
        this.x *= alpha;
        this.y *= alpha;
        this.z *= alpha;
        return this;
    }

    @Override
    public Vector3f clamp(float minLength, float maxLength) {
        float len2 = len2();
        if (len2 == 0.0f) return this;

        float max2 = maxLength * maxLength;
        if (len2 > max2) {
            float alpha = (float) Math.sqrt(max2 / len2);
            this.x *= alpha;
            this.y *= alpha;
            this.z *= alpha;
            return this;
        }

        float min2 = minLength * minLength;
        if (len2 < min2) {
            float alpha = (float) Math.sqrt(min2 / len2);
            this.x *= alpha;
            this.y *= alpha;
            this.z *= alpha;
            return this;
        }

        return this;
    }

    @Override
    public Vector3f add(Vector3f v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }

    @Override
    public Vector3f add(float v) {
        this.x += v;
        this.y += v;
        this.z += v;
        return this;
    }

    /**
     * Adds the given vector to this vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @return This vector for chaining.
     */
    public Vector3f add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public Vector3f sub(Vector3f v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }

    @Override
    public Vector3f sub(float v) {
        this.x -= v;
        this.y -= v;
        this.z -= v;
        return this;
    }

    /**
     * Subtracts the given vector from this vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @return This vector for chaining.
     */
    public Vector3f sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @Override
    public Vector3f scl(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    /**
     * Performs a component-wise multiplication (Hadamard product) of this vector and the given vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @return This vector for chaining.
     */
    public Vector3f scl(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public Vector3f scl(Vector3f v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
        return this;
    }

    @Override
    public Vector3f div(float scalar) {
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
        return this;
    }

    /**
     * Performs a component-wise division of this vector and the given vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @return This vector for chaining.
     */
    public Vector3f div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    @Override
    public Vector3f div(Vector3f v) {
        this.x /= v.x;
        this.y /= v.y;
        this.z /= v.z;
        return this;
    }

    /**
     * @param v1 The first vector.
     * @param v2 The second vector.
     * @return The distance between the two vectors.
     */
    public static float dst(Vector3f v1, Vector3f v2) {
        float a = v2.x - v1.x;
        float b = v2.y - v1.y;
        float c = v2.z - v1.z;
        return (float) Math.sqrt(a * a + b * b + c * c);
    }

    /**
     * @return The distance between the two vectors.
     */
    public static float dst(
            float x1, float y1, float z1,
            float x2, float y2, float z2
    ) {
        float a = x2 - x1;
        float b = y2 - y1;
        float c = z2 - z1;
        return (float) Math.sqrt(a * a + b * b + c * c);
    }

    /**
     * @param v1 The first vector.
     * @param v2 The second vector.
     * @return The square of the distance between the two vectors.
     */
    public static float dst2(Vector3f v1, Vector3f v2) {
        float a = v2.x - v1.x;
        float b = v2.y - v1.y;
        float c = v2.z - v1.z;
        return a * a + b * b + c * c;
    }

    /**
     * @return The square of the distance between the two vectors.
     */
    public static float dst2(
            float x1, float y1, float z1,
            float x2, float y2, float z2
    ) {
        float a = x2 - x1;
        float b = y2 - y1;
        float c = z2 - z1;
        return a * a + b * b + c * c;
    }

    @Override
    public float dst(Vector3f v) {
        float a = v.x - this.x;
        float b = v.y - this.y;
        float c = v.z - this.z;
        return (float) Math.sqrt(a * a + b * b + c * c);
    }

    /**
     * @return The distance between the two vectors.
     */
    public float dst(float x, float y, float z) {
        float a = x - this.x;
        float b = y - this.y;
        float c = z - this.z;
        return (float) Math.sqrt(a * a + b * b + c * c);
    }

    @Override
    public float dst2(Vector3f v) {
        float a = v.x - this.x;
        float b = v.y - this.y;
        float c = v.z - this.z;
        return a * a + b * b + c * c;
    }

    /**
     * @return The square of the distance between the two vectors.
     */
    public float dst2(float x, float y, float z) {
        float a = x - this.x;
        float b = y - this.y;
        float c = z - this.z;
        return a * a + b * b + c * c;
    }

    /**
     * Linearly interpolates between the first vector and the second vector based on the alpha coefficient.
     *
     * @param v1    The first vector.
     * @param v2    The second vector.
     * @param alpha The alpha coefficient.
     * @return The result of the linear interpolation.
     */
    public static Vector3f lerp(Vector3f v1, Vector3f v2, float alpha) {
        float x = v1.x + alpha * (v2.x - v1.x);
        float y = v1.y + alpha * (v2.y - v1.y);
        float z = v1.z + alpha * (v2.z - v1.z);
        return new Vector3f(x, y, z);
    }

    @Override
    public Vector3f lerp(Vector3f v, float alpha) {
        this.x += alpha * (v.x - this.x);
        this.y += alpha * (v.y - this.y);
        this.z += alpha * (v.z - this.z);
        return this;
    }

    @Override
    public Vector3f addMul(Vector3f v, float scalar) {
        this.x += v.x * scalar;
        this.y += v.y * scalar;
        this.z += v.z * scalar;
        return this;
    }

    @Override
    public Vector3f addMul(Vector3f v, Vector3f v2) {
        this.x += v.x * v2.x;
        this.y += v.y * v2.y;
        this.z += v.z * v2.z;
        return this;
    }

    @Override
    public float dot(Vector3f v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    /**
     * Sets this vector to the cross product of the two vectors.
     *
     * @param v The other vector.
     * @return This vector for chaining.
     */
    public Vector3f crs(Vector3f v) {
        float ax = this.x;
        float ay = this.y;
        float az = this.z;
        this.x = ay * v.z - az * v.y;
        this.y = az * v.x - ax * v.z;
        this.z = ax * v.y - ay * v.x;
        return this;
    }

    /**
     * Sets this vector to the vector from this vector to the specified vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @return This vector for chaining.
     */
    public Vector3f to(float x, float y, float z) {
        this.x = x - this.x;
        this.y = y - this.y;
        this.z = z - this.z;
        return this;
    }

    @Override
    public Vector3f to(Vector3f v) {
        this.x = v.x - this.x;
        this.y = v.y - this.y;
        this.z = v.z - this.z;
        return this;
    }

    @Override
    public boolean isOnLine(Vector3f v) {
        return isOnLine(v, MathUtils.EPSILON);
    }

    @Override
    public boolean isOnLine(Vector3f v, float epsilon) {
        float a = v.x / this.x;
        float b = v.y / this.y;
        float c = v.z / this.z;
        return Math.abs(a - b) <= epsilon
                && Math.abs(b - c) <= epsilon
                && Math.abs(c - a) <= epsilon;
    }

    @Override
    public boolean isCollinear(Vector3f v) {
        return isCollinear(v, MathUtils.EPSILON);
    }

    @Override
    public boolean isCollinear(Vector3f v, float epsilon) {
        return isOnLine(v, epsilon) && (this.x * v.x + this.y * v.y + this.z * v.z > 0);
    }

    @Override
    public boolean isCollinearOpposite(Vector3f v) {
        return isCollinearOpposite(v, MathUtils.EPSILON);
    }

    @Override
    public boolean isCollinearOpposite(Vector3f v, float epsilon) {
        return isOnLine(v, epsilon) && (this.x * v.x + this.y * v.y + this.z * v.z < 0);
    }

    @Override
    public boolean isOrthogonal(Vector3f v) {
        return isOrthogonal(v, MathUtils.EPSILON);
    }

    @Override
    public boolean isOrthogonal(Vector3f v, float epsilon) {
        return Math.abs(this.x * v.x + this.y * v.y + this.z * v.z) < epsilon;
    }

    @Override
    public boolean hasSameDirection(Vector3f v) {
        return this.x * v.x + this.y * v.y + this.z * v.z > 0;
    }

    @Override
    public boolean hasOppositeDirection(Vector3f v) {
        return this.x * v.x + this.y * v.y + this.z * v.z < 0;
    }

    @Override
    public boolean isUnit() {
        return isUnit(MathUtils.EPSILON);
    }

    @Override
    public boolean isUnit(float epsilon) {
        return MathUtils.epsEquals(x * x + y * y + z * z, 1.0f, epsilon);
    }

    @Override
    public boolean isZero() {
        return isZero(MathUtils.EPSILON);
    }

    @Override
    public boolean isZero(float epsilon) {
        return MathUtils.epsEquals(x * x + y * y + z * z, 0.0f, epsilon);
    }
    /**
     * Calculates the cross product of this vector and the given vector.
     *
     * @param v The other vector.
     * @return A new vector representing the cross product.
     */
    public Vector3f crossVect(Vector3f v) {
        float newX = this.y * v.z - this.z * v.y;
        float newY = this.z * v.x - this.x * v.z;
        float newZ = this.x * v.y - this.y * v.x;
        return new Vector3f(newX, newY, newZ);
    }





}
