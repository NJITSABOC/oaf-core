package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel.AggregationAction;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.DisplayDisjointAbNSubsetSelectionPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.DisplayDisjointAbNSubsetSelectionPanel.DisplayDisjointAbNSubsetAction;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class DisjointAbNExplorationPanelInitializer extends AggregateableAbNExplorationPanelInitializer {
    
    private final DisjointAbNConfiguration config;
    private final PartitionedAbNConfiguration parentConfig;
    private final DisplayDisjointAbNSubsetAction displayAction;
    
    public DisjointAbNExplorationPanelInitializer(DisjointAbNConfiguration config, 
            PartitionedAbNConfiguration parentConfig, 
            AggregationAction aggregationAction,
            DisplayDisjointAbNSubsetAction displayAction) {
        
        super(aggregationAction);
        
        this.config = config;
        this.parentConfig = parentConfig;
        this.displayAction = displayAction;
    }
    
    @Override
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel) {
        
        super.initializeAbNDisplayPanel(displayPanel);
               
        DisplayDisjointAbNSubsetSelectionPanel subsetSelectionPanel = 
                new DisplayDisjointAbNSubsetSelectionPanel(
                        displayPanel, 
                        config, 
                        parentConfig, 
                        displayAction);
        
        displayPanel.addWidget(subsetSelectionPanel);
    }
}