
import GUI.Appli;
import javax.swing.UIManager;

/**
 *
 * @author pierrecharbit
 */
public class Prog {
        
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } 
        catch (Exception ex) {
            System.out.println("Erreur : " + ex);
        }
                
        (new Appli()).setVisible(true);
    }    
    
}
