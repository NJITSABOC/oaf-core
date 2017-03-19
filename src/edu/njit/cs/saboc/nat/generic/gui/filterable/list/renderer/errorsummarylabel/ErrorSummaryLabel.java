package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.errorsummarylabel;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
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
    private final ConceptBrowserDataSource<T> dataSource;
    
    public ErrorSummaryLabel(NATBrowserPanel<T> mainPanel, ConceptBrowserDataSource<T> dataSource) {
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;
        
        this.setForeground(Color.RED);
    }
    
    public NATBrowserPanel<T> getMainPanel() {
        return mainPanel;
    }
    
    public ConceptBrowserDataSource<T> getDataSource() {
        return dataSource;
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
