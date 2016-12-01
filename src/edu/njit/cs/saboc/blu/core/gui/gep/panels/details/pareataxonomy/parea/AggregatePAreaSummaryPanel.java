package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaSummaryPanel extends PAreaSummaryPanel {
    public AggregatePAreaSummaryPanel(PAreaTaxonomyConfiguration configuration) {
        super(configuration, new AggregatePAreaSummaryTextFactory(configuration));
    }
}
