package com.edu.vsu.khanin.dmitrii.preparation;

import com.edu.vsu.khanin.dmitrii.Model;
import com.edu.vsu.khanin.dmitrii.Polygon;
import com.edu.vsu.khanin.dmitrii.Vector3f;
import com.edu.vsu.khanin.dmitrii.exceptions.TooLowVerticesException;

import java.util.ArrayList;

public class PrepareModel {
    public static Model prepareModel(Model model) throws TooLowVerticesException {
        Model result = new Model();
        result.vertices.addAll(model.vertices);
        result.textureVertices.addAll(model.textureVertices);
        result.normals.addAll(model.normals);

        ArrayList<Polygon> triangles = SimpleTriangulation.toTriangles(model.polygons);
        ArrayList<Polygon> polygons = new ArrayList<>();

        for (Polygon polygon : triangles) {
            polygons.add(calcNormalForPolygon(polygon));
        }

        result.polygons = polygons;

        return result;
    }

    private static PolygonWithNormal calcNormalForPolygon(Polygon polygon) {
        PolygonWithNormal result = new PolygonWithNormal(polygon);

        Vector3f normal = new Vector3f(0, 0, 0);
        result.setNormal(normal);

        return result;
    }
}
