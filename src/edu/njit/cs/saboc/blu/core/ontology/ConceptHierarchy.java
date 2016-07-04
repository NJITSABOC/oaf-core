package edu.njit.cs.saboc.blu.core.ontology;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class ConceptHierarchy<T extends Concept> extends Hierarchy<T> {
    
    public ConceptHierarchy(Set<T> roots) {
        super(roots);
    }
    
    public ConceptHierarchy(T root) {
        this(Collections.singleton(root));
    }
    
    public ConceptHierarchy(Set<T> roots, Map<T, Set<T>> hierarchy) {
        super(roots, hierarchy);
    }
    
    public ConceptHierarchy(T root, Map<T, Set<T>> hierarchy) {
        this(Collections.singleton(root), hierarchy);
    }
    
    public ConceptHierarchy(Hierarchy<T> hierarchy) {
        super(hierarchy.getRoots(), hierarchy.getAllChildRelationships());
    }
    
    public Set<T> getConceptsInHierarchy() {
        return super.getNodesInHierarchy();
    }
    
    public ConceptHierarchy<T> getSubhierarchyRootedAt(T root) {
        Hierarchy<T> base = super.getSubhierarchyRootedAt(root);
        
        return new ConceptHierarchy<>(base);
    }
}
