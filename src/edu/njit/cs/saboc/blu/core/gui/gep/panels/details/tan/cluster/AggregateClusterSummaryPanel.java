package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;

/**
 *
 * @author Chris O
 */
public class AggregateClusterSummaryPanel extends ClusterSummaryPanel {
    public AggregateClusterSummaryPanel(TANConfiguration configuration) {
        super(configuration, new AggregateClusterSummaryTextFactory(configuration));
    }
}
