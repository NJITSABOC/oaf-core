
package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import com.sun.javafx.css.SizeUnits;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayWidget;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JTextField;
import jdk.nashorn.internal.objects.Global;

/**
 *
 * @author Chris O
 */
public class AggregatationSliderPanel extends AbNDisplayWidget {
    public static AggregationAction AggregationAction;
    
    public interface AggregationAction {
        public void createAndDisplayAggregateAbN(int minBound, boolean weightedAggregated);
    }
    
    private final JSlider aggregationSlider;
    
    private final JTextField txtCurrentBound;
    
    private final JCheckBox aggregationCheckBox;
    
    private final AggregationAction aggregationAction;
    
//    private final Dimension panelSize = new Dimension(150, 48);
    private final Dimension panelSize = new Dimension(150, 70);
    
    private int currentBound = 1;
    
    private boolean initialized = false;
    
    private boolean weightedFlag = false;
    
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
            
            if (initialized && !aggregationSlider.getValueIsAdjusting()) {
                setBound(newValue, weightedFlag);

                displayCurrentBound();
            } else {
                displayBound(newValue);
            }

            displayCurrentWeightedCheckBox();
            // Hack solution to preventing slider listeners from triggering when programmatically setting 
            // bound value on initialization
            initialized = true;
        });
        
        this.txtCurrentBound = new JTextField();
        this.txtCurrentBound.setText("1");
        this.txtCurrentBound.setPreferredSize(new Dimension(50, -1));
        
        this.txtCurrentBound.addActionListener((ae) -> {
            String txt = txtCurrentBound.getText();

            try {
                int x = Integer.parseInt(txt);
                
                setBound(Math.max(1, x), weightedFlag);
            } catch (NumberFormatException nfe) {

            }

            displayCurrentBound();
            displayCurrentWeightedCheckBox();
        });
        
        aggregationCheckBox = new JCheckBox("Weighted");
        aggregationCheckBox.addItemListener((ie) -> {  
 
            if (ie.getStateChange()==ItemEvent.SELECTED){
                this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Weighted Aggregate"));
                weightedFlag = true;
            }
            else{
                this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Aggregate"));
                weightedFlag = false;
            }  
        });
        
        this.add(aggregationCheckBox, BorderLayout.NORTH);
        this.add(aggregationSlider, BorderLayout.CENTER);
        this.add(txtCurrentBound, BorderLayout.EAST);
        
    }
    
    private void setBound(int bound, boolean weightedAggregated) {
        if (currentBound != bound) {
            aggregationAction.createAndDisplayAggregateAbN(bound, weightedAggregated);
        }
    }
    
    private void displayCurrentBound() {
        displayBound(currentBound);
    }
    
    private void displayBound(int bound) {
        txtCurrentBound.setText(Integer.toString(bound));
    }
    
    private void displayCurrentWeightedCheckBox(){
        displayWeightedFlag(weightedFlag);
    }
    
    private void displayWeightedFlag(boolean flag){
        aggregationCheckBox.setSelected(flag);
    }
    
    @Override
    public void initialize(AbNDisplayPanel displayPanel) {
        this.initialized = false;
        
        AggregateableAbstractionNetwork abn = (AggregateableAbstractionNetwork)displayPanel.getGraph().getAbstractionNetwork();
        
        AbstractionNetwork abnToProcess;

        if(abn.isAggregated()) {
            AggregateAbstractionNetwork aggregateAbN = (AggregateAbstractionNetwork)abn;
            
            abnToProcess = aggregateAbN.getNonAggregateSourceAbN();
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
        
         this.initialized = false;
        
        if(abn instanceof AggregateAbstractionNetwork) {
            int abnBound = ((AggregateAbstractionNetwork)abn).getAggregateBound();
            
            aggregationSlider.setValue(abnBound);
            
            this.currentBound = abnBound;
        } else {
            this.initialized = true;
        }
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
