package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff.DiffNodeList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNReportPanel;
import java.awt.BorderLayout;

/**
 *
 * @author Chris Ochs
 */
public class DiffPAreaImplicitChangePanel extends AbNReportPanel<DiffPAreaTaxonomy> {
    
    private final DiffNodeList<DiffPArea> diffNodeList;
    
    public DiffPAreaImplicitChangePanel(DiffPAreaTaxonomyConfiguration config) {
        super(config);
        
        this.diffNodeList = new DiffNodeList(config);
        
        this.setLayout(new BorderLayout());
        
        this.add(diffNodeList, BorderLayout.CENTER);
    }

    @Override
    public void displayAbNReport(DiffPAreaTaxonomy abn) {
        DiffPAreaImplicitChangeReport report = new DiffPAreaImplicitChangeReport(abn);       
        
        diffNodeList.setContents(report.getPAreasWithOnlyImplicitChanges());
    }
}
