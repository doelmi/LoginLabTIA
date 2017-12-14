/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginlabtia;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author LabTIA-40
 */
public class ShutdownConfirmController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public void shutdown_action(ActionEvent event) throws IOException {
        Runtime.getRuntime().exec("shutdown -s -t 3");
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
    @FXML
    public void batal_action(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
