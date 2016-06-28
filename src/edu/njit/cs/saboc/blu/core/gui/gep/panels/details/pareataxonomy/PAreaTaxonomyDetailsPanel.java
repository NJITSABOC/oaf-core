
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class PAreaTaxonomyDetailsPanel extends AbstractAbNDetailsPanel<PAreaTaxonomy> {
    
    public PAreaTaxonomyDetailsPanel(PAreaTaxonomyConfiguration config) {
        super(config);

        if (config.getPAreaTaxonomy().isAggregated()) {
//            SCTAggregatePAreaTaxonomyLevelReportPanel levelReportPanel = new SCTAggregatePAreaTaxonomyLevelReportPanel(config);
//            levelReportPanel.displayAbNReport(config.getDataConfiguration().getPAreaTaxonomy());
//
//            SCTAggregatePAreaTaxonomyAreaReportPanel areaReportPanel = new SCTAggregatePAreaTaxonomyAreaReportPanel(config);
//            areaReportPanel.displayAbNReport(config.getDataConfiguration().getPAreaTaxonomy());
//
//            super.addDetailsTab("Aggregate Partial-area Taxonomy Levels", levelReportPanel);
//            super.addDetailsTab("Areas in Aggregate Partial-area Taxonomy", areaReportPanel);
        } else {
//            SCTPAreaTaxonomyLevelReportPanel levelReportPanel = new SCTPAreaTaxonomyLevelReportPanel(config);
//            levelReportPanel.displayAbNReport(config.getDataConfiguration().getPAreaTaxonomy());
//
//            SCTPAreaTaxonomyAreaReportPanel areaReportPanel = new SCTPAreaTaxonomyAreaReportPanel(config);
//            areaReportPanel.displayAbNReport(config.getDataConfiguration().getPAreaTaxonomy());
//
//            super.addDetailsTab("Partial-area Taxonomy Levels", levelReportPanel);
//            super.addDetailsTab("Areas in Partial-area Taxonomy", areaReportPanel);
        }
    }
}