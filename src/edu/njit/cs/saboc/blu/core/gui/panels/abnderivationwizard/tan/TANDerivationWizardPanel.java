package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.OntologySearcher;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.RootSelectionPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.RootSelectionPanel.RootSelectionListener;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class TANDerivationWizardPanel extends AbNDerivationWizardPanel {
    
    private final RootSelectionPanel<ClusterTribalAbstractionNetwork> rootSelectionPanel;
    
    private final TANPatriarchListPanel selectedPatriarchPanel;
    
    private final JButton deriveButton;
    
    public TANDerivationWizardPanel(TANConfiguration config) {
        
        this.setLayout(new BorderLayout());
        
        JPanel derivationOptionsPanel = new JPanel();
        derivationOptionsPanel.setLayout(new BoxLayout(derivationOptionsPanel, BoxLayout.X_AXIS));
                
        this.rootSelectionPanel = new RootSelectionPanel<>(config);
        
        this.rootSelectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                "1. Select Tribal Abstraction Network Root Classes"));
        
        this.rootSelectionPanel.addRootSelectionListener(new RootSelectionListener() {

            @Override
            public void rootSelected(Concept root) {
                if(selectedPatriarchPanel.inUseChildrenMode()) {
                    selectedPatriarchPanel.conceptSelected(root);
                }
            }

            @Override
            public void rootDoubleClicked(Concept root) {
                selectedPatriarchPanel.conceptSelected(root);
            }

            @Override
            public void noRootSelected() {
                selectedPatriarchPanel.resetView();
            }
        });
        
        this.selectedPatriarchPanel = new TANPatriarchListPanel(config);
        this.selectedPatriarchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                "2. Selected Patriarchs"));
        
        derivationOptionsPanel.add(rootSelectionPanel);
        derivationOptionsPanel.add(selectedPatriarchPanel);
        
        this.add(derivationOptionsPanel, BorderLayout.CENTER);
        
        this.deriveButton = new JButton("Derive Tribal Abstraction Network");
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(deriveButton, BorderLayout.EAST);
        
        this.add(southPanel, BorderLayout.SOUTH);
        
        resetView();
    }
    
    public void setEnabled(boolean value) {
        super.setEnabled(value);

        rootSelectionPanel.setEnabled(value);
        selectedPatriarchPanel.setEnabled(value);
        
        deriveButton.setEnabled(value);
    }
    
    public void initialize(Ontology ontology, OntologySearcher searcher) {
        super.initialize(ontology);
        
        rootSelectionPanel.initialize(ontology, searcher);
        selectedPatriarchPanel.initialize(ontology);
    }
    
    public final void resetView() {
        rootSelectionPanel.resetView();
        selectedPatriarchPanel.resetView();
    }
}
