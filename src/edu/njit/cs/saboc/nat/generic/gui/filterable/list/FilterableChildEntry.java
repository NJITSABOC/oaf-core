package edu.njit.cs.saboc.nat.generic.gui.filterable.list;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.child.IncorrectChildError;
import java.util.List;

/**
 * Filterable entry for the 
 * 
 * @author Chris O
 * @param <T>
 */
public class FilterableChildEntry<T extends Concept> extends FilterableConceptEntry<T> {
    
    private final NATBrowserPanel<T> mainPanel;
    
    public FilterableChildEntry(
            NATBrowserPanel<T> mainPanel,
            T concept) {
        
        super(mainPanel, concept);
        
        this.mainPanel = mainPanel;
    }

    @Override
    public String getToolTipText() {
        
        String result = super.getToolTipText();

        if (mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            AuditSet<T> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
            T focusConcept = mainPanel.getFocusConceptManager().getActiveFocusConcept();

            if (auditSet.getConcepts().contains(focusConcept)) {

                List<IncorrectChildError<T>> relevantErrors = auditSet.getRelatedChildErrors(
                        focusConcept,
                        this.getObject());

                if (!relevantErrors.isEmpty()) {
                    result += String.format("<p><p><font size = '4'><b>"
                            + "Reported Errors (%d)</b></font><br>", relevantErrors.size());
                    
                    for(IncorrectChildError<T> error : relevantErrors) {
                        result += error.getTooltipText();
                        result += "<br>";
                    }
                }
            }
        }
        
        return result;
    }
}
