package controller;

import classe.Profil;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import service.ProfilService;

/**
 * FXML Controller class
 *
 * @author Utilisateur
 */
public class ProfilController implements Initializable {

    ProfilService ps = new ProfilService();
    ObservableList<Profil> profilList = FXCollections.observableArrayList();
    @FXML
    private JFXTextField libelle;
    @FXML
    private JFXTextField code;
    @FXML
    private TableView<Profil> profils;
    @FXML
    private TableColumn<Profil, String> cCode;
    @FXML
    private TableColumn<Profil, String> cLibelle;
    @FXML
    private JFXTreeTableView<Profil> treeView;

    @FXML
    void deleteProfil(ActionEvent event) {

    }

    @FXML
    void saveProfil(ActionEvent event) {
        ps.create(new Profil(code.getText(), libelle.getText()));
        load();
    }

    @FXML
    void updateProfil(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        load();
        loadTreeView();
    }

    public void load() {
        profilList.clear();
        cCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        cLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));

        for (Profil p : ps.findAll()) {
            profilList.add(new Profil(p.getId(), p.getCode(), p.getLibelle()));
        }
        profils.setItems(profilList);
    }

    public void loadTreeView() {
        profilList.clear();
        for (Profil p : ps.findAll()) {
            profilList.add(new Profil(p.getId(), p.getCode(), p.getLibelle()));
        }

        JFXTreeTableColumn<Profil, String> cTcode = new JFXTreeTableColumn<>("Code");
        cTcode.setPrefWidth(250);
        cTcode.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Profil, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Profil, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getCode());
            }
        });

        JFXTreeTableColumn<Profil, String> cTlibelle = new JFXTreeTableColumn<>("Libelle");
        cTcode.setPrefWidth(250);
        cTlibelle.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Profil, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Profil, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getLibelle());
            }
        });

        treeView.getColumns().add(cTcode);
        treeView.getColumns().add(cTlibelle);
        TreeItem<Profil> treeItem = new RecursiveTreeItem<Profil>(profilList, RecursiveTreeObject::getChildren);
        treeItem.setExpanded(true);
        treeView.setRoot(treeItem);
        treeView.setShowRoot(false);
        treeView.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.show();
            }
            
        });
    }

}
