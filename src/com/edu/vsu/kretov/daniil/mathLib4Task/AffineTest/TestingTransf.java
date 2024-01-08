package com.edu.vsu.kretov.daniil.mathLib4Task.AffineTest;


import com.edu.vsu.kretov.daniil.mathLib4Task.AffineTransforms.AffineTransformations;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.model.Model;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.testng.AssertJUnit.assertEquals;


class TestingTransf {

    @Test
    void testScale() {
        ArrayList<Vector3f> vertices = new ArrayList<>();
        vertices.add(new Vector3f(1, 2, 3));

        AffineTransformations.scale(vertices, 2, 3, 4);

        assertEquals(new Vector3f(2, 6, 12), vertices.get(0));
    }

    @Test
    void testTranslate() {
        ArrayList<Vector3f> vertices = new ArrayList<>();
        vertices.add(new Vector3f(1, 2, 3));

        AffineTransformations.translate(vertices, 1, 2, 3);

        assertEquals(new Vector3f(2, 4, 6), vertices.get(0));
    }

    @Test
    void testRotate() {
        Model model = new Model();
        model.vertices.add(new Vector3f(1, 0, 0));

        AffineTransformations.rotate(model, (float) Math.PI, 0, 0);

        assertEquals(new Vector3f(1, 0, 0), model.vertices.get(0));
    }

    @Test
    void testMakeInWorldCoord() {
        Model model = new Model();
        model.vertices.add(0, new Vector3f(1,1,1));
        Vector3f vector3f = new Vector3f();
        String modelname = null;
        ModelInScene modelInScene = new ModelInScene(model,modelname, vector3f,vector3f,vector3f);
        modelInScene.setScale(new Vector3f(2, 2, 2));
        modelInScene.setRotation(new Vector3f(0, 0, 0));
        modelInScene.setPosition(new Vector3f(1, 2, 3));

        Model resultModel = AffineTransformations.makeInWorldCoord(modelInScene);

        assertEquals(new Vector3f(3,4,5), resultModel.vertices.get(0));
    }

    // Add similar tests for other methods

    // Additional tests for handling zero values and non-integer values
    @Test
    void testScaleWithZeroValues() {
        ArrayList<Vector3f> vertices = new ArrayList<>();
        vertices.add(new Vector3f(1, 2, 3));

        AffineTransformations.scale(vertices, 0, 0, 0);

        assertEquals(new Vector3f(0, 0, 0), vertices.get(0));
    }

    @Test
    void testTranslateWithZeroValues() {
        ArrayList<Vector3f> vertices = new ArrayList<>();
        vertices.add(new Vector3f(1, 2, 3));

        AffineTransformations.translate(vertices, 0, 0, 0);

        assertEquals(new Vector3f(1, 2, 3), vertices.get(0));
    }

    @Test
    void testRotateWithZeroValues() {
        Model model = new Model();
        model.vertices.add(new Vector3f(1, 0, 0));

        AffineTransformations.rotate(model, 0, 0, 0);

        assertEquals(new Vector3f(1, 0, 0), model.vertices.get(0));
    }

    @Test
    void testMakeInWorldCoordWithZeroValues() {
        Model model = new Model();
        model.vertices.add(0, new Vector3f(1,1,1));
        Vector3f vector3f = new Vector3f();
        String modelname = null;
        ModelInScene modelInScene = new ModelInScene(model,modelname, vector3f,vector3f,vector3f);
        modelInScene.setScale(new Vector3f(0, 0, 0));
        modelInScene.setRotation(new Vector3f(0, 0, 0));
        modelInScene.setPosition(new Vector3f(0, 0, 0));

        Model resultModel = AffineTransformations.makeInWorldCoord(modelInScene);

        assertEquals(new Vector3f(0, 0, 0), resultModel.vertices.get(0));
    }
}
