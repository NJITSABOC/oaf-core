package edu.njit.cs.saboc.blu.core.datastructure.hierarchy;

import SnomedShared.generic.GenericConceptGroup;

/**
 *
 * @author Chris O
 */
public class ConceptGroupHierarchy<T extends GenericConceptGroup> extends SingleRootedHierarchy<T> {
    public ConceptGroupHierarchy(T root) {
        super(root);
    }
    
    public SingleRootedHierarchy<T> getSubhierarchyRootedAt(T root) {
        // TODO: Get this working...
        
        return new ConceptGroupHierarchy(root);
    }
}
