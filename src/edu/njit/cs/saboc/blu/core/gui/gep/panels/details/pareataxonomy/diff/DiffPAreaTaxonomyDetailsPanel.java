package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.diff.DiffNodeStatusReportPanel;

/**
 *
 * @author Chris O
 */
public class DiffPAreaTaxonomyDetailsPanel extends AbstractAbNDetailsPanel<DiffPAreaTaxonomy> {
    
    private final DiffNodeStatusReportPanel nodeStatusReportPanel;
    
    public DiffPAreaTaxonomyDetailsPanel(DiffPAreaTaxonomyConfiguration config) {
        super(config);

        this.nodeStatusReportPanel = new DiffNodeStatusReportPanel(config);
        
        super.addDetailsTab("Diff Parital-area Status", nodeStatusReportPanel);
        nodeStatusReportPanel.displayAbNReport(config.getAbstractionNetwork());
    }
}