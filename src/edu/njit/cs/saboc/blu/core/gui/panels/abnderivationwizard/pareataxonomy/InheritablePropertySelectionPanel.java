package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Chris O
 */
public class InheritablePropertySelectionPanel extends AbNDerivationWizardPanel {
        
    private final ArrayList<JCheckBox> propertyBoxes;
    private final ArrayList<InheritableProperty> availableProperties;
    
    private final JPanel propertyListPanel;
    
    private final JButton btnUnselectAll;
    private final JButton btnSelectAll;
    
    private final JScrollPane propertyScroller;
    
    public InheritablePropertySelectionPanel() {
        this.setLayout(new BorderLayout());
        
        this.propertyBoxes = new ArrayList<>();
        this.availableProperties = new ArrayList<>();
        
        this.propertyListPanel = new JPanel();
        this.propertyListPanel.setLayout(new BoxLayout(propertyListPanel, BoxLayout.Y_AXIS));
        
        this.propertyListPanel.setBackground(Color.WHITE);
        
        this.propertyScroller = new JScrollPane(propertyListPanel);
        
        this.add(propertyScroller, BorderLayout.CENTER);

        btnUnselectAll = new JButton("Select None");
        btnUnselectAll.addActionListener( (ae) -> {
            propertyBoxes.forEach( (cb) -> {
               cb.setSelected(false);
            });
        });
        
        btnSelectAll = new JButton("Select All");
        btnSelectAll.addActionListener( (ae) -> {
            propertyBoxes.forEach( (cb) -> {
               cb.setSelected(true);
            });
        });

        JPanel selectionBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        selectionBtnPanel.add(btnUnselectAll);
        selectionBtnPanel.add(btnSelectAll);
        
        this.add(selectionBtnPanel, BorderLayout.SOUTH);
    }
    
    public Set<InheritableProperty> getAvailableProperties() {
        return new HashSet<>(availableProperties);
    }
    
    public Set<InheritableProperty> getUserSelectedProperties() {
        Set<InheritableProperty> propertyDetails = new HashSet<>();
        
        for(int c = 0; c < availableProperties.size(); c++) {
            if(propertyBoxes.get(c).isSelected()) {
                propertyDetails.add(availableProperties.get(c));
            }
        }
        
        return propertyDetails;
    }
    
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        btnUnselectAll.setEnabled(value);
        btnSelectAll.setEnabled(value);
    }
    
    public void clearContents() {
        propertyBoxes.clear();
        availableProperties.clear();

        propertyListPanel.removeAll();
    }
    
    public void resetView() {
        clearContents();
        
        doRepaint();
    }
    
    public void initialize(ArrayList<InheritableProperty> availableProperties) {
        initialize(availableProperties, new HashSet<>(availableProperties));
    }
    
    public void initialize(ArrayList<InheritableProperty> availableProperties, Set<InheritableProperty> selectedProperties) {
        this.propertyBoxes.clear();
        this.availableProperties.clear();

        this.propertyListPanel.removeAll();
        
        availableProperties.forEach( (property) -> {
            String propertyName = property.getName();

            JCheckBox chkSelectProperty = new JCheckBox(propertyName);
            chkSelectProperty.setOpaque(false);

            chkSelectProperty.setSelected(selectedProperties.contains(property));

            propertyBoxes.add(chkSelectProperty);
            propertyListPanel.add(chkSelectProperty);
            
            this.availableProperties.add(property);
        });
        
       doRepaint();
    }
    
    private void doRepaint() {
        propertyScroller.validate();
        propertyScroller.repaint();

        propertyListPanel.validate();
        propertyListPanel.repaint();
    }
}
