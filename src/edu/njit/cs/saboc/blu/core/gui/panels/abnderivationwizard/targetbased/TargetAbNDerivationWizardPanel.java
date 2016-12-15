package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.targetbased;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.OntologySearcher;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.RootSelectionPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.RootSelectionPanel.RootSelectionListener;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.pareataxonomy.InheritablePropertySelectionPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.pareataxonomy.InheritablePropertySelectionPanel.SelectionType;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class TargetAbNDerivationWizardPanel extends AbNDerivationWizardPanel{
    
    public interface InheritablePropertyRetriever {
        public Set<InheritableProperty> getInheritablePropertiesInSubhierarchy(Concept root);
    }
    
    public interface DeriveTargetAbNAction {
        public void deriveTargetAbN();
    }
    
    private final RootSelectionPanel rootSelectionPanel;
    
    private final InheritablePropertySelectionPanel propertyListPanel;
    
    private final DeriveTargetAbNAction derivationAction;
    
    public TargetAbNDerivationWizardPanel(
            AbNConfiguration config, 
            InheritablePropertyRetriever propertyRetriever,
            DeriveTargetAbNAction derivationAction) {
        
        this.derivationAction = derivationAction;
        
        this.setLayout(new BorderLayout());
        
        JPanel derivationOptionsPanel = new JPanel();
        derivationOptionsPanel.setLayout(new BoxLayout(derivationOptionsPanel, BoxLayout.X_AXIS));
        
        this.rootSelectionPanel = new RootSelectionPanel(config);
        this.rootSelectionPanel.addRootSelectionListener(new RootSelectionListener() {

            @Override
            public void rootSelected(Concept root) {
                Set<InheritableProperty> propertiesInSubhierarchy = propertyRetriever.getInheritablePropertiesInSubhierarchy(root);
                
                ArrayList<InheritableProperty> sortedProperties = new ArrayList<>(propertiesInSubhierarchy);
                sortedProperties.sort( (a, b) -> {
                    return a.getName().compareToIgnoreCase(b.getName());
                });
                
                propertyListPanel.initialize(sortedProperties, Collections.emptySet());
            }

            @Override
            public void rootDoubleClicked(Concept root) {
                rootSelected(root);
            }

            @Override
            public void noRootSelected() {
                
            }
            
        });
        
        derivationOptionsPanel.add(rootSelectionPanel);
        
        this.propertyListPanel = new InheritablePropertySelectionPanel(SelectionType.Single);
        
        derivationOptionsPanel.add(propertyListPanel);
        
        this.add(derivationOptionsPanel, BorderLayout.CENTER);
    }

    public void initialize(Ontology ontology, OntologySearcher searcher) {
        super.initialize(ontology);
        
        this.rootSelectionPanel.initialize(ontology, searcher);
    }

    public void setEnabled(boolean value) {
       rootSelectionPanel.setEnabled(value);
    }
    
    @Override
    public void resetView() {
       rootSelectionPanel.resetView();
    }
}
