package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.ConceptListPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ParentsPanel<T extends Concept> extends ConceptListPanel<T> {
    
    public ParentsPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource,
            boolean showFilter) {
        
        super(mainPanel, 
                dataSource, 
                CommonBrowserDataRetrievers.getParentsRetriever(dataSource), 
                showFilter,
                true);
    }
}