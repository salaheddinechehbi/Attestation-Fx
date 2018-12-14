package classe;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Etudiant extends RecursiveTreeObject<Etudiant>  {
    @Id @GeneratedValue
    private int id;
    private String numInscription;
    private String nom;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    private String lieuNaissance;
    private String niveau;
    private String codeNationale;
    @Temporal(TemporalType.DATE)
    private Date dateDepart;
    private String decision;
    private String numDossier;
    @ManyToOne
    private Etablissement  etablissement;

    public Etudiant(int id, String numInscription, String nom, Date dateNaissance, String lieuNaissance, String niveau, String codeNationale, Date dateDepart, String decision, String numDossier, Etablissement etablissement) {
        this.id = id;
        this.numInscription = numInscription;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.niveau = niveau;
        this.codeNationale = codeNationale;
        this.dateDepart = dateDepart;
        this.decision = decision;
        this.numDossier = numDossier;
        this.etablissement = etablissement;
    }

    public Etudiant(String numInscription, String nom, Date dateNaissance, String lieuNaissance, String niveau, String codeNationale, Date dateDepart, String decision, String numDossier, Etablissement etablissement) {
        this.numInscription = numInscription;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.niveau = niveau;
        this.codeNationale = codeNationale;
        this.dateDepart = dateDepart;
        this.decision = decision;
        this.numDossier = numDossier;
        this.etablissement = etablissement;
    }

    public Etudiant(int id, String numInscription, String nom, Date dateNaissance, String lieuNaissance, String niveau, String codeNationale, Date dateDepart, String decision, String numDossier) {
        this.id = id;
        this.numInscription = numInscription;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.niveau = niveau;
        this.codeNationale = codeNationale;
        this.dateDepart = dateDepart;
        this.decision = decision;
        this.numDossier = numDossier;
    }
    
    public Etudiant() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumInscription() {
        return numInscription;
    }

    public void setNumInscription(String numInscription) {
        this.numInscription = numInscription;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getCodeNationale() {
        return codeNationale;
    }

    public void setCodeNationale(String codeNationale) {
        this.codeNationale = codeNationale;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getNumDossier() {
        return numDossier;
    }

    public void setNumDossier(String numDossier) {
        this.numDossier = numDossier;
    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    
}
