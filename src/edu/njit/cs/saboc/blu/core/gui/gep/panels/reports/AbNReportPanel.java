
package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class AbNReportPanel extends JPanel {
    
    protected final AbNConfiguration config;
    
    public AbNReportPanel(AbNConfiguration config) {
        this.config = config;
    }
    
    public AbNConfiguration getConfiguration() {
        return config;
    }
    
    public abstract void displayAbNReport(AbstractionNetwork abn);
}
