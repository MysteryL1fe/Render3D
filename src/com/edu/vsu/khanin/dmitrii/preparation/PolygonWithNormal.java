package com.edu.vsu.khanin.dmitrii.preparation;

import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.model.Polygon;

public class PolygonWithNormal extends Polygon {
    private Vector3f normal;

    public PolygonWithNormal() {
        super();
    }

    public PolygonWithNormal(Polygon polygon) {
        this();
        this.getVertexIndices().addAll(polygon.getVertexIndices());
        this.getTextureVertexIndices().addAll(polygon.getTextureVertexIndices());
        this.getNormalIndices().addAll(polygon.getNormalIndices());
    }

    public PolygonWithNormal (Polygon polygon, Vector3f normal) {
        this(polygon);
        this.normal = normal;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }
}
