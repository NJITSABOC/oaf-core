package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.panels.ConceptSearchConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.ConceptSearchPanel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.blu.core.utils.comparators.ConceptNameComparator;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author Chris O
 */
public class RootSelectionPanel<ABN_T extends AbstractionNetwork> extends AbNDerivationWizardPanel {

    public static enum RootSelectionMode {
        WholeOntology,
        TopLevelRoot,
        SearchForRoot
    }
    
    public interface RootSelectionListener {
        public void rootSelected(Concept root);
        public void rootDoubleClicked(Concept root);
        public void noRootSelected();
    }
    
    public interface RootSelectionModeChangedListener {
        public void selectionModeChanged(RootSelectionMode mode);
    }
        
    private final ConceptSearchPanel conceptSearchPanel;
    
    private final ConceptList ontologyRootList;
    
    private final JToggleButton btnUseWholeOntology;
    private final JToggleButton btnSelectTopLevelRoot;
    private final JToggleButton btnSearchForRoot;
    
    private final JPanel optionPanel;
    
    private final ArrayList<RootSelectionListener> rootSelectionListeners = new ArrayList<>();
    private final ArrayList<RootSelectionModeChangedListener> selectionModeChangedListeners = new ArrayList<>();
    
    private Optional<Concept> selectedRoot = Optional.empty();
    
    private Optional<OntologySearcher> optSearcher = Optional.empty();
    
    private RootSelectionMode rootSelectionMode = RootSelectionMode.WholeOntology;

    public RootSelectionPanel(AbNConfiguration config) {
        this.setLayout(new BorderLayout());
                
        this.ontologyRootList = new ConceptList(config);
        this.ontologyRootList.addEntitySelectionListener(new EntitySelectionListener<Concept>() {

            @Override
            public void entityClicked(Concept c) {
                selectedRoot = Optional.of(c);

                rootSelectionListeners.forEach((listener) -> {
                    listener.rootSelected(c);
                });
            }

            @Override
            public void entityDoubleClicked(Concept c) {
                entityClicked(c);
            }

            @Override
            public void noEntitySelected() {
                rootSelectionListeners.forEach((listener) -> {
                    listener.noRootSelected();
                });
            }
        });
        
        this.conceptSearchPanel = new ConceptSearchPanel(config, new ConceptSearchConfiguration() {

            @Override
            public ArrayList<Concept> doSearch(ConceptSearchPanel.SearchType type, String query) {
                return searchOntology(type, query);
            }

            @Override
            public void searchResultSelected(Concept c) {
                selectedRoot = Optional.of(c);
                
                rootSelectionListeners.forEach( (listener) -> {
                   listener.rootSelected(c);
                });
            }

            @Override
            public void searchResultDoubleClicked(Concept c) {
               rootSelectionListeners.forEach( (listener) -> {
                   listener.rootDoubleClicked(c);
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
        
        this.btnSelectTopLevelRoot = new JToggleButton("Select Ontology Root");
        this.btnSelectTopLevelRoot.addActionListener( (ae) -> {
            selectTopLevelRootSelected();
        });
        
        this.btnSearchForRoot = new JToggleButton("Search for Specific Root");
        this.btnSearchForRoot.addActionListener( (ae) -> {
            searchForRootSelected();
        });
        
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(btnUseWholeOntology);
        buttonGroup.add(btnSelectTopLevelRoot);
        buttonGroup.add(btnSearchForRoot);

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(btnUseWholeOntology);
        northPanel.add(btnSelectTopLevelRoot);
        northPanel.add(btnSearchForRoot);

        this.add(northPanel, BorderLayout.NORTH);
        
        optionPanel = new JPanel(new CardLayout());
        
        JPanel useWholeOntologyPanel = new JPanel();
        
        optionPanel.add(useWholeOntologyPanel, RootSelectionMode.WholeOntology.toString());
        optionPanel.add(ontologyRootList, RootSelectionMode.TopLevelRoot.toString());
        optionPanel.add(conceptSearchPanel, RootSelectionMode.SearchForRoot.toString());
        
        this.add(optionPanel, BorderLayout.CENTER);
        
        resetView();
    }
    
    public void addRootSelectionListener(RootSelectionListener rootSelectionListener) {
        rootSelectionListeners.add(rootSelectionListener);
    }
    
    public void removeRootSelectionListener(RootSelectionListener rootSelectionListener) {
        rootSelectionListeners.remove(rootSelectionListener);
    }
    
    public void addRootSelectionModeChangedListener(RootSelectionModeChangedListener rootSelectionModeChangedListener) {
        selectionModeChangedListeners.add(rootSelectionModeChangedListener);
    }
    
    public void removeRootSelectionModeChangedListener(RootSelectionModeChangedListener rootSelectionModeChangedListener) {
        selectionModeChangedListeners.remove(rootSelectionModeChangedListener);
    }
    
    public Optional<OntologySearcher> getSearcher() {
        return optSearcher;
    }
    
    public Optional<Concept> getSelectedRoot() {
        return selectedRoot;
    }
    
    public RootSelectionMode getRootSelectionMode() {
        return rootSelectionMode;
    }

    public void initialize(Ontology ontology, OntologySearcher searcher) {        
        super.initialize(ontology);
        
        this.optSearcher = Optional.of(searcher);
        
        this.btnUseWholeOntology.setSelected(true);
        
        ArrayList<Concept> roots = new ArrayList<>(ontology.getConceptHierarchy().getChildren(
                ontology.getConceptHierarchy().getRoot()));
        
        roots.sort(new ConceptNameComparator());
        
        this.ontologyRootList.setContents(roots);
        
        resetView();
    }
    
    public void clearContents() {
        super.clearContents();
        
        this.conceptSearchPanel.clearContents();
        this.ontologyRootList.clearContents();
        
        this.selectedRoot = Optional.empty();
        this.optSearcher = Optional.empty();
    }

    public final void resetView() {
        
        this.conceptSearchPanel.clearContents();
        
        if(btnSearchForRoot.isSelected()) {
            searchForRootSelected();
        } else if(btnSelectTopLevelRoot.isSelected()) {
            selectTopLevelRootSelected();
        } else {
            useEntireOntologySelected();
        }
    }

    private void useEntireOntologySelected() {
        
        CardLayout layout = (CardLayout)optionPanel.getLayout();
        layout.show(optionPanel, RootSelectionMode.WholeOntology.toString());
        
        this.rootSelectionMode = RootSelectionMode.WholeOntology;
        
        this.btnSearchForRoot.setSelected(false);
        
        this.conceptSearchPanel.setEnabled(false);
        this.conceptSearchPanel.clearResults();
        
        this.selectionModeChangedListeners.forEach( (listener) -> {
            listener.selectionModeChanged(RootSelectionMode.WholeOntology);
        });

        if (super.getCurrentOntology().isPresent()) {
            Concept root = super.getCurrentOntology().get().getConceptHierarchy().getRoot();
            
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
    
    private void selectTopLevelRootSelected() {
        CardLayout layout = (CardLayout)optionPanel.getLayout();
        layout.show(optionPanel, RootSelectionMode.TopLevelRoot.toString());
        
        
        this.rootSelectionMode = RootSelectionMode.TopLevelRoot;
        
        this.ontologyRootList.setEnabled(true);

        this.selectionModeChangedListeners.forEach( (listener) -> {
            listener.selectionModeChanged(RootSelectionMode.TopLevelRoot);
        });
        
        rootSelectionListeners.forEach( (listener) -> {
            listener.noRootSelected();
        });
    }
    
    private void searchForRootSelected() {
        
        CardLayout layout = (CardLayout)optionPanel.getLayout();
        layout.show(optionPanel, RootSelectionMode.SearchForRoot.toString());
        
        this.rootSelectionMode = RootSelectionMode.SearchForRoot;
        
        this.conceptSearchPanel.setEnabled(true);

        this.selectionModeChangedListeners.forEach( (listener) -> {
            listener.selectionModeChanged(RootSelectionMode.SearchForRoot);
        });
        
        rootSelectionListeners.forEach((listener) -> {
            listener.noRootSelected();
        });
    }
    
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        this.btnUseWholeOntology.setEnabled(value);
        this.btnSelectTopLevelRoot.setEnabled(value);
        this.btnSearchForRoot.setEnabled(value);
        
        this.ontologyRootList.setEnabled(value && btnSelectTopLevelRoot.isSelected());
        this.conceptSearchPanel.setEnabled(value && btnSearchForRoot.isSelected());
    }
    
    public ArrayList<Concept> searchOntology(ConceptSearchPanel.SearchType type, String query) {
        
        ArrayList<Concept> results = new ArrayList<>();
        
        if (optSearcher.isPresent()) {
            query = query.trim();
            query = query.toLowerCase();

            OntologySearcher searcher = optSearcher.get();

            switch (type) {
                case Starting:
                    results.addAll(searcher.searchStarting(query));
                    break;
                    
                case Anywhere:
                    results.addAll(searcher.searchAnywhere(query));
                    break;
                    
                case Exact:
                    results.addAll(searcher.searchExact(query));
                    break;
                    
                case ID:
                    results.addAll(searcher.searchID(query));
                    break;
            }
        }
        
        results.sort(new ConceptNameComparator());
        
        return results;
    }
}
