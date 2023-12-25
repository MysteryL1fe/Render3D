package com.edu.vsu.khanin.dmitrii.rasterization;

import com.edu.vsu.kretov.daniil.mathLib4Task.matrix.Matrix4f;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public interface RasterizationAlgorithm {
    HashSet<ColorPixel> rasterization(final ArrayList<ModelInScene> sceneModels, Matrix4f mVPMatrix, int width, int height);

    class ColorPixel {
        public int x, y;
        public Color color;

        public ColorPixel(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ColorPixel that = (ColorPixel) o;
            return x == that.x && y == that.y && color.equals(that.color);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, color);
        }
    }
}
