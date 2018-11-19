
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Utilisateur
 */
public class MenuController implements Initializable {
    
    @FXML private BorderPane borderpane;
    @FXML private Label employeId;
    @FXML private Circle circle;
    private Boolean etat = true;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Preferences userConfig = Preferences.userRoot();
        String userId = userConfig.get("userId", "0");
        employeId.setText("Etablissement Employe est : "+userId);
        ///////////////important code starts from here
        Image im = new Image("/images/3.jpg",false);
        circle.setFill(new ImagePattern(im));
        
    }    

    @FXML
    private void etablissement(MouseEvent event) {
        Load("/vue/EtablissementVue.fxml");
    }

    @FXML
    private void employe(MouseEvent event) {
        Load("/vue/EmployeVue.fxml");
    }

    @FXML
    private void profil(MouseEvent event) {
        Load("/vue/ProfilVue.fxml");
    }
    
    private void Load(String L){
     Parent root = null;
      try {
       root=   FXMLLoader.load(getClass().getResource(L));
      } catch (IOException ex) {
          Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
      }
      borderpane.setCenter(root);
    }
    
    @FXML
    private void maximize_app(MouseEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        if(etat == true){
            stage.setMaximized(true);
            etat = false;
        }else if(etat == false){
            stage.setMaximized(false);
            etat = true;
        }
    }
    
    @FXML
    private void close_app(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void minimize_app(MouseEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
//    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//      public void handle(WindowEvent we) {
//          System.out.println("Stage is closing");
//      }
//    });
}
