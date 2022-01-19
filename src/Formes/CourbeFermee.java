package Formes;

/**
 *
 * @author pierrecharbit
 */

public interface CourbeFermee extends Courbe, Perimetrable, Surfacable{
    boolean isConvex();  
    boolean interieurContient(Point p);
    //public void remplirForme(Ardoise a); 
}
