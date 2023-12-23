package com.edu.vsu.khanin.dmitrii.rasterization;

import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;

import java.awt.*;

public class Rasterization {
    public static Vector3f toBarycentricCoordinates(float x, float y, Vector3f v1, Vector3f v2, Vector3f v3) {
        float alpha = ((v2.y - v3.y) * (x - v3.x) + (v3.x - v2.x) * (y - v3.y)) /
                ((v2.y - v3.y) * (v1.x - v3.x) + (v3.x - v2.x) * (v1.y - v3.y));

        float beta = ((v3.y - v1.y) * (x - v3.x) + (v1.x - v3.x) * (y - v3.y)) /
                ((v2.y - v3.y) * (v1.x - v3.x) + (v3.x - v2.x) * (v1.y - v3.y));

        float gamma = 1 - alpha - beta;

        return new Vector3f(alpha, beta, gamma);
    }
}
