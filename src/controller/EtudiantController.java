package controller;

import classe.Etablissement;
import classe.Etudiant;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
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
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.EtablissementService;
import service.EtudiantService;

public class EtudiantController implements Initializable {

    @FXML private AnchorPane anchorEtudiant;
    @FXML private JFXTreeTableView<Etudiant> treeTableView1;
    @FXML private JFXTextField searshInput;
    private int index = 0;
    EtudiantService es = new EtudiantService();
    ObservableList<Etudiant> etudiantList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTreeTableView();
        remplirTreeTableView();
    }   
    
    @FXML
    void updateEtudiant(ActionEvent event) {

    }
    
    @FXML
    void addEtudiant(ActionEvent event) {

    }

    @FXML
    void deleteEtudiant(ActionEvent event) {

    }
    
    @FXML
    void uploadFile(ActionEvent event) throws Exception {
        
        Preferences userConfig = Preferences.userRoot();
        String nomEtab = userConfig.get("nomEtab", "0");
        int idEtab = userConfig.getInt("idEtab", 0);
        
        Etudiant etudiant = new Etudiant();
        EtudiantService etudiantService = new EtudiantService();
        Etablissement etablissement = new Etablissement();
        EtablissementService etablissementService = new EtablissementService();
        
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            System.out.println("the file path is : "+selectedFile.getPath());
            File myFile = new File(selectedFile.getPath());
            FileInputStream fis = new FileInputStream(myFile);
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
            XSSFSheet mySheet = myWorkBook.getSheetAt(1);
            for(int i=1;i<mySheet.getLastRowNum();i++) {
                XSSFRow rowx = mySheet.getRow(i);
                XSSFCell cell1 = rowx.getCell(1);//num inscription
                XSSFCell cell2 = rowx.getCell(2);//nom etudaint
                if(cell2 == null || cell2.getCellType() == CellType.BLANK) {
                    continue;
                }else if(es.checkEtudiant(cell1.getStringCellValue(), cell2.getStringCellValue()) >0){
                    continue;    
                }else {
                    XSSFCell cell3 = rowx.getCell(3);//date naissance
                    XSSFCell cell4 = rowx.getCell(4);//lieu naissance
                    XSSFCell cell5 = rowx.getCell(5);//niveau
                    XSSFCell cell6 = rowx.getCell(6);//code national
                    XSSFCell cell7 = rowx.getCell(7);//date depart
                    XSSFCell cell8 = rowx.getCell(8);//decision
                    XSSFCell cell9 = rowx.getCell(9);//num dossier
                    
                    etudiant.setNumInscription(cell1.getStringCellValue());
                    etudiant.setNom(cell2.getStringCellValue());
                    etudiant.setDateNaissance(cell3.getDateCellValue());
                    etudiant.setLieuNaissance(cell4.getStringCellValue());
                    if(cell5 == null || cell5.getCellType() == CellType.BLANK) {
                        etudiant.setNiveau("غير متوفر");
                    }else{
                        etudiant.setNiveau(cell5.getStringCellValue());
                    }
                    if(cell6 == null || cell6.getCellType() == CellType.BLANK) {
                        etudiant.setCodeNationale("غير متوفر");
                    }else{
                        etudiant.setCodeNationale(""+cell6.getNumericCellValue());
                    }
                    etudiant.setDateDepart(cell7.getDateCellValue());
                    if(cell8 == null || cell8.getCellType() == CellType.BLANK) {
                        etudiant.setDecision("غير متوفر");
                    }else{
                        etudiant.setDecision(cell8.getStringCellValue());
                    }
                    etudiant.setNumDossier(""+cell9.getNumericCellValue());
                    etablissement = etablissementService.findById(idEtab);
                    etudiant.setEtablissement(etablissement);
                    etudiantService.create(etudiant);
//                    System.out.print(cell1.getStringCellValue()+"\t");
//                    System.out.print(cell2.getStringCellValue().toString()+"\t");
//                    System.out.print(cell3.getNumericCellValue()+"\t");                        
//                    System.out.print(cell4.getStringCellValue().toString()+"\t");
//                    System.out.print(cell5.getStringCellValue().toString()+"\t");                        
//                    System.out.print(cell6.getNumericCellValue()+"\t");                        
//                    System.out.print(cell7.getNumericCellValue()+"\t");                        
//                    System.out.print(cell8.getStringCellValue().toString()+"\t");                        
//                    System.out.print(cell9.getNumericCellValue());
//                    System.out.println("");
                }
            }
            remplirTreeTableView();
            JFXSnackbar snackbar = new JFXSnackbar(anchorEtudiant);
            snackbar.show("Bien Ajouté", 3000);
        }
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
