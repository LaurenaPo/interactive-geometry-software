
package Formes;

/**
 * Une classe contenant juste une méthode statique pour dire qu'un double peut etre considéré comme nul
 * @author pierrecharbit && Lauréna
 */
public class Calcul {
    static final double precision = 0.0000000001;
    
    public static boolean isNul(double d){
        return Math.abs(d)<precision;
    }
    
    public static double norm2(double a, double b){
        return Math.sqrt(a*a+b*b);
    }
    
    //Minimum entre 4 points ( utilisé dans les classes Parallelogramme et Rectangle)
    public static double minimum(double a, double b, double c, double d){
        double min1 = Math.min(a, b);
        double min2 = Math.min(c, d);
        return Math.min(min1, min2);
    }
}
