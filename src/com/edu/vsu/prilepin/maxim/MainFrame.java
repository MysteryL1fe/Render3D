package com.edu.vsu.prilepin.maxim;

import com.edu.vsu.khanin.dmitrii.exceptions.TooLowVerticesException;
import com.edu.vsu.khanin.dmitrii.preparation.PrepareModel;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;
import com.edu.vsu.prilepin.maxim.obj.ObjReader;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.ArrayList;


public class MainFrame extends JFrame {
    private final JPanel scenePanel;
    private final JPanel propertiesPanel;
    private final DefaultListModel<String> modelList;
    private final JList<String> jModelList;
    private final ArrayList<ModelInScene> sceneModels;

    private ModelInScene selectedModel;

    private final JTextField locationXField;
    private final JTextField locationYField;
    private final JTextField locationZField;

    private final JTextField rotationXField;
    private final JTextField rotationYField;
    private final JTextField rotationZField;
    private final JTextField scaleXField;
    private final JTextField scaleYField;
    private final JTextField scaleZField;

    private final JTextField nameField;



    public MainFrame() {
        setTitle("3D Рендеринг");
        setLayout(null);

        sceneModels = new ArrayList<>();

        modelList = new DefaultListModel<>();

        jModelList = new JList<>(modelList);
        jModelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane modelListScrollPane = new JScrollPane(jModelList);
        modelListScrollPane.setBounds(20, 140, 150, 700);
        add(modelListScrollPane);



        JButton saveButton = new JButton("Сохранить");
        JButton loadButton = new JButton("Загрузить модель");
        JButton deleteButton = new JButton("Удалить модель");
        JFrame frame = new JFrame("Model Loader");


        saveButton.setBounds(20, 20, 150, 30);
        loadButton.setBounds(20, 60, 150, 30);
        deleteButton.setBounds(20, 100, 150, 30);


        add(saveButton);
        add(loadButton);
        add(deleteButton);

        scenePanel = new JPanel();
        scenePanel.setBounds(200, 20, 400, 400);
        scenePanel.setBorder(BorderFactory.createEtchedBorder());
        add(scenePanel);

        propertiesPanel = new JPanel();
        propertiesPanel.setBounds(620, 20, 150, 400);
        propertiesPanel.setBorder(BorderFactory.createTitledBorder("Свойства объекта"));
        propertiesPanel.setLayout(null);

        JLabel locationLabel = new JLabel("Позиция:");
        locationLabel.setBounds(150, 20, 100, 20);
        locationXField = new JTextField();
        locationYField = new JTextField();
        locationZField = new JTextField();
        locationXField.setBounds(120, 50, 130, 25);
        locationYField.setBounds(120, 80, 130, 25);
        locationZField.setBounds(120, 110, 130, 25);
        propertiesPanel.add(locationLabel);
        propertiesPanel.add(locationXField);
        propertiesPanel.add(locationYField);
        propertiesPanel.add(locationZField);

        JLabel rotationLabel = new JLabel("Поворот:");
        rotationLabel.setBounds(150, 140, 100, 20);
        rotationXField = new JTextField();
        rotationYField = new JTextField();
        rotationZField = new JTextField();
        rotationXField.setBounds(120, 170, 130, 25);
        rotationYField.setBounds(120, 200, 130, 25);
        rotationZField.setBounds(120, 230, 130, 25);
        propertiesPanel.add(rotationLabel);
        propertiesPanel.add(rotationXField);
        propertiesPanel.add(rotationYField);
        propertiesPanel.add(rotationZField);


        JLabel scaleLabel = new JLabel("Масштаб:");
        scaleLabel.setBounds(150, 260, 100, 20);
        scaleXField = new JTextField();
        scaleYField = new JTextField();
        scaleZField = new JTextField();
        scaleXField.setBounds(120, 290, 130, 25);
        scaleYField.setBounds(120, 320, 130, 25);
        scaleZField.setBounds(120, 350, 130, 25);
        propertiesPanel.add(scaleLabel);
        propertiesPanel.add(scaleXField);
        propertiesPanel.add(scaleYField);
        propertiesPanel.add(scaleZField);


        JLabel nameLabel = new JLabel("Название:");
        nameLabel.setBounds(150, 380, 100, 20);
        nameField = new JTextField();
        nameField.setBounds(120, 410, 130, 25);
        propertiesPanel.add(nameLabel);
        propertiesPanel.add(nameField);


        // Добавьте другие свойства объекта по аналогии

        add(propertiesPanel);


        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedModel != null) {
                    selectedModel.setLocation(new Vector3f(Float.parseFloat(locationXField.getText()), Float.parseFloat(locationYField.getText()), Float.parseFloat(locationZField.getText())));
                    selectedModel.setRotation(new Vector3f(Float.parseFloat(rotationXField.getText()), Float.parseFloat(rotationYField.getText()), Float.parseFloat(rotationZField.getText())));
                    selectedModel.setScale(new Vector3f(Float.parseFloat(scaleXField.getText()), Float.parseFloat(scaleYField.getText()), Float.parseFloat(scaleZField.getText())));
                    selectedModel.setModelName(nameField.getText());

                    // Additional logic to update the selected model in the 3D scene
                    System.out.println("Changes saved for model: " + selectedModel.getModelName());
                } else {
                    JOptionPane.showMessageDialog(frame, "Выберите модель для сохранения изменений");
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Выберите .obj файл");

                int userSelection = fileChooser.showOpenDialog(frame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();

                    if (filePath.endsWith(".obj")) {
                        // Создание экземпляра Model и добавление его в sceneModels
                        Vector3f defaultPosition = new Vector3f(0, 0, 0);
                        Vector3f defaultRotation = new Vector3f(0, 0, 0);
                        Vector3f defaultScale = new Vector3f(1, 1, 1);
                        ModelInScene newModel = null;
                        try {
                            newModel = new ModelInScene(PrepareModel.prepareModel(ObjReader.read(filePath)),selectedFile.getName(), defaultPosition, defaultRotation, defaultScale);
                        } catch (TooLowVerticesException ex) {
                            JOptionPane.showMessageDialog(propertiesPanel, "Произошла ошибка, выберите другой .obj файл");
                        }
                        sceneModels.add(newModel);

                        // Обновление списка моделей в интерфейсе
                        modelList.addElement(selectedFile.getName());

                        System.out.println("Выбран .obj файл: " + filePath);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Пожалуйста, выберите .obj файл");
                    }
                }
            }
        });

        jModelList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = jModelList.getSelectedIndex();
                if (selectedIndex != -1) {
                   selectedModel = sceneModels.get(selectedIndex);
                  updatePropertiesPanel(selectedModel);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = jModelList.getSelectedIndex();
                if (selectedIndex != -1) { // If a model is selected
                    String selectedModel = modelList.getElementAt(selectedIndex);
                    modelList.remove(selectedIndex);

                    // Удаление модели из sceneModels
                    sceneModels.remove(selectedIndex);

                    System.out.println("Удалена модель: " + selectedModel);
                }
            }
        });



        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                scenePanel.setSize((int) (width * 0.5), height - 40);
                propertiesPanel.setLocation((int) (width * 0.75), 20);
                propertiesPanel.setSize((int) (width * 0.25) - 20, height - 40);
            }
        });

        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void updatePropertiesPanel(ModelInScene model) {
        locationXField.setText(String.valueOf(model.getLocation().x));
        locationYField.setText(String.valueOf(model.getLocation().y));
        locationZField.setText(String.valueOf(model.getLocation().z));

        rotationXField.setText(String.valueOf(model.getRotation().x));
        rotationYField.setText(String.valueOf(model.getRotation().y));
        rotationZField.setText(String.valueOf(model.getRotation().z));

        scaleXField.setText(String.valueOf(model.getScale().x));
        scaleYField.setText(String.valueOf(model.getScale().y));
        scaleZField.setText(String.valueOf(model.getScale().z));

        nameField.setText(model.getModelName());
    }

//    private void saveChanges(ModelInScene model) {
//        model.setLocation(new Vector3f(Float.parseFloat(locationXField.getText()), Float.parseFloat(locationYField.getText()), Float.parseFloat(locationZField.getText())));
//        model.setRotation(new Vector3f(Float.parseFloat(rotationXField.getText()), Float.parseFloat(rotationYField.getText()), Float.parseFloat(rotationZField.getText())));
//        model.setScale(new Vector3f(Float.parseFloat(scaleXField.getText()), Float.parseFloat(scaleYField.getText()), Float.parseFloat(scaleZField.getText())));
//
//        model.setModelName(nameField.getText());
//
//        // Additional logic to update the selected model in the 3D scene
//        System.out.println("Changes saved for model: " + model.getModelName());
//    }

    private void addModelToScene(ModelInScene model) {
        sceneModels.add(model);
        modelList.addElement(model.getModelName());
    }

    private void removeModelFromScene(int index) {
        if (index >= 0 && index < sceneModels.size()) {
            sceneModels.remove(index);
            modelList.remove(index);
            // Additional logic to remove the selected model from the 3D scene
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }
}

