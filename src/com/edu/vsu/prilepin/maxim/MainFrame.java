package com.edu.vsu.prilepin.maxim;

import com.edu.vsu.khanin.dmitrii.exceptions.TooLowVerticesException;
import com.edu.vsu.khanin.dmitrii.preparation.PrepareModel;
import com.edu.vsu.kretov.daniil.mathLib4Task.vector.Vector3f;
import com.edu.vsu.khanin.dmitrii.render_engine.Camera;
import com.edu.vsu.khanin.dmitrii.render_engine.RenderEngine;
import com.edu.vsu.khanin.dmitrii.render_engine.Viewport;
import com.edu.vsu.prilepin.maxim.model.Model;
import com.edu.vsu.prilepin.maxim.model.ModelInScene;
import com.edu.vsu.prilepin.maxim.obj.ObjReader;
import com.edu.vsu.prilepin.maxim.obj.ObjWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.awt.Color;
import com.edu.vsu.prilepin.maxim.JFrameCirc.*;

import static com.edu.vsu.kretov.daniil.mathLib4Task.AffineTransforms.AffineTransformations.*;

public class MainFrame extends JFrame {
    private final Viewport viewport;
    private final JPanel propertiesPanel;
    private final DefaultListModel<String> modelList;
    private final JList<String> jModelList;
    private final ArrayList<ModelInScene> sceneModels;
    private ModelInScene selectedModel;
    private final JList<String> jCamList;
    private final DefaultListModel<String> camList;
    private final ArrayList<Camera> cameras;
    private Camera selectedCamera;
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
    private final JTextField redField;
    private final JTextField greenField;
    private final JTextField blueField;
    private final JLabel textureNameLabel; // Add a JLabel to display the texture name
    private FileInfo textureFileInfo; // Create a new class to hold file information
    private CameraState cameraState = CameraState.MOVE_CAMERA;
    private final JCheckBox contourCheckBox;
    private final JCheckBox colorCheckBox;
    private final JCheckBox textureCheckBox;
    private final JCheckBox lightCheckBox;
    private RenderEngine.RenderState renderState = RenderEngine.RenderState.CONTOUR;

    public MainFrame() {
        setTitle("3D Рендеринг");
        setLayout(null);

        sceneModels = new ArrayList<>();
        cameras = new ArrayList<>();

        modelList = new DefaultListModel<>();
        camList = new DefaultListModel<>();

        jModelList = new JList<>(modelList);
        jModelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane modelListScrollPane = new JScrollPane(jModelList);
        modelListScrollPane.setBounds(20, 140, 150, 300);
        add(modelListScrollPane);

        jCamList = new JList<>();
        jCamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane camListScrollPane = new JScrollPane(jCamList);
        camListScrollPane.setBounds(20, 540, 150, 300);
        add(camListScrollPane);

        textureNameLabel = new JLabel("Название текстуры:");
        textureNameLabel.setBounds(120, 440, 300, 20);

        JButton loadTextureButton = new JButton("Загрузить текстуру");
        JButton saveButton = new JButton("Сохранить");
        JButton loadButton = new JButton("Загрузить модель");
        JButton deleteButton = new JButton("Удалить модель");
        JFrame frame = new JFrame("Model Loader");
        JButton createCameraButton = new JButton("Добавить камеру");
        JButton deleteCameraButton = new JButton("Удалить камеру");
        JButton saveModelButton = new JButton("Сохранить модель");
        JButton createColorButton = new JButton("Закрасить");
        CircleButton helpButton = new CircleButton("?");

        redField = new JTextField();
        greenField = new JTextField();
        blueField = new JTextField();

        JLabel cameraLabel = new JLabel("Камера:");
        cameraLabel.setBounds(200, 0, 50, 20);
        add(cameraLabel);
        ButtonGroup cameraButtonGroup = new ButtonGroup();

        JRadioButton moveRadioButton = new JRadioButton("Двигать");
        JRadioButton rotateRadioButton = new JRadioButton("Вращать");

        moveRadioButton.addActionListener(e -> cameraState = CameraState.MOVE_CAMERA);
        rotateRadioButton.addActionListener(e -> cameraState = CameraState.ROTATE_CAMERA);

        moveRadioButton.setBounds(250, 0, 100, 20);
        rotateRadioButton.setBounds(350, 0, 100, 20);

        cameraButtonGroup.add(moveRadioButton);
        cameraButtonGroup.add(rotateRadioButton);
        moveRadioButton.setSelected(true);

        add(moveRadioButton);
        add(rotateRadioButton);

        JLabel renderLabel = new JLabel("Рендер:");
        renderLabel.setBounds(480, 0, 50, 20);
        add(renderLabel);

        contourCheckBox = new JCheckBox("Контур");
        colorCheckBox = new JCheckBox("Цвет");
        textureCheckBox = new JCheckBox("Текстура");
        lightCheckBox = new JCheckBox("Свет");

        contourCheckBox.addActionListener(e -> {
            calcRenderState();
            render();
        });
        colorCheckBox.addActionListener(e -> {
            calcRenderState();
            render();
        });
        textureCheckBox.addActionListener(e -> {
            calcRenderState();
            render();
        });
        lightCheckBox.addActionListener(e -> {
            calcRenderState();
            render();
        });

        contourCheckBox.setBounds(540, 0, 100, 20);
        colorCheckBox.setBounds(640, 0, 100, 20);
        textureCheckBox.setBounds(740, 0, 100, 20);
        lightCheckBox.setBounds(840, 0, 100, 20);
        helpButton.setBounds(35, 40, 35, 35);
        helpButton.setBackground(Color.BLUE);
        helpButton.setForeground(Color.YELLOW);

        contourCheckBox.setSelected(true);

        add(contourCheckBox);
        add(colorCheckBox);
        add(textureCheckBox);
        add(lightCheckBox);

        loadTextureButton.setBounds(100, 680, 180, 30);
        saveButton.setBounds(100, 720, 180, 30);
        loadButton.setBounds(20, 20, 150, 30);
        deleteButton.setBounds(20, 60, 150, 30);
        createCameraButton.setBounds(20, 460, 150, 30);
        deleteCameraButton.setBounds(20, 500, 150, 30);
        saveModelButton.setBounds(20, 100, 150, 30);
        createColorButton.setBounds(100, 640, 180, 30);

        add(saveButton);
        add(loadButton);
        add(deleteButton);
        add(createCameraButton);
        add(deleteCameraButton);
        add(saveModelButton);

        viewport = new Viewport(this);
        viewport.setBounds(200, 20, 900, 700);
        viewport.setBorder(BorderFactory.createEtchedBorder());
        add(viewport);

        propertiesPanel = new JPanel();
        propertiesPanel.setBounds(620, 20, 150, 400);
        propertiesPanel.setBorder(BorderFactory.createTitledBorder("Свойства объекта"));
        propertiesPanel.setLayout(null);
        propertiesPanel.add(loadTextureButton);
        propertiesPanel.add(saveButton);
        propertiesPanel.add(textureNameLabel);
        propertiesPanel.add(redField);
        propertiesPanel.add(greenField);
        propertiesPanel.add(blueField);
        propertiesPanel.add(createColorButton);
        propertiesPanel.add(helpButton);

        JLabel locationLabel = new JLabel("Позиция:");
        JLabel locationLabelX = new JLabel("X");
        JLabel locationLabelY = new JLabel("Y");
        JLabel locationLabelZ = new JLabel("Z");
        locationLabel.setBounds(150, 20, 100, 20);
        locationLabelX.setBounds(100, 50, 100, 20);
        locationLabelY.setBounds(100, 80, 100, 20);
        locationLabelZ.setBounds(100, 110, 100, 20);
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
        propertiesPanel.add(locationLabelX);
        propertiesPanel.add(locationLabelY);
        propertiesPanel.add(locationLabelZ);

        JLabel rotationLabel = new JLabel("Поворот:");
        JLabel rotationLabelX = new JLabel("X");
        JLabel rotationLabelY = new JLabel("Y");
        JLabel rotationLabelZ = new JLabel("Z");
        rotationLabel.setBounds(150, 140, 100, 20);
        rotationLabelX.setBounds(100, 170, 100, 20);
        rotationLabelY.setBounds(100, 200, 100, 20);
        rotationLabelZ.setBounds(100, 230, 100, 20);
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
        propertiesPanel.add(rotationLabelX);
        propertiesPanel.add(rotationLabelY);
        propertiesPanel.add(rotationLabelZ);

        JLabel scaleLabel = new JLabel("Масштаб:");
        JLabel scaleLabelX = new JLabel("X");
        JLabel scaleLabelY = new JLabel("Y");
        JLabel scaleLabelZ = new JLabel("Z");
        scaleLabel.setBounds(150, 260, 100, 20);
        scaleLabelX.setBounds(100, 290, 100, 20);
        scaleLabelY.setBounds(100, 320, 100, 20);
        scaleLabelZ.setBounds(100, 350, 100, 20);
        scaleXField = new JTextField();
        scaleYField = new JTextField();
        scaleZField = new JTextField();
        scaleXField.setBounds(120, 290, 130, 25);
        scaleYField.setBounds(120, 320, 130, 25);
        scaleZField.setBounds(120, 350, 130, 25);

        JLabel colorLabel = new JLabel("Цвет:");
        JLabel colorLabelR = new JLabel("R");
        JLabel colorLabelG = new JLabel("G");
        JLabel colorLabelB = new JLabel("B");
        colorLabel.setBounds(150, 500, 100, 20);
        colorLabelR.setBounds(100, 530, 130, 20);
        colorLabelG.setBounds(100, 560, 130, 20);
        colorLabelB.setBounds(100, 590, 130, 20);
        redField.setBounds(120, 530, 130, 25);
        greenField.setBounds(120, 560, 130, 25);
        blueField.setBounds(120, 590, 130, 25);

        propertiesPanel.add(scaleLabel);
        propertiesPanel.add(scaleXField);
        propertiesPanel.add(scaleYField);
        propertiesPanel.add(scaleZField);
        propertiesPanel.add(scaleLabelX);
        propertiesPanel.add(scaleLabelY);
        propertiesPanel.add(scaleLabelZ);
        propertiesPanel.add(colorLabel);
        propertiesPanel.add(colorLabelR);
        propertiesPanel.add(colorLabelG);
        propertiesPanel.add(colorLabelB);

        JLabel nameLabel = new JLabel("Название:");
        nameLabel.setBounds(150, 380, 100, 20);
        nameField = new JTextField();
        nameField.setBounds(120, 410, 130, 25);
        propertiesPanel.add(nameLabel);
        propertiesPanel.add(nameField);

        add(propertiesPanel);
        addKeyListener(viewport);
        addMouseListener(viewport);
        addMouseWheelListener(viewport);


        saveButton.addActionListener(e -> {
            if (selectedModel != null) {
                selectedModel.setPosition(new Vector3f(Float.parseFloat(locationXField.getText()), Float.parseFloat(locationYField.getText()), Float.parseFloat(locationZField.getText())));
                selectedModel.setRotation(new Vector3f(Float.parseFloat(rotationXField.getText()), Float.parseFloat(rotationYField.getText()), Float.parseFloat(rotationZField.getText())));
                selectedModel.setScale(new Vector3f(Float.parseFloat(scaleXField.getText()), Float.parseFloat(scaleYField.getText()), Float.parseFloat(scaleZField.getText())));
                selectedModel.setModelName(nameField.getText());

                render();
/*                locationXField.setText("0.0");
                locationYField.setText("0.0");
                locationZField.setText("0.0");
                scaleXField.setText("1.0");
                scaleYField.setText("1.0");
                scaleZField.setText("1.0");

                rotationXField.setText("0.0");
                rotationYField.setText("0.0");
                rotationZField.setText("0.0");*/

                // Additional logic to update the selected model in the 3D scene
                System.out.println("Изменения сохранены для модели: " + selectedModel.getModelName());
            } else {
                JOptionPane.showMessageDialog(frame, "Выберите модель для сохранения изменений");
            }
        });

        createCameraButton.addActionListener(e -> {
            Camera camera = new Camera(
                    new Vector3f(100, 100, 100), new Vector3f(0, 0, 0),
                    1, 1, 0.1f, 1000
            );
            cameras.add(camera);
            String newCameraName = "Camera" + (camList.getSize() + 1); // Генерация нового имени камеры
            camList.addElement(newCameraName); // Добавление новой камеры в список
        });

        deleteCameraButton.addActionListener(e -> {
            int selectedIndex = jCamList.getSelectedIndex();
            if (selectedIndex != -1 && camList.size() > 1) {
                cameras.remove(selectedIndex);
                camList.remove(selectedIndex);
                selectedCamera = cameras.get(0);
            } else {
                JOptionPane.showMessageDialog(frame, "Нельзя удалить последнюю камеру");
            }
        });

        jCamList.addListSelectionListener(e -> {
            int selectedIndex = jCamList.getSelectedIndex();
            if (selectedIndex != -1) {
                selectedCamera = cameras.get(selectedIndex);
                render();
            }
        });

        createColorButton.addActionListener(e -> {
            if (selectedModel != null) {
                int red = Integer.parseInt(redField.getText());
                int green = Integer.parseInt(greenField.getText());
                int blue = Integer.parseInt(blueField.getText());

                selectedModel.setColor(new Color(red, green, blue));

                render();

                System.out.println("Model filled with color: " + selectedModel.getColor());
            } else {
                JOptionPane.showMessageDialog(frame, "Выберите модель для закраски");
            }
        });
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, """
                        W - движение камеры вперёд
                        A - движение камеры влево
                        S - движение камеры назад
                        D - движение камеры вправо
                        Q - движение камеры вниз
                        E - движение камеры вверх

                        I, J, K, L - вращение камеры вокруг разных осей

                        Колесо мыши - изменение масштаба камеры

                        """);
            }
        });

        loadTextureButton.addActionListener(e -> {
            if (selectedModel != null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Выберите текстурный файл");
                int userSelection = fileChooser.showOpenDialog(frame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textureFileInfo = new FileInfo(selectedFile); // Store the file information
                    textureNameLabel.setText("Название текстуры: " + textureFileInfo.getFileName()); // Update the texture name label
                    selectedModel.setTexture(Path.of(selectedFile.getAbsolutePath()));

                    render();

                    System.out.println("Текстура загружена для модели: " + selectedModel.getModelName());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Выберите модель для загрузки текстуры");
            }
        });


        saveModelButton.addActionListener(e -> {
            if (selectedModel != null) {
                // Открыть окошко для выбора места сохранения модели
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Выберите место для сохранения модели");
                int userSelection = fileChooser.showSaveDialog(frame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();

                    // Вызвать функцию MakeInWorldCoord для модели и сохранить модель в формате OBJ
                    Model transformedModel = makeInWorldCoord(selectedModel);
                    ObjWriter.writeModelToObjFile(filePath, transformedModel);

                    System.out.println("Модель успешно сохранена в файл: " + filePath);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Выберите модель для сохранения в файл");
            }
        });

        loadButton.addActionListener(e -> {
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

                        render();
                    } catch (TooLowVerticesException | IOException ex) {
                        JOptionPane.showMessageDialog(propertiesPanel, "Произошла ошибка, выберите другой .obj файл");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Пожалуйста, выберите .obj файл");
                }
            }
        });

        jModelList.addListSelectionListener(e -> {
            int selectedIndex = jModelList.getSelectedIndex();
            if (selectedIndex != -1) {
                selectedModel = sceneModels.get(selectedIndex);
                updatePropertiesPanel(selectedModel);
            }
        });

        deleteButton.addActionListener(e -> {
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

                render();

                System.out.println("Удалена модель: " + selectedModelName);
            }
        });

        camList.addElement("Camera1");
        jCamList.setModel(camList);

        selectedCamera = new Camera(
                new Vector3f(100, 100, 100), new Vector3f(0, 0, 0),
                1, 1, 0.1f, 1000
        );
        cameras.add(selectedCamera);

        camList.addElement("Camera2");
        camList.addElement("Camera3");
        camList.addElement("Camera4");
        cameras.add(new Camera(
                new Vector3f(-100, 100, 100), new Vector3f(0, 0, 0),
                1, 1, 0.1f, 1000
        ));
        cameras.add(new Camera(
                new Vector3f(-100, 100, -100), new Vector3f(0, 0, 0),
                1, 1, 0.1f, 1000
        ));
        cameras.add(new Camera(
                new Vector3f(100, 100, -100), new Vector3f(0, 0, 0),
                1, 1, 0.1f, 1000
        ));

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                //viewport.setSize((int) (width * 0.5), height - 40);
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

    public Camera getSelectedCamera() {
        return selectedCamera;
    }

    public CameraState getCameraState() {
        return cameraState;
    }

    public RenderEngine.RenderState getRenderState() {
        return renderState;
    }

    public void render() {
        RenderEngine.render(viewport, selectedCamera, sceneModels, renderState);
    }

    private void calcRenderState() {
        if (colorCheckBox.isSelected() && textureCheckBox.isSelected()
                || !contourCheckBox.isSelected() && !colorCheckBox.isSelected() && !textureCheckBox.isSelected())
            renderState = RenderEngine.RenderState.NONE;
        else if (contourCheckBox.isSelected() && colorCheckBox.isSelected() && lightCheckBox.isSelected())
            renderState = RenderEngine.RenderState.LIGHT_COLOR_CONTOUR;
        else if (contourCheckBox.isSelected() && textureCheckBox.isSelected() && lightCheckBox.isSelected())
            renderState = RenderEngine.RenderState.LIGHT_TEXTURE_CONTOUR;
        else if (contourCheckBox.isSelected() && colorCheckBox.isSelected())
            renderState = RenderEngine.RenderState.COLOR_CONTOUR;
        else if (contourCheckBox.isSelected() && textureCheckBox.isSelected())
            renderState = RenderEngine.RenderState.TEXTURE_CONTOUR;
        else if (colorCheckBox.isSelected() && lightCheckBox.isSelected())
            renderState = RenderEngine.RenderState.LIGHT_COLOR;
        else if (textureCheckBox.isSelected() && lightCheckBox.isSelected())
            renderState = RenderEngine.RenderState.LIGHT_TEXTURE;
        else if (contourCheckBox.isSelected())
            renderState = RenderEngine.RenderState.CONTOUR;
        else if (colorCheckBox.isSelected())
            renderState = RenderEngine.RenderState.COLOR;
        else renderState = RenderEngine.RenderState.TEXTURE;
    }

    public enum CameraState {
        MOVE_CAMERA,
        ROTATE_CAMERA
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

    class FileInfo {
        private final File file;

        public FileInfo(File file) {
            this.file = file;
        }

        public String getFileName() {
            return file.getName();
        }
    }
}

