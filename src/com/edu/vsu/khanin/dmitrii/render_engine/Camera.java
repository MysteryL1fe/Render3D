package com.edu.vsu.khanin.dmitrii.render_engine;

import com.edu.vsu.kretov.daniil.mathLib4Task.matrix.Matrix4f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;

public class Camera {

    public Camera(
            final Vector3f position,
            final Vector3f target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public void setPosition(final Vector3f position) {
        this.position = position;
    }

    public void setTarget(final Vector3f target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3f getPosition() {
        return position;
    }
/*
    public Vector3f clone() {
        Camera camera = new Camera(position,target,fov,aspectRatio,nearPlane,farPlane);
        Vector3f v = new Vector3f(position.cpy());
        camera.position = v;
        return camera.position;
    }
*/

    public Vector3f getTarget() {
        return target;
    }

    public void movePosition(final Vector3f translation) {
        this.position.add(translation);
    }

    public void movePosSet(final Vector3f translation) {
        this.position.set(translation);
    }

    public void moveTarget(final Vector3f translation) {
        this.target.add(translation);
    }

    public void moveTargSet(final Vector3f translation) {
        this.target.set(translation);
    }

    Matrix4f getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    Matrix4f getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    /*public void rotateView(float rotateX, float rotateY, float rotateZ) {
        // Convert angles to radians for trigonometric functions
        float radRotateX = (float) Math.toRadians(rotateX);
        float radRotateY = (float) Math.toRadians(rotateY);
        float radRotateZ = (float) Math.toRadians(rotateZ);

        // Calculate the rotation matrix
        Matrix4f rotationMatrix = Matrix4f.rotationXYZ(radRotateX, radRotateY, radRotateZ);

        // Update the position and target using the rotation matrix
        Vector3f relativePosition = position.sub(target);
        Vector4f rotatedPosition = rotationMatrix.mul(new Vector4f(relativePosition));
        position = target.add(new Vector3f(rotatedPosition));

        // Update the view matrix
        Matrix4f viewMatrix = GraphicConveyor.lookAt(position, target);
        // Assuming you have a method to set the view matrix in GraphicConveyor
        GraphicConveyor.multiplyMatrix4ByVector3(viewMatrix, relativePosition);
    }*/

    private Vector3f position;
    private Vector3f target;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;

    @Override
    public String toString() {
        return "Camera{" +
                "position=" + position +
                ", target=" + target +
                ", fov=" + fov +
                ", aspectRatio=" + aspectRatio +
                ", nearPlane=" + nearPlane +
                ", farPlane=" + farPlane +
                '}';
    }
}
