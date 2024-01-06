package com.edu.vsu.khanin.dmitrii.rasterization;

import com.edu.vsu.kretov.daniil.mathLib4Task.AffineTransforms.AffineTransformations;
import com.edu.vsu.kretov.daniil.mathLib4Task.matrix.Matrix4f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector2f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.khanin.dmitrii.render_engine.Camera;
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

public class LightRasterization implements RasterizationAlgorithm {
    @Override
    public HashSet<ColorPixel> rasterization(final Camera camera, ArrayList<ModelInScene> sceneModels,
                                             Matrix4f mVPMatrix, int width, int height) {
        HashSet<ColorPixel> colorPixels = new HashSet<>();
        HashMap<Pixel, ZBufferColor> zBuffer = new HashMap<>();

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
                float k = 1f;
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

                Vector3f ray = camera.getTarget().cpy().sub(camera.getPosition()).nor();
                Vector3f normal1 = null;
                Vector3f normal2 = null;
                Vector3f normal3 = null;
                if (polygon.getNormalIndices().size() >= 3) {
                    normal1 = mesh.normals.get(polygon.getTextureVertexIndices().get(0));
                    normal2 = mesh.normals.get(polygon.getTextureVertexIndices().get(1));
                    normal3 = mesh.normals.get(polygon.getTextureVertexIndices().get(2));
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

                        Pixel pixel = new Pixel(x, y);
                        if (zBuffer.containsKey(pixel) && zBuffer.get(pixel).zBuffer < z) continue;

                        if (Math.min(textureIndex1, Math.min(textureIndex2, textureIndex3)) > -1) {
                            // set pixel color from texture
                            Vector2f textureCoords1 = mesh.textureVertices.get(textureIndex1);
                            Vector2f textureCoords2 = mesh.textureVertices.get(textureIndex2);
                            Vector2f textureCoords3 = mesh.textureVertices.get(textureIndex3);

                            Vector2f textureCoords = new Vector2f(
                                    (textureCoords1.x * barycentricCoords.x
                                            + textureCoords2.x * barycentricCoords.y
                                            + textureCoords3.x * barycentricCoords.z) * img.getWidth(),
                                    (textureCoords1.y * barycentricCoords.x
                                            + textureCoords2.y * barycentricCoords.y
                                            + textureCoords3.y * barycentricCoords.z) * img.getHeight()
                            );

                            int color = img.getRGB((int) textureCoords.x, (int) textureCoords.y);
                            int red = (color >> 16) & 0xff;
                            int green = (color >> 8) & 0xff;
                            int blue = (color) & 0xff;

                            if (normal1 != null && normal2 != null && normal3 != null) {
                                Vector3f n = new Vector3f(
                                        normal1.x * barycentricCoords.x
                                                + normal2.x * barycentricCoords.y
                                                + normal3.x * barycentricCoords.z,
                                        normal1.y * barycentricCoords.x
                                                + normal2.y * barycentricCoords.y
                                                + normal3.y * barycentricCoords.z,
                                        normal1.z * barycentricCoords.x
                                                + normal2.z * barycentricCoords.y
                                                + normal3.z * barycentricCoords.z
                                ).nor();

                                float l = -n.cpy().dot(ray);

                                if (l < 0) red = green = blue = 0;
                                else {
                                    red = (int) (red * (1 - k) + (red * k * l));
                                    green = (int) (green * (1 - k) + (green * k * l));
                                    blue = (int) (blue * (1 - k) + (blue * k * l));
                                }
                            }

                            zBuffer.put(pixel, new ZBufferColor(z, new Color(red, green, blue)));
                        } else {
                            // set pixel from model's color
                            int red = model.getColor().getRed();
                            int green = model.getColor().getGreen();
                            int blue = model.getColor().getBlue();

                            if (normal1 != null && normal2 != null && normal3 != null) {
                                Vector3f n = new Vector3f(
                                        normal1.x * barycentricCoords.x
                                                + normal2.x * barycentricCoords.y
                                                + normal3.x * barycentricCoords.z,
                                        normal1.y * barycentricCoords.x
                                                + normal2.y * barycentricCoords.y
                                                + normal3.y * barycentricCoords.z,
                                        normal1.z * barycentricCoords.x
                                                + normal2.z * barycentricCoords.y
                                                + normal3.z * barycentricCoords.z
                                ).nor();

                                float l = -n.cpy().dot(ray);

                                if (l < 0) red = green = blue = 0;
                                else {
                                    red = (int) (red * (1 - k) + (red * k * l));
                                    green = (int) (green * (1 - k) + (green * k * l));
                                    blue = (int) (blue * (1 - k) + (blue * k * l));
                                }
                            }

                            zBuffer.put(pixel, new ZBufferColor(z, new Color(red, green, blue)));
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
