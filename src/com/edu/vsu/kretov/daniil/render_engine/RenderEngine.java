package com.edu.vsu.kretov.daniil.render_engine;

import com.edu.vsu.khanin.dmitrii.rasterization.ColorRasterization;
import com.edu.vsu.khanin.dmitrii.rasterization.RasterizationAlgorithm;
import com.edu.vsu.kretov.daniil.mathLib4Task.matrix.Matrix4f;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;

import java.util.ArrayList;
import java.util.HashSet;

import static com.edu.vsu.kretov.daniil.render_engine.GraphicConveyor.*;

public class RenderEngine {
    private static RenderThread renderThread;

    public static void render(final Viewport viewport, final Camera camera, final ArrayList<ModelInScene> sceneModels) {
        render(viewport, camera, sceneModels, new ColorRasterization());
    }

    public static void render(final Viewport viewport, final Camera camera, final ArrayList<ModelInScene> sceneModels,
                              ColorRasterization rasterizationAlgorithm) {
        if (renderThread != null) renderThread.stopRender();

        viewport.clear();

        renderThread = new RenderThread(viewport, camera, sceneModels, rasterizationAlgorithm);
        renderThread.start();
    }

    private static class RenderThread extends Thread {
        private boolean isRenderActive;
        private final Viewport viewport;
        private final Camera camera;
        private final ArrayList<ModelInScene> sceneModels;
        private final RasterizationAlgorithm rasterizationAlgorithm;

        public RenderThread(Viewport viewport, Camera camera, ArrayList<ModelInScene> sceneModels,
                            RasterizationAlgorithm rasterizationAlgorithm) {
            this.viewport = viewport;
            this.camera = camera;
            this.sceneModels = sceneModels;
            this.rasterizationAlgorithm = rasterizationAlgorithm;
        }

        @Override
        public void run() {
            super.run();
            isRenderActive = true;

            Matrix4f modelMatrix = rotateScaleTranslate();
            Matrix4f viewMatrix = camera.getViewMatrix();
            Matrix4f projectionMatrix = camera.getProjectionMatrix();

            Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
            modelViewProjectionMatrix.mul(viewMatrix);
            modelViewProjectionMatrix.mul(projectionMatrix);

            HashSet<RasterizationAlgorithm.ColorPixel> pixels = rasterizationAlgorithm.rasterization(
                    sceneModels, modelViewProjectionMatrix, viewport.getBounds().width, viewport.getBounds().height
            );

            for (RasterizationAlgorithm.ColorPixel pixel : pixels) {
                if (!isRenderActive) break;
                viewport.drawPixel(pixel.x, pixel.y, pixel.color);
            }
        }

        public void stopRender() {
            isRenderActive = false;
            System.out.println("Stop render");
        }
    }
}
