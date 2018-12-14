package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController implements Initializable {
    
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML private BorderPane borderpane;
    @FXML private Circle circle;
    @FXML private Hyperlink nomEmploye;
    @FXML private Hyperlink logout;
    @FXML private HBox wadifa;
    @FXML private HBox mosasa;
    @FXML private HBox mowadafon;
    @FXML private HBox talamid;
    @FXML private HBox chahada;
    @FXML private AnchorPane proetaemp;
    @FXML private HBox statistique;
    Parent root = null;
    Scene scene;
    private Boolean etat = true;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Preferences userConfig = Preferences.userRoot();
        String nomEmp = userConfig.get("nomEmploye", "0");
        String libelleProfil = userConfig.get("libelleProfil", "0");
        nomEmploye.setText(nomEmp);
        Image im = new Image("/images/3.jpg",false);
        circle.setFill(new ImagePattern(im));
        try {
            Parent root=   FXMLLoader.load(getClass().getResource("/vue/StatistiqueVue.fxml"));
            borderpane.setCenter(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(libelleProfil.equals("مدير")){
        }else{
            proetaemp.setDisable(true);
            talamid.setDisable(true);
//            wadifa.setVisible(false
            //wadifa.setDisable(true);
        }
        
        makeStageDrageable();
    }    

    @FXML
    private void statistique(MouseEvent event) {
        scene = statistique.getScene();
        LoadFromButtom("/vue/StatistiqueVue.fxml");
    }
    
    @FXML
    void imprimer(MouseEvent event) {
        scene = chahada.getScene();
        LoadFromRight("/vue/AttestationVue.fxml");
    }
    
    @FXML
    private void etablissement(MouseEvent event) {
        scene = mosasa.getScene();
        LoadFromButtom("/vue/EtablissementVue.fxml");
    }

    @FXML
    private void employe(MouseEvent event) {
        scene = mowadafon.getScene();
        LoadFromRight("/vue/EmployeVue.fxml");
    }

    @FXML
    private void profil(MouseEvent event) {
        scene = wadifa.getScene();
        LoadFromRight("/vue/ProfilVue.fxml");
    }
    
    @FXML
    private void etudiant(MouseEvent event) {
        scene = talamid.getScene();
        LoadFromButtom("/vue/EtudiantVue.fxml");
    }
    
    @FXML
    void infoView(ActionEvent event) {
        scene = nomEmploye.getScene();
        LoadFromRight("/vue/InfoVue.fxml");
    }
    
    @FXML
    void logOut(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/vue/LoginVue.fxml"));
            Stage stage = new Stage();
            Scene scenee = new Scene(parent);
            stage.setScene(scenee);
            stage.show();
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
    
    private void LoadFromButtom(String L){
      try {
       root=   FXMLLoader.load(getClass().getResource(L));
      } catch (IOException ex) {
          Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
      }
      root.translateYProperty().set(scene.getHeight());
      borderpane.setCenter(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();      
    }
    
    private void LoadFromRight(String L){
      try {
       root=   FXMLLoader.load(getClass().getResource(L));
      } catch (IOException ex) {
          Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
      }
      root.translateXProperty().set(scene.getWidth());
      borderpane.setCenter(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();      
    }
    
    private void makeStageDrageable() {
          
        borderpane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });        
        borderpane.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                SplashScreenController.stagee.setX(event.getScreenX() - xOffset);
                SplashScreenController.stagee.setY(event.getScreenY() - yOffset);
                SplashScreenController.stagee.setOpacity(0.7f);
            }            
        });        
        borderpane.setOnDragDone((e) -> {
            SplashScreenController.stagee.setOpacity(1.0f);
        });
        borderpane.setOnMouseReleased((e) -> {
            SplashScreenController.stagee.setOpacity(1.0f);
        });
    }    
}
