package com.edu.vsu.kretov.daniil.mathLib4Task.AphineTransforms;


import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.model.Model;

public class AffineTransformations {
    /**
     * Scales the model by the given factors.
     *
     * @param model   The model to be scaled.
     * @param scaleX  The scaling factor along the x-axis.
     * @param scaleY  The scaling factor along the y-axis.
     * @param scaleZ  The scaling factor along the z-axis.
     */
    public static void scale(Model model, float scaleX, float scaleY, float scaleZ) {
        for (Vector3f vertex : model.vertices) {
            vertex.x *= scaleX;
            vertex.y *= scaleY;
            vertex.z *= scaleZ;
        }
    }

    /**
     * Translates the model by the given offsets.
     *
     * @param model    The model to be translated.
     * @param offsetX  The translation offset along the x-axis.
     * @param offsetY  The translation offset along the y-axis.
     * @param offsetZ  The translation offset along the z-axis.
     */
    public static void translate(Model model, float offsetX, float offsetY, float offsetZ) {
        for (Vector3f vertex : model.vertices) {
            vertex.x += offsetX;
            vertex.y += offsetY;
            vertex.z += offsetZ;
        }
    }

    /**
     * Applies rotation to the model around the specified axis.
     *
     * @param model The model to be rotated.
     * @param axisX The x-component of the rotation axis.
     * @param axisY The y-component of the rotation axis.
     * @param axisZ The z-component of the rotation axis.
     * @param angle The angle of rotation in radians.
     */
    public static void rotate(Model model, float axisX, float axisY, float axisZ, float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        for (Vector3f vertex : model.vertices) {
            float x = vertex.x;
            float y = vertex.y;
            float z = vertex.z;

            float newX = x * (cos + (1 - cos) * axisX * axisX) +
                    y * ((1 - cos) * axisX * axisY - sin * axisZ) +
                    z * ((1 - cos) * axisX * axisZ + sin * axisY);

            float newY = x * ((1 - cos) * axisY * axisX + sin * axisZ) +
                    y * (cos + (1 - cos) * axisY * axisY) +
                    z * ((1 - cos) * axisY * axisZ - sin * axisX);

            float newZ = x * ((1 - cos) * axisZ * axisX - sin * axisY) +
                    y * ((1 - cos) * axisZ * axisY + sin * axisX) +
                    z * (cos + (1 - cos) * axisZ * axisZ);

            vertex.set(newX, newY, newZ);
        }
    }
}
