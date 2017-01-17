
package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AbNDerivationHistoryEntry<T extends AbstractionNetwork> {
    
    public interface DisplayDerivedAbNAction<T extends AbstractionNetwork> {
        public void display(T abn);
    }
    
    private final AbNDerivation<T> derivation;
    private final String abnTypeName;
    
    private final DisplayDerivedAbNAction<T> displayAction;
    
    public AbNDerivationHistoryEntry(
            AbNDerivation<T> derivation, 
            DisplayDerivedAbNAction<T> displayAction, 
            String abnTypeName) {
        
        this.derivation = derivation;
        this.displayAction = displayAction;
        this.abnTypeName = abnTypeName;
    }
    
    public AbNDerivation<T> getDerivation() {
        return derivation;
    }
    
    public String getAbNTypeName() {
        return abnTypeName;
    }
    
    public void displayEntry() {
        
        Thread displayThread = new Thread( () -> {
           T abn = derivation.getAbstractionNetwork();
           
           displayAction.display(abn);
        });
        
        displayThread.start();
    }
}
