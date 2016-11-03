package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RelationshipSubtaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Chris O
 */
public class RelationshipSubtaxonomyDerivationPanel extends JPanel {
    public interface RelationshipSubtaxonomyDerivationAction {
        public void createAndDisplayRelationshipSubtaxonomy(Set<InheritableProperty> properties);
    }
    
    private final ArrayList<JCheckBox> propertyBoxes;
    private final ArrayList<InheritableProperty> availableProperties;
    
    private final JPanel propertyListPanel;
    
    private final JButton derivationButton;
    
    public RelationshipSubtaxonomyDerivationPanel(
            PAreaTaxonomyConfiguration config,
            RelationshipSubtaxonomyDerivationAction derivationAction) {
        
        super(new BorderLayout());
        
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), 
                    String.format("Select %s to Use in Derivation", 
                        config.getTextConfiguration().getPropertyTypeName(true))));
        
        this.propertyBoxes = new ArrayList<>();
        this.availableProperties = new ArrayList<>();
        
        this.propertyListPanel = new JPanel();
        this.propertyListPanel.setLayout(new BoxLayout(propertyListPanel, BoxLayout.Y_AXIS));
        
        this.propertyListPanel.setBackground(Color.WHITE);
        
        this.add(new JScrollPane(propertyListPanel), BorderLayout.CENTER);
        
        JPanel selectionBtnPanel = new JPanel(new BorderLayout());
        
        JButton unselectAllBtn = new JButton("Select None");
        unselectAllBtn.addActionListener( (ae) -> {
            propertyBoxes.forEach( (cb) -> {
               cb.setSelected(false);
            });
        });
        
        JButton selectAllbtn = new JButton("Select All");
        selectAllbtn.addActionListener( (ae) -> {
            propertyBoxes.forEach( (cb) -> {
               cb.setSelected(true);
            });
        });
        
        JPanel optionPanel = new JPanel(new BorderLayout());
        
        selectionBtnPanel.add(unselectAllBtn, BorderLayout.WEST);
        selectionBtnPanel.add(selectAllbtn, BorderLayout.EAST);
        
        optionPanel.add(selectionBtnPanel, BorderLayout.NORTH);
        
        derivationButton = new JButton("Derive Subtaxonomy");
        derivationButton.addActionListener( (ae) -> {
            derivationAction.createAndDisplayRelationshipSubtaxonomy(this.getUserSelectedProperties());
        });
        
        optionPanel.add(derivationButton, BorderLayout.SOUTH);
        
        this.add(optionPanel, BorderLayout.SOUTH);
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
    
    public void initialize(PAreaTaxonomy taxonomy) {
        ArrayList<InheritableProperty> availableProperties = new ArrayList<>(
                taxonomy.getAreaTaxonomy().getPropertiesInTaxonomy());
        
        availableProperties.sort( (a, b) -> {
            return a.getName().compareToIgnoreCase(b.getName());
        });
        
        propertyBoxes.clear();
        
        this.availableProperties.clear();
        availableProperties.addAll(availableProperties);
        
        propertyListPanel.removeAll();
        
        availableProperties.forEach((property) -> {
            String propertyName = property.getName();

            JCheckBox chkSelectProperty = new JCheckBox(propertyName);
            chkSelectProperty.setOpaque(false);
            chkSelectProperty.setSelected(true);

            propertyBoxes.add(chkSelectProperty);
            propertyListPanel.add(chkSelectProperty);
        });
 
        propertyListPanel.validate();
        propertyListPanel.repaint();
    }
    
    public void initializeSubtaxonomy(RelationshipSubtaxonomy taxonomy) {
        initialize(taxonomy.getSourceTaxonomy());
        
        Set<InheritableProperty> usedProperties = taxonomy.getAllowedProperties();
                
        for(int c = 0; c < propertyBoxes.size(); c++) {
            InheritableProperty property = availableProperties.get(c);
            JCheckBox propertyBox = propertyBoxes.get(c);
            
            propertyBox.setSelected(usedProperties.contains(property));
        }
    }
}