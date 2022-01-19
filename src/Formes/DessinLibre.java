package Formes;

import GUI.Ardoise;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Lauréna
 */
public class DessinLibre implements Dessinable{
    ArrayList<Point> tabPoints;
    StyleGraphique style;

    public DessinLibre(ArrayList<Point> tabPoints) {
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
        //list.add(tabPoints.get(tabPoints.size() - 1));
        return list;
    }
    
    @Override
    public StyleGraphique getStyle() {
        return this.style;
    }

}

