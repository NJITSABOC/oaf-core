package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;

/**
 *
 * @author Chris O
 */
public class AggregateAreaDetailsPanel extends NodeDetailsPanel {
    
    public AggregateAreaDetailsPanel(PAreaTaxonomyConfiguration configuration) {

        super(new AggregateAreaSummaryPanel(configuration), 
                configuration.getUIConfiguration().getPartitionedNodeOptionsPanel(), 
                new ConceptList(configuration),
                configuration);
    }
}