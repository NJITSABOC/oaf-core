
package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayWidget;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
    
    private int currentBound = 1;
    
    public AggregatationSliderPanel(AbNDisplayPanel displayPanel, AggregationAction aggregationAction) {
        super(displayPanel);
        
        this.aggregationAction = aggregationAction;
        
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Aggregate"));
        
        aggregationSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 1);
        aggregationSlider.setMajorTickSpacing(1);
        aggregationSlider.setSnapToTicks(true);
        
        aggregationSlider.addChangeListener( (ce) -> {
            
            int newValue = aggregationSlider.getValue();
            
            if (!aggregationSlider.getValueIsAdjusting()) {
                if (currentBound != newValue) {
                    aggregationAction.createAndDisplayAggregateAbN(newValue);
                    this.currentBound = newValue;
                }
                
            }
                
            boundLabel.setText(String.format("(%d/%d)", newValue, aggregationSlider.getMaximum()));
        });

        this.add(aggregationSlider, BorderLayout.CENTER);
        this.add(boundLabel, BorderLayout.EAST);
    }
    
    @Override
    public void initialize(AbNDisplayPanel displayPanel) {
        
        AggregateableAbstractionNetwork abn = (AggregateableAbstractionNetwork)displayPanel.getGraph().getAbstractionNetwork();
        
        AbstractionNetwork abnToProcess;

        if(abn.isAggregated()) {
            AggregateAbstractionNetwork aggregateAbN = (AggregateAbstractionNetwork)abn;
            
            abnToProcess = aggregateAbN.getSource();
        } else {
            abnToProcess = displayPanel.getGraph().getAbstractionNetwork();
        }
        
        Set<Node> nodes = abnToProcess.getNodes();
        
        Map<Integer, Integer> sizeDistribution = new HashMap<>();
        
        int [] max = new int [1];
        max[0] = -1;
        
        nodes.forEach( (node) -> {
            int size = node.getConceptCount();
            
            if(!sizeDistribution.containsKey(size)) {
                sizeDistribution.put(size, 0);
            }
            
            if(size > max[0]) {
                max[0] = size;
            }
            
            sizeDistribution.put(size, sizeDistribution.get(size) + 1);
        });
        
        int cumulative = 0;
        int bound = 1;
        
        for(int i = 1; i <= max[0]; i++) {
            if(sizeDistribution.containsKey(i)) {
                cumulative += sizeDistribution.get(i);
            }
            
            if((double)cumulative / nodes.size() > 0.99) {
                bound = i;
                break;
            }
        }
        
        bound = Math.min(bound, 100);
        
        aggregationSlider.setMaximum(bound);
    }

    @Override
    public void displayPanelResized(AbNDisplayPanel displayPanel) {
        super.displayPanelResized(displayPanel);
        
        this.setBounds(displayPanel.getWidth() - panelSize.width - 20, 
                displayPanel.getHeight() - panelSize.height - 20, 
                panelSize.width, 
                panelSize.height);
    }
}
