package com.edu.vsu.khanin.dmitrii.render_engine;

import com.edu.vsu.khanin.dmitrii.Light;
import com.edu.vsu.khanin.dmitrii.rasterization.*;
import com.edu.vsu.kretov.daniil.mathLib4Task.matrix.Matrix4f;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;

import java.util.ArrayList;
import java.util.HashSet;

import static com.edu.vsu.khanin.dmitrii.render_engine.GraphicConveyor.*;

public class RenderEngine {
    private static RenderThread renderThread;

    public static void render(final Viewport viewport, final Camera camera, final ArrayList<ModelInScene> sceneModels,
                              ArrayList<ModelInScene> lights, RenderState renderState) {
        render(viewport, camera, sceneModels, lights, switch (renderState) {
            case NONE -> null;
            case CONTOUR -> new ContourRasterization();
            case COLOR -> new ColorRasterization();
            case COLOR_CONTOUR -> new ColorContourRasterization();
            case TEXTURE -> new TextureRasterization();
            case TEXTURE_CONTOUR -> new TextureContourRasterization();
            case LIGHT_COLOR -> new LightColorRasterization();
            case LIGHT_COLOR_CONTOUR -> new LightColorContourRasterization();
            case LIGHT_TEXTURE -> new LightTextureRasterization();
            case LIGHT_TEXTURE_CONTOUR -> new LightTextureContourRasterization();
        });
    }

    public static void render(final Viewport viewport, final Camera camera, final ArrayList<ModelInScene> sceneModels,
                              ArrayList<ModelInScene> lights, RasterizationAlgorithm rasterizationAlgorithm) {
        if (renderThread != null) renderThread.stopRender();
        
        viewport.clear();

        if (rasterizationAlgorithm == null) return;
        renderThread = new RenderThread(viewport, camera, sceneModels, lights, rasterizationAlgorithm);
        renderThread.start();
    }

    private static class RenderThread extends Thread {
        private boolean isRenderActive = true;
        private final Viewport viewport;
        private final Camera camera;
        private final ArrayList<ModelInScene> sceneModels;
        private final ArrayList<ModelInScene> lights;
        private final RasterizationAlgorithm rasterizationAlgorithm;

        public RenderThread(Viewport viewport, Camera camera, ArrayList<ModelInScene> sceneModels,
                            ArrayList<ModelInScene> lights, RasterizationAlgorithm rasterizationAlgorithm) {
            this.viewport = viewport;
            this.camera = camera;
            this.sceneModels = sceneModels;
            this.lights = lights;
            this.rasterizationAlgorithm = rasterizationAlgorithm;
        }

        @Override
        public void run() {
            super.run();

            Matrix4f modelMatrix = rotateScaleTranslate();
            Matrix4f viewMatrix = camera.getViewMatrix();
            Matrix4f projectionMatrix = camera.getProjectionMatrix();

            Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
            modelViewProjectionMatrix.mul(viewMatrix);
            modelViewProjectionMatrix.mul(projectionMatrix);

            HashSet<RasterizationAlgorithm.ColorPixel> pixels = rasterizationAlgorithm.rasterization(
                    camera, sceneModels, lights, modelViewProjectionMatrix,
                    viewport.getWidth(), viewport.getHeight()
            );

            for (RasterizationAlgorithm.ColorPixel pixel : pixels) {
                if (!isRenderActive) break;
                viewport.drawPixel(pixel.pixel.x, pixel.pixel.y, pixel.color);
            }
        }

        public void stopRender() {
            isRenderActive = false;
        }
    }

    public enum RenderState {
        NONE,
        CONTOUR,
        COLOR,
        COLOR_CONTOUR,
        TEXTURE,
        TEXTURE_CONTOUR,
        LIGHT_COLOR,
        LIGHT_COLOR_CONTOUR,
        LIGHT_TEXTURE,
        LIGHT_TEXTURE_CONTOUR
    }
}
