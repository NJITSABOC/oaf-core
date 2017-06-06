package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.errorsummarylabel;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class ErrorSummaryLabel<T extends Concept> extends JLabel {
    
    private final NATBrowserPanel<T> mainPanel;
    
    public ErrorSummaryLabel(NATBrowserPanel<T> mainPanel) {
        this.mainPanel = mainPanel;
        
        this.setForeground(Color.RED);
    }
    
    public NATBrowserPanel<T> getMainPanel() {
        return mainPanel;
    }
        
    public void showDetailsFor(T concept) {
        this.setText("");
        
        if(mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            AuditSet<T> currentAuditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();

            displayErrorSummary(currentAuditSet, concept);
        }
    }
    
    protected abstract void displayErrorSummary(AuditSet<T> theAuditResult, T concept);
}
