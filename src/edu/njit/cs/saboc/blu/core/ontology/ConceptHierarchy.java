package edu.njit.cs.saboc.blu.core.ontology;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class MultiRootedConceptHierarchy extends MultiRootedHierarchy<Concept> {
    
    public MultiRootedConceptHierarchy(Set<Concept> roots) {
        super(roots);
    }
    
    public MultiRootedConceptHierarchy(Concept root) {
        this(Collections.singleton(root));
    }
    
    public MultiRootedConceptHierarchy(Set<Concept> roots, HashMap<Concept, Set<Concept>> hierarchy) {
        super(roots, hierarchy);
    }
    
    public MultiRootedConceptHierarchy(Concept root, HashMap<Concept, Set<Concept>> hierarchy) {
        this(Collections.singleton(root), hierarchy);
    }
    
    public MultiRootedConceptHierarchy(MultiRootedHierarchy<Concept> hierarchy) {
        super(hierarchy.getRoots(), hierarchy.getAllChildRelationships());
    }
    
    public Set<Concept> getConceptsInHierarchy() {
        return super.getNodesInHierarchy();
    }
}
