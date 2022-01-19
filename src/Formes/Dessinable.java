
package Formes;

import GUI.Ardoise;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Lauréna
 */
public interface Dessinable extends Serializable  {
    void dessineSur(Ardoise a);
    
    double distance(Point p);
    
    void translate(Point a, Point b);

    public ArrayList<Point> getConstituants();
    
    public StyleGraphique getStyle();
    
    //public StyleGraphique setStyle();
}
