
package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel.AggregationAction;

/**
 *
 * @author Chris O
 */
public class AggregateableAbNExplorationPanelInitializer extends BaseAbNExplorationPanelInitializer {
    
    private final AggregationAction aggregationAction;
    
    public AggregateableAbNExplorationPanelInitializer(AggregationAction aggregationAction) {
        this.aggregationAction = aggregationAction;
    }

    @Override
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel, boolean startUp) {
        super.initializeAbNDisplayPanel(displayPanel, startUp);
        
        AggregatationSliderPanel aggregationPanel = new AggregatationSliderPanel(displayPanel, aggregationAction);
        
        displayPanel.addWidget(aggregationPanel);
    }
}
