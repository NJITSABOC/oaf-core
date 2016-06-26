package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.PartitionedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;

/**
 *
 * @author Chris O
 */
public class BandPanel extends PartitionedNodePanel {
    
    private final TANConfiguration config;

    public BandPanel(TANConfiguration config) {
        super(new BandDetailsPanel(config), 
                config);
        
        this.config = config;
    }
}
