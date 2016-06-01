package edu.njit.cs.saboc.blu.core.ontology;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class SingleRootedConceptHierarchy extends MultiRootedConceptHierarchy {
    public SingleRootedConceptHierarchy(Concept root) {
        super(root);
    }
    
    public SingleRootedConceptHierarchy(Concept root, HashMap<Concept, HashSet<Concept>> groupHierarchy) {
        super(root, groupHierarchy);
    }
    
    public Concept getRoot() {
        return roots.iterator().next();
    }
}