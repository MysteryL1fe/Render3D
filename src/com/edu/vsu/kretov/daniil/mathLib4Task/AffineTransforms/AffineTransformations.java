package com.edu.vsu.kretov.daniil.mathLib4Task.AffineTransforms;


import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.model.Model;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;

import java.util.ArrayList;

public class AffineTransformations {
    /**
     * Scales the model by the given factors.
     *
     * @param vertices The model to be scaled.
     * @param scaleX   The scaling factor along the x-axis.
     * @param scaleY   The scaling factor along the y-axis.
     * @param scaleZ   The scaling factor along the z-axis.
     */
    public static void scale(ArrayList<Vector3f> vertices, float scaleX, float scaleY, float scaleZ) {
        for (Vector3f vertex : vertices) {
            vertex.x *= scaleX;
            vertex.y *= scaleY;
            vertex.z *= scaleZ;
        }
    }

    /**
     * Translates the model by the given offsets.
     *
     * @param vertices The model to be translated.
     * @param offsetX  The translation offset along the x-axis.
     * @param offsetY  The translation offset along the y-axis.
     * @param offsetZ  The translation offset along the z-axis.
     */
    public static void translate(ArrayList<Vector3f> vertices, float offsetX, float offsetY, float offsetZ) {
        for (Vector3f vertex : vertices) {
            vertex.x += offsetX;
            vertex.y += offsetY;
            vertex.z += offsetZ;
        }
    }

//    /**
//     * Applies rotation to the model around the specified axis.
//     *
//     * @param model The model to be rotated.
//     * @param axisX The x-component of the rotation axis.
//     * @param axisY The y-component of the rotation axis.
//     * @param axisZ The z-component of the rotation axis.
//     */
//    public static void rotate(Model model, float axisX, float axisY, float axisZ) {
//
//        for (Vector3f vertex : model.vertices) {
//            if (axisX != 0)
//            rotateX(vertex, (float) Math.toRadians(axisX));
//            if (axisY != 0)
//            rotateY(vertex,(float) Math.toRadians(axisY));
//            if (axisZ !=0)
//            rotateZ(vertex, (float) Math.toRadians(axisZ));
//
//        }
//    }

    // Вспомогательный метод для поворота точки вокруг оси X
    public static void rotate(Model model, float angleX, float angleY, float angleZ) {
        // Поворот вершин
        for (int i = 0; i < model.vertices.size(); i++) {
            Vector3f rotatedVertex = rotatePoint(model.vertices.get(i), angleX, angleY, angleZ);
            model.vertices.set(i, rotatedVertex);
        }
    }

    // Вспомогательный метод для поворота точки вокруг осей X, Y и Z
    private static Vector3f rotatePoint(Vector3f point, float angleX, float angleY, float angleZ) {
        // Поворот вокруг оси X
        point = rotateX(point, angleX);

        // Поворот вокруг оси Y
        point = rotateY(point, angleY);

        // Поворот вокруг оси Z
        point = rotateZ(point, angleZ);

        return point;
    }

    // Вспомогательный метод для поворота точки вокруг оси X
    private static Vector3f rotateX(Vector3f point, float angle) {
        float sinA = (float) Math.sin(angle);
        float cosA = (float) Math.cos(angle);

        float y = point.y * cosA - point.z * sinA;
        float z = point.y * sinA + point.z * cosA;

        return new Vector3f(point.x, y, z);
    }

    // Вспомогательный метод для поворота точки вокруг оси Y
    private static Vector3f rotateY(Vector3f point, float angle) {
        float sinA = (float) Math.sin(angle);
        float cosA = (float) Math.cos(angle);

        float x = point.x * cosA + point.z * sinA;
        float z = -point.x * sinA + point.z * cosA;

        return new Vector3f(x, point.y, z);
    }

    // Вспомогательный метод для поворота точки вокруг оси Z
    private static Vector3f rotateZ(Vector3f point, float angle) {
        float sinA = (float) Math.sin(angle);
        float cosA = (float) Math.cos(angle);

        float x = point.x * cosA - point.y * sinA;
        float y = point.x * sinA + point.y * cosA;

        return new Vector3f(x, y, point.z);
    }

    public static Model makeInWorldCoord(ModelInScene model) {
        Model resModel = model.getModel().clone();

        if (model.getScale().x != 1 || model.getScale().y != 1 || model.getScale().z != 1)
            scale(resModel.vertices, model.getScale().x, model.getScale().y, model.getScale().z);
        if (model.getRotation().x != 0 || model.getRotation().y != 0 || model.getRotation().z != 0)
            rotate(resModel, model.getRotation().x, model.getRotation().y, model.getRotation().z);
        translate(resModel.vertices, model.getPosition().x, model.getPosition().y, model.getPosition().z);

        for (int i = 0; i < resModel.normals.size(); i++) {
            resModel.normals.set(
                    i,
                    rotateZ(
                            rotateY(
                                    rotateX(
                                            resModel.normals.get(i),
                                            model.getRotation().x
                                    ),
                                    model.getRotation().y
                            ),
                            model.getRotation().z
                    ).nor());
        }

        return resModel;
    }
}
