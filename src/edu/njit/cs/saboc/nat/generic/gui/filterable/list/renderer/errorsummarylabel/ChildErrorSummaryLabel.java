package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.errorsummarylabel;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.child.IncorrectChildError;
import java.util.List;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ChildErrorSummaryLabel<T extends Concept> extends ErrorSummaryLabel<T> {
    
    public ChildErrorSummaryLabel(NATBrowserPanel<T> mainPanel) {
        
        super(mainPanel);
    }

    @Override
    protected void displayErrorSummary(AuditSet<T> auditSet, T concept) {
        
        List<IncorrectChildError<T>> relevantErrors = auditSet.getRelatedChildErrors(
                getMainPanel().getFocusConceptManager().getActiveFocusConcept(), 
                concept);
        
        if(relevantErrors.isEmpty()) {
            return;
        }
        
        if (relevantErrors.size() == 1) {
            this.setText("Error");
        } else {
            this.setText(String.format("Errors (%d)", relevantErrors.size()));
        }
    }
}