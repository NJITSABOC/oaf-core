package edu.njit.cs.saboc.blu.core.datastructure.hierarchy;

import SnomedShared.generic.GenericConceptGroup;

/**
 *
 * @author Chris O
 */
public class ConceptGroupHierarchy<T extends GenericConceptGroup> extends SingleRootedHierarchy<T, ConceptGroupHierarchy<T>> {
    public ConceptGroupHierarchy(T root) {
        super(root);
    }
    
    public ConceptGroupHierarchy<T> getSubhierarchyRootedAt(T root) {
        // TODO: Get this working...
        
        return new ConceptGroupHierarchy<T>(root);
    }
    
    protected ConceptGroupHierarchy<T> createHierarchy(T root) {
        return new ConceptGroupHierarchy<T>(root); 
    }
}
