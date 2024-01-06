package com.edu.vsu.khanin.dmitrii.render_engine;

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
        addMouseListener(this);
        addKeyListener(this);
        addMouseWheelListener(this);
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
        requestFocusInWindow();
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
//                rotateCamera(0.0349066F);
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
                moveCamera(0, 10);
            }
            case KeyEvent.VK_A -> {
                moveCamera(10, 0);
            }
            case KeyEvent.VK_S -> {
                moveCamera(0, -10);
            }
            case KeyEvent.VK_D -> {
                moveCamera(-10, 0);
            }
            case KeyEvent.VK_I -> {
                rotateCamera(0.0349066F, 1);
            }
            case KeyEvent.VK_J -> {
                rotateCamera(0.0349066F, 2);
            }
            case KeyEvent.VK_K -> {
                rotateCamera(0.0349066F, 3);
            }
            case KeyEvent.VK_L -> {
                rotateCamera(0.0349066F, 4);
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int scrollAmount = e.getScrollAmount();
        double scrollType = e.getPreciseWheelRotation();
        zoomCamera((float) (scrollAmount * 0.35), scrollType);
    }

    private void moveCamera(float difX, float difY) {
        Camera camera = mainFrame.getSelectedCamera();
        Vector3f change = camera.getPosition();
        Vector3f changeT = camera.getTarget();
        camera.setPosition(change.set(change.x + difX, change.y + difY, change.z));
        camera.moveTarget(changeT.set(changeT.x + difX, changeT.y + difY, changeT.z));
        mainFrame.render();
    }

    private void rotateCamera(float arc, int rType) {
        Camera camera = mainFrame.getSelectedCamera();
        Vector3f change = camera.getPosition();
        float sin = (float) Math.sin(arc);
        float cos = (float) Math.cos(arc);
        float sinNegat = (float) Math.sin(-arc);
        float cosNegat = (float) Math.cos(-arc);

        switch (rType) {
            case 1:
                camera.movePosSet(new Vector3f(change.x, change.y * cos - change.z * sin, change.y * sin + change.z * cos));
//                camera.aBitDiffrentMoveTarget(new Vector3f(0, change.y * cosA - change.z * sinA, change.y * sinA + change.z * cosA));
                break;
            case 2:
                camera.movePosSet(new Vector3f(change.x * cos + change.z * sin, change.y, -change.x * sin + change.z * cos));
                break;
            case 3:
                camera.movePosSet(new Vector3f(change.x, change.y * cosNegat - change.z * sinNegat, change.y * sinNegat + change.z * cosNegat));

                break;
            case 4:
                camera.movePosSet(new Vector3f(change.x * cosNegat + change.z * sinNegat, change.y, -change.x * sinNegat + change.z * cosNegat));

                break;
        }

        mainFrame.render();
    }


    private void zoomCamera(float scale, double scrlType) {
        Camera camera = mainFrame.getSelectedCamera();
        if (scrlType == 1.0) {
            camera.movePosition(new Vector3f((scale), (scale), (scale)));
        } else {
            if (camera.getPosition().y >= 30)
                camera.movePosition(new Vector3f(-(1 / scale), -(1 / scale), -(1 / scale)));
        }
        mainFrame.render();

    }
}
