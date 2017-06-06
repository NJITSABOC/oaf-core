
package edu.njit.cs.saboc.nat.generic.gui.filterable.list;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;

/**
 * Filterable entry for the list of audit set concepts
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class FilterableAuditSetEntry<T extends Concept> extends FilterableConceptEntry<T> {
    
    private final NATBrowserPanel<T> mainPanel;
    
    public FilterableAuditSetEntry(
            NATBrowserPanel<T> mainPanel,
            T concept) {
        
        super(mainPanel, concept);
        
        this.mainPanel = mainPanel;
    }

    @Override
    public String getToolTipText() {

        if (mainPanel.getDataSource().isPresent()) {
            ConceptBrowserDataSource<T> dataSource = mainPanel.getDataSource().get();

            T concept = super.getObject();

            if (mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
                return dataSource.getAuditConceptToolTipText(
                        mainPanel.getAuditDatabase().getLoadedAuditSet().get(), concept);

            } else {
                return dataSource.getConceptToolTipText(concept);
            }

        } else {
            return null;
        }
    }

}