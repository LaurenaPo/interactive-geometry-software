package Formes.MouseAdapter;

import Formes.Point;
import Formes.PolyLine;
import GUI.Appli;
import GUI.Ardoise;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Lauréna
 * 
 * Le MouseAdapter utilisé quand on dessine une Polyline
 */

public class PolyLineMouseAdapter extends MouseAdapter  {

    Point pointClique;
    Point nextPoint;
    ArrayList<Point> tabPoints;

    Ardoise ardoise;
    Appli app;

    public PolyLineMouseAdapter(Ardoise ardoise, Appli app) {
        this.ardoise = ardoise;
        this.app = app;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        //le premier clic, on crée un point et on l'affecte a dCourant
        if (pointClique == null) {
            tabPoints = new ArrayList<>();
            pointClique = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            tabPoints.add(pointClique);
            ardoise.setdCourant(pointClique);
        }

        else { 
            if (e.getButton() == MouseEvent.BUTTON3){ // Si c'est un clic droit : on ajoute la forme à la liste
                    ardoise.getListeDes().add(ardoise.getdCourant());
                    ardoise.setdCourant(null);
                    pointClique = null;

            }  
            else {
                nextPoint = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
                tabPoints.add(nextPoint);
                ardoise.setdCourant(new PolyLine(tabPoints));
                ardoise.getdCourant().getStyle().setCouleur(app.color);
                ardoise.getdCourant().getStyle().setEpaisseur(app.value);
            }
        }   
        ardoise.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (pointClique != null) {
            nextPoint = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            if (tabPoints.size() > 1) {
                tabPoints.remove(tabPoints.size() - 1);
            }
            tabPoints.add(nextPoint);
            ardoise.setdCourant(new PolyLine(tabPoints));
            ardoise.getdCourant().getStyle().setCouleur(app.color);
            ardoise.getdCourant().getStyle().setEpaisseur(app.value);
        }
        ardoise.repaint();
    }
}
