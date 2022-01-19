package Formes;

import GUI.Ardoise;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Lauréna
 */

public class Droite implements Dessinable {
    Point p1;
    Point p2;
    StyleGraphique style;

    public Droite(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.style = new StyleGraphique(Color.black, 1);
    }
    
    public Droite(double a, double b, double c) { //Constructeur pour equation cartésienne : 
        //on détermine deux point appartenant à la droite
        double x1 = 1.0;
        double y1 = (-a - c) / b;
        this.p1 = new Point(x1, y1);
        
        double x2 = -1.0;
        double y2 = (a - c) / b;
        this.p2 = new Point(x2, y2);
        
        this.style = new StyleGraphique(Color.black, 1);
    }
    
    @Override
    public void dessineSur(Ardoise a) {
       //a.dessineSegment(this.p1.x, this.p1.y, this.p2.x, this.p2.y);
       if (p1.x == p2.x) {
           a.dessineSegment(p1.x, a.getyMin(), p2.x, a.getyMax());
       }
       else {
           double c = (p2.y - p1.y) / (p2.x - p1.x); // coefficient directeur
           double b = p1.y - c*p1.x; // ordonnée à l'origine
           double xMin = a.getxMin();
           double xMax = a.getxMax();
           a.dessineSegment(xMin, xMin*c + b, xMax, xMax*c + b);
       } 
    }

    @Override
    public double distance(Point p) {
        Vecteur vdirecteur = new Vecteur(p1, p2);
        double a = vdirecteur.getY();
        double b = -vdirecteur.getX();
        double c = -a*p1.x -b*p1.y;
        
        return Math.abs((a*p.x + b*p.y + c) / (Math.sqrt(a*a + b*b)));
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
    
}
