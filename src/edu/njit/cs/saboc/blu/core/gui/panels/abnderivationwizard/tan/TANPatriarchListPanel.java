package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.tan;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.blu.core.utils.comparators.ConceptNameComparator;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author Chris O
 */
public class TANPatriarchListPanel extends JPanel {
    
    private Optional<Ontology> optCurrentOntology = Optional.empty();

    private final Set<Concept> selectedRoots = new HashSet<>();
    
    private final ConceptList selectedRootList;
    
    private final JToggleButton useChildrenBtn;
    private final JToggleButton userSelectionBtn;
    
    public TANPatriarchListPanel(AbNConfiguration config) {
        
        this.setLayout(new BorderLayout());
        
        this.useChildrenBtn = new JToggleButton(String.format("Use %s as Roots", 
                    config.getTextConfiguration().getChildConceptTypeName(true)));
        
        this.userSelectionBtn = new JToggleButton("Select Individual Roots");
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(useChildrenBtn);
        bg.add(userSelectionBtn);
        
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        northPanel.add(useChildrenBtn);
        northPanel.add(userSelectionBtn);
        
        this.add(northPanel, BorderLayout.NORTH);
        
        this.selectedRootList = new ConceptList(config);
        
        this.add(selectedRootList, BorderLayout.CENTER);
        
        reset();
    }
    
    
    public void conceptSelected(Concept concept) {
        selectedRoots.clear();

        if (useChildrenBtn.isSelected()) {
            if (optCurrentOntology.isPresent()) {
                selectedRoots.addAll(optCurrentOntology.get().getConceptHierarchy().getChildren(concept));
            }
        } else {
            selectedRoots.add(concept);
        }

        ArrayList<Concept> sortedConcepts = new ArrayList<>(selectedRoots);
        sortedConcepts.sort(new ConceptNameComparator());

        selectedRootList.setContents(sortedConcepts);
    }
    
    public void initialize(Ontology ont) {
        this.optCurrentOntology = Optional.of(ont);
    }
    
    public final void reset() {
        this.optCurrentOntology = Optional.empty();
        
        useChildrenBtn.setSelected(true);
        
        selectedRootList.clearContents();
    }
    
}
