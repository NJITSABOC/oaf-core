package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;

/**
 *
 * @author Chris O
 */
public class ClusterDetailsPanel extends NodeDetailsPanel<Cluster> {
    
    public ClusterDetailsPanel(TANConfiguration config) {
        
        super(new ClusterSummaryPanel(config), 
                config.getUIConfiguration().getNodeOptionsPanel(),
                new ConceptList(config),
                config);
    }
}
