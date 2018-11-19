
package controller;
import classe.Etablissement;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.EtablissementService;

/**
 * FXML Controller class
 *
 * @author Utilisateur
 */
public class EtablissementController implements Initializable {
    
    EtablissementService es = new EtablissementService();
    ObservableList<Etablissement> etablissementList = FXCollections.observableArrayList();
    private static int index;

    @FXML private JFXTextField region;
    @FXML private JFXTextField type;
    @FXML private JFXTextField nom;   
    @FXML private TableView<Etablissement> etablissement;
    @FXML private TableColumn<Etablissement, String> ctype;
    @FXML private TableColumn<Etablissement, String> cnom;    
    @FXML private TableColumn<Etablissement, String> cregion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          load();
        etablissement.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TablePosition pos = (TablePosition) etablissement.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                Etablissement item = etablissement.getItems().get(row);
                nom.setText(item.getNom());
                type.setText(item.getType());
                
                region.setText(item.getRegion());
                index = item.getId();
                load();
            }
        });
        // TODO
    }
   public void clean() {
        nom.setText("");
        type.setText("");
        region.setText("");
    }    

    @FXML
    private void update() {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de modification");
        alert.setContentText("Vous vous vraiment modifier cette etablissement?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Etablissement e = es.findById(index);
            //MachinList.set(index, m2);
           
            e.setNom(nom.getText());
            e.setType(type.getText());
            
            e.setRegion(region.getText());
            es.update(e);
            etablissementList.clear();
            load();
        }
    }

    @FXML
    private void delete(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de suppression");
        alert.setContentText("Vous vous vraiment supprimer cette etablissement?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            es.delete(es.findById(index));
            etablissementList.clear();
            load();
        } 

    }

    @FXML
    private void save(ActionEvent event) {
         String n = nom.getText().toString();
        String t = type.getText().toString();
        String r = region.getText().toString();

        es.create(new Etablissement(n,t, r));
        load();
        clean();
    }
 
    
    public void load() {
        etablissementList.clear();
        cnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ctype.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        cregion.setCellValueFactory(new PropertyValueFactory<>("region"));
        
        for (Etablissement e : es.findAll()) {
            etablissementList.add(new Etablissement(e.getId(),e.getNom(), e.getType(), e.getRegion()));
        }

        etablissement.setItems(etablissementList);
}
}
