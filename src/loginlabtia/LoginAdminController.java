/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginlabtia;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    
    @FXML
    Label pesan;
    
    @FXML
    Button kirim;
    
    @FXML
    TextField password_admin;
    
    @FXML
    public void kirim_action(ActionEvent event){
        if ("tiatiatia".equalsIgnoreCase(password_admin.getText())) {
            System.exit(0);
        }else{
            pesan.setText("Password salah!");
            pesan.setTextFill(Color.RED);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}