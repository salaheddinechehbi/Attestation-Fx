package controller;

import classe.Employe;
import util.Util;
import classe.Etablissement;
import classe.Profil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.prefs.Preferences;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTablePosition;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.action.Action;
import service.EmployeService;
import service.EtablissementService;
import service.ProfilService;

public class EmployeController implements Initializable {

    EmployeService es = new EmployeService();
    EtablissementService ets = new EtablissementService();
    ProfilService ps = new ProfilService();
    ObservableList<Employe> employeList = FXCollections.observableArrayList();
    private static int index;
    @FXML private JFXButton buttonAddEmploye;
    @FXML private JFXButton buttonUpdateEmploye;
    @FXML private JFXButton buttonDeleteEmploye;
    @FXML private JFXTreeTableView<Employe> treeTableView;
    @FXML private JFXTextField searshEmploye;
    @FXML private JFXTextField nom;
    @FXML private JFXTextField prenom;
    @FXML private JFXTextField email;
    @FXML private JFXPasswordField pass;
    @FXML private JFXDatePicker dateN;
    @FXML private JFXDatePicker dateE;
    @FXML private JFXComboBox<String> profil;
    private String libelle;
    Preferences userConfig = Preferences.userRoot();
    int idEtab = userConfig.getInt("idEtab", 1);
    String emailEmploye = userConfig.get("emailEmploye", "w@.com");
    Etablissement etablissement = ets.findById(idEtab);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            loadTreeTableView();
            remplireTreeTableView();
            remplireCombobox();           
    } 
    
    @FXML void deleteEmploye(ActionEvent event) {
        es.delete(es.findById(index));
        remplireTreeTableView();
        clean();
    }

    @FXML
    void saveEmploye(ActionEvent event) {
        String n = nom.getText();
        String p = prenom.getText();
        String em = email.getText();
        String pa = Util.md5(pass.getText());
        LocalDate de = dateE.getValue();
        LocalDate dn = dateN.getValue();
        Instant instantE = Instant.from(de.atStartOfDay(ZoneId.systemDefault()));
        Instant instantN = Instant.from(dn.atStartOfDay(ZoneId.systemDefault()));
        Date datee = Date.from(instantE);
        Date daten = Date.from(instantN);
        String pr = profil.getValue();
        Employe employe = new Employe(n, p, em, ps.findProfilByLibelle(pr), pa, daten, datee, etablissement);
        es.create(employe);
        Util.sendEmail(emailEmploye, em, n+" "+p, pa);
        remplireTreeTableView();
        clean();
    }

    @FXML
    void updateEmploye(ActionEvent event) {
        Employe employe = es.findById(index);
        employe.setNom(nom.getText());
        employe.setPrenom(prenom.getText());
        employe.setEmail(email.getText());
        employe.setPassword(Util.md5(pass.getText()));
        LocalDate de = dateE.getValue();
        LocalDate dn = dateN.getValue();
        Instant instantE = Instant.from(de.atStartOfDay(ZoneId.systemDefault()));
        Instant instantN = Instant.from(dn.atStartOfDay(ZoneId.systemDefault()));
        Date datee = Date.from(instantE);
        Date daten = Date.from(instantN);
        employe.setDateEmbouche(datee);
        employe.setDateNaissance(daten);
        employe.setProfil(ps.findProfilByLibelle(profil.getValue()));
        employe.setEtablissement(etablissement);
        employe.setId(index);
        es.update(employe);
        Util.sendEmail(emailEmploye, email.getText(), nom.getText()+" "+prenom.getText(), pass.getText());
        clean();
        remplireTreeTableView();
    }

    private void loadTreeTableView() {
        JFXTreeTableColumn<Employe, String> cTprenom = new JFXTreeTableColumn<>("الإسم الشخصي");
        cTprenom.setCellValueFactory((TreeTableColumn.CellDataFeatures<Employe, String> param) -> new SimpleStringProperty(param.getValue().getValue().getPrenom()));
        JFXTreeTableColumn<Employe, String> cTnom = new JFXTreeTableColumn<>("الإسم العائلي");
        cTnom.setCellValueFactory((TreeTableColumn.CellDataFeatures<Employe, String> param) -> new SimpleStringProperty(param.getValue().getValue().getNom()));
        JFXTreeTableColumn<Employe, String> cTemail = new JFXTreeTableColumn<>("البريد الإلكتروني");
        cTemail.setCellValueFactory((TreeTableColumn.CellDataFeatures<Employe, String> param) -> new SimpleStringProperty(param.getValue().getValue().getEmail()));
        JFXTreeTableColumn<Employe, String> cTdateN = new JFXTreeTableColumn<>("تاريخ الإزدياد");
        cTdateN.setCellValueFactory((TreeTableColumn.CellDataFeatures<Employe, String> param) -> new SimpleStringProperty(param.getValue().getValue().getDateNaissance().toString()));
        JFXTreeTableColumn<Employe, String> cTdateE = new JFXTreeTableColumn<>("تاريخ بداية العمل");
        cTdateE.setCellValueFactory((TreeTableColumn.CellDataFeatures<Employe, String> param) -> new SimpleStringProperty(param.getValue().getValue().getDateEmbouche().toString()));
        JFXTreeTableColumn<Employe, String> cTprofil = new JFXTreeTableColumn<>("الوظيفة");
        cTprofil.setCellValueFactory((TreeTableColumn.CellDataFeatures<Employe, String> param) -> new SimpleStringProperty(param.getValue().getValue().getProfil().getLibelle()));
        treeTableView.getColumns().add(cTprofil);
        treeTableView.getColumns().add(cTdateE);
        treeTableView.getColumns().add(cTdateN);
        treeTableView.getColumns().add(cTemail);
        treeTableView.getColumns().add(cTnom);
        treeTableView.getColumns().add(cTprenom);
        
        treeTableView.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                TreeTablePosition<Employe, ?> pos =  treeTableView.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                TreeItem<Employe> item = treeTableView.getTreeItem(row);
                index = item.getValue().getId();
                nom.setText(item.getValue().getNom());
                prenom.setText(item.getValue().getPrenom());
                email.setText(item.getValue().getEmail());
                pass.setText(item.getValue().getPassword());
                profil.setValue(item.getValue().getProfil().getLibelle());
                Date datee = item.getValue().getDateEmbouche();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                SimpleDateFormat sdf = new  SimpleDateFormat("dd-MM-yyyy");
                LocalDate localDatee = LocalDate.parse(sdf.format(datee), formatter);
                dateE.setValue(localDatee);
                Date daten = item.getValue().getDateNaissance();
                LocalDate localDaten = LocalDate.parse(sdf.format(daten), formatter);
                dateN.setValue(localDaten);
            } 
        });
        
        searshEmploye.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableView.setPredicate(new Predicate<TreeItem<Employe>>(){
                    @Override
                    public boolean test(TreeItem<Employe> employe) {
                        Boolean flag = employe.getValue().getNom().contains(newValue)||employe.getValue().getPrenom().contains(newValue)||employe.getValue().getProfil().getLibelle().contains(newValue);
                        return flag;
                    }
                });
            }
        });
    }
    
    private void remplireTreeTableView(){
        employeList.clear();
        treeTableView.setRoot(null);
        for (Employe e : es.findAll()) {
            employeList.add(new Employe(e.getId(), e.getNom(), e.getPrenom(), e.getEmail(), e.getProfil(), e.getPassword(), e.getDateNaissance(), e.getDateEmbouche(), e.getEtablissement()));
        }
        TreeItem<Employe> treeItem;
        treeItem = new RecursiveTreeItem<Employe>(employeList, RecursiveTreeObject::getChildren);
        treeItem.setExpanded(true);
        treeTableView.setRoot(treeItem);
        treeTableView.setShowRoot(false);
    }
    
    private void remplireCombobox(){
        String[] pr= new String[ps.countProfil()];
        int m = 0;
        for(Profil p : ps.findAll()){
            pr[m] = p.getLibelle();
            m++;
        }
        profil.getItems().addAll(pr);
        profil.setOnAction((ActionEvent e) ->{
            libelle = profil.getValue().toString();
        });
    }
    
    private void clean(){
        nom.setText("");
        prenom.setText("");
        email.setText("");
        pass.setText("");
        searshEmploye.setText("");
        profil.setPromptText("إختر الوظيفة من هنا ");
    }
    
}
