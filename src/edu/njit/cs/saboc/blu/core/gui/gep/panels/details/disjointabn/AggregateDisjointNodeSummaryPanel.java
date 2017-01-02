package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class AggregateDisjointNodeSummaryPanel extends DisjointNodeSummaryPanel {
    
    public AggregateDisjointNodeSummaryPanel(DisjointAbNConfiguration configuration) {
        super(configuration, 
                new AggregateDisjointNodeTextFactory(configuration));
    }
}
