package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author salah
 */
public class SplashScreenController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    final Window window = new Stage();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Rectangle r = new Rectangle();
        r.setHeight(50);
        r.setWidth(50);
        r.setRotate(45);
        r.setFill(Color.CORNFLOWERBLUE);
        r.setLayoutX(50);
        r.setLayoutY(200);

        TranslateTransition t = new TranslateTransition();
        t.setDuration(Duration.seconds(1));
        t.setAutoReverse(true);
        t.setCycleCount(3);
        t.setToX(360);
        t.setNode(r);
        t.play();

        Rectangle r1 = new Rectangle();
        r1.setHeight(50);
        r1.setWidth(50);
        r1.setRotate(45);
        r1.setFill(Color.CORNFLOWERBLUE);
        r1.setLayoutX(900);
        r1.setLayoutY(200);

        TranslateTransition t1 = new TranslateTransition();
        t1.setDuration(Duration.seconds(1));
        t1.setAutoReverse(true);
        t1.setCycleCount(3);
        t1.setToX(-370);
        t1.setNode(r1);
        t1.play();

        Rectangle r2 = new Rectangle();
        r2.setHeight(50);
        r2.setWidth(50);
        r2.setRotate(45);
        r2.setFill(Color.CORNFLOWERBLUE);
        r2.setLayoutX(470);
        r2.setLayoutY(450);

        TranslateTransition t2 = new TranslateTransition();
        t2.setDuration(Duration.seconds(1));
        t2.setAutoReverse(true);
        t2.setCycleCount(3);
        t2.setToY(-170);
        t2.setNode(r2);
        t2.play();

        Rectangle r3 = new Rectangle();
        r3.setHeight(50);
        r3.setWidth(50);
        r3.setRotate(45);
        r3.setFill(Color.CORNFLOWERBLUE);
        r3.setLayoutX(470);
        r3.setLayoutY(50);

        TranslateTransition t3 = new TranslateTransition();
        t3.setDuration(Duration.seconds(1));
        t3.setAutoReverse(true);
        t3.setCycleCount(3);
        t3.setToY(100);
        t3.setNode(r3);
        t3.play();
        anchorPane.getChildren().add(r);
        anchorPane.getChildren().add(r1);
        anchorPane.getChildren().add(r2);
        anchorPane.getChildren().add(r3);

        t1.setOnFinished((e) -> {
            //Platform.exit();
            Parent parent = null;
            try {
                parent = FXMLLoader.load(getClass().getResource("/vue/Menu.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("ISTA");
            stage.show();
            anchorPane.getScene().getWindow().hide();
        });
    }

}
