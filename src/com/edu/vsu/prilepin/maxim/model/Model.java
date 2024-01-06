package com.edu.vsu.prilepin.maxim.model;

import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector2f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;

import java.util.*;

public class Model implements Cloneable {
    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    @Override
    public Model clone() {
        Model model = new Model();

        ArrayList<Vector3f> newVertices = new ArrayList<>();
        for (Vector3f v : vertices) {
            newVertices.add(v.cpy());
        }
        model.vertices = newVertices;

        ArrayList<Vector2f> newTextVertices = new ArrayList<>();
        for (Vector2f v : textureVertices) {
            newTextVertices.add(v.cpy());
        }
        model.textureVertices = newTextVertices;

        ArrayList<Vector3f> newNormals = new ArrayList<>();
        for (Vector3f v : normals) {
            newNormals.add(v.cpy());
        }
        model.normals = newNormals;
        ArrayList<Polygon> newPolygons = new ArrayList<>();
        for (Polygon p : polygons) {
            newPolygons.add(p.clone());
        }
        model.polygons = newPolygons;

        return model;
    }
}
