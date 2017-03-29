
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
    private final ConceptBrowserDataSource<T> dataSource;
    
    public FilterableAuditSetEntry(NATBrowserPanel<T> mainPanel,
            ConceptBrowserDataSource<T> dataSource,
            T concept) {
        
        super(concept, dataSource);
        
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;
    }

    @Override
    public String getToolTipText() {
        T concept = super.getObject();

        if (mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
           return dataSource.getAuditConceptToolTipText(
                   mainPanel.getAuditDatabase().getLoadedAuditSet().get(), concept);
           
        } else {
            return dataSource.getConceptToolTipText(concept);
        }
    }

}