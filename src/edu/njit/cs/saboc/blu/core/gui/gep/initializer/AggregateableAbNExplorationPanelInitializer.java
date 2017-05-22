
package edu.njit.cs.saboc.blu.core.gui.gep.initializer;

import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel.AggregationAction;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.AbNWarningManager;

/**
 *
 * @author Chris O
 */
public class AggregateableAbNExplorationPanelInitializer extends BaseAbNExplorationPanelInitializer {
    
    private final AggregationAction aggregationAction;
    
    public AggregateableAbNExplorationPanelInitializer(
            AbNWarningManager warningManager, 
            AggregationAction aggregationAction) {
        
        super(warningManager);
        
        this.aggregationAction = aggregationAction;
    }

    @Override
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel, boolean startUp) {
        super.initializeAbNDisplayPanel(displayPanel, startUp);
        
        AggregatationSliderPanel aggregationPanel = new AggregatationSliderPanel(displayPanel, aggregationAction);
        
        displayPanel.addWidget(aggregationPanel);
    }
}
