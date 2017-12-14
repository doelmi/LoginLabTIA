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
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    Button tutup, lewati, login, daftar, go;

    @FXML
    TextField username, password, no_pc, address;

    @FXML
    Label pesan;

    String alamat = "labtia.trunojoyo.ac.id";
    WebEngine engine;

    @FXML
    public void tutup_action(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();

        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("RunningApp.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Login Sebagai");
        stage.getIcons().add(new Image("/loginlabtia/img/logo.png"));
        stage.resizableProperty().setValue(Boolean.FALSE);

        Platform.setImplicitExit(false);

        stage.setOnCloseRequest((event1) -> {
            System.out.println("keluar");
            event1.consume();
        });

        stage.show();
    }

    @FXML
    public void tes_koneksi_action(ActionEvent event) {
        try {
            if (!model.isConnected()) {
                pesan.setText("Koneksi database bermasalah.");
                pesan.setTextFill(Color.RED);
            } else {
                pesan.setText("Database terkoneksi.");
                pesan.setTextFill(Color.GREEN);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
        stage.initOwner(daftar.getScene().getWindow());
        stage.showAndWait();
    }

    @FXML
    public void login_action(ActionEvent event) throws SQLException {
        if (!model.isConnected()) {
            pesan.setText("Koneksi database bermasalah.");
            pesan.setTextFill(Color.RED);
        } else {
            if (model.login(username.getText(), password.getText(), no_pc.getText())) {
                if ("1".equals(model.getValidasi())) {
                    model.insertLogData(username.getText(), no_pc.getText());
                    pesan.setText(model.Keterangan(model.getValidasi()));
                    pesan.setTextFill(Color.GREEN);
                    tutup.setVisible(true);
                    username_ = username.getText();
                    model.updateStatusLogin(username.getText(), 1);
                } else {
                    pesan.setText(model.Keterangan(model.getValidasi()));
                    pesan.setTextFill(Color.RED);
                    tutup.setVisible(false);
                }
            } else {
                pesan.setText("Username atau Password salah.");
                pesan.setTextFill(Color.RED);
                tutup.setVisible(false);
            }
        }
    }

    @FXML
    public void go_action(ActionEvent event) throws SQLException {
        alamat = "http://" + address.getText();
        engine.load(alamat);
    }

    @FXML
    public void shutdown_action(MouseEvent event) throws IOException {
        Runtime.getRuntime().exec("shutdown -s -t 0");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        engine = webview.getEngine();
        engine.load("http://" + alamat);
        address.setText(alamat);
        tutup.setVisible(false);

        no_pc.setText(model.getPCName());
        no_pc.setEditable(false);
    }

}
