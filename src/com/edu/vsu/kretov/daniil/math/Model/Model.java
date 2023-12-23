package com.edu.vsu.kretov.daniil.math.Model;

import com.edu.vsu.kretov.daniil.math.mathLib4Task.vector.Vector2f;
import com.edu.vsu.kretov.daniil.math.mathLib4Task.vector.Vector3f;

import java.util.*;

public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();
}