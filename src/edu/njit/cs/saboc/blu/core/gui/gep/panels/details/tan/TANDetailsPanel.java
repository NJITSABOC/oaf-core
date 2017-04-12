package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.SimpleAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNContainerReportPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNLevelReportPanel;

/**
 *
 * @author Chris O
 */
public class TANDetailsPanel extends SimpleAbNDetailsPanel<ClusterTribalAbstractionNetwork> {

    public TANDetailsPanel(TANConfiguration config) {
        super(config);

        AbNLevelReportPanel levelReportPanel = new AbNLevelReportPanel(config);
        levelReportPanel.displayAbNReport(config.getTribalAbstractionNetwork());

        AbNContainerReportPanel areaReportPanel = new AbNContainerReportPanel(config);
        areaReportPanel.displayAbNReport(config.getTribalAbstractionNetwork());

        super.addDetailsTab("Cluster TAN Levels", levelReportPanel);
        super.addDetailsTab("Bands in Cluster TAN", areaReportPanel);
    }
}
