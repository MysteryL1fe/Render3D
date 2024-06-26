package com.edu.vsu.khanin.dmitrii.render_engine;

import com.edu.vsu.kretov.daniil.mathLib4Task.matrix.Matrix4f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector2f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector4f;
import com.edu.vsu.prilepin.maxim.MainFrame;

public class GraphicConveyor {
    public static Matrix4f rotateScaleTranslate() {
        float[] matrix = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        return new Matrix4f(matrix);
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultZ = target.cpy().sub(eye);
        Vector3f resultX = up.cpy().crs(resultZ);
        Vector3f resultY = resultZ.cpy().crs(resultX);

        resultX.nor();
        resultY.nor();
        resultZ.nor();

        float[] matrix = new float[]{
                resultX.x, resultY.x, resultZ.x, 0,
                resultX.y, resultY.y, resultZ.y, 0,
                resultX.z, resultY.z, resultZ.z, 0,
                -resultX.dot(eye), -resultY.dot(eye), -resultZ.dot(eye), 1};
        return new Matrix4f(matrix);
    }
    public static Vector3f worldToLocal(Vector3f worldPos, Matrix4f viewMatrix) {
        Matrix4f inverseViewMatrix = new Matrix4f(viewMatrix).inv();

        // Создаем 4-мерный вектор с x, y, z координатами мировой позиции и 1 в w
        Vector4f worldPosHomogeneous = new Vector4f(worldPos.x, worldPos.y, worldPos.z, 1.0f);

        // Умножаем вектор мировой позиции на обратную матрицу вида
        Vector4f localPosHomogeneous = inverseViewMatrix.mul(worldPosHomogeneous);

        // Делим на w, чтобы получить 3-мерные локальные координаты
        localPosHomogeneous.div(localPosHomogeneous.w);

        // Создаем и возвращаем 3-мерный вектор локальных координат
        return new Vector3f(localPosHomogeneous.x, localPosHomogeneous.y, localPosHomogeneous.z);
    }


    public static Matrix4f perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        Matrix4f result = new Matrix4f();
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        result.val[Matrix4f.M11] = tangentMinusOnDegree / aspectRatio;
        result.val[Matrix4f.M22] = tangentMinusOnDegree;
        result.val[Matrix4f.M33] = (farPlane + nearPlane) / (farPlane - nearPlane);
        result.val[Matrix4f.M34] = 1.0F;
        result.val[Matrix4f.M43] = 2 * (nearPlane * farPlane) / (nearPlane - farPlane);
        return result;
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4f matrix, final Vector3f vertex) {
        final float x = (vertex.x * matrix.val[Matrix4f.M11]) + (vertex.y * matrix.val[Matrix4f.M21]) + (vertex.z * matrix.val[Matrix4f.M31]) + matrix.val[Matrix4f.M41];
        final float y = (vertex.x * matrix.val[Matrix4f.M12]) + (vertex.y * matrix.val[Matrix4f.M22]) + (vertex.z * matrix.val[Matrix4f.M32]) + matrix.val[Matrix4f.M42];
        final float z = (vertex.x * matrix.val[Matrix4f.M13]) + (vertex.y * matrix.val[Matrix4f.M23]) + (vertex.z * matrix.val[Matrix4f.M33]) + matrix.val[Matrix4f.M43];
        final float w = (vertex.x * matrix.val[Matrix4f.M14]) + (vertex.y * matrix.val[Matrix4f.M24]) + (vertex.z * matrix.val[Matrix4f.M34]) + matrix.val[Matrix4f.M44];
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Vector2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Vector2f(vertex.x * width + width / 2.0F, -vertex.y * height + height / 2.0F);
    }
    public static Vector2f convertToCenterOrigin(int x, int y) {
        // Получаем размеры экрана (здесь используем фиксированные значения, замените на актуальные)
        int screenWidth = 900;
        int screenHeight = 700;

        // Переводим координаты, чтобы центр стал (0, 0)
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;
        int convertedX = x - centerX;
        int convertedY = centerY - y; // Инвертируем y для системы координат, где y растет вниз

        return new Vector2f(convertedX, convertedY);
    }
}
