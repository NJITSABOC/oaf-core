
package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayWidget;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 *
 * @author Chris O
 */
public class AggregatationSliderPanel extends AbNDisplayWidget {
    
    public interface AggregationAction {
        public void createAndDisplayAggregateAbN(int minBound);
    }
    
    private final JSlider aggregationSlider;
    private final JLabel boundLabel = new JLabel("(1)");
    
    private final AggregationAction aggregationAction;
    
    private final Dimension panelSize = new Dimension(150, 48);
    
    public AggregatationSliderPanel(AbNDisplayPanel displayPanel, AggregationAction aggregationAction) {
        super(displayPanel);
        
        this.aggregationAction = aggregationAction;
        
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Aggregate"));
        
        aggregationSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 1);
        aggregationSlider.setMajorTickSpacing(1);
        aggregationSlider.setSnapToTicks(true);
        
        aggregationSlider.addChangeListener( (ce) -> {
            if (!aggregationSlider.getValueIsAdjusting()) {
                aggregationAction.createAndDisplayAggregateAbN(aggregationSlider.getValue());
            } else {
                boundLabel.setText(String.format("(%d)", aggregationSlider.getValue()));
            }
        });

        this.add(aggregationSlider, BorderLayout.CENTER);
        this.add(boundLabel, BorderLayout.EAST);
    }
    
    @Override
    public void initialize(AbNDisplayPanel displayPanel) {
        // Need to handle aggregate of aggregate...
    }

    @Override
    public void displayPanelResized(AbNDisplayPanel displayPanel) {
        super.displayPanelResized(displayPanel);
        
        this.setBounds(displayPanel.getWidth() - panelSize.width - 20, displayPanel.getHeight() - panelSize.height - 20, panelSize.width, panelSize.height);
    }
}
