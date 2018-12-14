package controller;

import classe.Employe;
import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import service.EmployeService;
import service.EtudiantService;
import service.ProfilService;

public class StatistiqueController implements Initializable {

    @FXML private Label etablissementNom;
    @FXML private Label nomEmploye;
    @FXML private Label countEtud;
    @FXML private Label countProf;
    @FXML private Label countEmp;
    @FXML private BarChart<?, ?> empStatique;
    @FXML private PieChart pieDecision;
    EmployeService es = new EmployeService();
    EtudiantService ets = new EtudiantService();
    ProfilService ps = new ProfilService();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      Preferences userConfig = Preferences.userRoot();
      String nomEtab = userConfig.get("nomEtab", "0");
      String nomEmp = userConfig.get("nomEmploye", "0");
      etablissementNom.setText(nomEtab);
      nomEmploye.setText(" مرحبا السيد المدير "+nomEmp);
      countEmp.setText(""+es.countEmploye());
      countEtud.setText(""+ets.countEtudiant());
      countProf.setText(""+ps.countProfil());
      remplireEmployeStatiqueChart();
      pieDecision.setData(remplireDecisionStatiqueChart());
    }   
    
    public void remplireEmployeStatiqueChart(){
        XYChart.Series series = new XYChart.Series<>();
        for(Object[] o : es.employeParProfil()){
            series.getData().add(new XYChart.Data(o[0].toString(), (BigInteger)o[1]));
        }
        empStatique.getData().add(series);
    }
    
    private ObservableList<PieChart.Data> remplireDecisionStatiqueChart() {  
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();  
         for(Object[] o : ets.etudiantParDecision()){
            list.add(new PieChart.Data(o[0].toString(), Double.valueOf(o[1].toString())));
        } 
        return list;  
    } 
    
}
