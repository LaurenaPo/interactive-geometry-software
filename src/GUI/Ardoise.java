package GUI;

import Formes.DessinLibre;
import Formes.Dessinable;
import Formes.Droite;
import Formes.MouseAdapter.CarreMouseAdapter;
import Formes.MouseAdapter.CircleMouseAdapter;
import Formes.MouseAdapter.DessinLibreMouseAdapter;
import Formes.MouseAdapter.DroiteLineMouseAdapter;
import Formes.MouseAdapter.ParallelogrammeMouseAdapter;
import Formes.MouseAdapter.PolyLineMouseAdapter;
import Formes.MouseAdapter.PolygoneMouseAdapter;
import Formes.MouseAdapter.RectangleMouseAdapter;
import Formes.MouseAdapter.SelecMouseAdapter;
import Formes.Point;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import javax.swing.JPanel;


/**
 *
 * @author pierrecharbit && Lauréna
 */
public class Ardoise extends JPanel {

    
    /**
     * l'objet graphics qui sert aux méthodes de dessin. 
     */
    Graphics2D crayon;
    
    /**
     * La liste des objets dessinables
     */
    private ArrayList<Dessinable> liste;
    
    /**
     * le Dessinable courant *
     */
    private Dessinable dCourant;
    
    /**
     * le MouseAdapter ecouteur du panneau. Cet ecouteur va changer selon ce qu'on est en train
     * de faire, on va donc créer des classes internes (voir fin du fichier) pour les différents modes de l'appli
     */
    private MouseAdapter mouseAda;
    
    /**
     * les coordonées min max de la partie visible actuellement dans l'ardoise
     */
    private double xMin, xMax, yMin, yMax;

    /**
     * Un pointeur vers l'appli qui contient cette ardoise (pour avoir un lien
     * dans les deux sens).
     * Ca pourra vous être utile.
     */
    private Appli app;

    private boolean booleanRepere = true; //Pour afficher ou non le repère
    private boolean booleanRemplir = false; //Pour remplir la forme
    
    /**
     * Le constructeur : il prend en arguemnt les coordonées de la fenetre
     * visible
     */
    public Ardoise(double xMin, double xMax, double yMin, double yMax) {
        this.setBackground(Color.white);
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;

        //creation des listes et de la map
        liste = new ArrayList<>();

        // au début un mouseSelecAdapter qui ne fait rien du tout 
        mouseAda = new SelecMouseAdapter(this, app);
    }

    /**
     *
     */
    public Ardoise() {
        this(-1, 10, -1, 10);
    }

    /**
     * change le mode de la souris
     *
     * @param mode un String, peut etre mieux de remplacer ca par un int ou un
     * membre d'une Enum (TO DO)
     */
    public void setMode(String mode) {
        this.removeMouseListener(mouseAda);
        this.removeMouseMotionListener(mouseAda);
        setdCourant(null);
        if (mode.equals("Cercle")) {
            mouseAda = new CircleMouseAdapter(this, app);
        }
        else if (mode.equals("Select")) {
            mouseAda = new SelecMouseAdapter(this, app);
        }
        else if (mode.equals("PolyLine")) {
            mouseAda = new PolyLineMouseAdapter(this, app);
        }
        else if (mode.equals("Droite")) {
            mouseAda = new DroiteLineMouseAdapter(this, app);
        }
        else if (mode.equals("Polygone")) {
            mouseAda = new PolygoneMouseAdapter(this, app);
        }
        else if (mode.equals("Parallelogramme")) {
            mouseAda = new ParallelogrammeMouseAdapter(this, app);
        }
        else if (mode.equals("Rectangle")) {
            mouseAda = new RectangleMouseAdapter(this, app);
        }
        else if (mode.equals("Carre")) {
            mouseAda = new CarreMouseAdapter(this, app);
        }
        else if (mode.equals("DessinLibre")) {
            mouseAda = new DessinLibreMouseAdapter(this, app);
        }
        this.addMouseListener(mouseAda);
        this.addMouseMotionListener(mouseAda);

    }

    /*
    * ATTENTION ON A FAIT LE CHOIX D'AVOIR LES OBJETS DESSINABLES (POINTS, DROITES ETC)
    * DONNÉES AVEC DES COORDONNÉES DOUBLE DANS UN REPERE ORTHONORMÉ MATHEMATIQUE STANDARD
    * 
    * LE PANNEAU FONCTIONNE POUR LE DESSIN EN PIXELS (ENTIERS) ET L'ORIGINE EST EN HAUT A GAUCHE DE L'ARDOISE
    * 
    * ICI ON TROUVE LES FONCTIONS DE CONVERSIONS ENTRE LES DEUX SYSTEMES DE COORDONNEES
     */
    /**
     * @param xRepere l'abscisse double du Point en tant qu'objet mathematique
     * de notre modele
     * @return l'abscisse entière du point dans le repère du panneau pour le
     * dessin. Peut etre negatif ou plus grand que la taille de la fenetre si le
     * point demandé est hors de la fenetre xmin xmax ymin ymax
     *
     */
    public int getXArdoise(double xRepere) {
        int largeurArdoise = this.getWidth();
        return (int) ((xRepere - xMin) * largeurArdoise / (xMax - xMin));
    }

    /**
     * @param yRepere l'ordonnée double du Point en tant qu'objet mathematique
     * de notre modele
     * @return l'ordonnée entière du point dans le repère du panneau pour le
     * dessin. Peut etre negatif ou plus grand que la taille de la fenetre si le
     * point demandé est hors de la fenetre xmin xmax ymin ymax
     *
     */
    public int getYArdoise(double yRepere) {
        int hauteurArdoise = this.getHeight();
        return hauteurArdoise - (int) ((yRepere - yMin) * hauteurArdoise / (yMax - yMin));
    }

    /**
     * @param xArdoise
     * @return fonction inverse de getXArdoise
     */
    public double getXRepere(int xArdoise) {
        int largeurArdoise = this.getWidth();
        return xMin + (0.0 + xArdoise) / largeurArdoise * (xMax - xMin);
    }

    /**
     * @param yArdoise
     * @return fonction inverse de getYArdoise
     */
    public double getYRepere(int yArdoise) {
        int hauteurArdoise = this.getHeight();
        return (hauteurArdoise - yArdoise) * (yMax - yMin) / (0.0 + hauteurArdoise) + yMin;
    }

    /**
     * ajoute un Dessinable a la liste
     *
     * @param d le DEssinable a jouter
     */
    public void ajoute(Dessinable d) {
        liste.add(d);
    }

    /*
    * Les trois commandes de base pour dessiner les formes
    * Elles sont utilisées par les méthodes dessineSur de chacune des classes Dessinable
    * Notez que l'on commence par convertir les coordonées double en int pour avoir les coordonées pour le dessin dans le panneau
     */
    public void dessineCercle(double x, double y, double rayon) {
        int xx = getXArdoise(x);
        int yy = getYArdoise(y);
        int rrx = (int) (rayon * this.getWidth() / (xMax - xMin));
        int rry = (int) (rayon * this.getHeight() / (yMax - yMin));
        
        crayon.drawOval(xx - rrx, yy - rry, 2 * rrx, 2 * rry); 
    }
    
    public void dessinePoint(double x, double y) {
        int xx = getXArdoise(x);
        int yy = getYArdoise(y);
        crayon.fillOval(xx - 5, yy - 5, 10, 10);
    }

    //Pour que les points du repère s'affichent correctement
    public void dessinePoint_repere(double x, double y, int pixelSize) {
        int xx = getXArdoise(x);
        int yy = getYArdoise(y);
        crayon.fillOval(xx - pixelSize/2 - 1, yy - pixelSize/2 - 1, pixelSize, pixelSize);
    }

    public void dessineSegment(double x1, double y1, double x2, double y2) {
        int xx1 = getXArdoise(x1);
        int yy1 = getYArdoise(y1);
        int xx2 = getXArdoise(x2);
        int yy2 = getYArdoise(y2);
        crayon.drawLine(xx1, yy1, xx2, yy2);
    }
    
    /* Tous les getters et setters publics */
    public double getxMin() {
        return xMin;
    }

    public void setxMin(double xMin) {
        this.xMin = xMin;
    }

    public double getxMax() {
        return xMax;
    }

    public void setxMax(double xMax) {
        this.xMax = xMax;
    }

    public double getyMin() {
        return yMin;
    }

    public void setyMin(double yMin) {
        this.yMin = yMin;
    }

    public double getyMax() {
        return yMax;
    }

    public void setyMax(double yMax) {
        this.yMax = yMax;
    }

    public ArrayList<Dessinable> getListeDes() {
        return liste;
    }

    public void setListeDes(ArrayList<Dessinable> listeDes) {
        this.liste = listeDes;
    }

    public Dessinable getdCourant() {
        return dCourant;
    }

    public void setdCourant(Dessinable dCourant) {
        this.dCourant = dCourant;
    }

    public void setApp(Appli app) {
        this.app = app;
    }

    public boolean getBooleanRepere() {
        return booleanRepere;
    }

    public void setBooleanRepere(boolean booleanRepere) {
        this.booleanRepere = booleanRepere;
    }

    public boolean isBooleanRemplir() {
        return booleanRemplir;
    }

    public void setBooleanRemplir(boolean booleanRemplir) {
        this.booleanRemplir = booleanRemplir;
    }

    public Appli getApp() {
        return app;
    }
    

    /**
     * La méthode qui contient les instructions de redessinage du panneau. Elle
     * est appelée a chaque fois que la fenetre doit s'actualiser On peut la
     * rappeler par la commande repaint()
     *
     * @param g le contexte graphique de dessin
     */
    
    public void affichageRepere() {
        Droite abscisse = new Droite(new Point(0,0), new Point(1,0));
        Droite ordonnee = new Droite(new Point(0,0), new Point(0,1));
        abscisse.dessineSur(this);
        ordonnee.dessineSur(this);
        
        //On réduit le nombre de points / numéros lorsque l'ardoise est fortement dézoomée
        //int largeur = (int) (Math.abs(this.getxMax()) + Math.abs(this.getxMin()));
        //int hauteur = (int) (Math.abs(this.getyMax()) + Math.abs(this.getyMin()));
        
        // Points du repère
        for (int i = (int) this.getxMin(); i <= this.getxMax(); i++) {
            for (int j = (int) this.getyMin(); j <= this.getyMax(); j++) {
                dessinePoint_repere(i, j, 3);
                
                //Numéros
                int x = this.getXArdoise(i);
                int y = this.getYArdoise(j);
                if (i == 0 && j != 0) {
                   crayon.drawString("" + j, x-20, y+5);
                }
                if (j == 0 && i != 0) {
                    crayon.drawString("" + i, x-5, y+15);
                }
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics gg) {
        
        super.paintComponent(gg);
        // on reactualise le crayon
        crayon = (Graphics2D) gg;
        
        //Rend plus beau les traits
        crayon.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (this.booleanRepere == true) {
            affichageRepere();
        }
        
        // on dessine tous les dessinables de la liste
        for (Dessinable d : liste) {
            crayon.setColor(d.getStyle().couleur);
            crayon.setStroke(new BasicStroke(d.getStyle().epaisseur));
            d.dessineSur(this);
        }

        //s'il n'est pas vide, on dessine le dessinable courant
        if (dCourant != null) {
            if (dCourant instanceof DessinLibre) {
                crayon.setColor(Color.blue);
                crayon.setStroke(new BasicStroke(2));
                dCourant.dessineSur(this);
            }
            else {
                crayon.setColor(Color.red);
                crayon.setStroke(new BasicStroke(4));
                dCourant.dessineSur(this);
            }

            //Affichage des points des formes :
            ArrayList<Point> listeConstituant = dCourant.getConstituants();
            if (listeConstituant != null) {
                for (Point p : listeConstituant) {
                    p.dessineSur(this);
                }               
            }
        }
    }

}