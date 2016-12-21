package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

/**
 *
 * @author Chris O
 */
public class InheritablePropertySelectionPanel extends AbNDerivationWizardPanel {
    
    public static enum SelectionType {
        Multiple,
        Single
    }
    
    public interface InheritablePropertySelectionListener {
        public void propertiesSelected(Set<InheritableProperty> properties);
    }
    
    private final ArrayList<JToggleButton> propertyBoxes;
    private final ArrayList<InheritableProperty> availableProperties;
    
    private final JPanel propertyListPanel;
    
    private final JButton btnUnselectAll;
    private final JButton btnSelectAll;
    
    private final JScrollPane propertyScroller;
    
    private final SelectionType selectionType;
    
    private final ArrayList<InheritablePropertySelectionListener> selectedPropertiesChangedListeners = new ArrayList<>();
    
    public InheritablePropertySelectionPanel(SelectionType selectionType) {
        this.selectionType = selectionType;
        
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
        
        if(this.selectionType == SelectionType.Multiple) {
            selectionBtnPanel.add(btnSelectAll);
        }

        this.add(selectionBtnPanel, BorderLayout.SOUTH);
    }
    
    public void addSelectedPropertiesChangedListener(InheritablePropertySelectionListener listener) {
        this.selectedPropertiesChangedListeners.add(listener);
    }
    
    public void removeSelectedPropertiesChangedListener(InheritablePropertySelectionListener listener) {
        this.selectedPropertiesChangedListeners.remove(listener);
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
        
        fireSelectedPropertiesChangedListeners();

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

        ButtonGroup buttonGroup = new ButtonGroup();

        availableProperties.forEach( (property) -> {
            String propertyName = property.getName();

            JToggleButton chkSelectProperty;

            if (selectionType == SelectionType.Multiple) {
                chkSelectProperty = new JCheckBox(propertyName);
            } else {
                chkSelectProperty = new JRadioButton(propertyName);
                buttonGroup.add(chkSelectProperty);
            }

            chkSelectProperty.setOpaque(false);

            chkSelectProperty.setSelected(selectedProperties.contains(property));
            
            chkSelectProperty.addActionListener( (ae) -> {
                fireSelectedPropertiesChangedListeners();
            });

            propertyBoxes.add(chkSelectProperty);
            propertyListPanel.add(chkSelectProperty);

            this.availableProperties.add(property);
        });
        
        fireSelectedPropertiesChangedListeners();

        buttonGroup.clearSelection();

        doRepaint();
    }
    
    private void doRepaint() {
        propertyScroller.validate();
        propertyScroller.repaint();

        propertyListPanel.validate();
        propertyListPanel.repaint();
    }
    
    private void fireSelectedPropertiesChangedListeners() {
        selectedPropertiesChangedListeners.forEach( (listener) -> {
            listener.propertiesSelected(this.getUserSelectedProperties());
        });
    }
}
