package controller;

import classe.Employe;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.EmployeService;

public class LoginController implements Initializable {

    @FXML private JFXTextField emailField;
    @FXML private JFXPasswordField pass;

    @FXML
    private void loginEmploye(ActionEvent event) throws IOException {

        EmployeService es = new EmployeService();
        Employe emp = es.findByEmail(emailField.getText(),pass.getText());
        if (emp != null) {
            Preferences userconfig = Preferences.userRoot();
            userconfig.put("nomEtab", emp.getEtablissement().getNom());
            userconfig.putInt("idEtab", emp.getEtablissement().getId());
            userconfig.putInt("idEmploye", emp.getId());
            userconfig.put("nomEmploye", emp.getNom()+" "+emp.getPrenom());
            userconfig.put("emailEmploye", emp.getEmail());
            userconfig.put("libelleProfil", emp.getProfil().getLibelle());
            userconfig.putInt("idProfil", emp.getProfil().getId());
            System.out.println(""+emp.getEmail());
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/vue/SplashScrenVue.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
