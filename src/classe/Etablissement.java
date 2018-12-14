package classe;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Etablissement extends RecursiveTreeObject<Etablissement> {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;
    private String type;
    private String region;
    private String acadimie;
    private String code;
    private String tel;
    private String baladiya;

    public Etablissement(int id, String nom, String type, String region, String acadimie, String code, String tel, String baladiya) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.region = region;
        this.acadimie = acadimie;
        this.code = code;
        this.tel = tel;
        this.baladiya = baladiya;
    }

    public Etablissement(String nom, String type, String region, String acadimie, String code, String tel, String baladiya) {
        this.nom = nom;
        this.type = type;
        this.region = region;
        this.acadimie = acadimie;
        this.code = code;
        this.tel = tel;
        this.baladiya = baladiya;
    }
    
    
    
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBaladiya() {
        return baladiya;
    }

    public void setBaladiya(String baladiya) {
        this.baladiya = baladiya;
    }

    public Etablissement(int id, String nom, String type, String region, String acadimie) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.region = region;
        this.acadimie = acadimie;
    }

    public Etablissement(String nom, String type, String region, String acadimie) {
        this.nom = nom;
        this.type = type;
        this.region = region;
        this.acadimie = acadimie;
    }

    public Etablissement() {
    }

    public String getAcadimie() {
        return acadimie;
    }

    public void setAcadimie(String acadimie) {
        this.acadimie = acadimie;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    
    
    
}
