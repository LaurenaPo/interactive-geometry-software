package Formes.MouseAdapter;

import Formes.Parallelogramme;
import Formes.Point;
import Formes.Segment;
import GUI.Appli;
import GUI.Ardoise;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Lauréna
 * 
 * Le MouseAdapter utilisé quand on dessine un Parallelogramme
 */

public class ParallelogrammeMouseAdapter extends MouseAdapter {

    int clique = 0;
    Point p1;
    Point p2;
    Point p3;
    Point p4;

    Ardoise ardoise;
    Appli app;

    public ParallelogrammeMouseAdapter(Ardoise ardoise, Appli app) {
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
            p3 = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));

            //On calcul les coordonnées du milieu des diagonales :
            double xm = (p1.x + p3.x)/2;
            double ym = (p1.y + p3.y)/2;
            //On en déduit les coordonnées du quatrième point :
            double x4 = 2*xm - p2.x;
            double y4 = 2*ym - p2.y;
            p4 = new Point(x4, y4);

            ardoise.setdCourant(new Parallelogramme(p1, p2, p3, p4));
            ardoise.getdCourant().getStyle().setCouleur(app.color);
            ardoise.getdCourant().getStyle().setEpaisseur(app.value);
        }
        ardoise.repaint();
    }
}
