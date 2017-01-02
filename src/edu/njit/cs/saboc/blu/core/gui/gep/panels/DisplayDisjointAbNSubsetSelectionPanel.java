package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayWidget;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.disjointabn.DisjointAbNSubsetSelectionPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JDialog;

/**
 *
 * @author Chris O
 */
public class DisplayDisjointAbNSubsetSelectionPanel extends AbNDisplayWidget {
    
    public interface DisplayDisjointAbNSubsetAction {
        public void displayDisjointAbNSubset(DisjointAbstractionNetwork subsetDisjointAbN);
    }
    
    private final JButton createSubsetButton;

    private final Dimension panelSize = new Dimension(80, 48);
    
    private final DisplayDisjointAbNSubsetAction displayAction;

    public DisplayDisjointAbNSubsetSelectionPanel(
            AbNDisplayPanel displayPanel, 
            DisjointAbNConfiguration config, 
            PartitionedAbNConfiguration parentConfig, 
            DisplayDisjointAbNSubsetAction displayAction) {
        
        super(displayPanel);
        
        this.displayAction = displayAction;
                
        this.setLayout(new BorderLayout());

        this.createSubsetButton = new JButton("<html><div align='center'>View<br>Subset");
        
        this.createSubsetButton.addActionListener((ae) -> {
            
            JDialog dialog = new JDialog();
            
            DisjointAbNSubsetSelectionPanel selectionPanel = new DisjointAbNSubsetSelectionPanel(parentConfig, (subset) -> {

                DisjointAbstractionNetwork parentDisjointAbN = config.getAbstractionNetwork();

                DisjointAbstractionNetwork subsetDisjointAbN = parentDisjointAbN.getSubsetDisjointAbN(subset);

                displayAction.displayDisjointAbNSubset(subsetDisjointAbN);
                
                dialog.dispose();
            });
            
            dialog.setSize(1800, 600);
            dialog.add(selectionPanel);
            
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            
            selectionPanel.initialize(config.getAbstractionNetwork());
        });
        
        this.add(createSubsetButton, BorderLayout.CENTER);
    }

    @Override
    public void initialize(AbNDisplayPanel displayPanel) {
        
    }

    @Override
    public void displayPanelResized(AbNDisplayPanel displayPanel) {
        super.displayPanelResized(displayPanel);
        
        this.setBounds(displayPanel.getWidth() - panelSize.width - 300, 
                displayPanel.getHeight() - panelSize.height - 20, 
                panelSize.width, 
                panelSize.height);
    }
}