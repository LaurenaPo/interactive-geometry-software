package Formes.MouseAdapter;

import Formes.Carre;
import Formes.Point;
import Formes.Vecteur;
import GUI.Appli;
import GUI.Ardoise;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Lauréna
 * 
 * Le MouseAdapter utilisé quand on dessine un Carré
 */

public class CarreMouseAdapter extends MouseAdapter {

    Point p;
    Ardoise ardoise;
    Appli app;

    public CarreMouseAdapter(Ardoise ardoise, Appli app) {
        this.ardoise = ardoise;
        this.app = app;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (p == null) {
            p = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            ardoise.setdCourant(p);
            ardoise.repaint();
        }
        else {
            ardoise.getListeDes().add(ardoise.getdCourant());
            ardoise.setdCourant(null);
            p = null;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (p != null) {
            Point pDrag = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            
            //Coordonnées du milieu des diagonales :
            double xm = (p.x + pDrag.x)/2;
            double ym = (p.y + pDrag.y)/2;
            Point pMilieu = new Point(xm, ym);
            
            Vecteur vDiagonale = new Vecteur(p, pMilieu);
            Vecteur vn = vDiagonale.normale();
            
            double x1 = xm + vn.getX();
            double y1 = ym + vn.getY();
            Point p1 = new Point(x1, y1);

            double x2 = xm - vn.getX();
            double y2 = ym - vn.getY();
            Point p2 = new Point(x2, y2);

            ardoise.setdCourant(new Carre(p, p1, pDrag, p2));
            ardoise.getdCourant().getStyle().setCouleur(app.color);
            ardoise.getdCourant().getStyle().setEpaisseur(app.value);
        }
        ardoise.repaint();
    }
}
