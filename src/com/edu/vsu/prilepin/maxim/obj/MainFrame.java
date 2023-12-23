package com.edu.vsu.prilepin.maxim.obj;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame {
    private JPanel scenePanel;
    private JPanel propertiesPanel;

    public MainFrame() {
        setTitle("3D Рендеринг");
        setLayout(null);

        JButton rotateButton = new JButton("Повернуть камеру");
        JButton scaleButton = new JButton("Изменить масштаб");
        JButton moveButton = new JButton("Переместить модель");

        rotateButton.setBounds(20, 20, 150, 30);
        scaleButton.setBounds(20, 60, 150, 30);
        moveButton.setBounds(20, 100, 150, 30);

        add(rotateButton);
        add(scaleButton);
        add(moveButton);

        scenePanel = new JPanel();
        scenePanel.setBounds(200, 20, 400, 400);
        scenePanel.setBorder(BorderFactory.createEtchedBorder());
        add(scenePanel);

        propertiesPanel = new JPanel();
        propertiesPanel.setBounds(620, 20, 150, 400);
        propertiesPanel.setBorder(BorderFactory.createTitledBorder("Свойства объекта"));
        propertiesPanel.setLayout(null);

        JLabel sizeLabel = new JLabel("Размер:");
        sizeLabel.setBounds(10, 30, 100, 20);
        JTextField sizeField = new JTextField();
        sizeField.setBounds(10, 50, 130, 25);
        propertiesPanel.add(sizeLabel);
        propertiesPanel.add(sizeField);

        JLabel scaleLabel = new JLabel("Масштаб:");
        scaleLabel.setBounds(10, 80, 100, 20);
        JTextField scaleField = new JTextField();
        scaleField.setBounds(10, 100, 130, 25);
        propertiesPanel.add(scaleLabel);
        propertiesPanel.add(scaleField);


        // Добавьте другие свойства объекта по аналогии

        add(propertiesPanel);

        rotateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Логика для поворота камеры
            }
        });

        scaleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Логика для изменения масштаба
            }
        });

        moveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Логика для перемещения модели
            }
        });

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                scenePanel.setSize((int)(width * 0.5), height - 40);
                propertiesPanel.setLocation((int)(width * 0.75), 20);
                propertiesPanel.setSize((int)(width * 0.25) - 20, height - 40);
            }
        });

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }
}


//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class MainFrame extends JFrame {
//    public MainFrame() {
//        setTitle("3D Рендеринг");
//        setLayout(new BorderLayout());
//
//        JPanel controlPanel = new JPanel(new GridLayout(3, 1));
//        JButton rotateButton = new JButton("Повернуть камеру");
//        JButton scaleButton = new JButton("Изменить масштаб");
//        JButton moveButton = new JButton("Переместить модель");
//
//        controlPanel.add(rotateButton);
//        controlPanel.add(scaleButton);
//        controlPanel.add(moveButton);
//
//        JPanel scenePanel = new JPanel();
//        // Здесь будет отображаться сцена
//
//        JPanel propertiesPanel = new JPanel(new BorderLayout());
//        propertiesPanel.setBorder(BorderFactory.createTitledBorder("Свойства объекта"));
//
//        // Список свойств объекта
//        JTextArea propertiesTextArea = new JTextArea(10, 20);
//        JScrollPane propertiesScrollPane = new JScrollPane(propertiesTextArea);
//
//        propertiesPanel.add(propertiesScrollPane, BorderLayout.CENTER);
//
//        add(controlPanel, BorderLayout.WEST);
//        add(scenePanel, BorderLayout.CENTER);
//        add(propertiesPanel, BorderLayout.EAST);
//
//        rotateButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Логика для поворота камеры
//            }
//        });
//
//        scaleButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Логика для изменения масштаба
//            }
//        });
//
//        moveButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Логика для перемещения модели
//            }
//        });
//
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new MainFrame();
//            }
//        });
//    }
//}
