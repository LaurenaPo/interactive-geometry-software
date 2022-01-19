package Formes;

import GUI.Ardoise;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Lauréna
 */
public class Parallelogramme implements Dessinable, Perimetrable, Surfacable, Convex {
    Point p1;
    Point p2;
    Point p3;
    Point p4;
    StyleGraphique style;
    
    public Parallelogramme(Point p1, Point p2, Point p3, Point p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.style = new StyleGraphique(Color.black, 1);      
    }
    
    @Override
    public void dessineSur(Ardoise a) {
        a.dessineSegment(p1.x, p1.y, p2.x, p2.y);
        a.dessineSegment(p2.x, p2.y, p3.x, p3.y);
        a.dessineSegment(p3.x, p3.y, p4.x, p4.y);
        a.dessineSegment(p4.x, p4.y, p1.x, p1.y);
    }

    @Override
    public double distance(Point p) {
        Segment segment1 = new Segment(p1, p2);
        Segment segment2 = new Segment(p2, p3);
        Segment segment3 = new Segment(p3, p4);
        Segment segment4 = new Segment(p4, p1);
        
        double d1 = segment1.distance(p);
        double d2 = segment2.distance(p);
        double d3 = segment3.distance(p);
        double d4 = segment4.distance(p);
        
        return Calcul.minimum(d1, d2, d3, d4);
    }

    @Override
    public double getPerimetre() {
        double a = p1.distance(p2);
        double b = p2.distance(p3);
        return 2*a + 2*b;
    }

    @Override
    public double getSurface() {
        return Math.abs((p2.x - p1.x)*(p4.y - p1.y) - (p4.x - p1.x)*(p2.y - p1.y));
    }

    @Override
    public void translate(Point a, Point b) {
        Vecteur ab = new Vecteur(a, b);
        this.p1.x += ab.x;
        this.p1.y += ab.y;
        this.p2.x += ab.x;
        this.p2.y += ab.y;
        this.p3.x += ab.x;
        this.p3.y += ab.y;
        this.p4.x += ab.x;
        this.p4.y += ab.y;         
    }

    @Override
    public ArrayList<Point> getConstituants() {
        ArrayList<Point> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        return list;
    }

    @Override
    public StyleGraphique getStyle() {
        return this.style;
    }

    @Override
    public boolean isConvex() {
        return true;
    }
    
}
