package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

/**
 *
 * @author Chris O
 */
public class AbNHistoryNavigationManager {
    
    private final AbNDerivationHistory history;
    
    private int currentHistoryLocation;
    
    public AbNHistoryNavigationManager(AbNDerivationHistory history) {
        this.history = history;
        this.currentHistoryLocation = -1;
        
        history.addHistoryChangedListener( () -> {
            reset();
        });
    }
    
    public final boolean canGoBack() {
        return currentHistoryLocation > 0;
    }
    
    public final boolean canGoForward() {
        return currentHistoryLocation < history.getHistory().size() - 1;
    }
    
    public final void reset() {
        if(history.getHistory().isEmpty()) {
            return;
        }
        
        currentHistoryLocation = history.getHistory().size() - 1;
        
        history.getHistory().get(currentHistoryLocation).displayEntry();
    }
    
    public final void goBack() {
        
        if(!canGoBack()) {
            return;
        }
        
        currentHistoryLocation--;
        
        history.getHistory().get(currentHistoryLocation).displayEntry();
    }
    
    public final void goForward() {
        if(!canGoForward()) {
            return;
        }
        
        currentHistoryLocation++;
        
        history.getHistory().get(currentHistoryLocation).displayEntry();
    }
}
