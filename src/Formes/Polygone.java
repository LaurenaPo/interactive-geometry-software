package Formes;

import GUI.Ardoise;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Lauréna
 */

public class Polygone implements Dessinable, Perimetrable, Surfacable, Convex {
    ArrayList<Point> tabPoints;
    StyleGraphique style;

    public Polygone(ArrayList<Point> tabPoints) {
        this.tabPoints = tabPoints;
        this.style = new StyleGraphique(Color.black, 1);  
    }
    
    
    @Override
    public void dessineSur(Ardoise a) {
        int size = this.tabPoints.size();
        Point ti;
        Point ti1;
        for (int i=0; i<size - 1; i++) { 
            ti = this.tabPoints.get(i);
            ti1 = this.tabPoints.get(i+1);
            
            a.dessineSegment(ti.x, ti.y, ti1.x, ti1.y);
        }
        a.dessineSegment(this.tabPoints.get(0).x, this.tabPoints.get(0).y, this.tabPoints.get(size - 1).x, this.tabPoints.get(size - 1).y);
    }

    @Override
    public double distance(Point p) {
        // On suppose que la taille de la liste est supérieur à 2
        int size = this.tabPoints.size();
        double dmin = Double.MAX_VALUE;
        double d;
        for (int i=0; i<size - 1; i++) { 
            Segment segment = new Segment(this.tabPoints.get(i), this.tabPoints.get(i+1));
            d = segment.distance(p);
            if(d < dmin) {
                dmin = d;
            }
        }
        Segment segmentFin = new Segment(this.tabPoints.get(size -1),this.tabPoints.get(0));
        Double dFin = segmentFin.distance(p);
        if (dFin < dmin) {
            dmin = dFin;
        }
        return dmin;
    }

    @Override
    public double getPerimetre() {
        double perimetre = 0;
        int size = tabPoints.size();
        for (int i=0; i<size-2; i++) {
            perimetre += tabPoints.get(i).distance(tabPoints.get(i+1));
        }
        perimetre += tabPoints.get(0).distance(tabPoints.get(size-1));
        perimetre += tabPoints.get(size-2).distance(tabPoints.get(size-1));
        return perimetre;
    }

    //Test si deux segments s'interceptent :
    public static boolean isIntersection(Point p1, Point p2, Point p3, Point p4) {
           Vecteur v1 = new Vecteur(p1, p2);
           Vecteur v2 = new Vecteur(p3, p4);
           
           Vecteur v3 = new Vecteur(p1, p4);
           Vecteur v4 = new Vecteur(p1, p3);
           
           Vecteur v5 = new Vecteur(p3, p2);
           Vecteur v6 = new Vecteur(p3, p1);
           
           if (p2 != p3) {
                if (!Calcul.isNul(v1.prod(v2))) { //Les droites ne sont pas parallèles
                    if (v1.prod(v3) * v1.prod(v4) <= 0) { //point d'intersection entre p3 et p4
                        if (v2.prod(v5) * v2.prod(v6) <= 0) { //point d'intersection entre p1 et p2
                            return true;
                        }
                    }
                }
           }
           return false;
    }
    
    //Test si le Polygone est croisé :
    public boolean isCroise() {
        int size = tabPoints.size();
        for (int i = 0; i < size - 1; i++) {
            if (i!=0 && i!= size - 1 && i!= size - 2) {
                if (isIntersection(tabPoints.get(size - 1), tabPoints.get(0), tabPoints.get(i), tabPoints.get(i+1))){
                    return true;
                }
            }
            for (int j = i+2; j < size - 1; j++) {
                if (isIntersection(tabPoints.get(i), tabPoints.get(i+1), tabPoints.get(j), tabPoints.get(j+1))) {
                    return true;
                }
            }    
        }
        return false;
    }
    
    @Override
    public double getSurface() {
        if (isCroise()) {
            return 0;
        }
        else {
            double aire = 0;
            int size = tabPoints.size();

            if (size < 3) return -1;

            for (int i=1; i<size - 1; i++) {
                aire += tabPoints.get(i).x * (tabPoints.get(i+1).y - tabPoints.get(i-1).y);
            }
            aire += tabPoints.get(0).x * (tabPoints.get(1).y - tabPoints.get(size-1).y); 
            aire += tabPoints.get(size - 1).x * (tabPoints.get(0).y - tabPoints.get(size - 2).y);
            return Math.abs(aire)/2;
        }
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
    
    @Override
    public boolean isConvex() {
        boolean bn = false;
        boolean bp = false;
        
        int size = tabPoints.size();
        int j, k;
        for (int i=0; i<size; i++) {
            j = (i+1)%size;
            k = (j+1)%size;
             
            Vecteur v1 = new Vecteur(tabPoints.get(i), tabPoints.get(j));
            Vecteur v2 = new Vecteur(tabPoints.get(i), tabPoints.get(k));
            
            int prod = (int) v1.prod(v2);
            
            if (prod < 0){
                bn = true;
            }
            else if(prod > 0){
                bp = true;
            }
            if (bn && bp) {
                return false;
            }
        }
        return true;
    }

    
}
