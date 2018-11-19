package classe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author salah
 */
@Entity
public class Etablissement {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;
    private String type;
    private String region;

    public Etablissement(int id, String nom, String type, String region) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.region = region;
    }

    public Etablissement(String nom, String type, String region) {
        this.nom = nom;
        this.type = type;
        this.region = region;
    }

    public Etablissement() {
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
