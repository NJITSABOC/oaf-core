package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.OntologySearcher;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.RootSelectionPanel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class TANDerivationWizardPanel extends AbNDerivationWizardPanel {
    
    public interface DeriveTANAction {
        public void deriveTribalAbstractionNetwork(Set<Concept> patriarchs);
    }
    
    private final RootSelectionPanel<ClusterTribalAbstractionNetwork> rootSelectionPanel;
    
    private final TANPatriarchListPanel selectedPatriarchPanel;
    
    private final JButton deriveButton;
    
    private final DeriveTANAction derivationAction;
        
    public TANDerivationWizardPanel(TANConfiguration config, DeriveTANAction derivationAction) {
        
        this.setLayout(new BorderLayout());
        
        this.derivationAction = derivationAction;
        
        JPanel derivationOptionsPanel = new JPanel();
        derivationOptionsPanel.setLayout(new BoxLayout(derivationOptionsPanel, BoxLayout.X_AXIS));
                
        this.rootSelectionPanel = new RootSelectionPanel<>(config);
        
        this.rootSelectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                String.format("1. Select Tribal Abstraction Network Root %s", 
                        config.getTextConfiguration().getConceptTypeName(true))));
                
        this.selectedPatriarchPanel = new TANPatriarchListPanel(config, rootSelectionPanel);
        this.selectedPatriarchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                "2. Selected Patriarchs"));
        
        derivationOptionsPanel.add(rootSelectionPanel);
        derivationOptionsPanel.add(selectedPatriarchPanel);
        
        this.add(derivationOptionsPanel, BorderLayout.CENTER);
        
        this.deriveButton = new JButton("Derive Tribal Abstraction Network");
        this.deriveButton.addActionListener( (ae) -> {
            deriveTribalAbstractionNetwork();
        });
        
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
        
        selectedPatriarchPanel.resetView();
        rootSelectionPanel.resetView();
    }
    
    public final void resetView() {
        rootSelectionPanel.resetView();
        selectedPatriarchPanel.resetView();
    }
    
    private void deriveTribalAbstractionNetwork() {
        if(!super.getCurrentOntology().isPresent()) {
            
           return;
        }
        
        if(!rootSelectionPanel.getSelectedRoot().isPresent()) {
            
            return;
        }
        
        if(selectedPatriarchPanel.getSelectedPatriarchs().size() < 2) {
            
            return;
        }
        
        derivationAction.deriveTribalAbstractionNetwork(selectedPatriarchPanel.getSelectedPatriarchs());
    }
}
