package Formes.MouseAdapter;

import Formes.DessinLibre;
import Formes.Point;
import GUI.Appli;
import GUI.Ardoise;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Lauréna
 * 
 * Le MouseAdapter utilisé quand on dessine librement
 */

public class DessinLibreMouseAdapter extends MouseAdapter {

    Point pointClique;
    Point nextPoint;
    ArrayList<Point> tabPoints;

    Ardoise ardoise;
    Appli app;

    public DessinLibreMouseAdapter(Ardoise ardoise, Appli app) {
        this.ardoise = ardoise;
        this.app = app;
    }

    @Override
    public void mouseClicked (MouseEvent e) {
        Point p = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
        ardoise.setdCourant(p);
        ardoise.getdCourant().getStyle().setCouleur(app.color);
        ardoise.getdCourant().getStyle().setEpaisseur(app.value);
        ardoise.getListeDes().add(ardoise.getdCourant());
        ardoise.setdCourant(null);
        p = null;
        ardoise.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (pointClique != null) {
            ardoise.getListeDes().add(ardoise.getdCourant());
            ardoise.setdCourant(null);
            pointClique = null;
        } 
        ardoise.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (pointClique == null) {
            tabPoints = new ArrayList<>();
            pointClique = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            tabPoints.add(pointClique);
            ardoise.setdCourant(new DessinLibre(tabPoints));
        }
        else {
            nextPoint = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            tabPoints.add(nextPoint);
            ardoise.setdCourant(new DessinLibre(tabPoints));
            ardoise.getdCourant().getStyle().setCouleur(app.color);
            ardoise.getdCourant().getStyle().setEpaisseur(app.value);

        }
        ardoise.repaint();
    }
}
