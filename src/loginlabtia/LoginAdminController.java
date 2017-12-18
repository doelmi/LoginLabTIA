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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author LabTIA-40
 */
public class LoginAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Model model = new Model();

    @FXML
    Label pesan;

    @FXML
    Button kirim;

    @FXML
    TextField password_admin;

    public void login_admin() {
        if (("tiaifutm" + model.getPCName()).equalsIgnoreCase(password_admin.getText())) {
            System.exit(0);
        } else {
            pesan.setText("Password salah!");
            pesan.setTextFill(Color.RED);
        }
    }

    @FXML
    public void kirim_action(ActionEvent event) {
        login_admin();
    }

    @FXML
    public void password_admin_onKeyPress(KeyEvent key) {
        if (key.getCode().equals(KeyCode.ENTER)) {
            login_admin();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
