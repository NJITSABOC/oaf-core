package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class InheritablePropertyChange extends DiffAbNConceptChange {
    
    public static enum ConceptPropertyState {
        Added,
        Removed
    }
    
    private final InheritableProperty theProperty;
    private final ConceptPropertyState propertyState;
    
    public InheritablePropertyChange(
            Concept affectedConcept, 
            ChangeInheritanceType inheritanceType, 
            InheritableProperty theProperty, 
            ConceptPropertyState propertyState) {
        
        super(affectedConcept, inheritanceType);
        
        this.theProperty = theProperty;
        this.propertyState = propertyState;
    }
    
    public InheritableProperty getProperty() {
        return theProperty;
    }
    
    public ConceptPropertyState getPropertyState() {
        return propertyState;
    }
}
