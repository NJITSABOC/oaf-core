
package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUAbNConfiguration;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class AbNReportPanel<CONCEPT_T, GROUP_T extends GenericConceptGroup, ABN_T extends AbstractionNetwork> extends JPanel {
    
    protected final BLUAbNConfiguration config;
    
    public AbNReportPanel(BLUAbNConfiguration config) {
        this.config = config;
    }
    
    public abstract void displayAbNReport(ABN_T abn);
}
