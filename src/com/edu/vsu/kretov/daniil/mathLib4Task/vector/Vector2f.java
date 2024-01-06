package com.edu.vsu.kretov.daniil.mathLib4Task.vector;

import com.edu.vsu.kretov.daniil.mathLib4Task.MathUtils;

import java.util.Objects;

/**
 * A mutable two-dimensional float vector.
 *
 * @author Alexander Laptev
 */
public class Vector2f implements Vector<Vector2f> {
    /**
     * The x component of this vector.
     */
    public float x;

    /**
     * The y component of this vector.
     */
    public float y;

    /**
     * @return A zero vector.
     */
    public static Vector2f zero() {
        return new Vector2f();
    }

    /**
     * @return An x unit vector.
     */
    public static Vector2f unitX() {
        return new Vector2f(1.0f, 0.0f);
    }

    /**
     * @return A y unit vector.
     */
    public static Vector2f unitY() {
        return new Vector2f(0.0f, 1.0f);
    }

    /**
     * Constructs a zero vector.
     */
    public Vector2f() {}

    /**
     * Constructs a vector from the given float array. The array must have at least two values.
     *
     * @param values The array of floats.
     */
    public Vector2f(float[] values) {
        this.x = values[0];
        this.y = values[1];
    }

    /**
     * Constructs a vector from the given vector.
     *
     * @param v The vector.
     */
    public Vector2f(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * Constructs a vector with the given components.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a vector from the given 4D vector. The z and w components are discarded.
     *
     * @param v The vector.
     */
    public Vector2f(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * Constructs a vector from the given 4D vector. The z component is discarded.
     *
     * @param v The vector.
     */
    public Vector2f(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
    }

    @Override
    public boolean epsEquals(Vector2f v, float epsilon) {
        if (this == v) return true;
        if (v == null) return false;
        if (Math.abs(this.x - v.x) > epsilon) return false;
        return Math.abs(this.y - v.y) <= epsilon;
    }

    @Override
    public String toString() {
        return "v2f(x=" + x + ", y=" + y + ")";
    }

    @Override
    public boolean idt(Vector2f v) {
        return epsIdt(v, MathUtils.EPSILON);
    }

    @Override
    public boolean epsIdt(Vector2f v, float epsilon) {
        if (Math.abs(this.x - v.x) > epsilon) return false;
        return Math.abs(this.y - v.y) <= epsilon;
    }

    @Override
    public Vector2f cpy() {
        return new Vector2f(this.x, this.y);
    }

    @Override
    public float[] toArray() {
        float[] result = new float[2];
        result[0] = x;
        result[1] = y;
        return result;
    }

    /**
     * Sets the components of this vector to the specified values.
     *
     * @param x The new x component of this vector.
     * @param y The new y component of this vector.
     * @return This vector for chaining.
     */
    public Vector2f set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Sets this vector from the given float array. The array must have at least two values.
     *
     * @param values The array of floats.
     * @return This vector for chaining.
     */
    public Vector2f set(float[] values) {
        this.x = values[0];
        this.y = values[1];
        return this;
    }

    @Override
    public Vector2f set(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    /**
     * Sets this vector from the given 4D vector. The z component is discarded.
     *
     * @param v The vector.
     * @return This vector for chaining.
     */
    public Vector2f set(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    /**
     * Sets this vector from the given 4D vector. The z and w components are discarded.
     *
     * @param v The vector.
     * @return This vector for chaining.
     */
    public Vector2f set(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    @Override
    public Vector2f set(float v) {
        this.x = v;
        this.y = v;
        return this;
    }

    @Override
    public Vector2f setZero() {
        this.x = 0;
        this.y = 0;
        return this;
    }

    @Override
    public Vector2f nor() {
        if (x == 0.0f && y == 0.0f) return this;

        float len = len();
        this.x /= len;
        this.y /= len;
        return this;
    }

    /**
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @return The length of the given vector.
     */
    public static float len(float x, float y) {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @return The square of the length of the given vector.
     */
    public static float len2(float x, float y) {
        return x * x + y * y;
    }

    @Override
    public float len() {
        return (float) Math.sqrt(x * x + y * y);
    }

    @Override
    public float len2() {
        return x * x + y * y;
    }

    @Override
    public Vector2f limit(float limit) {
        float len2 = x * x + y * y;
        if (len2 > limit * limit) {
            float alpha = limit / (float) Math.sqrt(len2);
            this.x *= alpha;
            this.y *= alpha;
        }
        return this;
    }

    @Override
    public Vector2f limit2(float limit2) {
        float len2 = x * x + y * y;
        if (len2 > limit2) {
            float alpha = (float) Math.sqrt(limit2 / len2);
            this.x *= alpha;
            this.y *= alpha;
        }
        return this;
    }

    @Override
    public Vector2f setLength(float length) {
        float oldLen = (float) Math.sqrt(x * x + y * y);
        float alpha = length / oldLen;
        this.x *= alpha;
        this.y *= alpha;
        return this;
    }

    @Override
    public Vector2f setLength2(float length2) {
        float oldLen2 = x * x + y * y;
        float alpha = (float) Math.sqrt(length2 / oldLen2);
        this.x *= alpha;
        this.y *= alpha;
        return this;
    }

    @Override
    public Vector2f clamp(float minLength, float maxLength) {
        float len2 = len2();
        if (len2 == 0.0f) return this;

        float max2 = maxLength * maxLength;
        if (len2 > max2) {
            float alpha = (float) Math.sqrt(max2 / len2);
            this.x *= alpha;
            this.y *= alpha;
            return this;
        }

        float min2 = minLength * minLength;
        if (len2 < min2) {
            float alpha = (float) Math.sqrt(min2 / len2);
            this.x *= alpha;
            this.y *= alpha;
            return this;
        }

        return this;
    }

    @Override
    public Vector2f add(Vector2f v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    @Override
    public Vector2f add(float v) {
        this.x += v;
        this.y += v;
        return this;
    }

    /**
     * Adds the given vector to this vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @return This vector for chaining.
     */
    public Vector2f add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    @Override
    public Vector2f sub(Vector2f v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    @Override
    public Vector2f sub(float v) {
        this.x -= v;
        this.y -= v;
        return this;
    }

    /**
     * Subtracts the given vector from this vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @return This vector for chaining.
     */
    public Vector2f sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    @Override
    public Vector2f scl(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    /**
     * Performs a component-wise multiplication (Hadamard product) of this vector and the given vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @return This vector for chaining.
     */
    public Vector2f scl(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    @Override
    public Vector2f scl(Vector2f v) {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }

    @Override
    public Vector2f div(float scalar) {
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }

    /**
     * Performs a component-wise division of this vector and the given vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @return This vector for chaining.
     */
    public Vector2f div(float x, float y) {
        this.x /= x;
        this.y /= y;
        return this;
    }

    @Override
    public Vector2f div(Vector2f v) {
        this.x /= v.x;
        this.y /= v.y;
        return this;
    }

    /**
     * @param v1 The first vector.
     * @param v2 The second vector.
     * @return The distance between the two vectors.
     */
    public static float dst(Vector2f v1, Vector2f v2) {
        float a = v2.x - v1.x;
        float b = v2.y - v1.y;
        return (float) Math.sqrt(a * a + b * b);
    }

    /**
     * @return The distance between the two vectors.
     */
    public static float dst(
            float x1, float y1,
            float x2, float y2
    ) {
        float a = x2 - x1;
        float b = y2 - y1;
        return (float) Math.sqrt(a * a + b * b);
    }

    /**
     * @param v1 The first vector.
     * @param v2 The second vector.
     * @return The square of the distance between the two vectors.
     */
    public static float dst2(Vector2f v1, Vector2f v2) {
        float a = v2.x - v1.x;
        float b = v2.y - v1.y;
        return a * a + b * b;
    }

    /**
     * @return The square of the distance between the two vectors.
     */
    public static float dst2(float x1, float y1, float x2, float y2) {
        float a = x2 - x1;
        float b = y2 - y1;
        return a * a + b * b;
    }

    @Override
    public float dst(Vector2f v) {
        float a = v.x - this.x;
        float b = v.y - this.y;
        return (float) Math.sqrt(a * a + b * b);
    }

    /**
     * @return The distance between the two vectors.
     */
    public float dst(float x, float y) {
        float a = x - this.x;
        float b = y - this.y;
        return (float) Math.sqrt(a * a + b * b);
    }

    @Override
    public float dst2(Vector2f v) {
        float a = v.x - this.x;
        float b = v.y - this.y;
        return a * a + b * b;
    }

    /**
     * @return The square of the distance between the two vectors.
     */
    public float dst2(float x, float y) {
        float a = x - this.x;
        float b = y - this.y;
        return a * a + b * b;
    }

    /**
     * Linearly interpolates between the first vector and the second vector based on the alpha coefficient.
     *
     * @param v1    The first vector.
     * @param v2    The second vector.
     * @param alpha The alpha coefficient.
     * @return The result of the linear interpolation.
     */
    public static Vector2f lerp(Vector2f v1, Vector2f v2, float alpha) {
        float x = v1.x + alpha * (v2.x - v1.x);
        float y = v1.y + alpha * (v2.y - v1.y);
        return new Vector2f(x, y);
    }

    @Override
    public Vector2f lerp(Vector2f v, float alpha) {
        this.x += alpha * (v.x - this.x);
        this.y += alpha * (v.y - this.y);
        return this;
    }

    @Override
    public Vector2f addMul(Vector2f v, float scalar) {
        this.x += v.x * scalar;
        this.y += v.y * scalar;
        return this;
    }

    @Override
    public Vector2f addMul(Vector2f v, Vector2f v2) {
        this.x += v.x * v2.x;
        this.y += v.y * v2.y;
        return this;
    }

    @Override
    public float dot(Vector2f v) {
        return this.x * v.x + this.y * v.y;
    }

    /**
     * @param v The other vector.
     * @return The 2D cross product (wedge product) of the two vectors.
     */
    public float crs(Vector2f v) {
        return this.x * v.y - this.y * v.x;
    }

    /**
     * Sets this vector to the vector from this vector to the specified vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @return This vector for chaining.
     */
    public Vector2f to(float x, float y) {
        this.x = x - this.x;
        this.y = y - this.y;
        return this;
    }

    @Override
    public Vector2f to(Vector2f v) {
        this.x = v.x - this.x;
        this.y = v.y - this.y;
        return this;
    }

    @Override
    public boolean isOnLine(Vector2f v) {
        return isOnLine(v, MathUtils.EPSILON);
    }

    @Override
    public boolean isOnLine(Vector2f v, float epsilon) {
        return Math.abs(this.x * v.y - this.y * v.x) <= epsilon;
    }

    @Override
    public boolean isCollinear(Vector2f v) {
        return isCollinear(v, MathUtils.EPSILON);
    }

    @Override
    public boolean isCollinear(Vector2f v, float epsilon) {
        return Math.abs(this.x * v.y - this.y * v.x) < epsilon && (this.x * v.x + this.y * v.y > 0);
    }

    @Override
    public boolean isCollinearOpposite(Vector2f v) {
        return isCollinearOpposite(v, MathUtils.EPSILON);
    }

    @Override
    public boolean isCollinearOpposite(Vector2f v, float epsilon) {
        return Math.abs(this.x * v.y - this.y * v.x) < epsilon && (this.x * v.x + this.y * v.y < 0);
    }

    @Override
    public boolean isOrthogonal(Vector2f v) {
        return isOrthogonal(v, MathUtils.EPSILON);
    }

    @Override
    public boolean isOrthogonal(Vector2f v, float epsilon) {
        return Math.abs(this.x * v.x + this.y * v.y) < epsilon;
    }

    @Override
    public boolean hasSameDirection(Vector2f v) {
        return this.x * v.x + this.y * v.y > 0;
    }

    @Override
    public boolean hasOppositeDirection(Vector2f v) {
        return this.x * v.x + this.y * v.y < 0;
    }

    @Override
    public boolean isUnit() {
        return isUnit(MathUtils.EPSILON);
    }

    public boolean isUnit(float epsilon) {
        return MathUtils.epsEquals(x * x + y * y, 1.0f, epsilon);
    }

    @Override
    public boolean isZero() {
        return isZero(MathUtils.EPSILON);
    }

    @Override
    public boolean isZero(float epsilon) {
        return MathUtils.epsEquals(x * x + y * y, 0.0f, epsilon);
    }

    /**
     * Calculates the 2D cross product (wedge product) of this vector and another vector.
     *
     * @param v The other vector.
     * @return The 2D cross product.
     */
    public float cross(Vector2f v) {
        return this.x * v.y - this.y * v.x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2f vector2f = (Vector2f) o;
        return Float.compare(vector2f.x, x) == 0 && Float.compare(vector2f.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
