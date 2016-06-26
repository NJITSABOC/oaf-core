
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;

/**
 *
 * @author Chris O
 */
public class AreaDetailsPanel extends NodeDetailsPanel {
    
    public AreaDetailsPanel(PAreaTaxonomyConfiguration configuration) {

        super(new AreaSummaryPanel(configuration), 
                configuration.getUIConfiguration().getPartitionedNodeOptionsPanel(), 
                new ConceptList(configuration),
                configuration);
    }
}