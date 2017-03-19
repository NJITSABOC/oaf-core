
package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.errorsummarylabel;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.IncorrectParentError;
import java.util.List;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class ParentErrorSummaryLabel<T extends Concept> extends ErrorSummaryLabel<T> {
    
    public ParentErrorSummaryLabel(NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, dataSource);
    }

    @Override
    protected void displayErrorSummary(AuditSet<T> auditSet, T concept) {
        
        List<IncorrectParentError<T>> relevantErrors = auditSet.getRelatedParentErrors(
                getMainPanel().getFocusConceptManager().getActiveFocusConcept(), 
                concept);
        
        if(relevantErrors.isEmpty()) {
            return;
        }
        
        if(relevantErrors.size() == 1) {
            this.setText("Error");
        } else {
            this.setText(String.format("Errors (%d)", relevantErrors.size()));
        }
    }
}
