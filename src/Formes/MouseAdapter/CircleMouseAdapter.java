
package Formes.MouseAdapter;

import Formes.Cercle;
import Formes.Point;
import GUI.Appli;
import GUI.Ardoise;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Lauréna
 * 
 * Le MouseAdapter utilisé quand on dessine un cercle
 */

public class CircleMouseAdapter extends MouseAdapter {

    Point centre;
    Ardoise ardoise;
    Appli app;

    public CircleMouseAdapter(Ardoise ardoise, Appli app) {
        this.ardoise = ardoise;
        this.app = app;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //le premier clic, on crée le cercle et on l'affecte a dCourant
        if (centre == null) {
            centre = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            ardoise.setdCourant(new Cercle(centre, centre));
        }
        //le second clic, on l'ajoute a la liste et on reinitialise
        else {
            ardoise.getListeDes().add(ardoise.getdCourant());
            ardoise.setdCourant(null);
            centre = null;
        }
        ardoise.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //si le centre a deja ete choisi, on reaffecte dCour en fonction de l'emplacement de la souris
        if (centre != null) {
            Point p = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            ardoise.setdCourant(new Cercle(centre, p));
            ardoise.getdCourant().getStyle().setCouleur(app.color);
            ardoise.getdCourant().getStyle().setEpaisseur(app.value);
        }
        ardoise.repaint();
    }

}