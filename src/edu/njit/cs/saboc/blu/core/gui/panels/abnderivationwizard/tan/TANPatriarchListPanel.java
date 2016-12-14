package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.tan;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
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
public class TANPatriarchListPanel extends AbNDerivationWizardPanel {
    
    private final Set<Concept> selectedPatriarchs = new HashSet<>();
    
    private final ConceptList selectedPatriarchList;
    
    private final JToggleButton useChildrenBtn;
    private final JToggleButton userSelectionBtn;
    
    public TANPatriarchListPanel(AbNConfiguration config) {
        
        this.setLayout(new BorderLayout());
        
        this.useChildrenBtn = new JToggleButton(String.format("Use %s as Roots", 
                    config.getTextConfiguration().getChildConceptTypeName(true)));
        this.useChildrenBtn.addActionListener( (ae) -> {
            useChildrenSelected();
        });
        
        this.userSelectionBtn = new JToggleButton("Select Individual Roots");
        this.userSelectionBtn.addActionListener( (ae) -> {
            userSelectionSelected();
        });
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(useChildrenBtn);
        bg.add(userSelectionBtn);
        
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        northPanel.add(useChildrenBtn);
        northPanel.add(userSelectionBtn);
        
        this.add(northPanel, BorderLayout.NORTH);
        
        this.selectedPatriarchList = new ConceptList(config);
        
        this.add(selectedPatriarchList, BorderLayout.CENTER);
        
        resetView();
    }
    
    public Set<Concept> getSelectedPatriarchs() {
        return selectedPatriarchs;
    }
    
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        useChildrenBtn.setEnabled(value);
        userSelectionBtn.setEnabled(value);
    }
    
    public void conceptSelected(Concept concept) {

        if (useChildrenBtn.isSelected()) {
            selectedPatriarchs.clear();
            
            if (super.getCurrentOntology().isPresent()) {
                selectedPatriarchs.addAll(super.getCurrentOntology().get().getConceptHierarchy().getChildren(concept));
            }
        } else {
            selectedPatriarchs.add(concept);
        }

        ArrayList<Concept> sortedConcepts = new ArrayList<>(selectedPatriarchs);
        sortedConcepts.sort(new ConceptNameComparator());

        selectedPatriarchList.setContents(sortedConcepts);
    }
    
    public void initialize(Ontology ont) {
        super.initialize(ont);
        
        resetView();
    }
    
    public void clearContents() {
        super.clearContents();
        
        this.selectedPatriarchList.clearContents();
    }
    
    public final void resetView() {
        useChildrenBtn.setSelected(true);
        
        useChildrenSelected();
    }
    
    public boolean inUseChildrenMode() {
        return useChildrenBtn.isSelected();
    }
    
    private void useChildrenSelected() {
        this.selectedPatriarchList.clearContents();
        this.selectedPatriarchs.clear();
    }
    
    private void userSelectionSelected() {
        this.selectedPatriarchList.clearContents();
        this.selectedPatriarchs.clear();
    }
}