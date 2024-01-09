package com.edu.vsu.khanin.dmitrii;

import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector2f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.model.Model;
import com.edu.vsu.prilepin.maxim.model.Polygon;

import java.awt.*;
import java.util.ArrayList;

public class Light extends Model {
    private Color lightColor;

    public Light(Model model, Color lightColor) {
        ArrayList<Vector3f> newVertices = new ArrayList<>();
        for (Vector3f v : model.vertices) {
            newVertices.add(v.cpy());
        }
        vertices = newVertices;

        ArrayList<Vector2f> newTextVertices = new ArrayList<>();
        for (Vector2f v : model.textureVertices) {
            newTextVertices.add(v.cpy());
        }
        textureVertices = newTextVertices;

        ArrayList<Vector3f> newNormals = new ArrayList<>();
        for (Vector3f v : model.normals) {
            newNormals.add(v.cpy());
        }
        normals = newNormals;
        ArrayList<com.edu.vsu.prilepin.maxim.model.Polygon> newPolygons = new ArrayList<>();
        for (Polygon p : model.polygons) {
            newPolygons.add(p.clone());
        }
        polygons = newPolygons;

        this.lightColor = lightColor;
    }

    public Color getLightColor() {
        return lightColor;
    }

    public void setLightColor(Color lightColor) {
        this.lightColor = lightColor;
    }
}
