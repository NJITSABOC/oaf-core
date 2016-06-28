package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.PartitionedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class AreaPanel extends PartitionedNodePanel {

    public AreaPanel(PAreaTaxonomyConfiguration configuration) {
        super(new AreaDetailsPanel(configuration), configuration);
    }
}
