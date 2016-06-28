package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class AggregateAreaSummaryPanel extends AreaSummaryPanel {
    public AggregateAreaSummaryPanel(PAreaTaxonomyConfiguration configuration) {  
        super(configuration, new AggregateAreaSummaryTextFactory(configuration));
    }
}