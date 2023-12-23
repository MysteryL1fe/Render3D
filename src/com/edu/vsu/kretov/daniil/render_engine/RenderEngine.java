package com.edu.vsu.kretov.daniil.render_engine;

import com.edu.vsu.khanin.dmitrii.rasterization.Rasterization;
import com.edu.vsu.kretov.daniil.mathLib4Task.AphineTransforms.AffineTransformations;
import com.edu.vsu.kretov.daniil.mathLib4Task.matrix.Matrix4f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector2f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.model.Model;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;
import com.edu.vsu.prilepin.maxim.model.Polygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static com.edu.vsu.kretov.daniil.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public static void render(
            final Viewport viewport,
            final Camera camera,
            final ArrayList<ModelInScene> sceneModels)
    {
        Matrix4f modelMatrix = rotateScaleTranslate();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
        modelViewProjectionMatrix.mul(viewMatrix);
        modelViewProjectionMatrix.mul(projectionMatrix);

        HashMap<Vector2f, ZBufferColor> zBuffer = new HashMap<>();

        for (ModelInScene mesh : sceneModels) {
            Model model = AffineTransformations.MakeInWorldCoord(mesh);
            for (Polygon polygon : model.polygons) {
                Vector3f v1 = multiplyMatrix4ByVector3(modelViewProjectionMatrix, model.vertices.get(polygon.getVertexIndices().get(0)));
                Vector3f v2 = multiplyMatrix4ByVector3(modelViewProjectionMatrix, model.vertices.get(polygon.getVertexIndices().get(1)));
                Vector3f v3 = multiplyMatrix4ByVector3(modelViewProjectionMatrix, model.vertices.get(polygon.getVertexIndices().get(2)));

                float minX = Math.min(Math.min(v1.x, v2.x), v3.x);
                float maxX = Math.max(Math.max(v1.x, v2.x), v3.x);
                float minY = Math.min(Math.min(v1.y, v2.y), v3.y);
                float maxY = Math.max(Math.max(v1.y, v2.y), v3.y);
                float minZ = Math.min(Math.min(v1.z, v2.z), v3.z);
                float maxZ = Math.max(Math.max(v1.z, v2.z), v3.z);

                if (minX > 1 || maxX < -1 || minY > 1 || maxY < -1 || minZ > 1 || maxZ < -1) continue;

                minX = Math.max(minX, -1);
                maxX = Math.min(maxX, 1);
                minY = Math.max(minY, -1);
                maxY = Math.min(maxY, 1);

                for (float x = minX; x <= Math.floor(maxX); x += (float) 1 / viewport.getBounds().width) {
                    for (float y = minY; y <= Math.floor(maxY); y += (float) 1 / viewport.getBounds().height) {
                        Vector3f barycentricCoords = Rasterization.toBarycentricCoordinates(x, y, v1, v2, v3);
                        float z = barycentricCoords.x * v1.z + barycentricCoords.y * v2.z + barycentricCoords.z * v3.z;

                        if (Math.abs(z) > 1) continue;

                        int newX = (int) x;
                        int newY = (int) y;
                        Vector2f pixel = new Vector2f(x, y);
                        if (!zBuffer.containsKey(pixel) || zBuffer.get(pixel).zBuffer > z)
                            zBuffer.put(pixel, new ZBufferColor(z, Color.BLACK));
                    }
                }
            }
        }

        for (Vector2f pixel : zBuffer.keySet())
            viewport.drawPixel((int) pixel.x, (int) pixel.y, zBuffer.get(pixel).color);
    }

    private static class ZBufferColor {
        public float zBuffer;
        public Color color;

        public ZBufferColor(float zBuffer, Color color) {
            this.zBuffer = zBuffer;
            this.color = color;
        }
    }
}
