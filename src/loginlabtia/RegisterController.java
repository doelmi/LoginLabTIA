/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginlabtia;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author LabTIA-40
 */
public class RegisterController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Model model = new Model();

    @FXML
    TextField username, password, confirm_password, nama, nim;

    @FXML
    Label pesan;

    @FXML
    public void register_action(ActionEvent event) throws SQLException {
        if (!model.isConnected()) {
            pesan.setText("Koneksi database bermasalah.");
            pesan.setTextFill(Color.RED);
        } else {
            if (username.getText().length() == 0) {
                pesan.setText("Username harus diisi.");
                pesan.setTextFill(Color.RED);
            } else if (model.cekUsername(username.getText())) {
                pesan.setText("Username telah terdaftar.");
                pesan.setTextFill(Color.RED);
            } else if (password.getText().length() == 0) {
                pesan.setText("Password harus diisi.");
                pesan.setTextFill(Color.RED);
            } else if (!password.getText().equals(confirm_password.getText())) {
                pesan.setText("Password dan Confirm Password tidak sama.");
                pesan.setTextFill(Color.RED);
            } else if (nama.getText().length() == 0) {
                pesan.setText("Nama harus diisi.");
                pesan.setTextFill(Color.RED);
            } else if (nim.getText().length() == 0) {
                pesan.setText("NIM harus diisi.");
                pesan.setTextFill(Color.RED);
            } else if (model.cekNIM(nim.getText())) {
                pesan.setText("NIM telah terdaftar.");
                pesan.setTextFill(Color.RED);
            } else {
                if (model.register(username.getText(), password.getText(), nama.getText(), nim.getText())) {
                    pesan.setText("Berhasil Daftar! Minta Admin untuk Validasi.");
                    pesan.setTextFill(Color.GREEN);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Akun kamu telah terdaftar.");
                    alert.setContentText("Akun kamu telah berhasil dibuat. Tahap selanjutnya adalah mengaktifkan akun kamu. Segera hubungi Admin untuk validasi akun kamu.");
                    alert.setTitle("Akun terdaftar");
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.initOwner(pesan.getScene().getWindow());
                    alert.showAndWait();
                    ((Node) event.getSource()).getScene().getWindow().hide();
                } else {
                    pesan.setText("Gagal Daftar. Ada yang salah.");
                    pesan.setTextFill(Color.RED);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
