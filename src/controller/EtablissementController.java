package controller;

import classe.Etablissement;
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
import service.EtablissementService;

public class EtablissementController implements Initializable {

    EtablissementService es = new EtablissementService();
    ObservableList<Etablissement> etablissementList = FXCollections.observableArrayList();
    private static int index;
    @FXML private JFXTextField region;
    @FXML private JFXTextField type;
    @FXML private JFXTextField nom;
    @FXML private JFXTextField acadimie;
    @FXML private JFXTextField code;
    @FXML private JFXTextField tel;
    @FXML private JFXTextField baladiya;
    @FXML private JFXButton buttonAddEtab;
    @FXML private JFXTreeTableView<Etablissement> treeTableView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTreeTableView();
        remplirTreeTableView();
        testNbrEtablissment();
    }

    @FXML
    private void updateEtablissement() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de modification");
        alert.setContentText("Vous vous vraiment modifier cette etablissement?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Etablissement e = es.findById(index);
            e.setNom(nom.getText());
            e.setType(type.getText());
            e.setAcadimie(acadimie.getText());
            e.setRegion(region.getText());
            e.setTel(tel.getText());
            e.setCode(code.getText());
            e.setBaladiya(baladiya.getText());
            e.setId(index);
            es.update(e);
            remplirTreeTableView();
            testNbrEtablissment();
            clean();
        }else{
            clean();
        }
    }

    @FXML
    private void saveEtablissement(ActionEvent event) {
        String n = nom.getText();
        String t = type.getText();
        String r = region.getText();
        String a = acadimie.getText();
        String c = code.getText();
        String b = baladiya.getText();
        String te = tel.getText();
        es.create(new Etablissement(n, t, r, a, c, te, b));
        remplirTreeTableView();
        testNbrEtablissment();
        clean();
    }
    
    public void loadTreeTableView() {
        
        JFXTreeTableColumn<Etablissement, String> cTnom = new JFXTreeTableColumn<>("الإسم");
        cTnom.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etablissement, String> param) -> new SimpleStringProperty(param.getValue().getValue().getNom()));
        JFXTreeTableColumn<Etablissement, String> cTtype = new JFXTreeTableColumn<>("النوع");
        cTtype.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etablissement, String> param) -> new SimpleStringProperty(param.getValue().getValue().getType()));
        JFXTreeTableColumn<Etablissement, String> cTregion = new JFXTreeTableColumn<>("الجهة ");
        cTregion.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etablissement, String> param) -> new SimpleStringProperty(param.getValue().getValue().getRegion()));
        JFXTreeTableColumn<Etablissement, String> cTacadimie = new JFXTreeTableColumn<>("النيابة");
        cTacadimie.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etablissement, String> param) -> new SimpleStringProperty(param.getValue().getValue().getAcadimie()));
        JFXTreeTableColumn<Etablissement, String> cTcode = new JFXTreeTableColumn<>("الرمز");
        cTcode.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etablissement, String> param) -> new SimpleStringProperty(param.getValue().getValue().getCode()));
        JFXTreeTableColumn<Etablissement, String> cTtel = new JFXTreeTableColumn<>("الهاتف");
        cTtel.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etablissement, String> param) -> new SimpleStringProperty(param.getValue().getValue().getTel()));
        JFXTreeTableColumn<Etablissement, String> cTbaladiya = new JFXTreeTableColumn<>("البلدية");
        cTbaladiya.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etablissement, String> param) -> new SimpleStringProperty(param.getValue().getValue().getBaladiya()));
        
        treeTableView.getColumns().add(cTcode);
        treeTableView.getColumns().add(cTregion);
        treeTableView.getColumns().add(cTacadimie);
        treeTableView.getColumns().add(cTtype);
        treeTableView.getColumns().add(cTbaladiya);
        treeTableView.getColumns().add(cTtel);
        treeTableView.getColumns().add(cTnom);
        treeTableView.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                TreeTablePosition<Etablissement, ?> pos =  treeTableView.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                TreeItem<Etablissement> item = treeTableView.getTreeItem(row);
                index = item.getValue().getId();
                nom.setText(item.getValue().getNom());
                type.setText(item.getValue().getType());
                region.setText(item.getValue().getRegion());
                acadimie.setText(item.getValue().getAcadimie());
                tel.setText(item.getValue().getTel());
                code.setText(item.getValue().getCode());
                baladiya.setText(item.getValue().getBaladiya());
            } 
        });            
    }
    
    public void remplirTreeTableView(){
        etablissementList.clear();
        treeTableView.setRoot(null);
        for (Etablissement e : es.findAll()) {
            etablissementList.add(new Etablissement(e.getId(), e.getNom(), e.getType(), e.getRegion(), e.getAcadimie(), e.getCode(), e.getTel(), e.getBaladiya()));
        }
        TreeItem<Etablissement> treeItem;
        treeItem = new RecursiveTreeItem<Etablissement>(etablissementList, RecursiveTreeObject::getChildren);
        treeItem.setExpanded(true);
        treeTableView.setRoot(treeItem);
        treeTableView.setShowRoot(false);
    }
    
    private void clean() {
        nom.setText("");
        type.setText("");
        region.setText("");
        acadimie.setText("");
        tel.setText("");
        code.setText("");
        baladiya.setText("");
    }
    
    private void testNbrEtablissment(){
        if(es.countEtablissement()==1){
            buttonAddEtab.setDisable(true);
        }else{
            buttonAddEtab.setDisable(false);
        }
    }
}
