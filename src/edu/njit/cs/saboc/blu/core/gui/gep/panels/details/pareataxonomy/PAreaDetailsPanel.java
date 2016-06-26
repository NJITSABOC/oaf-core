package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;

/**
 *
 * @author Chris O
 */
public class PAreaDetailsPanel extends NodeDetailsPanel {
    public PAreaDetailsPanel(PAreaTaxonomyConfiguration config) {
        
        super(new PAreaSummaryPanel(config), 
                config.getUIConfiguration().getNodeOptionsPanel(), 
                new ConceptList(config),
                config);
    }
}
