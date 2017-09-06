
package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayWidget;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.framestate.FrameState;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import sun.java2d.loops.CompositeType;

/**
 *
 * @author Chris O
 */
public class AggregatationSliderPanel extends AbNDisplayWidget {
   
    public interface AggregationAction {
        public void createAndDisplayAggregateAbN(AggregatedProperty ap);
    }
    
    private final JSlider aggregationSlider;
    
    private final JTextField txtCurrentBound;
    
    private final JTextField txtAutoBound;
    
    private final JComboBox<String> selectBox;
    
    private final JCheckBox aggregationCheckBox;
    
    private final JCheckBox autoScaleCheckBox;
    
    private final AggregationAction aggregationAction;
    
    private final Dimension panelSize = new Dimension(170, 120);
    
    private int currentBound = 1;
        
    private int autoScaleBound = 25;
    
    private boolean initialized = false;
    
    private boolean isWeightedAggregated = false;
    
    private boolean isAutoScaled = false;
    
    private FrameState frameState;
    
    public AggregatationSliderPanel(
            AbNDisplayPanel displayPanel,
            FrameState frameState,
            AggregationAction aggregationAction) {
        
        super(displayPanel);
        
        this.frameState = frameState;
        this.aggregationAction = aggregationAction;
        String[] choices = {"Auto Scale", "Manual Scale"};
        this.selectBox = new JComboBox<>(choices);
        selectBox.setSelectedIndex(0);
               
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
            displayCurrentAutoScaleBound();
            displayCurrentAutoScaleCheckBox();
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
                setPanelEnabled(aggregationPanel, false);
            } else {
                this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Aggregate"));
                setAutoScale(autoScaleBound, false);
                isAutoScaled = false;  
                setPanelEnabled(aggregationPanel, true);
            }
        });
        
               
        aggregationCheckBox.addItemListener((ie) -> {

            if (aggregationCheckBox.isSelected()) {
                this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Weighted Aggregate"));

                if (currentBound != 1) {
                    setBound(aggregationSlider.getValue(), true);
                }
                
                isWeightedAggregated = true; 
                setPanelEnabled(autoScalePanel, false);
            } else {
                this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Aggregate"));

                if(currentBound != 1) {
                    setBound(aggregationSlider.getValue(), false);
                }
                
                isWeightedAggregated = false;
                setPanelEnabled(autoScalePanel, true);
            }
        });
        

       


        autoScalePanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        aggregationPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        
        JPanel cards = new JPanel(new CardLayout());
        cards.add(autoScalePanel, "Auto Scale");
        cards.add(aggregationPanel, "Manual Scale");
        
        selectBox.addActionListener((ae) -> {
            
                JComboBox jcb = (JComboBox) ae.getSource();
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, jcb.getSelectedItem().toString());
                 
        });

        
        this.add(selectBox);
        this.add(cards);
//        this.add(autoScalePanel);             
//        this.add(aggregationPanel);            
        
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
    
    private void setFrameState(FrameState frameState){
        aggregationAction.createAndDisplayAggregateAbN(frameState.getAggregateProperty());
        frameState.setInitialized(false);
    }
    
    
    private void displayCurrentBound() {
        displayBound(currentBound);
    }
    
    private void displayBound(int bound) {
        txtCurrentBound.setText(Integer.toString(bound));
    }
    
    private void displayCurrentWeightedCheckBox(){
        displayWeightedCheckBox(isWeightedAggregated);
    }
    
    private void displayWeightedCheckBox(boolean flag){   
        aggregationCheckBox.setSelected(flag);
    }
    
    private void displayCurrentAutoScaleBound(){
        displayAutoScaleBound(autoScaleBound);
    }
    
    private void displayAutoScaleBound(int bound){
        txtAutoBound.setText(Integer.toString(bound));
    }
    
    private void displayCurrentAutoScaleCheckBox(){
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
        
//        this.initialized = false;
        if(abn instanceof AggregateAbstractionNetwork) {
            
            this.currentBound = ((AggregateAbstractionNetwork)abn).getAggregatedProperty().getBound();
            this.isWeightedAggregated = ((AggregateAbstractionNetwork)abn).getAggregatedProperty().getWeighted();
            this.autoScaleBound = ((AggregateAbstractionNetwork)abn).getAggregatedProperty().getAutoScaleBound();
            this.isAutoScaled = ((AggregateAbstractionNetwork)abn).getAggregatedProperty().getAutoScaled();
                         
            aggregationSlider.setValue(currentBound);
            aggregationCheckBox.setSelected(isWeightedAggregated);
            autoScaleCheckBox.setSelected(isAutoScaled);
            txtAutoBound.setText(String.valueOf(this.autoScaleBound));
            
            
        } else {
            this.currentBound = frameState.getAggregateProperty().getBound();
            this.isWeightedAggregated = frameState.getAggregateProperty().getWeighted();
            this.autoScaleBound = frameState.getAggregateProperty().getAutoScaleBound();
            this.isAutoScaled = frameState.getAggregateProperty().getAutoScaled();
            
            aggregationSlider.setValue(currentBound);
            aggregationCheckBox.setSelected(isWeightedAggregated);
            autoScaleCheckBox.setSelected(isAutoScaled);
            txtAutoBound.setText(String.valueOf(this.autoScaleBound));
            
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
    
    
    private void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);
        for (Component cp : panel.getComponents()) {
            cp.setEnabled(isEnabled);
        }
    }

    
}
