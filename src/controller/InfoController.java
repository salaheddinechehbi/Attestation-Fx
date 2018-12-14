package controller;

import classe.Employe;
import classe.Etablissement;
import classe.Profil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import service.EmployeService;
import service.EtablissementService;
import service.ProfilService;

public class InfoController implements Initializable {

    EmployeService es = new EmployeService();
    EtablissementService ets = new EtablissementService();
    ProfilService ps = new ProfilService();
    
    @FXML private JFXTextField prenom;
    @FXML private JFXTextField nom;
    @FXML private JFXTextField email;
    @FXML private JFXDatePicker dateN;
    @FXML private JFXDatePicker dateE;
    @FXML private JFXTextField profil;
    @FXML private JFXTextField etab;
    @FXML private JFXButton update;
    @FXML private JFXPasswordField pass;  
    
    Preferences userConfig = Preferences.userRoot();
    String nomEmp = userConfig.get("nomEmploye", "0");
    int idEmploye = userConfig.getInt("idEmploye", 0);
    int idEtab = userConfig.getInt("idEtab", 1);
    int idProfil = userConfig.getInt("idProfil", 1);
    private Etablissement etablissement = ets.findById(idEtab); 
    private Profil prof = ps.findById(idProfil);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEmploye();
    }    
    
    @FXML
    private void updateInfo(ActionEvent event){
        Employe employe = es.findById(idEmploye);
        employe.setNom(nom.getText());
        employe.setPrenom(prenom.getText());
        employe.setEmail(email.getText());
        employe.setPassword(util.Util.md5(pass.getText()));
        LocalDate de = dateE.getValue();
        LocalDate dn = dateN.getValue();
        Instant instantE = Instant.from(de.atStartOfDay(ZoneId.systemDefault()));
        Instant instantN = Instant.from(dn.atStartOfDay(ZoneId.systemDefault()));
        Date datee = Date.from(instantE);
        Date daten = Date.from(instantN);
        employe.setDateEmbouche(datee);
        employe.setDateNaissance(daten);
        employe.setProfil(prof);
        employe.setEtablissement(etablissement);
        employe.setId(idEmploye);
        es.update(employe);
        loadEmploye();
    }
    
    private void loadEmploye(){
        Employe employe = es.findById(idEmploye);
        prenom.setText(employe.getPrenom());
        nom.setText(employe.getNom());
        email.setText(employe.getEmail());
        etab.setText(employe.getEtablissement().getNom());
        profil.setText(employe.getProfil().getLibelle());
        pass.setText(employe.getPassword());
        Date datee = employe.getDateEmbouche();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        SimpleDateFormat sdf = new  SimpleDateFormat("dd-MM-yyyy");
        LocalDate localDatee = LocalDate.parse(sdf.format(datee), formatter);
        dateE.setValue(localDatee);
        Date daten = employe.getDateNaissance();
        LocalDate localDaten = LocalDate.parse(sdf.format(daten), formatter);
        dateN.setValue(localDaten);
    }
    
    
}
