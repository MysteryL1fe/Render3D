package com.edu.vsu.kretov.daniil.mathLib4Task.vector;

import com.edu.vsu.kretov.daniil.mathLib4Task.MathUtils;

import java.util.Objects;

/**
 * A mutable three-dimensional float vector.
 *
 * @author Alexander Laptev
 */
public class Vector4f implements Vector<Vector4f> {
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
     * The w component of this vector.
     */
    public float w;

    /**
     * @return A zero vector.
     */
    public static Vector4f zero() {
        return new Vector4f();
    }

    /**
     * @return An x unit vector.
     */
    public static Vector4f unitX() {
        return new Vector4f(1.0f, 0.0f, 0.0f, 0.0f);
    }

    /**
     * @return A y unit vector.
     */
    public static Vector4f unitY() {
        return new Vector4f(0.0f, 1.0f, 0.0f, 0.0f);
    }

    /**
     * @return A z unit vector.
     */
    public static Vector4f unitZ() {
        return new Vector4f(0.0f, 0.0f, 1.0f, 0.0f);
    }

    /**
     * @return A w unit vector.
     */
    public static Vector4f unitW() {
        return new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
    }

    /**
     * Constructs a zero vector.
     */
    public Vector4f() { }

    /**
     * Constructs a vector from the given float array. The array must have at least four values.
     *
     * @param values The array of floats.
     */
    public Vector4f(float[] values) {
        this.x = values[0];
        this.y = values[1];
        this.z = values[2];
        this.w = values[3];
    }

    /**
     * Constructs a vector from the given vector.
     *
     * @param v The vector.
     */
    public Vector4f(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    /**
     * Constructs a vector with the given components.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @param w The w component of the vector.
     */
    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Constructs a vector from the given 2D vector. The z and w components are set to zero.
     *
     * @param v The vector.
     */
    public Vector4f(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = 0.0f;
        this.w = 0.0f;
    }

    /**
     * Constructs a vector from the given 2D vector with the provided z and w components.
     *
     * @param v The vector.
     * @param z The z component of the vector.
     * @param w The w component of the vector.
     */
    public Vector4f(Vector2f v, float z, float w) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
        this.w = w;
    }

    /**
     * Constructs a vector from the given 3D vector. The w component is set to one.
     *
     * @param v The vector.
     */
    public Vector4f(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = 1.0f;
    }

    /**
     * Constructs a vector from the given 3D vector with the provided w component.
     *
     * @param v The vector.
     * @param w The w component of the vector.
     */
    public Vector4f(Vector3f v, float w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector4f v = (Vector4f) o;
        return this.idt(v);
    }

    @Override
    public boolean epsEquals(Vector4f v, float epsilon) {
        if (this == v) return true;
        if (v == null) return false;
        if (Math.abs(this.x - v.x) > epsilon) return false;
        if (Math.abs(this.y - v.y) > epsilon) return false;
        if (Math.abs(this.z - v.z) > epsilon) return false;
        return Math.abs(this.w - v.w) <= epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    @Override
    public String toString() {
        return "v4f(x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + ")";
    }

    @Override
    public boolean idt(Vector4f v) {
        return epsIdt(v, MathUtils.EPSILON);
    }

    @Override
    public boolean epsIdt(Vector4f v, float epsilon) {
        if (Math.abs(this.x - v.x) > epsilon) return false;
        if (Math.abs(this.y - v.y) > epsilon) return false;
        if (Math.abs(this.z - v.z) > epsilon) return false;
        return Math.abs(this.w - v.w) <= epsilon;
    }

    @Override
    public Vector4f cpy() {
        return new Vector4f(this.x, this.y, this.z, this.w);
    }

    @Override
    public float[] toArray() {
        float[] result = new float[4];
        result[0] = x;
        result[1] = y;
        result[2] = z;
        result[3] = w;
        return result;
    }

    /**
     * Sets the components of this vector to the specified values.
     *
     * @param x The new x component of this vector.
     * @param y The new y component of this vector.
     * @param z The new z component of this vector.
     * @param w The new z component of this vector.
     * @return This vector for chaining.
     */
    public Vector4f set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    @Override
    public Vector4f set(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
        return this;
    }

    /**
     * Sets this vector from the given float array. The array must have at least four values.
     *
     * @param values The array of floats.
     * @return This vector for chaining.
     */
    public Vector4f set(float[] values) {
        this.x = values[0];
        this.y = values[1];
        this.z = values[2];
        this.w = values[3];
        return this;
    }

    /**
     * Sets this vector from the given 2D vector. The z and w components are set to zero.
     *
     * @param v The vector.
     * @return This vector for chaining.
     */
    public Vector4f set(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = 0.0f;
        this.w = 0.0f;
        return this;
    }

    /**
     * Sets this vector from the given 2D vector with the specified z and w components.
     *
     * @param v The vector.
     * @param z The z component of this vector.
     * @param w The w component of this vector.
     * @return This vector for chaining.
     */
    public Vector4f set(Vector2f v, float z, float w) {
        this.x = v.x;
        this.y = v.y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * Sets this vector from the given 3D vector. The w component is set to one.
     *
     * @param v The vector.
     * @return This vector for chaining.
     */
    public Vector4f set(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = 1.0f;
        return this;
    }

    /**
     * Sets this vector from the given 2D vector with the specified w component.
     *
     * @param v The vector.
     * @param w The w component of this vector.
     * @return This vector for chaining.
     */
    public Vector4f set(Vector3f v, float w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
        return this;
    }

    @Override
    public Vector4f set(float v) {
        this.x = v;
        this.y = v;
        this.z = v;
        this.w = v;
        return this;
    }

    @Override
    public Vector4f setZero() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
        return this;
    }

    @Override
    public Vector4f nor() {
        if (x == 0.0f && y == 0.0f && z == 0.0f && w == 0.0f) return this;

        float len = len();
        this.x /= len;
        this.y /= len;
        this.z /= len;
        this.w /= len;
        return this;
    }

    /**
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @param w The w component of the vector.
     * @return The length of the given vector.
     */
    public static float len(float x, float y, float z, float w) {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    /**
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @param w The w component of the vector.
     * @return The square of the length of the given vector.
     */
    public static float len2(float x, float y, float z, float w) {
        return x * x + y * y + z * z + w * w;
    }

    @Override
    public float len() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    @Override
    public float len2() {
        return x * x + y * y + z * z + w * w;
    }

    @Override
    public Vector4f limit(float limit) {
        float len2 = x * x + y * y + z * z + w * w;
        if (len2 > limit * limit) {
            float alpha = limit / (float) Math.sqrt(len2);
            this.x *= alpha;
            this.y *= alpha;
            this.z *= alpha;
            this.w *= alpha;
        }
        return this;
    }

    @Override
    public Vector4f limit2(float limit2) {
        float len2 = x * x + y * y + z * z + w * w;
        if (len2 > limit2) {
            float alpha = (float) Math.sqrt(limit2 / len2);
            this.x *= alpha;
            this.y *= alpha;
            this.z *= alpha;
            this.w *= alpha;
        }
        return this;
    }

    @Override
    public Vector4f setLength(float length) {
        float oldLen = (float) Math.sqrt(x * x + y * y + z * z + w * w);
        float alpha = length / oldLen;
        this.x *= alpha;
        this.y *= alpha;
        this.z *= alpha;
        this.w *= alpha;
        return this;
    }

    @Override
    public Vector4f setLength2(float length2) {
        float oldLen2 = x * x + y * y + z * z + w * w;
        float alpha = (float) Math.sqrt(length2 / oldLen2);
        this.x *= alpha;
        this.y *= alpha;
        this.z *= alpha;
        this.w *= alpha;
        return this;
    }

    @Override
    public Vector4f clamp(float minLength, float maxLength) {
        float len2 = len2();
        if (len2 == 0.0f) return this;

        float max2 = maxLength * maxLength;
        if (len2 > max2) {
            float alpha = (float) Math.sqrt(max2 / len2);
            this.x *= alpha;
            this.y *= alpha;
            this.z *= alpha;
            this.w *= alpha;
            return this;
        }

        float min2 = minLength * minLength;
        if (len2 < min2) {
            float alpha = (float) Math.sqrt(min2 / len2);
            this.x *= alpha;
            this.y *= alpha;
            this.z *= alpha;
            this.w *= alpha;
            return this;
        }

        return this;
    }

    @Override
    public Vector4f add(Vector4f v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        this.w += v.w;
        return this;
    }

    @Override
    public Vector4f add(float v) {
        this.x += v;
        this.y += v;
        this.z += v;
        this.w += v;
        return this;
    }

    /**
     * Adds the given vector to this vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @param w The w component of the vector.
     * @return This vector for chaining.
     */
    public Vector4f add(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    @Override
    public Vector4f sub(Vector4f v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        this.w -= v.w;
        return this;
    }

    @Override
    public Vector4f sub(float v) {
        this.x -= v;
        this.y -= v;
        this.z -= v;
        this.w -= v;
        return this;
    }

    /**
     * Subtracts the given vector from this vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @param w The w component of the vector.
     * @return This vector for chaining.
     */
    public Vector4f sub(float x, float y, float z, float w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    @Override
    public Vector4f scl(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        this.w *= scalar;
        return this;
    }

    /**
     * Performs a component-wise multiplication (Hadamard product) of this vector and the given vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @param w The w component of the vector.
     * @return This vector for chaining.
     */
    public Vector4f scl(float x, float y, float z, float w) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        this.w *= w;
        return this;
    }

    @Override
    public Vector4f scl(Vector4f v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
        this.w *= v.w;
        return this;
    }

    @Override
    public Vector4f div(float scalar) {
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
        this.w /= scalar;
        return this;
    }

    /**
     * Performs a component-wise division of this vector and the given vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @param w The w component of the vector.
     * @return This vector for chaining.
     */
    public Vector4f div(float x, float y, float z, float w) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        this.w /= w;
        return this;
    }

    @Override
    public Vector4f div(Vector4f v) {
        this.x /= v.x;
        this.y /= v.y;
        this.z /= v.z;
        this.w /= v.w;
        return this;
    }

    /**
     * @param v1 The first vector.
     * @param v2 The second vector.
     * @return The distance between the two vectors.
     */
    public static float dst(Vector4f v1, Vector4f v2) {
        float a = v2.x - v1.x;
        float b = v2.y - v1.y;
        float c = v2.z - v1.z;
        float d = v2.w - v1.w;
        return (float) Math.sqrt(a * a + b * b + c * c + d * d);
    }

    /**
     * @return The distance between the two vectors.
     */
    public static float dst(
            float x1, float y1, float z1, float w1,
            float x2, float y2, float z2, float w2
    ) {
        float a = x2 - x1;
        float b = y2 - y1;
        float c = z2 - z1;
        float d = w2 - w1;
        return (float) Math.sqrt(a * a + b * b + c * c + d * d);
    }

    /**
     * @param v1 The first vector.
     * @param v2 The second vector.
     * @return The square of the distance between the two vectors.
     */
    public static float dst2(Vector4f v1, Vector4f v2) {
        float a = v2.x - v1.x;
        float b = v2.y - v1.y;
        float c = v2.z - v1.z;
        float d = v2.w - v1.w;
        return a * a + b * b + c * c + d * d;
    }

    /**
     * @return The square of the distance between the two vectors.
     */
    public static float dst2(
            float x1, float y1, float z1, float w1,
            float x2, float y2, float z2, float w2
    ) {
        float a = x2 - x1;
        float b = y2 - y1;
        float c = z2 - z1;
        float d = w2 - w1;
        return a * a + b * b + c * c + d * d;
    }

    @Override
    public float dst(Vector4f v) {
        float a = v.x - this.x;
        float b = v.y - this.y;
        float c = v.z - this.z;
        float d = v.w - this.w;
        return (float) Math.sqrt(a * a + b * b + c * c + d * d);
    }

    /**
     * @return The distance between the two vectors.
     */
    public float dst(float x, float y, float z, float w) {
        float a = x - this.x;
        float b = y - this.y;
        float c = z - this.z;
        float d = w - this.w;
        return (float) Math.sqrt(a * a + b * b + c * c + d * d);
    }

    @Override
    public float dst2(Vector4f v) {
        float a = v.x - this.x;
        float b = v.y - this.y;
        float c = v.z - this.z;
        float d = v.w - this.w;
        return a * a + b * b + c * c + d * d;
    }

    /**
     * @return The square of the distance between the two vectors.
     */
    public float dst2(float x, float y, float z, float w) {
        float a = x - this.x;
        float b = y - this.y;
        float c = z - this.z;
        float d = w - this.w;
        return a * a + b * b + c * c + d * d;
    }

    /**
     * Linearly interpolates between the first vector and the second vector based on the alpha coefficient.
     *
     * @param v1    The first vector.
     * @param v2    The second vector.
     * @param alpha The alpha coefficient.
     * @return The result of the linear interpolation.
     */
    public static Vector4f lerp(Vector4f v1, Vector4f v2, float alpha) {
        float x = v1.x + alpha * (v2.x - v1.x);
        float y = v1.y + alpha * (v2.y - v1.y);
        float z = v1.z + alpha * (v2.z - v1.z);
        float w = v1.w + alpha * (v2.w - v1.w);
        return new Vector4f(x, y, z, w);
    }

    @Override
    public Vector4f lerp(Vector4f v, float alpha) {
        this.x += alpha * (v.x - this.x);
        this.y += alpha * (v.y - this.y);
        this.z += alpha * (v.z - this.z);
        this.w += alpha * (v.w - this.w);
        return this;
    }

    @Override
    public Vector4f addMul(Vector4f v, float scalar) {
        this.x += v.x * scalar;
        this.y += v.y * scalar;
        this.z += v.z * scalar;
        this.w += v.w * scalar;
        return this;
    }

    @Override
    public Vector4f addMul(Vector4f v, Vector4f v2) {
        this.x += v.x * v2.x;
        this.y += v.y * v2.y;
        this.z += v.z * v2.z;
        this.w += v.w * v2.w;
        return this;
    }

    @Override
    public float dot(Vector4f v) {
        return this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w;
    }

    @Override
    public Vector4f to(Vector4f v) {
        this.x = v.x - this.x;
        this.y = v.y - this.y;
        this.z = v.z - this.z;
        this.w = v.w - this.w;
        return this;
    }

    /**
     * Sets this vector to the vector from this vector to the specified vector.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @param z The z component of the vector.
     * @param w The w component of the vector.
     * @return This vector for chaining.
     */
    public Vector4f to(float x, float y, float z, float w) {
        this.x = x - this.x;
        this.y = y - this.y;
        this.z = z - this.z;
        this.w = w - this.w;
        return this;
    }

    @Override
    public boolean isOnLine(Vector4f v) {
        return isOnLine(v, MathUtils.EPSILON);
    }

    @Override
    public boolean isOnLine(Vector4f v, float epsilon) {
        float a = v.x / this.x;
        float b = v.y / this.y;
        float c = v.z / this.z;
        float d = v.w / this.w;
        return Math.abs(a - b) <= epsilon
                && Math.abs(b - c) <= epsilon
                && Math.abs(c - d) <= epsilon
                && Math.abs(a - d) <= epsilon;
    }

    @Override
    public boolean isCollinear(Vector4f v) {
        return isCollinear(v, MathUtils.EPSILON);
    }

    @Override
    public boolean isCollinear(Vector4f v, float epsilon) {
        return isOnLine(v, epsilon) && (this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w > 0);
    }

    @Override
    public boolean isCollinearOpposite(Vector4f v) {
        return isCollinearOpposite(v, MathUtils.EPSILON);
    }

    @Override
    public boolean isCollinearOpposite(Vector4f v, float epsilon) {
        return isOnLine(v, epsilon) && (this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w < 0);
    }

    @Override
    public boolean isOrthogonal(Vector4f v) {
        return isOrthogonal(v, MathUtils.EPSILON);
    }

    @Override
    public boolean isOrthogonal(Vector4f v, float epsilon) {
        return Math.abs(this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w) < epsilon;
    }

    @Override
    public boolean hasSameDirection(Vector4f v) {
        return this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w > 0;
    }

    @Override
    public boolean hasOppositeDirection(Vector4f v) {
        return this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w < 0;
    }

    @Override
    public boolean isUnit() {
        return isUnit(MathUtils.EPSILON);
    }

    @Override
    public boolean isUnit(float epsilon) {
        return MathUtils.epsEquals(x * x + y * y + z * z + w * w, 1.0f, epsilon);
    }

    @Override
    public boolean isZero() {
        return isZero(MathUtils.EPSILON);
    }

    @Override
    public boolean isZero(float epsilon) {
        return MathUtils.epsEquals(x * x + y * y + z * z + w * w, 0.0f, epsilon);
    }
}
