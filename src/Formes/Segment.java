package Formes;

import GUI.Ardoise;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Lauréna
 */
public class Segment implements Dessinable, Perimetrable {
    Point p1;
    Point p2;
    StyleGraphique style;

    public Segment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.style = new StyleGraphique(Color.black, 1);
    }

    @Override
    public void dessineSur(Ardoise a) {
        a.dessineSegment(p1.x, p1.y, p2.x, p2.y); 
    }

    @Override
    public double distance(Point p) {
        Vecteur ab = new Vecteur(p1, p2);
        Vecteur ap = new Vecteur(p1, p);
        double t = ap.scal(ab) / ab.getNorm2();
        t = Math.min(Math.max(0, t), 1);
        
        //projeté orthogonal
        double pox = p1.x + ab.getX()*t;
        double poy = p1.y + ab.getY()*t;
        Point po = new Point(pox, poy);
        
        Vecteur ppo = new Vecteur(p, po);
        return Math.sqrt(ppo.getNorm2());
        
    }

    @Override
    public void translate(Point a, Point b) {
        Vecteur ab = new Vecteur(a, b);
        this.p1.x += ab.x;
        this.p1.y += ab.y;
        this.p2.x += ab.x;
        this.p2.y += ab.y; 
    }

    @Override
    public ArrayList<Point> getConstituants() {
        ArrayList<Point> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        return list;
    }

    @Override
    public StyleGraphique getStyle() {
        return this.style;
    }

    @Override
    public double getPerimetre() {
        return p1.distance(p2);
    }
    
}
