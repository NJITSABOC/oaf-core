package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;

/**
 *
 * @author Chris O
 */
public class BandDetailsPanel extends NodeDetailsPanel<Band> {
    
    private final TANConfiguration config;

    public BandDetailsPanel(TANConfiguration config) {

        super(new BandSummaryPanel(config), 
                config.getUIConfiguration().getPartitionedNodeOptionsPanel(), 
                new ConceptList(config),
                config);
        
        this.config = config;
    }
}
