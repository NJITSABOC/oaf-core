
package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class AbNReportPanel<CONCEPT_T, GROUP_T extends GenericConceptGroup, ABN_T extends AbstractionNetwork> extends JPanel {
    
    protected final BLUConfiguration config;
    
    public AbNReportPanel(BLUConfiguration config) {
        this.config = config;
    }
    
    public BLUConfiguration getConfiguration() {
        return config;
    }
    
    public abstract void displayAbNReport(ABN_T abn);
}
