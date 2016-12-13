package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.ConceptSearchConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.ConceptSearchPanel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author Chris O
 */
public abstract class RootSelectionPanel<ABN_T extends AbstractionNetwork> extends JPanel {

    public interface RootSelectionListener {
        public void rootSelected(Concept root);
        public void noRootSelected();
    }
    
    private Optional<Ontology<Concept>> ontology = Optional.empty();

    private final ConceptSearchPanel conceptSearchPanel;
    
    private final JToggleButton btnUseWholeOntology;
    private final JToggleButton btnSelectSpecificRoot;
    
    private final ArrayList<RootSelectionListener> rootSelectionListeners = new ArrayList<>();
    
    private Optional<Concept> selectedRoot = Optional.empty();

    public RootSelectionPanel(AbNConfiguration config) {
        this.setLayout(new BorderLayout());
        
        this.conceptSearchPanel = new ConceptSearchPanel(config, new ConceptSearchConfiguration() {

            @Override
            public ArrayList<Concept> doSearch(ConceptSearchPanel.SearchType type, String query) {
                return (ArrayList<Concept>) (ArrayList<?>) searchOntology(type, query);
            }

            @Override
            public void searchResultSelected(Concept c) {
                
                selectedRoot = Optional.of(c);
                
                rootSelectionListeners.forEach( (listener) -> {
                   listener.rootSelected(c);
                });
            }

            @Override
            public void noSearchResultSelected() {
                rootSelectionListeners.forEach((listener) -> {
                    listener.noRootSelected();
                });
            }
        });

        this.btnUseWholeOntology = new JToggleButton("Use Complete Ontology");
        this.btnUseWholeOntology.addActionListener( (ae) -> {
            useEntireOntologySelected();
        });
        
        this.btnSelectSpecificRoot = new JToggleButton("Select Specific Class");
        this.btnSelectSpecificRoot.addActionListener( (ae) -> {
            useSpecificRootSelected();
        });
        
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(btnUseWholeOntology);
        buttonGroup.add(btnSelectSpecificRoot);

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(btnUseWholeOntology);
        northPanel.add(btnSelectSpecificRoot);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(conceptSearchPanel, BorderLayout.CENTER);
        
        reset();
    }
    
    public void addRootSelectionListener(RootSelectionListener rootSelectionListener) {
        rootSelectionListeners.add(rootSelectionListener);
    }
    
    public void removeRootSelectionListener(RootSelectionListener rootSelectionListener) {
        rootSelectionListeners.remove(rootSelectionListener);
    }
    
    public Optional<Concept> getSelectedRoot() {
        return selectedRoot;
    }
    
    public void initialize(Ontology ontology) {
        this.setEnabled(true);
        
        reset();
    }

    public void reset() {
        btnUseWholeOntology.setSelected(true);
        
        useEntireOntologySelected();
        
    }

    private void useEntireOntologySelected() {
        this.btnSelectSpecificRoot.setSelected(false);
        this.conceptSearchPanel.setEnabled(false);
        
        this.conceptSearchPanel.clearResults();

        if (ontology.isPresent()) {
            Concept root = ontology.get().getConceptHierarchy().getRoot();
            
            rootSelectionListeners.forEach((listener) -> {
                listener.rootSelected(root);
            });
            
            this.selectedRoot = Optional.of(root);
        } else {
            rootSelectionListeners.forEach((listener) -> {
                listener.noRootSelected();
            });

            this.selectedRoot = Optional.empty();
        }
    }
    
    private void useSpecificRootSelected() {
        this.btnUseWholeOntology.setSelected(false);
        this.conceptSearchPanel.setEnabled(true);

        rootSelectionListeners.forEach((listener) -> {
            listener.noRootSelected();
        });
    }
    
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        this.btnUseWholeOntology.setEnabled(value);
        this.btnSelectSpecificRoot.setEnabled(value);
        
        this.conceptSearchPanel.setEnabled(value && btnSelectSpecificRoot.isSelected());
    }
    
    public abstract ArrayList<Concept> searchOntology(ConceptSearchPanel.SearchType type, String query);
}
