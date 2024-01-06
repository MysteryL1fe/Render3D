package com.edu.vsu.khanin.dmitrii.preparation;

import com.edu.vsu.khanin.dmitrii.exceptions.TooLowVerticesException;
import com.edu.vsu.prilepin.maxim.model.Model;

public class PrepareModel {
    public static Model prepareModel(Model model) throws TooLowVerticesException {
        Model result = new Model();
        result.vertices.addAll(model.vertices);
        result.textureVertices.addAll(model.textureVertices);
        result.polygons = SimpleTriangulation.toTriangles(model.polygons);
        result.normals = NormalUtils.normalizeVertices(model.vertices, model.normals, result.polygons);

        return result;
    }
}
