package com.edu.vsu.prilepin.maxim;

import com.edu.vsu.khanin.dmitrii.exceptions.TooLowVerticesException;
import com.edu.vsu.khanin.dmitrii.preparation.PrepareModel;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.kretov.daniil.render_engine.Camera;
import com.edu.vsu.kretov.daniil.render_engine.RenderEngine;
import com.edu.vsu.kretov.daniil.render_engine.Viewport;
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
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.awt.Color;

class FileInfo {
    private final File file;

    public FileInfo(File file) {
        this.file = file;
    }

    public String getFileName() {
        return file.getName();
    }

}
public class MainFrame extends JFrame {
    private final Viewport viewport;
    private final JPanel propertiesPanel;
    private final DefaultListModel<String> modelList;
    private final JList<String> jModelList;
    private final JList<String> jCamList;
    private final DefaultListModel<String> camList;
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
    private final JLabel textureNameLabel; // Add a JLabel to display the texture name
    private final JComboBox<String> colorComboBox; // Add a ComboBox for selecting the model color

    private FileInfo textureFileInfo; // Create a new class to hold file information


    public MainFrame() {
        setTitle("3D Рендеринг");
        setLayout(null);

        sceneModels = new ArrayList<>();

        modelList = new DefaultListModel<>();
        camList = new DefaultListModel<>();

        jModelList = new JList<>(modelList);
        jModelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane modelListScrollPane = new JScrollPane(jModelList);
        modelListScrollPane.setBounds(20, 100, 150, 300);
        add(modelListScrollPane);

        jCamList = new JList<>();
        jCamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane camListScrollPane = new JScrollPane(jCamList);
        camListScrollPane.setBounds(20, 500, 150, 300);
        add(camListScrollPane);

        textureNameLabel = new JLabel("Название текстуры:");
        textureNameLabel.setBounds(120, 440, 300, 20);

        colorComboBox = new JComboBox<>(new String[]{"Красный", "Зеленый", "Синий"}); // Populate the ComboBox with color options
        colorComboBox.setBounds(120, 490, 130, 25);


        JButton loadTextureButton = new JButton("Загрузить текстуру");
        JButton saveButton = new JButton("Сохранить");
        JButton loadButton = new JButton("Загрузить модель");
        JButton deleteButton = new JButton("Удалить модель");
        JFrame frame = new JFrame("Model Loader");
        JButton createCameraButton = new JButton("Добавить камеру");
        JButton deleteCameraButton = new JButton("Удалить камеру");

        loadTextureButton.setBounds(100, 540, 180, 30);
        saveButton.setBounds(100, 580, 180, 30);
        loadButton.setBounds(20, 20, 150, 30);
        deleteButton.setBounds(20, 60, 150, 30);
        createCameraButton.setBounds(20, 420, 150, 30);
        deleteCameraButton.setBounds(20, 460, 150, 30);

        add(saveButton);
        add(loadButton);
        add(deleteButton);
        add(createCameraButton);
        add(deleteCameraButton);

        viewport = new Viewport();
        viewport.setBounds(200, 20, 400, 400);
        viewport.setBorder(BorderFactory.createEtchedBorder());
        add(viewport);

        propertiesPanel = new JPanel();
        propertiesPanel.setBounds(620, 20, 150, 400);
        propertiesPanel.setBorder(BorderFactory.createTitledBorder("Свойства объекта"));
        propertiesPanel.setLayout(null);
        propertiesPanel.add(loadTextureButton);
        propertiesPanel.add(saveButton);

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



        add(propertiesPanel);


        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedModel != null) {
                    selectedModel.setPosition(new Vector3f(Float.parseFloat(locationXField.getText()), Float.parseFloat(locationYField.getText()), Float.parseFloat(locationZField.getText())));
                    selectedModel.setRotation(new Vector3f(Float.parseFloat(rotationXField.getText()), Float.parseFloat(rotationYField.getText()), Float.parseFloat(rotationZField.getText())));
                    selectedModel.setScale(new Vector3f(Float.parseFloat(scaleXField.getText()), Float.parseFloat(scaleYField.getText()), Float.parseFloat(scaleZField.getText())));
                    selectedModel.setModelName(nameField.getText());

                    // Additional logic to update the selected model in the 3D scene
                    System.out.println("Изменения сохранены для модели: " + selectedModel.getModelName());
                } else {
                    JOptionPane.showMessageDialog(frame, "Выберите модель для сохранения изменений");
                }
            }
        });

        createCameraButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newCameraName = "Camera" + (camList.getSize() + 1); // Генерация нового имени камеры
                camList.addElement(newCameraName); // Добавление новой камеры в список
                jCamList.setModel(camList); // Обновление отображения списка камер
            }
        });

        deleteCameraButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = jCamList.getSelectedIndex();
                if (selectedIndex != -1 && camList.size() > 1) {
                    camList.remove(selectedIndex);
                    jCamList.setModel(camList); // Обновление jCamList
                } else {
                    JOptionPane.showMessageDialog(frame, "Нельзя удалить последнюю камеру");
                }
            }
        });


        loadTextureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedModel != null) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Выберите текстурный файл");
                    int userSelection = fileChooser.showOpenDialog(frame);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        textureFileInfo = new FileInfo(selectedFile); // Store the file information
                        textureNameLabel.setText("Название текстуры: " + textureFileInfo.getFileName()); // Update the texture name label
                        selectedModel.setTexture(Path.of(selectedFile.getAbsolutePath()));
                        System.out.println("Текстура загружена для модели: " + selectedModel.getModelName());
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Выберите модель для загрузки текстуры");
                }
                updateColorComboBoxStatus(); // Update the ComboBox status based on texture selection
            }
        });

        colorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedModel != null && textureFileInfo != null) {
                    selectedModel.setColor(getSelectedColorFromComboBox()); // Set the model color based on the ComboBox selection
                    System.out.println("Установлен цвет для модели: " + selectedModel.getModelName());
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
                        try {
                            ModelInScene newModel = new ModelInScene(
                                    PrepareModel.prepareModel(ObjReader.read(Path.of(filePath))),
                                    selectedFile.getName(), defaultPosition, defaultRotation, defaultScale
                            );

                            sceneModels.add(newModel);

                            // Обновление списка моделей в интерфейсе
                            modelList.addElement(selectedFile.getName());

                            RenderEngine.render(viewport, camera, sceneModels);
                        } catch (TooLowVerticesException | IOException ex) {
                            JOptionPane.showMessageDialog(propertiesPanel, "Произошла ошибка, выберите другой .obj файл");
                        }
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
                if (selectedIndex != -1) { // Если модель выбрана
                    String selectedModelName = modelList.getElementAt(selectedIndex);
                    modelList.remove(selectedIndex);

                    // Модель не является выбранной
                    jModelList.clearSelection();
                    selectedModel = null;

                    // Удаляет модель из sceneModels
                    sceneModels.remove(selectedIndex);

                    // Очищает панель свойств
                    clearPropertiesPanel();

                    System.out.println("Удалена модель: " + selectedModelName);
                }
            }
        });


        camList.addElement("Camera1");
        jCamList.setModel(camList);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                viewport.setSize((int) (width * 0.5), height - 40);
                propertiesPanel.setLocation((int) (width * 0.75), 20);
                propertiesPanel.setSize((int) (width * 0.25) - 20, height - 40);
            }
        });

        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void updatePropertiesPanel(ModelInScene model) {
        locationXField.setText(String.valueOf(model.getPosition().x));
        locationYField.setText(String.valueOf(model.getPosition().y));
        locationZField.setText(String.valueOf(model.getPosition().z));

        rotationXField.setText(String.valueOf(model.getRotation().x));
        rotationYField.setText(String.valueOf(model.getRotation().y));
        rotationZField.setText(String.valueOf(model.getRotation().z));

        scaleXField.setText(String.valueOf(model.getScale().x));
        scaleYField.setText(String.valueOf(model.getScale().y));
        scaleZField.setText(String.valueOf(model.getScale().z));

        nameField.setText(model.getModelName());
        textureNameLabel.setText("Название текстуры: " + (model.getPath() == null ? "" : model.getPath().toString()));
    }

    private void clearPropertiesPanel() {
        locationXField.setText("");
        locationYField.setText("");
        locationZField.setText("");

        rotationXField.setText("");
        rotationYField.setText("");
        rotationZField.setText("");

        scaleXField.setText("");
        scaleYField.setText("");
        scaleZField.setText("");

        nameField.setText("");
    }

    private void updateColorComboBoxStatus() {
        // Disable the ComboBox if no texture is selected
        colorComboBox.setEnabled(textureFileInfo == null); // Enable the ComboBox if a texture is selected
    }

    private Color getSelectedColorFromComboBox() {
        return switch (colorComboBox.getSelectedIndex()) {
            case 0 -> Color.RED;
            case 1 -> Color.GREEN;
            case 2 -> Color.BLUE;
            default -> Color.BLACK;
        };
    }


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

