
package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.awt.Component;
import java.util.Date;
import javax.swing.SwingUtilities;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AbNDerivationHistoryEntry<T extends AbstractionNetwork> extends Component{
   
    private final MultiAbNGraphFrame graphFrame;

    private final AbNDerivation<T> derivation;

    private final Date entryDate;
    
    public AbNDerivationHistoryEntry(
            AbNDerivation<T> derivation, 
            MultiAbNGraphFrame graphFrame,
            Date date) {
        
        this.derivation = derivation;
        this.graphFrame = graphFrame;
        
        this.entryDate = date;
    }
    
    public AbNDerivationHistoryEntry(
            AbNDerivation<T> derivation, 
            MultiAbNGraphFrame graphFrame) {
        
       this(derivation, graphFrame, new Date());
    } 
    
    public AbNDerivation<T> getDerivation() {
        return derivation;
    }
        
    public Date getDate() {
        return entryDate;
    }
    
    public void displayEntry(Ontology<Concept> ontology) {
        
        Thread displayThread = new Thread( () -> {
           T abn = derivation.getAbstractionNetwork(ontology);
           
           SwingUtilities.invokeLater( () -> {
               graphFrame.displayAbstractionNetwork(abn, false);
           });
        });
        
        displayThread.start();
    }
    
    public JSONObject toJSON() {
        
        JSONObject obj = new JSONObject();
        
        obj.put("TimeViewed", entryDate.getTime());
        obj.put("Derivation", derivation.serializeToJSON());
        
        return obj;
    }
}
