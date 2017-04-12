package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;

/**
 *
 * @author Chris O
 */
public class AggregateBandSummaryPanel extends BandSummaryPanel {
    public AggregateBandSummaryPanel(TANConfiguration configuration) {  
        super(configuration, new AggregateBandSummaryTextFactory(configuration));
    }
}