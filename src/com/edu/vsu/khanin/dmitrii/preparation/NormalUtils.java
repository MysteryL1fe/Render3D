package com.edu.vsu.khanin.dmitrii.preparation;

import com.edu.vsu.kretov.daniil.mathLib4Task.MathUtils;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.model.Polygon;

import java.util.ArrayList;

public class NormalUtils {
    public static Vector3f normalizePolygon(Polygon polygon, ArrayList<Vector3f> vertices,
                                         ArrayList<Vector3f> normals) {
        ArrayList<Integer> vertexIndices = polygon.getVertexIndices();

        Vector3f vertex1 = vertices.get(vertexIndices.get(0));
        Vector3f vertex2 = vertices.get(vertexIndices.get(1));
        Vector3f vertex3 = vertices.get(vertexIndices.get(2));

        Vector3f normal = MathUtils.normalizePolygon(vertex1, vertex2, vertex3);

        if (polygon.getNormalIndices().size() > 0 && normals.size() > polygon.getNormalIndices().get(0)) {
            Vector3f vertexNormal = normals.get(polygon.getNormalIndices().get(0));

            if (vertexNormal.dot(normal) < 0) normal.scl(-1);
        }

        return normal;
}

    public static ArrayList<Vector3f> normalizeVertices(ArrayList<Vector3f> vertices, ArrayList<Vector3f> normals,
                                                        ArrayList<Polygon> polygons) {
        ArrayList<Vector3f> normalsVertex = new ArrayList<>();

        Vector3f[] normalSummaVertex = new Vector3f[vertices.size()];

        for (Polygon polygon : polygons) {
            ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
            polygon.setNormalIndices((ArrayList<Integer>) vertexIndices.clone());
            Vector3f normal = normalizePolygon(polygon, vertices, normals);

            for (Integer vertexIndex : vertexIndices) {
                if (normalSummaVertex[vertexIndex] == null)
                    normalSummaVertex[vertexIndex] = normal;
                else normalSummaVertex[vertexIndex].add(normal);
            }
        }

        for (int i = 0; i < normalSummaVertex.length; i++) {
            if (normalSummaVertex[i] == null) normalsVertex.add(new Vector3f(0, 0, 0));
            else normalsVertex.add(normalSummaVertex[i].nor());
        }
        return normalsVertex;
    }
}
