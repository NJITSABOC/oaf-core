package edu.njit.cs.saboc.blu.core.abn.diff.explain;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class ConceptParentChange extends ConceptHierarchicalChange {
    
    public static enum ParentState {
        Added,
        Removed
    }

    private final ParentState parentState;
    private final Concept parent;
    
    private final Concept modifiedConcept;
    
    public ConceptParentChange(
            Concept affectedConcept, 
            Concept modifiedConcept, 
            ChangeInheritanceType inheritance, 
            Concept parent, 
            ParentState parentState) {
        
        super(affectedConcept, inheritance);
        
        this.parent = parent;
        this.parentState = parentState;
        this.modifiedConcept = modifiedConcept;
    }
    
    public Concept getParent() {
        return parent;
    }
    
    public Concept getModifiedConcept() {
        return modifiedConcept;
    }
    
    public ParentState getParentState() {
        return parentState;
    }
}
