package Formes.MouseAdapter;

import Formes.Droite;
import Formes.Point;
import GUI.Appli;
import GUI.Ardoise;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Lauréna
 * 
 * Le MouseAdapter utilisé quand on dessine une Droite
 */

public class DroiteLineMouseAdapter extends MouseAdapter {
        
    Point p1;
    Ardoise ardoise;
    Appli app;

    public DroiteLineMouseAdapter(Ardoise ardoise, Appli app) {
        this.ardoise = ardoise;
        this.app = app;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        //le premier clic, on crée un point et on l'affecte a dCourant
        if (p1 == null) {
            p1 = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            ardoise.setdCourant(p1);
        }

        else {
            ardoise.getListeDes().add(ardoise.getdCourant());
            ardoise.setdCourant(null);
            p1 = null; 
        }   
        ardoise.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (p1 != null) {
            Point p2 = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            ardoise.setdCourant(new Droite(p1, p2));
            ardoise.getdCourant().getStyle().setCouleur(app.color);
            ardoise.getdCourant().getStyle().setEpaisseur(app.value);
        }
        ardoise.repaint();
    }
}
