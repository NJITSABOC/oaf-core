
package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.diff.DiffTaxonomySubsetOptions;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Chris Ochs
 */
public class DiffTaxonomySubsetSelectionButton extends JButton {
    
    public interface DiffTaxonomySubsetCreationAction { 
        public void diffTaxonomySubsetCreated(DiffTaxonomySubsetOptions options);
    }
    
    private class DiffTaxonomyNodeSelectionPanel extends JPanel {
        
        private final DiffStateSelectionPanel diffAreaPanel;
        private final DiffStateSelectionPanel diffPAreaPanel;
        
        private final DiffTaxonomySubsetCreationAction creationAction;
        
        public DiffTaxonomyNodeSelectionPanel(DiffTaxonomySubsetCreationAction creationAction) {
            
            this.setLayout(new BorderLayout());
            
            this.creationAction = creationAction;
            
            JPanel selectionPanel = new JPanel(new GridLayout(1, 2));
            
            this.diffAreaPanel = new DiffStateSelectionPanel("Diff Areas");
            this.diffPAreaPanel = new DiffStateSelectionPanel("Diff Partial-areas");
            
            selectionPanel.add(diffAreaPanel);
            selectionPanel.add(diffPAreaPanel);
            
            this.add(selectionPanel, BorderLayout.CENTER);
            
            JButton btnApply = new JButton("Display Selected Subset");
            
            btnApply.addActionListener( (ae) -> {
                
                stateMenu.setVisible(false);
                
                creationAction.diffTaxonomySubsetCreated(
                        new DiffTaxonomySubsetOptions(
                                diffAreaPanel.getSelectedChangeStates(), 
                                diffPAreaPanel.getSelectedChangeStates()));
            });
            
            this.add(btnApply, BorderLayout.SOUTH);
        }
    }
    
    private final JPopupMenu stateMenu;
    
    public DiffTaxonomySubsetSelectionButton(DiffTaxonomySubsetCreationAction creationAction) {
        
        super("Display Options");
        
        this.stateMenu = new JPopupMenu();
        
        DiffTaxonomyNodeSelectionPanel selectionPanel = new DiffTaxonomyNodeSelectionPanel(creationAction);
        
        this.stateMenu.add(selectionPanel);

        this.addActionListener( (ae) -> {
            stateMenu.show(
                    this, 
                    0, 
                    -140 - this.getHeight());
        });
    }
}

class StateSelectionPanel extends JPanel {
    
    private final JCheckBox chkSelected;
    private final ChangeState state;
    
    public StateSelectionPanel(ChangeState state) {
        this.state = state;
        
        this.chkSelected = new JCheckBox(state.name());
        this.chkSelected.setSelected(true);
        
        this.setLayout(new BorderLayout());
        
        this.add(chkSelected, BorderLayout.CENTER);
    }
    
    public boolean isSelected() {
        return chkSelected.isSelected();
    }
    
    public ChangeState getChangeState() {
        return state;
    }
}

class DiffStateSelectionPanel extends JPanel {
    
    private final StateSelectionPanel introducedPanel;
    private final StateSelectionPanel removedPanel;
    private final StateSelectionPanel modifiedPanel;
    private final StateSelectionPanel unmodifiedPanel;
    
    private final List<StateSelectionPanel> optionPanels;
    
    public DiffStateSelectionPanel(String nodeName) {
        
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), 
                nodeName));
        
        
        this.setPreferredSize(new Dimension(120, 140));
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.introducedPanel = new StateSelectionPanel(ChangeState.Introduced);
        this.removedPanel = new StateSelectionPanel(ChangeState.Removed);
        this.modifiedPanel = new StateSelectionPanel(ChangeState.Modified);
        this.unmodifiedPanel = new StateSelectionPanel(ChangeState.Unmodified);
        
        this.optionPanels = Arrays.asList(
                introducedPanel, 
                removedPanel, 
                modifiedPanel, 
                unmodifiedPanel);
        
        optionPanels.forEach( (panel) -> {
            this.add(panel);
        });        
    }
    
    public Set<ChangeState> getSelectedChangeStates() {
        Set<ChangeState> selectedStates = new HashSet<>();
        
        this.optionPanels.forEach( (panel) -> {
            if(panel.isSelected()) {
                selectedStates.add(panel.getChangeState());
            }
        });
        
        return selectedStates;
    }
}
