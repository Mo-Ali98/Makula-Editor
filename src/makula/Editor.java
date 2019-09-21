package makula;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Editor implements Initializable {
    private MakImage image;
    @FXML
    protected ImageView displayImg;
    @FXML
    protected MenuItem undoButton;
    @FXML
    protected MenuItem importButton;
    @FXML
    protected MenuItem saveButton;
    @FXML
    protected Button cropButton;
    @FXML
    protected Button scaleButton;
    @FXML
    protected Button rotateButton;
    @FXML
    protected Button sharpenButton;
    @FXML
    protected Button blurButton;
    @FXML
    protected Button exposureButton;
    @FXML
    protected Button warmButton;
    @FXML
    protected Button gammaButton;
    @FXML
    protected Label dataLabel;

    public void renderImage(MakImage img) {
        displayImg.setImage(img.getDisplayImg());
        dataLabel.setText("Width: " + img.getWidth() + "px  Height: " + img.getHeight() + "px");
    }

    public void importImage() {

        if (image != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("The current image will be lost.");
            alert.setContentText("Save before continuing.");
            alert.showAndWait();
        }

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                String ext = file.getPath().substring(file.getPath().lastIndexOf(".")).toUpperCase();
                if (ext.equals(".BMP") || ext.equals(".JPEG") || ext.equals(".JPG") || ext.equals(".GIF") || ext.equals(".PNG")) {
                    image = new MakImage(file.getAbsolutePath());
                    renderImage(image);
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText(ext.toLowerCase() + " is not an accepted file extension.");
                    errorAlert.setContentText("Accepted extensions are .bpm, .jpeg, .jpg, .gif, .png.");
                    errorAlert.showAndWait();
                }
            } catch (Exception e) {
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("File does not exist");
            errorAlert.showAndWait();
        }
    }

    public void exportImage() {

        if (image != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("png", "*.png"),
                    new FileChooser.ExtensionFilter("jpg", "*.jpg"),
                    new FileChooser.ExtensionFilter("gif", "*.gif"),
                    new FileChooser.ExtensionFilter("bmp", "*.bmp")
            );

            //Show save file dialog
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                try {
                    ImageIO.write(image.getBuffImg(), fileChooser.getSelectedExtensionFilter().getDescription(), file);
                    Alert confirmAlert = new Alert(Alert.AlertType.INFORMATION);
                    confirmAlert.setHeaderText("Image Saved");
                    confirmAlert.setContentText(file.getPath());
                    confirmAlert.showAndWait();
                } catch (Exception ex) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Image could not be saved");
                    errorAlert.setContentText(ex.getMessage());
                    errorAlert.showAndWait();
                }
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Image could not be saved");
            errorAlert.setContentText("Import an image first");
            errorAlert.showAndWait();
        }
    }

    // GUI
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        undoButton.setOnAction((event) -> {
            if (image != null) {
                image.undo();
                renderImage(image);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Cannot undo");
                alert.setContentText("Import an image, then try again.");
                alert.showAndWait();
            }
        });

        importButton.setOnAction((event) -> {
            importImage();
        });

        saveButton.setOnAction((event) -> {
            if (image != null) {
                exportImage();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Image could not be saved");
                errorAlert.setContentText("Import an image first");
                errorAlert.showAndWait();
            }
        });

        cropButton.setOnAction((event) -> {

            if (image != null) {

                Stage window = new Stage();
                window.setTitle("Crop");

                Label x = new Label("X:");
                TextField xInput = new TextField();

                Label w = new Label("Width: ");
                TextField wInput = new TextField();
                HBox hboxXW = new HBox(x, xInput, w, wInput);
                hboxXW.setAlignment(Pos.CENTER);
                hboxXW.setSpacing(10);

                Label y = new Label("Y:");
                TextField yInput = new TextField();

                Label h = new Label("Height:");
                TextField hInput = new TextField();
                HBox hboxYH = new HBox(y, yInput, h, hInput);
                hboxYH.setAlignment(Pos.CENTER);
                hboxYH.setSpacing(10);

                Button okButton = new Button("Ok");
                Button cancelButton = new Button("Cancel");
                HBox hboxB = new HBox(okButton, cancelButton);
                hboxB.setSpacing(10);

                VBox layout = new VBox(10);
                layout.setPadding(new Insets(20, 20, 20, 20));
                layout.getChildren().addAll(hboxXW, hboxYH, hboxB);

                Scene scene = new Scene(layout, 420, 150);
                window.setScene(scene);
                window.show();

                okButton.setOnAction(o -> {
                    try {
                        int xCord = Integer.parseInt(xInput.getText());
                        int yCord = Integer.parseInt(yInput.getText());
                        int width = Integer.parseInt(wInput.getText());
                        int height = Integer.parseInt(hInput.getText());
                        Crop c = new Crop(image, xCord, yCord, width, height);
                        c.apply();
                        renderImage(image);
                    } catch (Exception e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Cannot crop");
                        errorAlert.setContentText(e.getMessage());
                        errorAlert.showAndWait();
                    }
                    window.close();
                });

                cancelButton.setOnAction(c -> {
                    window.close();
                });

            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Cannot crop");
                errorAlert.setContentText("Import an image and then try again.");
                errorAlert.showAndWait();
            }
        });

        scaleButton.setOnAction((event) -> {

            if (image != null) {

                Stage window = new Stage();
                window.setTitle("Scale");

                Label s = new Label("Enter amount:");
                s.setFont(new Font(20));

                Slider slider = new Slider();
                slider.setMin(0);
                slider.setMax(100);
                slider.setValue(0);
                slider.setShowTickLabels(true);
                slider.setShowTickMarks(false);
                slider.setMajorTickUnit(1);
                slider.setMinorTickCount(1);
                slider.setBlockIncrement(1);

                Button apply = new Button("Apply");
                Button cancel = new Button("Cancel");

                HBox buttons = new HBox(10);
                buttons.setPadding(new Insets(20, 20, 20, 20));
                buttons.getChildren().addAll(apply, cancel);

                VBox layout = new VBox(10);
                layout.setPadding(new Insets(20, 20, 20, 20));
                layout.getChildren().addAll(s, slider, buttons);

                Scene scene = new Scene(layout, 220, 150);
                window.setScene(scene);
                window.show();

                Scale scale = new Scale(image, 1);
                slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    scale.setAmount((int) slider.getValue());
                });

                apply.setOnAction((event1) -> {
                    scale.apply();
                    window.close();
                    renderImage(image);
                });

                cancel.setOnAction(c -> {
                    window.close();
                });

            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Cannot scale");
                errorAlert.setContentText("Import an image and then try again.");
                errorAlert.showAndWait();
            }
        });

        rotateButton.setOnAction((event) -> {
            try {
                Rotate r = new Rotate(image);
                r.apply();
                renderImage(image);
            } catch (NullPointerException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Cannot rotate");
                errorAlert.setContentText("Import an image and try again.");
                errorAlert.showAndWait();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Cannot rotate");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        });

        sharpenButton.setOnAction((event) -> {
            try {
                Sharpen sharpen = new Sharpen(image, 1);
                sharpen.apply();
                renderImage(image);
            } catch (NullPointerException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Cannot sharpen");
                errorAlert.setContentText("Import an image and try again.");
                errorAlert.showAndWait();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Cannot sharpen");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        });

        blurButton.setOnAction((event) -> {
            try {
                Blur b = new Blur(image, 1);
                b.apply();
                renderImage(image);
            } catch (NullPointerException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Cannot blur image");
                errorAlert.setContentText("Import an image and try again.");
                errorAlert.showAndWait();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Cannot blur image");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        });

        exposureButton.setOnAction((event) -> {

            if (image != null) {
                try {
                    Stage window = new Stage();
                    window.setTitle("Exposure");

                    Label s = new Label("Enter amount:");
                    s.setFont(new Font(20));

                    Slider slider = new Slider();
                    slider.setMin(-100);
                    slider.setMax(100);
                    slider.setValue(0);
                    slider.setShowTickLabels(true);
                    slider.setShowTickMarks(false);
                    slider.setMajorTickUnit(25);
                    slider.setMinorTickCount(5);
                    slider.setBlockIncrement(10);

                    Button apply = new Button("Apply");
                    Button cancel = new Button("Cancel");

                    HBox buttons = new HBox(10);
                    buttons.setPadding(new Insets(20, 20, 20, 20));
                    buttons.getChildren().addAll(apply, cancel);

                    VBox layout = new VBox(10);
                    layout.setPadding(new Insets(20, 20, 20, 20));
                    layout.getChildren().addAll(s, slider, buttons);

                    Scene scene = new Scene(layout, 220, 150);
                    window.setScene(scene);
                    window.show();

                    Exposure e = new Exposure(image, 1);
                    slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                        e.setLevel((int) slider.getValue());
                    });

                    apply.setOnAction((event1) -> {
                        e.apply();
                        window.close();
                        renderImage(image);
                    });

                    cancel.setOnAction((event1) -> {
                        window.close();
                    });

                } catch (NullPointerException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Cannot adjust exposure");
                    a.setContentText("Import an image and try again.");
                    a.showAndWait();
                } catch (Exception e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Cannot adjust exposure");
                    a.setContentText(e.getMessage());
                    a.showAndWait();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Cannot adjust exposure");
                a.setContentText("Import an image and try again.");
                a.showAndWait();
            }
        });

        warmButton.setOnAction((event) -> {
            try {
                Warm warm = new Warm(image, 1);
                warm.apply();
                renderImage(image);
            } catch (NullPointerException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Cannot warm image");
                errorAlert.setContentText("Import an image and try again.");
                errorAlert.showAndWait();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Cannot warm image");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }

        });

        gammaButton.setOnAction((event) -> {

            if (image != null) {
                try {
                    Stage window = new Stage();
                    window.setTitle("Gamma");

                    Label s = new Label("Enter amount:");
                    s.setFont(new Font(20));

                    Slider slider = new Slider();
                    slider.setMin(-2);
                    slider.setMax(2);
                    slider.setValue(0);
                    slider.setShowTickLabels(true);
                    slider.setShowTickMarks(false);
                    slider.setMajorTickUnit(1);
                    slider.setMinorTickCount(1);
                    slider.setBlockIncrement(0.1);

                    Button apply = new Button("Apply");
                    Button cancel = new Button("Cancel");

                    HBox buttons = new HBox(10);
                    buttons.setPadding(new Insets(20, 20, 20, 20));
                    buttons.getChildren().addAll(apply, cancel);

                    VBox layout = new VBox(10);
                    layout.setPadding(new Insets(20, 20, 20, 20));
                    layout.getChildren().addAll(s, slider, buttons);

                    Scene scene = new Scene(layout, 220, 150);
                    window.setScene(scene);
                    window.show();

                    GammaCorrection g = new GammaCorrection(image, 1);
                    slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                        g.setLevel(slider.getValue());
                    });

                    apply.setOnAction((event1) -> {
                        g.apply();
                        window.close();
                        renderImage(image);
                    });

                    cancel.setOnAction((event1) -> {
                        window.close();
                    });

                } catch (NullPointerException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Cannot adjust gamma");
                    a.setContentText("Import an image and try again.");
                    a.showAndWait();
                } catch (Exception e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Cannot adjust gamma");
                    a.setContentText(e.getMessage());
                    a.showAndWait();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Cannot adjust gamma");
                a.setContentText("Import an image and try again.");
                a.showAndWait();
            }
        });
    }
}