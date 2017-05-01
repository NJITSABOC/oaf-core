package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel.AggregationAction;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class DisjointAbNExplorationPanelInitializer extends AggregateableAbNExplorationPanelInitializer {
    
    private final DisjointAbNConfiguration config;
    private final PartitionedAbNConfiguration parentConfig;
    
    public DisjointAbNExplorationPanelInitializer(DisjointAbNConfiguration config, 
            PartitionedAbNConfiguration parentConfig, 
            AggregationAction aggregationAction) {
        
        super(aggregationAction);
        
        this.config = config;
        this.parentConfig = parentConfig;
    }
    
    @Override
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel) {
        super.initializeAbNDisplayPanel(displayPanel);
    }
}