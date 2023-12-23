package com.edu.vsu.khanin.dmitrii.preparation;

import com.edu.vsu.khanin.dmitrii.Polygon;
import com.edu.vsu.khanin.dmitrii.exceptions.TooLowVerticesException;

import java.util.ArrayList;
import java.util.List;

public class SimpleTriangulation {
    public static ArrayList<Polygon> toTriangles(List<Polygon> polygons) throws TooLowVerticesException {
        if (polygons == null) return null;

        ArrayList<Polygon> triangles = new ArrayList<>();

        for (Polygon polygon : polygons) {
            triangles.addAll(triangulatePolygon(polygon));
        }

        return triangles;
    }

    private static ArrayList<Polygon> triangulatePolygon(Polygon polygon) throws TooLowVerticesException {
        ArrayList<Polygon> triangles = new ArrayList<>();

        ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
        ArrayList<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
        ArrayList<Integer> normalIndices = polygon.getNormalIndices();
        if (vertexIndices.size() > 3) {
            for (int i = 2; i < vertexIndices.size(); i++) {
                ArrayList<Integer> newVertexIndices = new ArrayList<>();
                newVertexIndices.add(vertexIndices.get(0));
                newVertexIndices.add(vertexIndices.get(i - 1));
                newVertexIndices.add(vertexIndices.get(i));

                ArrayList<Integer> newTextureVertexIndices = new ArrayList<>();
                if (textureVertexIndices.size() > i) {
                    newTextureVertexIndices.add(textureVertexIndices.get(0));
                    newTextureVertexIndices.add(textureVertexIndices.get(i - 1));
                    newTextureVertexIndices.add(textureVertexIndices.get(i));
                }

                ArrayList<Integer> newNormalIndices = new ArrayList<>();
                if (normalIndices.size() > i) {
                    newNormalIndices.add(normalIndices.get(0));
                    newNormalIndices.add(normalIndices.get(i - 1));
                    newNormalIndices.add(normalIndices.get(i));
                }

                Polygon newPolygon = new Polygon();
                newPolygon.setVertexIndices(newVertexIndices);
                newPolygon.setTextureVertexIndices(newTextureVertexIndices);
                newPolygon.setNormalIndices(newNormalIndices);

                triangles.add(newPolygon);
            }
        } else if (vertexIndices.size() == 3) triangles.add(polygon);
        else throw new TooLowVerticesException("Less than three vertex in polygon");

        return triangles;
    }
}
