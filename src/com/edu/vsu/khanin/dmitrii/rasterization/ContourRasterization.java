package com.edu.vsu.khanin.dmitrii.rasterization;

import com.edu.vsu.kretov.daniil.mathLib4Task.AffineTransforms.AffineTransformations;
import com.edu.vsu.kretov.daniil.mathLib4Task.matrix.Matrix4f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.khanin.dmitrii.render_engine.Camera;
import com.edu.vsu.prilepin.maxim.model.Model;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;
import com.edu.vsu.prilepin.maxim.model.Polygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

import static com.edu.vsu.khanin.dmitrii.render_engine.GraphicConveyor.multiplyMatrix4ByVector3;

public class ContourRasterization implements RasterizationAlgorithm {
    @Override
    public HashSet<ColorPixel> rasterization(final Camera camera, ArrayList<ModelInScene> sceneModels,
                                             Matrix4f mVPMatrix, int width, int height) {
        HashSet<ColorPixel> colorPixels = new HashSet<>();

        for (ModelInScene model : sceneModels) {
            Model mesh = AffineTransformations.makeInWorldCoord(model);
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

                for (int x = (int) Math.floor(minX * width / 2 + width / 2.0f);
                     x <= Math.ceil(maxX * width / 2 + width / 2.0f); x++) {
                    for (int y = (int) Math.floor(-maxY * height / 2 + height / 2.0f);
                         y <= Math.ceil(-minY * height / 2 + height / 2.0f); y++) {
                        Vector3f barycentricCoords = BarycentricCoordinates.toBarycentricCoordinates(
                                2.0f * x / width - 1,
                                -2.0f * y / height + 1,
                                v1, v2, v3
                        );

                        if (barycentricCoords.x < 0 || barycentricCoords.y < 0 || barycentricCoords.z < 0) continue;

                        float z = barycentricCoords.x * v1.z + barycentricCoords.y * v2.z + barycentricCoords.z * v3.z;

                        if (Math.abs(z) > 1) continue;

                        float eps = 0.01f;
                        if (barycentricCoords.x <= eps || barycentricCoords.y <= eps || barycentricCoords.z <= eps)
                            colorPixels.add(new ColorPixel(new Pixel(x, y), Color.BLACK));
                    }
                }
            }
        }

        return colorPixels;
    }
}
