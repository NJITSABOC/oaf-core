package edu.njit.cs.saboc.blu.core.gui.gep.warning;

/**
 *
 * @author Chris Ochs
 */
public class DisjointAbNWarningManager extends AbNWarningManager {
    
    private boolean showGrayWarningMessage;
    
    public DisjointAbNWarningManager() {
        this.showGrayWarningMessage = true;
    }
    
    public boolean showGrayWarningMessage() {
        return this.showGrayWarningMessage;
    }
    
    public void setShowGrayWarningMessage(boolean value) {
        this.showGrayWarningMessage = value;
    }
}
