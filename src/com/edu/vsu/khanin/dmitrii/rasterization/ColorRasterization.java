package com.edu.vsu.khanin.dmitrii.rasterization;

import com.edu.vsu.kretov.daniil.mathLib4Task.AphineTransforms.AffineTransformations;
import com.edu.vsu.kretov.daniil.mathLib4Task.matrix.Matrix4f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.kretov.daniil.render_engine.Camera;
import com.edu.vsu.prilepin.maxim.model.Model;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;
import com.edu.vsu.prilepin.maxim.model.Polygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import static com.edu.vsu.kretov.daniil.render_engine.GraphicConveyor.multiplyMatrix4ByVector3;

public class ColorRasterization implements RasterizationAlgorithm {
    @Override
    public HashSet<ColorPixel> rasterization(final Camera camera, ArrayList<ModelInScene> sceneModels,
                                             Matrix4f mVPMatrix, int width, int height) {
        HashSet<ColorPixel> colorPixels = new HashSet<>();
        HashMap<Pixel, ZBufferColor> zBuffer = new HashMap<>();

        for (ModelInScene model : sceneModels) {
            Model mesh = AffineTransformations.MakeInWorldCoord(model);
            for (Polygon polygon : mesh.polygons) {
                Vector3f v1 = multiplyMatrix4ByVector3(mVPMatrix, mesh.vertices.get(polygon.getVertexIndices().get(0)).cpy());
                Vector3f v2 = multiplyMatrix4ByVector3(mVPMatrix, mesh.vertices.get(polygon.getVertexIndices().get(1)).cpy());
                Vector3f v3 = multiplyMatrix4ByVector3(mVPMatrix, mesh.vertices.get(polygon.getVertexIndices().get(2)).cpy());

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

                for (float x = minX; x <= maxX; x += (float) 1 / width) {
                    for (float y = minY; y <= maxY; y += (float) 1 / height) {
                        Vector3f barycentricCoords = BarycentricCoordinates.toBarycentricCoordinates(x, y, v1, v2, v3);

                        if (barycentricCoords.x < 0 || barycentricCoords.y < 0 || barycentricCoords.z < 0
                                || barycentricCoords.x > 1 || barycentricCoords.y > 1 || barycentricCoords.z > 1)
                            continue;

                        float z = barycentricCoords.x * v1.z + barycentricCoords.y * v2.z + barycentricCoords.z * v3.z;

                        if (Math.abs(z) > 1) continue;

                        int newX = (int) (x * width + width / 2.0F);
                        int newY = (int) (-y * height + height / 2.0F);
                        Pixel pixel = new Pixel(newX, newY);
                        if (!zBuffer.containsKey(pixel) || zBuffer.get(pixel).zBuffer > z) {
                            float eps = 0.01f;
                            if (barycentricCoords.x <= eps || barycentricCoords.y <= eps || barycentricCoords.z <= eps)
                                zBuffer.put(pixel, new ZBufferColor(z, Color.BLACK));
                            else zBuffer.put(pixel, new ZBufferColor(z, model.getColor()));
                        }
                    }
                }
            }
        }

        for (Pixel pixel : zBuffer.keySet())
            colorPixels.add(new ColorPixel(pixel, zBuffer.get(pixel).color));

        return colorPixels;
    }

    private static class ZBufferColor {
        public float zBuffer;
        public Color color;

        public ZBufferColor(float zBuffer, Color color) {
            this.zBuffer = zBuffer;
            this.color = color;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ZBufferColor that = (ZBufferColor) o;
            return Float.compare(that.zBuffer, zBuffer) == 0 && color.equals(that.color);
        }

        @Override
        public int hashCode() {
            return Objects.hash(zBuffer, color);
        }
    }
}
