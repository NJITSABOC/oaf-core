package edu.njit.cs.saboc.blu.core.ontology;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class MultiRootedConceptHierarchy extends MultiRootedHierarchy<Concept> {
    public MultiRootedConceptHierarchy(HashSet<Concept> roots) {
        super(roots);
    }
    
    public MultiRootedConceptHierarchy(Concept root) {
        this(new HashSet<>(Arrays.asList(root)));
    }
    
    public MultiRootedConceptHierarchy(HashSet<Concept> roots, HashMap<Concept, HashSet<Concept>> hierarchy) {
        super(roots, hierarchy);
    }
    
    public MultiRootedConceptHierarchy(Concept root, HashMap<Concept, HashSet<Concept>> hierarchy) {
        this(new HashSet<>(Arrays.asList(root)), hierarchy);
    }
    
    public MultiRootedConceptHierarchy(MultiRootedHierarchy<Concept> hierarchy) {
        super(hierarchy.getRoots(), hierarchy.getAllChildRelationships());
    }
    
    public HashSet<Concept> getConceptsInHierarchy() {
        return super.getNodesInHierarchy();
    }
}
