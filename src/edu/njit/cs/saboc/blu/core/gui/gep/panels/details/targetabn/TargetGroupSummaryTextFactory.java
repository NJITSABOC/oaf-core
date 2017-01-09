package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class TargetGroupSummaryTextFactory implements NodeSummaryTextFactory<TargetGroup> {
    
    private final TargetAbNConfiguration config;
    
    public TargetGroupSummaryTextFactory(TargetAbNConfiguration config) {
        this.config = config;
    }
    
    public TargetAbNConfiguration getConfiguration() {
        return config;
    }

    @Override
    public String createNodeSummaryText(TargetGroup parea) {
        return "[TARGET GROUP SUMMARY]";
    }
}
