package com.edu.vsu.kretov.daniil.render_engine;

import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector2f;
import com.edu.vsu.prilepin.maxim.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Viewport extends JPanel implements Runnable, MouseListener, KeyListener, MouseWheelListener {
    private final MainFrame mainFrame;
    //private float XCameraPosition = 0;
    //private float YCameryPosition = 0;
    private int startX = 0, startY = 0;
    //private boolean isMousePressed = false;

    public Viewport(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void drawPixel(int x, int y, Color color) {
        Graphics g = this.getGraphics();
        g.setColor(color);
        g.drawLine(x, y, x, y);
    }

    public void drawLine(Vector2f start, Vector2f end) {
        Graphics g = this.getGraphics();
        g.setColor(Color.BLACK);
        g.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y);
    }

    @Override
    public void run() {

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // Не используется в данном примере, но необходимо реализовать из-за интерфейса
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        //isMousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int endX = e.getX();
        int endY = e.getY();

        int cursorMoveX = endX - startX;
        int cursorMoveY = endY - startY;

        float cursorMoveXPercent = (float) cursorMoveX / this.getBounds().width;
        float cursorMoveYPercent = (float) cursorMoveY / this.getBounds().height;

        switch (mainFrame.getCameraState()) {
            case MOVE_CAMERA -> {
                moveCamera();
            }
            case ROTATE_CAMERA -> {
                rotateCamera();
            }
        }

        //isMousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Не используется в данном примере, но необходимо реализовать из-за интерфейса
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Не используется в данном примере, но необходимо реализовать из-за интерфейса
    }

    /*@Override
    public void mouseMoved(MouseEvent e) {
         newX = e.getX();
         newY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isMousePressed) {


            // Определение направления движения
            if (newX > XCameraPosition) {
                System.out.println("Движение вправо");
            } else if (newX < XCameraPosition) {
                System.out.println("Движение влево");
            }

            if (newY > YCameryPosition) {
                System.out.println("Движение вниз");
            } else if (newY < YCameryPosition) {
                System.out.println("Движение вверх");
            }

            // Обновление координат мыши
            XCameraPosition = newX;
            YCameryPosition = newY;
        }
    }*/

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                moveCamera();
            }
            case KeyEvent.VK_A -> {
                moveCamera();
            }
            case KeyEvent.VK_S -> {
                moveCamera();
            }
            case KeyEvent.VK_D -> {
                moveCamera();
            }
            case KeyEvent.VK_I -> {
                rotateCamera();
            }
            case KeyEvent.VK_J -> {
                rotateCamera();
            }
            case KeyEvent.VK_K -> {
                rotateCamera();
            }
            case KeyEvent.VK_L -> {
                rotateCamera();
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int scrollAmount = e.getScrollAmount();

        zoomCamera();
    }

    private void moveCamera() {
        Camera camera = mainFrame.getSelectedCamera();


    }

    private void rotateCamera() {
        Camera camera = mainFrame.getSelectedCamera();


    }

    private void zoomCamera() {
        Camera camera = mainFrame.getSelectedCamera();


    }

    public enum CameraState {
        MOVE_CAMERA,
        ROTATE_CAMERA
    }

    public enum RenderState {
        CONTOUR,
        COLOR,
        TEXTURE,
        LIGHT
    }
}
