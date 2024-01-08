package com.edu.vsu.prilepin.maxim.JFrameCirc;

import javax.swing.*;
import java.awt.*;

public class CircleButton extends JButton {

    public CircleButton(String label) {
        super(label);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.YELLOW);
        setBackground(Color.BLUE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

        g.setColor(getForeground());
        g.setFont(getFont());

        FontMetrics metrics = g.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(getText())) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(getText(), x, y);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);    }

    @Override
    public boolean contains(int x, int y) {
        return super.contains(x, y);
    }
}
