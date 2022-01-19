package Formes;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Lauréna
 */
public class StyleGraphique implements Serializable {
    public Color couleur;
    public int epaisseur = 1;

    public StyleGraphique(Color couleur, int epaisseur) {
        this.couleur = couleur;
        this.epaisseur = epaisseur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public void setEpaisseur(int epaisseur) {
        this.epaisseur = epaisseur;
    }

}
