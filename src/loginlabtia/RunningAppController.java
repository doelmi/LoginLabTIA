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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
public class RunningAppController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Model model = new Model();
    
//    @FXML
//    WebView chat_pane;
    
    @FXML
    Label username, pesan, waktu;

    @FXML
    Tooltip username_tooltip;

    @FXML
    public void logout_action(ActionEvent event) throws SQLException, IOException {
        if (!model.isConnected()) {
            pesan.setText("Koneksi database bermasalah.");
            pesan.setTextFill(Color.RED);
        } else {
            pesan.setText("Database terkoneksi.");
            pesan.setTextFill(Color.GREEN);

            model.updateStatusLogin(username.getText(), 0);
            ((Node) event.getSource()).getScene().getWindow().hide();
            
            timeline.stop();
            ceklogin.stop();

            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            Stage stage;
            stage = new Stage();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setAlwaysOnTop(true);
            stage.alwaysOnTopProperty();
            stage.getIcons().add(new Image("/loginlabtia/img/logo.png"));

            Platform.setImplicitExit(false);

            stage.setOnCloseRequest((event1) -> {
                System.out.println("keluar");
                event1.consume();
            });

            stage.show();
        }
    }
    int detik = 1, menit = 0, jam = 0;
    Timeline timeline = new Timeline();
    Timeline ceklogin = new Timeline();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        WebEngine engine = chat_pane.getEngine();
//        engine.load("http:"+model.IP_SERVER+":4000?handle="+LoginController.username_);
//        
        // TODO
        username.setText(LoginController.username_);

        try {
            username_tooltip.setText(model.getNamaNIM(LoginController.username_));
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        KeyFrame kf = new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            waktu.setText(String.format("%02d", jam) + ":" + String.format("%02d", menit) + ":" + String.format("%02d", detik));
            detik++;
            if (detik > 59) {
                detik = 0;
                menit++;
            }
            if (menit > 59) {
                menit = 0;
                jam++;
            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.playFromStart();

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(60), (ActionEvent event) -> {
            try {
                if (!model.isLoggedIn(username.getText())) {
                    System.out.println("masuk if");
                    LoginController.stageRunApp.close();
                    ceklogin.stop();
//                    ((Node) event.getSource()).getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage;
                    stage = new Stage();
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setAlwaysOnTop(true);
                    stage.alwaysOnTopProperty();
                    stage.setTitle("Aplikasi Login TIA+");
                    stage.getIcons().add(new Image("/loginlabtia/img/logo.png"));

                    Platform.setImplicitExit(false);

                    stage.setOnCloseRequest((event1) -> {
                        System.out.println("keluar");
                        event1.consume();
                    });

                    stage.show();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Kamu telah logout.");
                    alert.setContentText("Admin telah mengeluarkanmu, karena kamu terindikasi menggunakan akun orang lain. Jika ini adalah kesalahan, coba untuk Login kembali atau hubungi Admin.");
                    alert.setTitle("Akun telah di-logout");
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.initOwner(stage);
                    alert.show();

                } else {
                    System.out.println("masuk sini!");
                }
            } catch (SQLException | IOException ex) {
                System.out.println(ex);
            }
        });
        ceklogin.getKeyFrames().add(keyFrame);
        ceklogin.setCycleCount(Timeline.INDEFINITE);
        ceklogin.playFromStart();
    }

}
