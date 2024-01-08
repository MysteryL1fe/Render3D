package com.edu.vsu.khanin.dmitrii.render_engine;

import com.edu.vsu.kretov.daniil.mathLib4Task.AffineTransforms.AffineTransformations;
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

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int endX = e.getX();
        int endY = e.getY();

        int cursorMoveX = endX - startX;
        int cursorMoveY = endY - startY;
        Vector2f vector2f = GraphicConveyor.convertToCenterOrigin(cursorMoveX,cursorMoveY);
        float cursorMoveXPercent = (float) cursorMoveX / this.getBounds().width;
        float cursorMoveYPercent = (float) cursorMoveY / this.getBounds().height;

        switch (mainFrame.getCameraState()) {
            case MOVE_CAMERA -> {
                moveCamera(cursorMoveX, cursorMoveY, 0);
            }
            case ROTATE_CAMERA -> {
                rotateCamera((float) (cursorMoveXPercent * 2 * Math.PI), 2);
                rotateCamera((float) (cursorMoveYPercent * 2 * Math.PI), 1);
            }
        }

        //isMousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Не используется в данном примере, но необходимо реализовать из-за интерфейса
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W-> {
                moveCamera(0,0, 5);
            }
            case KeyEvent.VK_A -> {
                moveCamera(5,0,0);
            }
            case KeyEvent.VK_S -> {
                moveCamera(0,0, -5);
            }
            case KeyEvent.VK_D -> {
                moveCamera(-5,0,0);
            }
            case KeyEvent.VK_Q -> {
                moveCamera(0,-5,0);
            }
            case KeyEvent.VK_E -> {
                moveCamera(0,5,0);
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
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int scrollAmount = e.getScrollAmount();
        double scrollType = e.getPreciseWheelRotation();
        zoomCamera((float) (scrollAmount * 0.35), scrollType);
    }

//    private void moveCameraMouse(float difX, float difY, float difZ) {
//        Camera camera = mainFrame.getSelectedCamera();
//        Vector3f change = camera.getPosition();
//        Vector3f changeT = camera.getTarget();
//        camera.movePosSet(new Vector3f(change.x + difX, change.y + difY, change.z + difZ));
//        camera.moveTargSet(new Vector3f(changeT.x + difX, changeT.y + difY, changeT.z + difZ));
//    mainFrame.render();
//    }
    private void moveCamera(float difX, float difY, float difZ) {
        Camera camera = mainFrame.getSelectedCamera();
        Vector3f resultZ = camera.getTarget().cpy().sub(camera.getPosition());
        Vector3f resultX = new Vector3f(0, 1, 0).crs(resultZ);
        Vector3f resultY = resultZ.cpy().crs(resultX);

        resultX.nor();
        resultY.nor();
        resultZ.nor();

        camera.setPosition(camera.getPosition().add(resultX.cpy().scl(-difX)));
        camera.setPosition(camera.getPosition().add(resultY.cpy().scl(difY)));
        camera.setPosition(camera.getPosition().add(resultZ.cpy().scl(difZ)));
        camera.setTarget(camera.getTarget().add(resultX.cpy().scl(-difX)));
        camera.setTarget(camera.getTarget().add(resultY.cpy().scl(difY)));
        camera.setTarget(camera.getTarget().add(resultZ.cpy().scl(difZ)));

        mainFrame.render();
    }

    private void rotateCamera(float arc, int rType) {
        Camera camera = mainFrame.getSelectedCamera();
        Vector3f change = camera.getPosition();
        Vector3f changeT = camera.getTarget();
        float sin = (float) Math.sin(arc);
        float cos = (float) Math.cos(arc);
        float sinNegat = (float) Math.sin(-arc);
        float cosNegat = (float) Math.cos(-arc);

        switch (rType) {
            case 1 -> {
                camera.movePosSet(new Vector3f(change.x, change.y * cos - change.z * sin, change.y * sin + change.z * cos));
                camera.moveTargSet(new Vector3f(changeT.x, changeT.y * cos - changeT.z * sin, changeT.y * sin + changeT.z * cos));
            }
            case 2 -> {
                camera.movePosSet(new Vector3f(change.x * cos + change.z * sin, change.y, -change.x * sin + change.z * cos));
                camera.moveTargSet(new Vector3f(changeT.x * cos + changeT.z * sin, changeT.y, -changeT.x * sin + changeT.z * cos));
            }
            case 3 -> {
                camera.movePosSet(new Vector3f(change.x, change.y * cosNegat - change.z * sinNegat, change.y * sinNegat + change.z * cosNegat));
                camera.moveTargSet(new Vector3f(changeT.x, changeT.y * cosNegat - changeT.z * sinNegat, changeT.y * sinNegat + changeT.z * cosNegat));
            }
            case 4 -> {
                camera.movePosSet(new Vector3f(change.x * cosNegat + change.z * sinNegat, change.y, -change.x * sinNegat + change.z * cosNegat));
                camera.moveTargSet(new Vector3f(changeT.x * cosNegat + changeT.z * sinNegat, changeT.y, -changeT.x * sinNegat + changeT.z * cosNegat));
            }
        }
        mainFrame.render();
    }
    private void rotateCameraMouse(float radX, float radY, float radZ) {
        Camera camera = mainFrame.getSelectedCamera();
        Vector3f resultZ = camera.getTarget().cpy().sub(camera.getPosition());
        Vector3f resultX = new Vector3f(0, 1, 0).crs(resultZ);
        Vector3f resultY = resultZ.cpy().crs(resultX);


        resultY.nor();
        resultX.nor();
        resultZ.nor();
        System.out.println(camera);
        Vector3f v3f = camera.getTarget().cpy().sub(camera.getPosition());
//        Vector3f v3 =new Vector3f();
//        v3.y = (float) (v3f.y * Math.cos(radX) - v3f.z * Math.sin(radX));
//        v3.z = (float) (v3f.y * Math.sin(radX) + v3f.z * Math.cos(radX));
//        v3f = AffineTransformations.rotateY(v3f, radX);
        camera.setTarget(AffineTransformations.rotateX(v3f, radY).add(camera.getPosition()));
//        camera.setTarget(camera.getTarget().add(resultY.cpy().scl((float) Math.cos(radY))));


        mainFrame.render();
    }




    private void zoomCamera(float scale, double scrlType) {
        Camera camera = mainFrame.getSelectedCamera();
        if(scrlType == 1.0) {
            camera.movePosition(new Vector3f((scale), (scale), (scale)));
        }
        else {
            if(camera.getPosition().y >= 30 )
            camera.movePosition(new Vector3f(-(1/scale), -(1/scale), -(1/scale)));
        }
        mainFrame.render();

    }
}
