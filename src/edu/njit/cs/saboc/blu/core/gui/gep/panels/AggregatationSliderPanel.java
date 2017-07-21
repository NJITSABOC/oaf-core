
package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayWidget;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 *
 * @author Chris O
 */
public class AggregatationSliderPanel extends AbNDisplayWidget {
    public static AggregationAction AggregationAction;
    
    public interface AggregationAction {
        public void createAndDisplayAggregateAbN(AggregatedProperty ap);
    }
    
    private final JSlider aggregationSlider;
    
    private final JTextField txtCurrentBound;
    
    private final JTextField txtAutoBound;
    
    private final JCheckBox aggregationCheckBox;
    
    private final JCheckBox autoScaleCheckBox;
    
    private final AggregationAction aggregationAction;
    
    private final Dimension panelSize = new Dimension(170, 120);
    
    private int currentBound = 1;
        
    private int autoScaleBound = 25;
    
    private boolean initialized = false;
    
    private boolean isWeightedAggregated = false;
    
    private boolean isAutoScaled = false;
    
    public AggregatationSliderPanel(
            AbNDisplayPanel displayPanel, 
            AggregationAction aggregationAction) {
        
        super(displayPanel);
        
        this.aggregationAction = aggregationAction;
        
        this.setLayout(new GridLayout(2, 1, 1, 1));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Aggregate"));

        aggregationSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 1);
        aggregationSlider.setMajorTickSpacing(1);
        aggregationSlider.setSnapToTicks(true);
        
        aggregationSlider.addChangeListener( (ce) -> {
            
            int newValue = aggregationSlider.getValue();
            
            if (initialized && !aggregationSlider.getValueIsAdjusting()) {
                setBound(newValue, isWeightedAggregated);

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
        this.txtCurrentBound.setPreferredSize(new Dimension(30, -1));
        
        this.txtCurrentBound.addActionListener((ae) -> {
            String txt = txtCurrentBound.getText();

            try {
                int x = Integer.parseInt(txt);
                
                setBound(Math.max(1, x), isWeightedAggregated);
            } catch (NumberFormatException nfe) {

            }

            displayCurrentBound();
            displayCurrentWeightedCheckBox();
        });
        
        this.txtAutoBound = new JTextField();
        this.txtAutoBound.setText(String.valueOf(autoScaleBound));
        this.txtAutoBound.setPreferredSize(new Dimension(30, -1));
        
        this.txtAutoBound.addActionListener((ae) -> {
            String txt = txtAutoBound.getText();

            try {              
                setAutoScale(Math.max(1, Integer.parseInt(txt)), true);
            } catch (NumberFormatException nfe) {

            }           
            displayCurrentScaleBound();
            displayCurrentScaleCheckBox();
        });
                
        autoScaleCheckBox = new JCheckBox("Use Auto Scale");
        autoScaleCheckBox.setToolTipText("<html>Auto aggregate on hierarchy "
                + "<p> to the given number of nodes displayed.");
        
        aggregationCheckBox = new JCheckBox("Use Weighted");
        aggregationCheckBox.setToolTipText("<html>When applying aggregation consider "
                + "<p> the size of the subhierarchy rooted at each node.");
        
        
        JPanel autoScalePanel = new JPanel(new BorderLayout());        
        autoScalePanel.add(autoScaleCheckBox, BorderLayout.NORTH);
        autoScalePanel.add(new JLabel("Aggregate to "), BorderLayout.WEST);
        autoScalePanel.add(txtAutoBound, BorderLayout.CENTER);
        autoScalePanel.add(new JLabel("Node(s)"), BorderLayout.EAST);

              
        JPanel aggregationPanel = new JPanel(new BorderLayout());
        aggregationPanel.add(aggregationCheckBox, BorderLayout.NORTH);
        aggregationPanel.add(aggregationSlider, BorderLayout.CENTER);
        aggregationPanel.add(txtCurrentBound, BorderLayout.EAST);
        
        autoScaleCheckBox.addItemListener((ie) -> {

            if (autoScaleCheckBox.isSelected()) {
                this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Auto Scaled"));
                if (!isAutoScaled) {
                    setAutoScale(autoScaleBound, true);    
                }                   
                isAutoScaled = true;
            } else {
                this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Aggregate"));
                setAutoScale(autoScaleBound, false);
                isAutoScaled = false;                
            }
        });
        
               
        aggregationCheckBox.addItemListener((ie) -> {

            if (aggregationCheckBox.isSelected()) {
                this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Weighted Aggregate"));

                if (currentBound != 1) {
                    setBound(aggregationSlider.getValue(), true);
                }
                
                isWeightedAggregated = true;             
            } else {
                this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Aggregate"));

                if(currentBound != 1) {
                    setBound(aggregationSlider.getValue(), false);
                }
                
                isWeightedAggregated = false;
            }
        });
                
        autoScalePanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        aggregationPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        
        this.add(autoScalePanel);             
        this.add(aggregationPanel);
        
        
    }
    
    private void setBound(int bound, boolean weightedAggregated) {
        AggregatedProperty aggregatedProperty = new AggregatedProperty(bound, weightedAggregated, autoScaleBound, false);
        if (currentBound != bound || isWeightedAggregated != weightedAggregated) {
            aggregationAction.createAndDisplayAggregateAbN(aggregatedProperty);
        }
        
    }
    
    private void setAutoScale(int scaleBound, boolean isScaled) {
        AggregatedProperty aggregatedProperty = new AggregatedProperty(currentBound, isWeightedAggregated, scaleBound, isScaled);
        if (autoScaleBound != scaleBound || isAutoScaled != isScaled) {
            aggregationAction.createAndDisplayAggregateAbN(aggregatedProperty);
        }
    }
    
    
    private void displayCurrentBound() {
        displayBound(currentBound);
    }
    
    private void displayBound(int bound) {
        txtCurrentBound.setText(Integer.toString(bound));
    }
    
    private void displayCurrentWeightedCheckBox(){
        displayWeightedFlag(isWeightedAggregated);
    }
    
    private void displayWeightedFlag(boolean flag){   
        aggregationCheckBox.setSelected(flag);
    }
    
    private void displayCurrentScaleBound(){
        displayScaleBound(autoScaleBound);
    }
    
    private void displayScaleBound(int bound){
        txtAutoBound.setText(Integer.toString(bound));
    }
    
    private void displayCurrentScaleCheckBox(){
        displayAutoScaleCheckBox(isAutoScaled);
    }
    
    private void displayAutoScaleCheckBox(boolean flag){
        autoScaleCheckBox.setSelected(flag);
    }
    
    @Override
    public void initialize(AbNDisplayPanel displayPanel) {
        this.initialized = false;
        
        AggregateableAbstractionNetwork abn = (AggregateableAbstractionNetwork)displayPanel.getGraph().getAbstractionNetwork();
        
        AbstractionNetwork abnToProcess;
        
        boolean weightedAggregatedFlag = false;

        if(abn.isAggregated()) {
            AggregateAbstractionNetwork aggregateAbN = (AggregateAbstractionNetwork)abn;
            
            abnToProcess = aggregateAbN.getNonAggregateSourceAbN();
            weightedAggregatedFlag = aggregateAbN.getAggregatedProperty().getWeighted();
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
        
        if (weightedAggregatedFlag) {
            bound = max[0];
        } else {
            bound = Math.min(bound, 100);
        }
        
        aggregationSlider.setMaximum(bound);
        
        this.initialized = false;
        
        if(abn instanceof AggregateAbstractionNetwork) {
            int abnBound = ((AggregateAbstractionNetwork)abn).getAggregateBound();
            boolean weightedAggregate = ((AggregateAbstractionNetwork)abn).getAggregatedProperty().getWeighted();
            int autoBound = ((AggregateAbstractionNetwork)abn).getAggregatedProperty().getAutoScaleBound();
            boolean autoScaled = ((AggregateAbstractionNetwork)abn).getAggregatedProperty().getAutoScaled();
                         
            this.currentBound = abnBound;
            this.isWeightedAggregated = weightedAggregate;
            this.autoScaleBound = autoBound;
            this.isAutoScaled = autoScaled;
            
            aggregationSlider.setValue(abnBound);
            aggregationCheckBox.setSelected(weightedAggregate);
            autoScaleCheckBox.setSelected(autoScaled);
            txtAutoBound.setText(String.valueOf(autoBound));
                       
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
    
    
    private void setAutoPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);
        for (Component cp : panel.getComponents()) {
            cp.setEnabled(isEnabled);
        }
        autoScaleCheckBox.setEnabled(!isEnabled);
    }

    
}
