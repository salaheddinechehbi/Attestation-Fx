package controller;

import classe.Connexion;
import classe.Etudiant;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.prefs.Preferences;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTablePosition;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import service.EtudiantService;

public class AttestationController implements Initializable {

    @FXML private AnchorPane anchorPane;
    Connection cnx = null;
    PreparedStatement pre = null;
    ResultSet res = null;
    @FXML private JFXTreeTableView<Etudiant> treeTableView1;
    @FXML private JFXTextField searshInput;
    EtudiantService es = new EtudiantService();
    ObservableList<Etudiant> etudiantList = FXCollections.observableArrayList();
    private int index = 0;
    private String nomEmp="";
    private String libelleProfil;
    private int idEtab=0;
    private int idProfil=0;
    private int idEmploye=0;
    private void initialVariable(){
      Preferences userConfig = Preferences.userRoot();
      nomEmp = userConfig.get("nomEmploye", "0");
      libelleProfil = userConfig.get("libelleProfil", "0"); 
      idEtab = userConfig.getInt("idEtab", 0);
      idProfil = userConfig.getInt("idProfil", 0);
      idEmploye = userConfig.getInt("idEmploye", 0);
    }  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTreeTableView();
        remplirTreeTableView();
    }    
    
    @FXML void imprimerAttestation(ActionEvent event) throws JRException, SQLException {
        
        //JasperDesign jd = JRXmlLoader.load(filePath);
//        String sql1 = "Select * From Etudiant e,Etablissement et,Employe em where e.etablissement_id=et.id and et.id=em.etablissement_id and em.id=6 and e.id=64";
//        String sql = "Select * From Etudiant e,Etablissement et,Employe em where e.etablissement_id=et.id and et.id=em.etablissement_id and em.id="+6+" and e.id="+64;
//        JRDesignQuery designQuery = new JRDesignQuery();
//        designQuery.setText(sql);
//        jd.setQuery(designQuery);
        Etudiant et = es.findById(index);
        HashMap param = new HashMap();
        param.put("etudiant_nom", et.getNom());
        param.put("etudiant_lieu", et.getLieuNaissance());
        param.put("etudiant_niveau", et.getNiveau());
        param.put("etudiant_num", et.getNumInscription());
        param.put("etudiant_code", et.getCodeNationale());
        param.put("etudiant_dateD", et.getDateDepart().toString());
        param.put("etudiant_dateN", et.getDateNaissance().toString());
        param.put("etudiant_decision", et.getDecision());
        String filePath = "C:\\Users\\salah\\Documents\\NetBeansProjects\\FX\\AttestationFX\\src\\reports\\reportAttestation.jrxml";
        Connexion cn = new Connexion();
        cnx = (Connection) cn.getConnection();
        JasperReport jasperReport = JasperCompileManager.compileReport(filePath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param,cnx);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\salah\\Documents\\Attestation.pdf");
        
        JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
        snackbar.show("Bien Imprimé", 2000);
        //JRDataSource dataSource = new JREmptyDataSource();
        //JasperViewer.viewReport(jasperPrint,false);
        //JRProperties.setProperty("net.sf.jasperreports.default.pdf.encoding", "Cp1256");
        
    }
    
    public void loadTreeTableView() {
        JFXTreeTableColumn<Etudiant, String> cTnumIn = new JFXTreeTableColumn<>("رقم التسجيل");
        cTnumIn.setPrefWidth(100);
        cTnumIn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etudiant, String> param) -> new SimpleStringProperty(param.getValue().getValue().getNumInscription()));
        JFXTreeTableColumn<Etudiant, String> cTnom = new JFXTreeTableColumn<>("الاسم الكامل");
        cTnom.setPrefWidth(150);
        cTnom.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etudiant, String> param) -> new SimpleStringProperty(param.getValue().getValue().getNom()));
        JFXTreeTableColumn<Etudiant, String> cTdateN = new JFXTreeTableColumn<>("تاريخ الازدياد");
        cTdateN.setPrefWidth(100);
        cTdateN.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etudiant, String> param) -> new SimpleStringProperty(param.getValue().getValue().getDateNaissance().toString()));
        JFXTreeTableColumn<Etudiant, String> cTlieuN = new JFXTreeTableColumn<>("مكان الازدياد");
        cTlieuN.setPrefWidth(100);
        cTlieuN.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etudiant, String> param) -> new SimpleStringProperty(param.getValue().getValue().getLieuNaissance()));
        
        JFXTreeTableColumn<Etudiant, String> cTniv = new JFXTreeTableColumn<>("آخر مستوى");
        cTniv.setPrefWidth(100);
        cTniv.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etudiant, String> param) -> new SimpleStringProperty(param.getValue().getValue().getNiveau()));
        JFXTreeTableColumn<Etudiant, String> cTcode = new JFXTreeTableColumn<>("الرقم الوطني");
        cTcode.setPrefWidth(100);
        cTcode.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etudiant, String> param) -> new SimpleStringProperty(param.getValue().getValue().getCodeNationale()));
        JFXTreeTableColumn<Etudiant, String> cTdateD = new JFXTreeTableColumn<>("تاريخ المغادرة");
        cTdateD.setPrefWidth(100);
        cTdateD.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etudiant, String> param) -> new SimpleStringProperty(param.getValue().getValue().getDateDepart().toString()));
        JFXTreeTableColumn<Etudiant, String> cTdec = new JFXTreeTableColumn<>("قرار مجلس القسم");
        cTdec.setPrefWidth(100);
        cTdec.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etudiant, String> param) -> new SimpleStringProperty(param.getValue().getValue().getDecision()));
        JFXTreeTableColumn<Etudiant, String> cTnumD = new JFXTreeTableColumn<>("رقم الملف");
        cTnumD.setPrefWidth(80);
        cTnumD.setCellValueFactory((TreeTableColumn.CellDataFeatures<Etudiant, String> param) -> new SimpleStringProperty(param.getValue().getValue().getNumDossier()));
        
        
        treeTableView1.getColumns().add(cTnumD);
        treeTableView1.getColumns().add(cTdec);
        treeTableView1.getColumns().add(cTdateD);
        treeTableView1.getColumns().add(cTcode);
        treeTableView1.getColumns().add(cTniv);
        treeTableView1.getColumns().add(cTlieuN);
        treeTableView1.getColumns().add(cTdateN);
        treeTableView1.getColumns().add(cTnom);
        treeTableView1.getColumns().add(cTnumIn);
        
        treeTableView1.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                TreeTablePosition<Etudiant, ?> pos =  treeTableView1.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                TreeItem<Etudiant> item = treeTableView1.getTreeItem(row);
                index = item.getValue().getId();
            }
        });
        
        searshInput.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableView1.setPredicate(new Predicate<TreeItem<Etudiant>>(){
                    @Override
                    public boolean test(TreeItem<Etudiant> etudiant) {
                        Boolean flag = etudiant.getValue().getNom().contains(newValue)||etudiant.getValue().getNumInscription().contains(newValue)||etudiant.getValue().getCodeNationale().contains(newValue);
                        return flag;
                    } 
                });
            }
        });
    }
    
    public void remplirTreeTableView(){
        etudiantList.clear();
        treeTableView1.setRoot(null);
        for (Etudiant e : es.findAll()) {
            etudiantList.add(new Etudiant(e.getId(), e.getNumInscription(), e.getNom(), e.getDateNaissance(), e.getLieuNaissance(), e.getNiveau(), e.getCodeNationale(), e.getDateDepart(), e.getDecision(), e.getNumDossier()));
        }
        TreeItem<Etudiant> treeItem;
        treeItem = new RecursiveTreeItem<Etudiant>(etudiantList, RecursiveTreeObject::getChildren);
        treeItem.setExpanded(true);
        treeTableView1.setRoot(treeItem);
        treeTableView1.setShowRoot(false);
    }
}
