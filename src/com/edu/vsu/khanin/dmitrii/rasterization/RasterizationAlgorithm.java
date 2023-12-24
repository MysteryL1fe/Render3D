package com.edu.vsu.khanin.dmitrii.rasterization;

import com.edu.vsu.kretov.daniil.mathLib4Task.matrix.Matrix4f;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

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
    }

    class ZBufferColor {
        public float zBuffer;
        public Color color;

        public ZBufferColor(float zBuffer, Color color) {
            this.zBuffer = zBuffer;
            this.color = color;
        }
    }
}
