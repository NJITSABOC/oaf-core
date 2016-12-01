
package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel.AggregationAction;

/**
 *
 * @author Chris O
 */
public class AggregateableAbNInitializer extends BaseAbNExplorationPanelInitializer {
    
    private final AggregationAction aggregationAction;
    
    public AggregateableAbNInitializer(AggregationAction aggregationAction) {
        this.aggregationAction = aggregationAction;
    }

    @Override
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel) {
        super.initializeAbNDisplayPanel(displayPanel);
        
        AggregatationSliderPanel aggregationPanel = new AggregatationSliderPanel(displayPanel, aggregationAction);
        
        displayPanel.addWidget(aggregationPanel);
    }
}
