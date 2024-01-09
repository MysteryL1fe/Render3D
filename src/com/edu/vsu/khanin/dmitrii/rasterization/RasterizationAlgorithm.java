package com.edu.vsu.khanin.dmitrii.rasterization;

import com.edu.vsu.kretov.daniil.mathLib4Task.matrix.Matrix4f;
import com.edu.vsu.khanin.dmitrii.render_engine.Camera;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public interface RasterizationAlgorithm {
    HashSet<ColorPixel> rasterization(final Camera camera, final ArrayList<ModelInScene> sceneModels,
                                      ArrayList<ModelInScene> lights, Matrix4f mVPMatrix, int width, int height);

    class Pixel {
        public int x, y;

        public Pixel(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pixel pixel = (Pixel) o;
            return x == pixel.x && y == pixel.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    class ColorPixel {
        public Pixel pixel;
        public Color color;

        public ColorPixel(Pixel pixel, Color color) {
            this.pixel = pixel;
            this.color = color;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ColorPixel that = (ColorPixel) o;
            return pixel.equals(that.pixel) && color.equals(that.color);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pixel, color);
        }
    }
}
