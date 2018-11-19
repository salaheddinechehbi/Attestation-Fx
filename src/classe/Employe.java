/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author salah
 */
@Entity
public class Employe {
    
    @Id @GeneratedValue(strategy =  GenerationType.AUTO)
    private int id;
    private String nom;
    private String prenom;
    private String email;
    @ManyToOne
    private Profil profil;
    private String password;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    @Temporal(TemporalType.DATE)
    private Date dateEmbouche;
    @ManyToOne
    private Etablissement etablissement;

    public Employe() {
    }

    public Employe(String nom, String prenom, String email, Profil profil, String password, Date dateNaissance, Date dateEmbouche, Etablissement etablissement) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.profil = profil;
        this.password = password;
        this.dateNaissance = dateNaissance;
        this.dateEmbouche = dateEmbouche;
        this.etablissement = etablissement;
    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

   
    public Date getDateEmbouche() {
        return dateEmbouche;
    }

    public void setDateEmbouche(Date dateEmbouche) {
        this.dateEmbouche = dateEmbouche;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
