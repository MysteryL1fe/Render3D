package com.edu.vsu.kretov.daniil.mathLib4Task.AphineTransforms;


import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.model.Model;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;

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
     */
    public static void rotate(Model model, float axisX, float axisY, float axisZ) {
        float cosx = (float) Math.cos(Math.toRadians(axisX));
        float sinx = (float) Math.sin(Math.toRadians(axisX));
        float cosy = (float) Math.cos(Math.toRadians(axisY));
        float siny = (float) Math.sin(Math.toRadians(axisY));
        float cosz = (float) Math.cos(Math.toRadians(axisZ));
        float sinz = (float) Math.sin(Math.toRadians(axisZ));

        for (Vector3f vertex : model.vertices) {
            float x = vertex.x;
            float y = vertex.y;
            float z = vertex.z;

            float newX = x * (cosx + (1 - cosx) * axisX * axisX) +
                    y * ((1 - cosy) * axisX * axisY - siny * axisZ) +
                    z * ((1 - cosz) * axisX * axisZ + sinz * axisY);

            float newY = x * ((1 - cosx) * axisY * axisX + sinx * axisZ) +
                    y * (cosy + (1 - cosy) * axisY * axisY) +
                    z * ((1 - cosz) * axisY * axisZ - sinz * axisX);

            float newZ = x * ((1 - cosx) * axisZ * axisX - sinx * axisY) +
                    y * ((1 - cosy) * axisZ * axisY + siny * axisX) +
                    z * (cosz + (1 - cosz) * axisZ * axisZ);

            vertex.set(newX, newY, newZ);
        }
    }
    public static Model MakeInWorldCoord(ModelInScene model) {
        Model resModel = model.getModel();
        scale(resModel, model.getScale().x,  model.getScale().y,  model.getScale().z);
        rotate(resModel, model.getRotation().x, model.getRotation().y, model.getRotation().z);
        translate(resModel, model.getPosition().x,  model.getPosition().y,  model.getPosition().z);

       return resModel;
    }
}
