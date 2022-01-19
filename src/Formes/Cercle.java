
package Formes;

import GUI.Ardoise;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author pierrecharbit && Lauréna
 */
public class Cercle implements Dessinable, CourbeFermee {

    Point centre;
    Point bord;
    StyleGraphique style;

    
    public Cercle(Point centre, Point bord){
        this.centre=centre;
        this.bord=bord;
        this.style = new StyleGraphique(Color.black, 1);
    }

    public Point getCentre() {
        return centre;
    }
    
    public Cercle(double a, double b, double r) { //Constructeur pour équation
        this.centre = new Point(a, b); 
        this.bord = new Point(r+a, b);
        this.style = new StyleGraphique(Color.black, 1);
    }

    public double getRayon(){
        return centre.distance(bord);
    }
    
    @Override
    public boolean contient(Point p) {
        return Calcul.isNul(p.distance(centre)-getRayon());
    }


    @Override
    public boolean interieurContient(Point p) {
        return p.distance(centre)<getRayon();
    }

    @Override
    public double getPerimetre() {
        return 2*Math.PI*getRayon();
    }

    @Override
    public double getSurface() {
        return Math.PI*getRayon()*getRayon();
    }

    @Override
    public boolean isConvex() {
        return true;
    }

        @Override
    public void dessineSur(Ardoise a) {
        a.dessineCercle(centre.x,centre.y,getRayon());
    }

    @Override
    public double distance(Point p) {
        return Math.abs(centre.distance(p) - getRayon());
    }

    @Override
    public void translate(Point a, Point b) {
        Vecteur ab = new Vecteur(a, b);
        this.centre.x += ab.x;
        this.centre.y += ab.y;
        this.bord.x += ab.x;
        this.bord.y += ab.y;
    }
    
    @Override
    public ArrayList<Point> getConstituants() {
        ArrayList<Point> list = new ArrayList<>();
        list.add(bord);
        list.add(centre);
        return list;
    }

    @Override
    public StyleGraphique getStyle() {
        return this.style;
    }
}
