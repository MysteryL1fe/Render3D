package com.edu.vsu.prilepin.maxim.obj;

import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector2f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.model.Model;
import com.edu.vsu.prilepin.maxim.model.Polygon;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ObjWriter {
    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";

    public static void writeModelToObjFile(String fileName, Model model) {

        File file = new File(fileName);

        try (PrintWriter printWriter = new PrintWriter(file)) {
            writeVerticesOfModel(printWriter, model.vertices);
            writeTextureVerticesOfModel(printWriter, model.textureVertices);
            writeNormalsOfModel(printWriter, model.normals);
            writePolygonsOfModel(printWriter, model.polygons);
        } catch (IOException e) {
            throw new ObjWriterException("Error writing model to obj file: " + fileName + " " + e.getMessage());
        }
    }

    protected static void writeVerticesOfModel(PrintWriter printWriter, List<Vector3f> vertices) {
        for (Vector3f vertex : vertices) {
            printWriter.println(OBJ_VERTEX_TOKEN + " " + vertex.x + " " + vertex.y + " " + vertex.z);
        }
        printWriter.println();
    }

    protected static void writeTextureVerticesOfModel(PrintWriter printWriter, List<Vector2f> textureVertices) {
        for (Vector2f vertex : textureVertices) {
            printWriter.println(OBJ_TEXTURE_TOKEN + " " + vertex.x + " " + vertex.y);
        }
        printWriter.println();
    }

    protected static void writeNormalsOfModel(PrintWriter printWriter, List<Vector3f> normals) {
        for (Vector3f normal : normals) {
            printWriter.println(OBJ_NORMAL_TOKEN + " " + normal.x + " " + normal.y + " " + normal.z);
        }
        printWriter.println();
    }

    protected static void writePolygonsOfModel(PrintWriter printWriter, List<Polygon> polygons) {
        for (Polygon polygon : polygons) {
            printWriter.print(modelsPolygonToFaceForObjFile(polygon.getVertexIndices(), polygon.getTextureVertexIndices(), polygon.getNormalIndices()));
            printWriter.println();
        }
    }

    private static String modelsPolygonToFaceForObjFile(List<Integer> vertexIndices, List<Integer> textureVertexIndices, List<Integer> normalIndices) {
        StringBuilder objFace = new StringBuilder();
        objFace.append(OBJ_FACE_TOKEN + " ");

        for (int i = 0; i < vertexIndices.size(); i++) {

            if (!textureVertexIndices.isEmpty()) {
                objFace.append(vertexIndices.get(i) + 1).append("/").append(textureVertexIndices.get(i) + 1);
            } else {
                objFace.append(vertexIndices.get(i) + 1);
            }

            if (!normalIndices.isEmpty()) {
                if (textureVertexIndices.isEmpty()) {
                    objFace.append("/");
                }
                objFace.append("/").append(normalIndices.get(i) + 1);
            }

            if (i != vertexIndices.size() - 1) {
                objFace.append(" ");
            }

        }

        return objFace.toString();
    }
}
