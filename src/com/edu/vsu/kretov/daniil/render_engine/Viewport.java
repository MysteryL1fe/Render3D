package com.edu.vsu.kretov.daniil.render_engine;

import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector2f;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Viewport extends JPanel implements MouseListener, KeyListener, MouseWheelListener {
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

    public void clear() {
        Graphics g = this.getGraphics();
        g.clearRect(0, 0, getBounds().width, getBounds().height);
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
                moveCamera(cursorMoveXPercent, cursorMoveYPercent);
            }
            case ROTATE_CAMERA -> {
                rotateCamera(0.0349066F);
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
            case KeyEvent.VK_W-> {
                moveCamera(0,10);
            }
            case KeyEvent.VK_A -> {
                moveCamera(10,0);
            }
            case KeyEvent.VK_S -> {
                moveCamera(0,-10);
            }
            case KeyEvent.VK_D -> {
                moveCamera(-10,0);
            }
            case KeyEvent.VK_I -> {
                rotateCamera(0.0349066F);
            }
            case KeyEvent.VK_J -> {
                rotateCamera(0.0349066F);
            }
            case KeyEvent.VK_K -> {
                rotateCamera(-0.0349066F);
            }
            case KeyEvent.VK_L -> {
                rotateCamera(0.0349066F);
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int scrollAmount = e.getScrollAmount();

        zoomCamera(scrollAmount);
    }

    private void moveCamera(float difX, float difY) {
        Camera camera = mainFrame.getSelectedCamera();
        Vector3f change = camera.getPosition();
        Vector3f changeT = camera.getTarget();
        camera.setPosition(change.set(change.x + difX, change.y + difY, change.z));
        camera.moveTarget(changeT.set(changeT.x + difX,changeT.y + difY, changeT.z));
    mainFrame.render();
    }

    private void rotateCamera(float arc) {
        Camera camera = mainFrame.getSelectedCamera();
        Vector3f change = camera.getPosition();
        camera.moveTarget(change.set((float) (change.x * Math.sin(arc)), (float) ( change.y * Math.cos(arc)), change.z));
        mainFrame.render();

    }

    private void zoomCamera(float scale) {
        Camera camera = mainFrame.getSelectedCamera();
        Vector3f change = camera.getPosition();
        camera.moveTarget(change.set( (change.x * scale),  ( change.y * scale), change.z));
        mainFrame.render();

    }
}
