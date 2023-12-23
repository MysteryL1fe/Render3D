package com.edu.vsu.kretov.daniil.render_engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Viewport extends JPanel implements Runnable, MouseListener, MouseMotionListener {
    private float XCameraPosition = 0;
    private float YCameryPosition = 0;
    private boolean isMousePressed = false;

    public Viewport() {
        
    }

    public void drawPixel(int x, int y, Color color) {

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
        isMousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
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
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isMousePressed) {
            int newX = e.getX();
            int newY = e.getY();

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
