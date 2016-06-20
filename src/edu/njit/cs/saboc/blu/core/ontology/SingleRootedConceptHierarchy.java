package edu.njit.cs.saboc.blu.core.ontology;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class SingleRootedConceptHierarchy extends MultiRootedConceptHierarchy {
    public SingleRootedConceptHierarchy(Concept root) {
        super(root);
    }
    
    public SingleRootedConceptHierarchy(Concept root, HashMap<Concept, Set<Concept>> groupHierarchy) {
        super(root, groupHierarchy);
    }
}