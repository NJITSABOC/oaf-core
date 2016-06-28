package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaDetailsPanel extends NodeDetailsPanel {
    
    public AggregatePAreaDetailsPanel(PAreaTaxonomyConfiguration config) {
        
        super(new AggregatePAreaSummaryPanel(config), 
                config.getUIConfiguration().getNodeOptionsPanel(), 
                new ConceptList(config),
                config);
    }
}
