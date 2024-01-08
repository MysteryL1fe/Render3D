package com.edu.vsu.khanin.dmitrii.preparation;

import com.edu.vsu.khanin.dmitrii.exceptions.TooLowVerticesException;
import com.edu.vsu.khanin.dmitrii.triangulation.SimpleTriangulation;
import com.edu.vsu.khanin.dmitrii.triangulation.TriangulationAlgorithm;
import com.edu.vsu.prilepin.maxim.model.Model;

public class PrepareModel {
    public static Model prepareModel(Model model) throws TooLowVerticesException {
        return prepareModel(model, new SimpleTriangulation());
    }

    public static Model prepareModel(Model model, TriangulationAlgorithm triangulationAlgorithm)
            throws TooLowVerticesException {
        Model result = new Model();
        result.vertices.addAll(model.vertices);
        result.textureVertices.addAll(model.textureVertices);
        result.polygons = triangulationAlgorithm.toTriangles(model.polygons);
        result.normals = NormalUtils.normalizeVertices(model.vertices, model.normals, result.polygons);

        return result;
    }
}
