package com.edu.vsu.khanin.dmitrii.preparation;

import com.edu.vsu.khanin.dmitrii.exceptions.TooLowVerticesException;
import com.edu.vsu.kretov.daniil.mathLib4Task.MathUtils;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.model.Model;
import com.edu.vsu.prilepin.maxim.model.Polygon;

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
            PolygonWithNormal polygonWithNormal = new PolygonWithNormal(polygon);

            ArrayList<Integer> vertexIndices = polygon.getVertexIndices();

            Vector3f vertex1 = model.vertices.get(vertexIndices.get(0) - 1);
            Vector3f vertex2 = model.vertices.get(vertexIndices.get(1) - 1);
            Vector3f vertex3 = model.vertices.get(vertexIndices.get(2) - 1);

            Vector3f normal = MathUtils.normalizePolygon(vertex1, vertex2, vertex3);

            if (model.normals.size() > polygon.getNormalIndices().get(0)) {
                Vector3f vertexNormal = model.normals.get(polygon.getNormalIndices().get(0));

                if (vertexNormal.dot(normal) < 0) normal.scl(-1);
            }

            polygonWithNormal.setNormal(normal);
            polygons.add(polygonWithNormal);
        }

        result.polygons = polygons;

        return result;
    }
}
