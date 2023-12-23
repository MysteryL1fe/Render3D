package com.edu.vsu.kretov.daniil.mathLib4Task;


import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;

/**
 * Common math operations.
 *
 * @author Daniil Kretov
 */
public class MathUtils {
    /**
     * The default float epsilon value used for equality comparisons.
     */
    public static final float EPSILON = 3E-5f;

    /**
     * The default double epsilon value used for equality comparisons.
     */
    public static final double EPSILON_D = 3E-7;

    /**
     * The single-precision floating point value of pi (the ratio
     * of the circumference of the circle to its diameter).
     */
    public static final float PI = (float) Math.PI;

    /**
     * The pi constant, doubled.
     */
    public static final float PI2 = 2 * PI;

    /**
     * The pi constant, halved.
     */
    public static final float HALF_PI = PI / 2.0f;

    /**
     * The single precision floating point value of e (Euler's number).
     */
    public static final float E = (float) Math.E;

    /**
     * The full circle angle in radians.
     */
    public static final float RAD_FULL = PI2;

    /**
     * The full circle angle in degrees.
     */
    public static final float DEG_FULL = PI2;

    /**
     * The factor used to convert radians to degrees.
     */
    public static final float RAD_TO_DEG = 180.0f / PI;

    /**
     * The factor used to convert degrees to radians.
     */
    public static final float DEG_TO_RAD = PI / 180.0f;

    private MathUtils() { }

    /**
     * Performs an accurate floating-point equals comparison using epsilon.
     *
     * @param a       The first value.
     * @param b       The second value.
     * @param epsilon The epsilon value.
     * @return Whether the two given values are nearly equal.
     */
    public static boolean epsEquals(float a, float b, float epsilon) {
        float diff = Math.abs(a - b);
        return diff <= epsilon;
    }

    /**
     * Performs an accurate floating-point equals comparison using epsilon.
     *
     * @param a The first value.
     * @param b The second value.
     * @return Whether the two given values are nearly equal.
     */
    public static boolean epsEquals(float a, float b) {
        return epsEquals(a, b, EPSILON);
    }

    /**
     * Performs an accurate floating-point equals comparison using epsilon.
     *
     * @param a       The first value.
     * @param b       The second value.
     * @param epsilon The epsilon value.
     * @return Whether the two given values are nearly equal.
     */
    public static boolean epsEquals(double a, double b, double epsilon) {
        double diff = Math.abs(a - b);
        return diff <= epsilon;
    }

    /**
     * Performs an accurate floating-point equals comparison using epsilon.
     *
     * @param a The first value.
     * @param b The second value.
     * @return Whether the two given values are nearly equal.
     */
    public static boolean epsEquals(double a, double b) {
        return epsEquals(a, b, EPSILON_D);
    }

    /**
     * Linear interpolation between two values.
     *
     * @param a     The first value.
     * @param b     The second value.
     * @param alpha The interpolation coefficient.
     * @return The interpolated value.
     */
    public static float lerp(float a, float b, float alpha) {
        return (1.0f - alpha) * a + alpha * b;
    }

    /**
     * Linear interpolation between two values.
     *
     * @param a     The first value.
     * @param b     The second value.
     * @param alpha The interpolation coefficient.
     * @return The interpolated value.
     */
    public static double lerp(double a, double b, double alpha) {
        return (1.0 - alpha) * a + alpha * b;
    }

    /**
     * Clamps the given value between min and max.
     *
     * @param v   The value to clamp.
     * @param min The min value.
     * @param max The max value.
     * @return The clamped value.
     */
    public static float clamp(float v, float min, float max) {
        if (v > max) return max;
        if (v < min) return min;
        return v;
    }

    /**
     * Clamps the given value between min and max.
     *
     * @param v   The value to clamp.
     * @param min The min value.
     * @param max The max value.
     * @return The clamped value.
     */
    public static double clamp(double v, double min, double max) {
        if (v > max) return max;
        if (v < min) return min;
        return v;
    }

    /**
     * Clamps the given value between min and max.
     *
     * @param v   The value to clamp.
     * @param min The min value.
     * @param max The max value.
     * @return The clamped value.
     */
    public static int clamp(int v, int min, int max) {
        if (v > max) return max;
        if (v < min) return min;
        return v;
    }

    /**
     * Clamps the given value between min and max.
     *
     * @param v   The value to clamp.
     * @param min The min value.
     * @param max The max value.
     * @return The clamped value.
     */
    public static short clamp(short v, short min, short max) {
        if (v > max) return max;
        if (v < min) return min;
        return v;
    }

    /**
     * Clamps the given value between min and max.
     *
     * @param v   The value to clamp.
     * @param min The min value.
     * @param max The max value.
     * @return The clamped value.
     */
    public static long clamp(long v, long min, long max) {
        if (v > max) return max;
        if (v < min) return min;
        return v;
    }

    /**
     * Clamps the given value between 0 and 1.
     *
     * @param v The value to clamp.
     * @return The clamped value.
     */
    public static float clamp01(float v) {
        if (v > 1.0f) return 1.0f;
        if (v < 0.0f) return 0.0f;
        return v;
    }

    /**
     * Clamps the given value between 0 and 1.
     *
     * @param v The value to clamp.
     * @return The clamped value.
     */
    public static double clamp01(double v) {
        if (v > 1.0) return 1.0;
        if (v < 0.0) return 0.0;
        return v;
    }

    /**
     * Maps the given value from one range to another.
     *
     * @param v      The value to map.
     * @param inMin  The start of the input range.
     * @param inMax  The end of the input range.
     * @param outMin The start of the output range.
     * @param outMax The end of the output range.
     * @return The mapped value.
     */
    public static float map(float v, float inMin, float inMax, float outMin, float outMax) {
        return outMin + (v - inMin) / (inMax - inMin) * (outMax - outMin);
    }
    /**
     * Finds the normal vector of a plane defined by three points.
     *
     * @return The normal vector of the plane.
     */
    public static Vector3f normalizePolygon(Vector3f v1,Vector3f v2,Vector3f v3) {


        // Calculate two vectors in the plane
        Vector3f vector1 = v2.cpy().sub(v1);
        Vector3f vector2 = v3.cpy().sub(v1);

        // Calculate the normal vector using the cross product
        Vector3f normal = vector1.crossVect(vector2);

        // Normalize the normal vector
        return normal.nor();
    }

}
