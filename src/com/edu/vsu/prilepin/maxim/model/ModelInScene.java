package com.edu.vsu.prilepin.maxim.model;


import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;

import java.awt.*;
import java.nio.file.Path;

public class ModelInScene {
    private String modelName;
    private Vector3f location;
    private Vector3f rotation;
    private Vector3f scale;
    private Model model;
    private Path path;
    private Color color;

    public ModelInScene(Model model, String modelName, Vector3f location, Vector3f rotation, Vector3f scale) {
        this.model = model;
        this.modelName = modelName;
        this.location = location;
        this.rotation = rotation;
        this.scale = scale;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Vector3f getLocation() {
        return location;
    }

    public void setLocation(Vector3f location) {
        this.location = location;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setColor(Color selectedColorFromComboBox) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setTexture(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
