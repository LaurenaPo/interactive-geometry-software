
package Formes;

import GUI.Ardoise;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Lauréna
 */
public class Point implements Dessinable {
    String label;
    public double x;
    public double y;
    StyleGraphique style;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.style = new StyleGraphique(Color.black, 1);  
    }

    public Point(String label, double x, double y) {
        this.label = label;
        this.x = x;
        this.y = y;
        this.style.couleur = Color.BLACK;
        this.style.epaisseur = 1; 
        this.style = new StyleGraphique(Color.black, 1);
    }  

    @Override
    public double distance(Point p){
        return Calcul.norm2(x-p.x,y-p.y);
    }
    
    @Override
    public void dessineSur(Ardoise a) {
        a.dessinePoint(this.x,this.y);
    }

    @Override
    public String toString() {
        return "Point : "+this.x+","+this.y;
    }

    @Override
    public void translate(Point a, Point b) {
        Vecteur ab = new Vecteur(a, b);
        this.x += ab.x;
        this.y += ab.y;
    }

    @Override
    public ArrayList<Point> getConstituants() {
        ArrayList<Point> list = new ArrayList<>();
        Point p = new Point(x, y);
        list.add(p);
        return list;
    }

    @Override
    public StyleGraphique getStyle() {
        return this.style;
    }
    
    //Translate un point grâce à un vecteur donné :
    public Point translation(Vecteur v) {
        return new Point(v.getX() + x, v.getY() + y);
    }
    
}
