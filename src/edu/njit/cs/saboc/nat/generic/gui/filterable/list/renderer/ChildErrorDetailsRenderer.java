package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.errorsummarylabel.ChildErrorSummaryLabel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ChildErrorDetailsRenderer<T extends Concept> extends ConceptDescendantsRenderer<T> {
    
    private final ChildErrorSummaryLabel<T> childErrorLabel;
    
    public ChildErrorDetailsRenderer(NATBrowserPanel<T> mainPanel) {
        
        super(mainPanel);
        
        this.childErrorLabel = new ChildErrorSummaryLabel<>(mainPanel);
        
        super.addDetailsLabel(childErrorLabel);
    }

    @Override
    public void showDetailsFor(Filterable<T> filterableEntry) {
        super.showDetailsFor(filterableEntry);
        
        if(getMainPanel().getAuditDatabase().getLoadedAuditSet().isPresent()) {
            AuditSet<T> currentAuditSet = getMainPanel().getAuditDatabase().getLoadedAuditSet().get();
            T focusConcept = getMainPanel().getFocusConceptManager().getActiveFocusConcept();
            
            if(currentAuditSet.getConcepts().contains(focusConcept)) {
                this.childErrorLabel.showDetailsFor(filterableEntry.getObject());
            }
        }
    }
}