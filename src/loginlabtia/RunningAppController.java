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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author LabTIA-40
 */
public class RunningAppController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label username;

    @FXML
    public void logout_action(ActionEvent event) throws SQLException, IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();

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
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        username.setText(LoginController.username_);
    }

}
