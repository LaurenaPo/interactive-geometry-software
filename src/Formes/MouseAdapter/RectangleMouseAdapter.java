package Formes.MouseAdapter;

import Formes.Point;
import Formes.Rectangle;
import Formes.Segment;
import Formes.Vecteur;
import GUI.Appli;
import GUI.Ardoise;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Lauréna
 * 
 * Le MouseAdapter utilisé quand on dessine un Rectangle
 */

public class RectangleMouseAdapter extends MouseAdapter {

    int clique = 0;
    Point p1;
    Point p2;

    Ardoise ardoise;
    Appli app;

    public RectangleMouseAdapter(Ardoise ardoise, Appli app) {
        this.ardoise = ardoise;
        this.app = app;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (clique == 0) {
            clique ++;
            p1 = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            ardoise.setdCourant(p1);
        }
        else {
            if (clique == 1){
                clique ++;
                p2 = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
                ardoise.setdCourant(new Segment(p1, p2));
            }
            else {
                ardoise.getListeDes().add(ardoise.getdCourant());
                ardoise.setdCourant(null);
                clique = 0;
            }
        }
        ardoise.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (clique == 1) {
            p2 = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            ardoise.setdCourant(new Segment(p1, p2));
        }
        if (clique == 2){
            Point pSouris = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            Vecteur vp1ps = new Vecteur(p1, pSouris);

            //Vecteur normal normé de la droite (p1, p2) :
            Vecteur vDirecteur = new Vecteur(p1, p2);
            double xn = vDirecteur.getY();
            double yn = -vDirecteur.getX();       
            Vecteur vNormal = new Vecteur(xn, yn);
            double norme = Math.sqrt(vNormal.getNorm2());
            vNormal.setX(xn / norme);
            vNormal.setY(yn / norme);

            double scal = vNormal.scal(vp1ps);

            double xp3 = p2.x + scal*vNormal.getX();
            double yp3 = p2.y + scal*vNormal.getY();

            Point p3 = new Point(xp3, yp3);

            //On calcul les coordonnées du milieu des diagonales :
            double xm = (p1.x + p3.x)/2;
            double ym = (p1.y + p3.y)/2;
            //On en déduit les coordonnées du quatrième point :
            double x4 = 2*xm - p2.x;
            double y4 = 2*ym - p2.y;
            Point p4 = new Point(x4, y4);

            ardoise.setdCourant(new Rectangle(p1, p2, p3, p4));
            ardoise.getdCourant().getStyle().setCouleur(app.color);
            ardoise.getdCourant().getStyle().setEpaisseur(app.value);
        }
        ardoise.repaint();
    }
}
