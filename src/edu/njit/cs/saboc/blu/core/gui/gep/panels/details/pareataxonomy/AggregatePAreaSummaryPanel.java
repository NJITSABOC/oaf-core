package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaSummaryPanel extends PAreaSummaryPanel {
    public AggregatePAreaSummaryPanel(PAreaTaxonomyConfiguration configuration) {
        super(configuration, new AggregatePAreaSummaryTextFactory(configuration));
    }
}
