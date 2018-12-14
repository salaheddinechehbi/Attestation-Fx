package controller;

import classe.Profil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import service.ProfilService;

public class ProfilController implements Initializable {

    ProfilService ps = new ProfilService();
    ObservableList<Profil> profilList = FXCollections.observableArrayList();
    @FXML private JFXTextField libelle;
    @FXML private JFXTextField code;
    @FXML private JFXTreeTableView<Profil> treeView1;
    @FXML private JFXButton buttonAddEtab;
    private int index = 0;

    @FXML
    void deleteProfil(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de suppression");
        alert.setContentText("Vous vous vraiment supprimer ce profil ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            treeView1.getRoot().getChildren().clear();
            ps.delete(ps.findById(index));
            remplirTreeView();
            clean();
        }else{
            clean();
        }
    }

    @FXML
    void saveProfil(ActionEvent event) {
        treeView1.getRoot().getChildren().clear();
        ps.create(new Profil(code.getText(), libelle.getText()));
        remplirTreeView();
        clean();
    }

    @FXML
    void updateProfil(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de suppression");
        alert.setContentText("Vous vous vraiment modifier ce profil ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            treeView1.getRoot().getChildren().clear();
            Profil p = ps.findById(index);
            p.setCode(code.getText());
            p.setLibelle(libelle.getText());
            ps.update(p);
            remplirTreeView();
            clean();
        }else{
            clean();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTreeView();  
        remplirTreeView();
    }

    public void loadTreeView() {
        JFXTreeTableColumn<Profil, String> cTcode = new JFXTreeTableColumn<>("الرمز");
        cTcode.setPrefWidth(250);
        cTcode.setMaxWidth(400);
        cTcode.setCellValueFactory((TreeTableColumn.CellDataFeatures<Profil, String> param) -> new SimpleStringProperty(param.getValue().getValue().getCode()));
        JFXTreeTableColumn<Profil, String> cTlibelle = new JFXTreeTableColumn<>("الوظيفة");
        cTlibelle.setPrefWidth(250);
        cTlibelle.setMaxWidth(400);
        cTlibelle.setCellValueFactory((TreeTableColumn.CellDataFeatures<Profil, String> param) -> new SimpleStringProperty(param.getValue().getValue().getLibelle()));
        treeView1.getColumns().add(cTlibelle);
        treeView1.getColumns().add(cTcode);
        treeView1.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                TreeTablePosition<Profil, ?> pos =  treeView1.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                TreeItem<Profil> item = treeView1.getTreeItem(row);
                index = item.getValue().getId();
                code.setText(item.getValue().getCode());
                libelle.setText(item.getValue().getLibelle());
            } 
        });            
    }
    
    public void remplirTreeView(){
        profilList.clear();
        treeView1.setRoot(null);
        for (Profil p : ps.findAll()) {
            profilList.add(new Profil(p.getId(), p.getCode(), p.getLibelle()));
        }
        TreeItem<Profil> treeItem;
        treeItem = new RecursiveTreeItem<Profil>(profilList, RecursiveTreeObject::getChildren);
        treeItem.setExpanded(true);
        treeView1.setRoot(treeItem);
        treeView1.setShowRoot(false);
    }
    
    public void clean(){
        code.setText("");
        libelle.setText("");
    }

}
