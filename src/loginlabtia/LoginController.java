/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginlabtia;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author LabTIA-40
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Model model = new Model();

    public static String username_;

    @FXML
    WebView webview;

    @FXML
    Button lewati, login, daftar, go;

    @FXML
    TextField username, password, no_pc, address;

    @FXML
    Text pesan;

    @FXML
    Label detikan;

    String alamat = "labtia.trunojoyo.ac.id";
    WebEngine engine;

    @FXML
    public void tes_koneksi_action(ActionEvent event) {
        try {
            if (!model.isConnected()) {
                pesan.setText("Koneksi database bermasalah.");
                pesan.setFill(Color.RED);
            } else {
                pesan.setText("Database terkoneksi.");
                pesan.setFill(Color.GREEN);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Stage stageRunApp;

    public void runRunningApp() throws IOException {
        Parent root;
        stageRunApp = new Stage();
        root = FXMLLoader.load(getClass().getResource("RunningApp.fxml"));
        stageRunApp.setScene(new Scene(root));
        stageRunApp.setTitle("Login Sebagai");
        stageRunApp.getIcons().add(new Image("/loginlabtia/img/logo.png"));
        stageRunApp.resizableProperty().setValue(Boolean.FALSE);

        Platform.setImplicitExit(false);

        stageRunApp.setOnCloseRequest((event1) -> {
            stageRunApp.setIconified(true);
            event1.consume();
        });

        stageRunApp.show();
    }

    public void login(ActionEvent event) throws SQLException, IOException {
        if (!model.isConnected()) {
            pesan.setText("Koneksi database bermasalah.");
            pesan.setFill(Color.RED);
        } else {
            if (model.login(username.getText(), password.getText(), no_pc.getText())) {
                if ("1".equals(model.getValidasi())) {
                    if (!model.isLoggedIn(username.getText())) {
                        model.insertLogData(username.getText(), no_pc.getText());
                        pesan.setText(model.Keterangan(model.getValidasi()));
                        pesan.setFill(Color.GREEN);
                        username_ = username.getText();
                        model.updateStatusLogin(username.getText(), 1);

                        ((Node) event.getSource()).getScene().getWindow().hide();

                        runRunningApp();
                    } else {
                        pesan.setText("Akun ini sedang digunakan. Silakan hubungi Admin jika ini adalah kesalahan.");
                        pesan.setFill(Color.RED);
                    }

                } else {
                    pesan.setText(model.Keterangan(model.getValidasi()));
                    pesan.setFill(Color.RED);
                }
            } else {
                pesan.setText("Username atau Password salah.");
                pesan.setFill(Color.RED);
            }
        }
    }

    public void login(KeyEvent event) throws SQLException, IOException {
        if (!model.isConnected()) {
            pesan.setText("Koneksi database bermasalah.");
            pesan.setFill(Color.RED);
        } else {
            if (model.login(username.getText(), password.getText(), no_pc.getText())) {
                if ("1".equals(model.getValidasi())) {
                    if (!model.isLoggedIn(username.getText())) {
                        model.insertLogData(username.getText(), no_pc.getText());
                        pesan.setText(model.Keterangan(model.getValidasi()));
                        pesan.setFill(Color.GREEN);
                        username_ = username.getText();
                        model.updateStatusLogin(username.getText(), 1);

                        ((Node) event.getSource()).getScene().getWindow().hide();

                        runRunningApp();
                    } else {
                        pesan.setText("Akun ini sedang digunakan. Silakan hubungi Admin jika ini adalah kesalahan.");
                        pesan.setFill(Color.RED);
                    }

                } else {
                    pesan.setText(model.Keterangan(model.getValidasi()));
                    pesan.setFill(Color.RED);
                }
            } else {
                pesan.setText("Username atau Password salah.");
                pesan.setFill(Color.RED);
            }
        }
    }

    @FXML
    public void lewati_action(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("LoginAdmin.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Login Admin");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.initOwner(lewati.getScene().getWindow());
        stage.showAndWait();
    }

    @FXML
    public void daftar_action(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Register");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.initOwner(daftar.getScene().getWindow());
        stage.showAndWait();
    }

    @FXML
    public void login_action(ActionEvent event) throws SQLException, IOException {
        login(event);
    }

    @FXML
    public void go_action(ActionEvent event) throws SQLException {
        alamat = "http://" + address.getText();
        engine.load(alamat);
    }

    @FXML
    public void field_onKeyPressed(KeyEvent key) throws SQLException {
        if (key.getCode().equals(KeyCode.ENTER)) {
            try {
                login(key);
            } catch (SQLException | IOException ex) {
                System.out.println(ex);
            }
        }
    }

    @FXML
    public void shutdown_action(MouseEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("ShutdownConfirm.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Konfirmasi Shtudown");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.initOwner(daftar.getScene().getWindow());
        stage.showAndWait();
    }
    int detik = 10;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        engine = webview.getEngine();
        engine.load("http://" + alamat);
        address.setText(alamat);

        no_pc.setText(model.getPCName());
        no_pc.setEditable(false);

        Timeline timeline = new Timeline();

        KeyFrame kf = new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            detikan.setText(detik + " detik");
            detik--;
            if (detik <= 0) {
                detik = 0;
                try {
                    Runtime.getRuntime().exec("shutdown -s -t 3");
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.playFromStart();

    }

}
