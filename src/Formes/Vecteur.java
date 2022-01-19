
package Formes;

/**
 *
 * @author pierrecharbit && Lauréna
 */
public class Vecteur {
    double x;
    double y;

    public Vecteur(double x, double y) {
        this.x = x;
        this.y = y;
    }
  
    public Vecteur(Point a, Point b) {
        this.x = b.x-a.x;
        this.y = b.y-a.y;
    } 
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    
    public double getNorm2(){
        return x*x + y*y;
    }
            
    public double scal(Vecteur v){
        return this.getX()*v.getX() + this.getY()*v.getY();
    }
    
    public double prod(Vecteur v){
        return this.getX()*v.getY() - this.getY()*v.getX();
    }
    
    public boolean isColinear(Vecteur v){
        return Calcul.isNul(this.prod(v));
    }
    
    public boolean isOrthogonal(Vecteur v){
        return Calcul.isNul(this.scal(v));
    }
    
    //Retourne un vecteur normale:
    public Vecteur normale() { 
        return new Vecteur(-y, x); 
    }
    
    //Retourne un vecteur unitaire
    public Vecteur normaliser(){
      double norme = Math.sqrt(this.getNorm2());
      return new Vecteur(x/norme, y/norme);
    }
    
    //Multiplie un vecteur par un double
    public Vecteur multiplier(double d) {
      return new Vecteur(x * d, y * d);
    }

}
