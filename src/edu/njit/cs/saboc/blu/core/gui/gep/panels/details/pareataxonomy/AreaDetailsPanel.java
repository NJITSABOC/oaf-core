
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class AreaDetailsPanel extends NodeDetailsPanel<Area> {
    
    public AreaDetailsPanel(PAreaTaxonomyConfiguration configuration) {

        super(new AreaSummaryPanel(configuration), 
                configuration.getUIConfiguration().getPartitionedNodeOptionsPanel(), 
                new NodeConceptList(configuration),
                configuration);
    }
}