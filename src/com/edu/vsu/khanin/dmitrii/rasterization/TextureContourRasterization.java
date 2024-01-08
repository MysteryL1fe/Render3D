package com.edu.vsu.khanin.dmitrii.rasterization;

import com.edu.vsu.khanin.dmitrii.render_engine.Camera;
import com.edu.vsu.kretov.daniil.mathLib4Task.AffineTransforms.AffineTransformations;
import com.edu.vsu.kretov.daniil.mathLib4Task.matrix.Matrix4f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector2f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.model.Model;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;
import com.edu.vsu.prilepin.maxim.model.Polygon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import static com.edu.vsu.khanin.dmitrii.render_engine.GraphicConveyor.multiplyMatrix4ByVector3;

public class TextureContourRasterization implements RasterizationAlgorithm {
    @Override
    public HashSet<RasterizationAlgorithm.ColorPixel> rasterization(final Camera camera, ArrayList<ModelInScene> sceneModels,
                                                                    Matrix4f mVPMatrix, int width, int height) {
        HashSet<RasterizationAlgorithm.ColorPixel> colorPixels = new HashSet<>();
        HashMap<RasterizationAlgorithm.Pixel, ZBufferColor> zBuffer = new HashMap<>();

        for (ModelInScene model : sceneModels) {
            Model mesh = AffineTransformations.makeInWorldCoord(model);

            BufferedImage img = null;
            if (model.getPath() != null) {
                try {
                    img = ImageIO.read(model.getPath().toFile());
                } catch (IOException ignored) {
                }
            }

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

                int textureIndex1 = -1;
                int textureIndex2 = -1;
                int textureIndex3 = -1;

                if (img != null && polygon.getTextureVertexIndices().size() >= 3) {
                    textureIndex1 = polygon.getTextureVertexIndices().get(0);
                    textureIndex2 = polygon.getTextureVertexIndices().get(1);
                    textureIndex3 = polygon.getTextureVertexIndices().get(2);
                }

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

                        RasterizationAlgorithm.Pixel pixel = new RasterizationAlgorithm.Pixel(x, y);
                        if (zBuffer.containsKey(pixel) && zBuffer.get(pixel).zBuffer < z) continue;

                        int red, green, blue;

                        if (Math.min(textureIndex1, Math.min(textureIndex2, textureIndex3)) > -1) {
                            // set pixel color from texture
                            Vector2f textureCoords1 = mesh.textureVertices.get(textureIndex1);
                            Vector2f textureCoords2 = mesh.textureVertices.get(textureIndex2);
                            Vector2f textureCoords3 = mesh.textureVertices.get(textureIndex3);

                            float textureX = textureCoords1.x * barycentricCoords.x
                                    + textureCoords2.x * barycentricCoords.y
                                    + textureCoords3.x * barycentricCoords.z;
                            float textureY = textureCoords1.y * barycentricCoords.x
                                    + textureCoords2.y * barycentricCoords.y
                                    + textureCoords3.y * barycentricCoords.z;

                            while (textureX > 1) textureX--;
                            while (textureX < 0) textureX++;
                            while (textureY > 1) textureY--;
                            while (textureY < 0) textureY++;

                            textureX = 1 - textureX;
                            textureY = 1 - textureY;

                            textureX *= img.getWidth();
                            textureY *= img.getHeight();

                            int color = img.getRGB((int) textureX, (int) textureY);
                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = (color) & 0xff;
                        } else {
                            // set pixel from model's color
                            red = model.getColor().getRed();
                            green = model.getColor().getGreen();
                            blue = model.getColor().getBlue();
                        }

                        float eps = 0.01f;
                        if (barycentricCoords.x <= eps || barycentricCoords.y <= eps || barycentricCoords.z <= eps) {
                            red = (int) (red * 0.8);
                            green = (int) (green * 0.8);
                            blue = (int) (blue * 0.8);
                        }

                        zBuffer.put(pixel, new ZBufferColor(z, new Color(red, green, blue)));
                    }
                }
            }
        }

        for (RasterizationAlgorithm.Pixel pixel : zBuffer.keySet())
            colorPixels.add(new RasterizationAlgorithm.ColorPixel(pixel, zBuffer.get(pixel).color));

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
