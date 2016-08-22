package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffPartitionedNodeConceptListModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.AreaSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class DiffAreaDetailsPanel extends NodeDetailsPanel<DiffArea> {
    
    public DiffAreaDetailsPanel(DiffPAreaTaxonomyConfiguration config) {
        super(new AreaSummaryPanel(config, new DiffAreaSummaryTextFactory(config)),
                config.getUIConfiguration().getPartitionedNodeOptionsPanel(),
                new NodeConceptList(new DiffPartitionedNodeConceptListModel(config), config),
                config);
    }
    
}
