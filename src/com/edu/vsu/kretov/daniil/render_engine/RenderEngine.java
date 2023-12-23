package com.edu.vsu.kretov.daniil.render_engine;

import com.edu.vsu.kretov.daniil.mathLib4Task.matrix.Matrix4f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector2f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.model.Model;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;

import javax.swing.*;
import java.util.ArrayList;

import static com.edu.vsu.kretov.daniil.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public static void render(
            final Viewport viewport,
            final Camera camera,
            final ArrayList<ModelInScene> sceneModels)
    {
        Matrix4f modelMatrix = rotateScaleTranslate();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
        modelViewProjectionMatrix.mul(viewMatrix);
        modelViewProjectionMatrix.mul(projectionMatrix);
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nPolygons = mesh.polygons.size();
            for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
                //            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();
                //
                //            ArrayList<Vector2f> resultPoints = new ArrayList<>();
                //            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                //                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
                //
                //                Vector3f vertexVecmath = new Vector3f(vertex.x, vertex.y, vertex.z);
                //
                //                Vector2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                //                resultPoints.add(resultPoint);
                //            }
                //
                //            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                //                /*graphicsContext.strokeLine(
                //                        resultPoints.get(vertexInPolygonInd - 1).x,
                //                        resultPoints.get(vertexInPolygonInd - 1).y,
                //                        resultPoints.get(vertexInPolygonInd).x,
                //                        resultPoints.get(vertexInPolygonInd).y);*/
                //            }

                /*if (nVerticesInPolygon > 0)
                    graphicsContext.strokeLine(
                            resultPoints.get(nVerticesInPolygon - 1).x,
                            resultPoints.get(nVerticesInPolygon - 1).y,
                            resultPoints.get(0).x,
                            resultPoints.get(0).y);*/
            }
        }
    }
}
