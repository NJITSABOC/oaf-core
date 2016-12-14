package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.ConceptSearchPanel.SearchType;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.RootSelectionPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.RootSelectionPanel.RootSelectionListener;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class TANDerivationWizardPanel extends JPanel {
    
    private final RootSelectionPanel<ClusterTribalAbstractionNetwork> rootSelectionPanel;
    
    private final TANPatriarchListPanel selectedPatriarchPanel;
    
    public TANDerivationWizardPanel(TANConfiguration config) {
        
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        this.rootSelectionPanel = new RootSelectionPanel<ClusterTribalAbstractionNetwork>(config) {

            @Override
            public ArrayList<Concept> searchOntology(SearchType type, String query) {
                return new ArrayList<>();
            }
            
        };
        
        this.rootSelectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                "1. Select Partial-area Taxonomy Root Class"));
        
        this.rootSelectionPanel.addRootSelectionListener(new RootSelectionListener() {

            @Override
            public void rootSelected(Concept root) {
                selectedPatriarchPanel.conceptSelected(root);
            }

            @Override
            public void noRootSelected() {
                
            }
        });
        
        this.selectedPatriarchPanel = new TANPatriarchListPanel(config);
        this.selectedPatriarchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                "2. Selected Patriarchs"));
        
        this.add(rootSelectionPanel);
        this.add(selectedPatriarchPanel);
    }
    
    public void initialize(Ontology ontology) {
        rootSelectionPanel.initialize(ontology);
    }
    
    public void reset() {
        rootSelectionPanel.reset();
        selectedPatriarchPanel.reset();
    }
}
