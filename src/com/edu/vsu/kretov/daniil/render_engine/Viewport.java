package com.edu.vsu.kretov.daniil.render_engine;

import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Viewport extends JPanel implements Runnable, MouseListener, MouseMotionListener {
    private float XCameraPosition = 0;
    private float YCameryPosition = 0;
    private int newX = 0, newY = 0;
    private boolean isMousePressed = false;

    public Viewport() {
        
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
        newX = 99999;
        newY = 99999;
        isMousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x0 = 99999;
        int y0 = 99999;

        isMousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Не используется в данном примере, но необходимо реализовать из-за интерфейса
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Не используется в данном примере, но необходимо реализовать из-за интерфейса
    }

    @Override
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
    }
}
