package Formes.MouseAdapter;

import Formes.Convex;
import Formes.Dessinable;
import Formes.Perimetrable;
import Formes.Point;
import Formes.Surfacable;
import GUI.Appli;
import GUI.Ardoise;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Lauréna
 */
    /*
     * Ici on va écrire les différentes classes internes de MouseAdpater     
     */

    /**
     * Le MouseSelecAdapter utilisé quand on veut selectionner une forme
     */

public class SelecMouseAdapter extends MouseAdapter {

    Point pOld;
    Ardoise ardoise;
    Appli app;

    public SelecMouseAdapter(Ardoise ardoise, Appli app) {
        this.ardoise = ardoise;
        this.app = app;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
        double dmin = 0.2;
        Dessinable formes_dmin = null;
        double d;

        for (Dessinable formes : ardoise.getListeDes()) {
            if (ardoise.getdCourant() == formes) { // Si le ardoise.getdCourant() précédent est une forme: on cherche le point de la forme le plus proche du clique
                for (Point point : ardoise.getdCourant().getConstituants()) {
                    d = point.distance(p);
                    if (d < dmin) {
                        formes_dmin = point;
                        dmin = d;
                    }
                }
            } else if (ardoise.getdCourant() instanceof Point) { // Sinon si c'est un point et qu'on on reclique proche du point : le même point reste en ardoise.getdCourant()
                d = ardoise.getdCourant().distance(p);
                if (d < dmin) {
                    formes_dmin = ardoise.getdCourant();
                    dmin = d;
                }
            }

            if (!(formes_dmin instanceof Point)) { // Si la forme n'est pas trouvé : on regarde si la distance est bonne pour une autre forme
                d = formes.distance(p);
                if (d < dmin) {
                    formes_dmin = formes;
                    dmin = d;
                }
            }
        }

        if (formes_dmin instanceof Point) {
            this.pOld = (Point) formes_dmin;
        } else {
            this.pOld = p;
        }

        ardoise.setdCourant(formes_dmin);

        //Panneau de droite
        if (ardoise.getdCourant() instanceof Perimetrable) {
            Perimetrable formesPerimetrable = (Perimetrable) ardoise.getdCourant();
            ardoise.getApp().affichagePerimetre(formesPerimetrable.getPerimetre());
        } else {
            ardoise.getApp().affichagePerimetre(-1d);
        }

        if (ardoise.getdCourant() instanceof Surfacable) {
            Surfacable formesSurfacable = (Surfacable) ardoise.getdCourant();
            ardoise.getApp().affichageSurface(formesSurfacable.getSurface());
        } else {
            ardoise.getApp().affichageSurface(-1d);
        }

        if (ardoise.getdCourant() instanceof Convex) {
            Convex formesConvexe = (Convex) ardoise.getdCourant();
            ardoise.getApp().affichageIsConvexe(formesConvexe.isConvex());
        } else {
            ardoise.getApp().affichageIsConvexe(false);
        }

        if (ardoise.getdCourant() != null) {
            Class name = ardoise.getdCourant().getClass();
            ardoise.getApp().texteLabelNomElement(name);
            ardoise.getApp().remplissageBoutonCouleur(ardoise.getdCourant().getStyle().couleur);
            ardoise.getApp().setStrokeValue(ardoise.getdCourant().getStyle().epaisseur);
            app.color = ardoise.getdCourant().getStyle().couleur;
            
        } else {
            ardoise.getApp().reinitialiserAffichage();
        }

        ardoise.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (ardoise.getdCourant() != null) {
            Point pNew = new Point(ardoise.getXRepere(e.getX()), ardoise.getYRepere(e.getY()));
            ardoise.getdCourant().translate(pOld, pNew);
            pOld = pNew;
            pNew = null;
            ardoise.repaint();
        }
    }
}
