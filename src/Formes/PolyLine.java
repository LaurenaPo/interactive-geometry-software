
package Formes;

import GUI.Ardoise;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Lauréna
 */

public class PolyLine implements Dessinable,Perimetrable {
    ArrayList<Point> tabPoints;
    StyleGraphique style;

    public PolyLine(ArrayList<Point> tabPoints) {
        this.tabPoints = tabPoints;
        this.style = new StyleGraphique(Color.black, 1);  
    }


    @Override
    public void dessineSur(Ardoise a) {
        for (int i=0; i<this.tabPoints.size() - 1; i++) {      
            a.dessineSegment(this.tabPoints.get(i).x, this.tabPoints.get(i).y, this.tabPoints.get(i+1).x, this.tabPoints.get(i+1).y);
        }
    }

    @Override
    public double distance(Point p) {
        // Pour une PolyLine, on suppose que la taille de la liste est supérieur à 2
        double dmin = Double.MAX_VALUE;
        double d;
        for (int i=0; i<this.tabPoints.size() - 1; i++) { 
            Segment segment = new Segment(this.tabPoints.get(i), this.tabPoints.get(i+1));
            d = segment.distance(p);
            if(d < dmin) {
                dmin = d;
            }
        }
        return dmin;
    }

    @Override
    public double getPerimetre() {
        double perimetre = 0;
        for (int i=0; i<tabPoints.size()-2; i++) {
            perimetre += tabPoints.get(i).distance(tabPoints.get(i+1));
        }
        perimetre += tabPoints.get(tabPoints.size()-1).distance(tabPoints.get(0));
        return perimetre;
    }


    @Override
    public void translate(Point a, Point b) {
        Vecteur ab = new Vecteur(a, b);
        for (Point p : this.tabPoints) {
            p.x += ab.x;
            p.y += ab.y;
        }
    }

    @Override
    public ArrayList<Point> getConstituants() {
        ArrayList<Point> list = new ArrayList<>();
        for (Point p : this.tabPoints) {
            list.add(p);
        }
        return list;
    }

    @Override
    public StyleGraphique getStyle() {
        return this.style;
    }
    
}
