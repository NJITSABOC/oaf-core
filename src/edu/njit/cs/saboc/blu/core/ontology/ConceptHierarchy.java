package edu.njit.cs.saboc.blu.core.ontology;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class ConceptHierarchy extends Hierarchy<Concept> {
    
    public ConceptHierarchy(Set<Concept> roots) {
        super(roots);
    }
    
    public ConceptHierarchy(Concept root) {
        this(Collections.singleton(root));
    }
    
    public ConceptHierarchy(Set<Concept> roots, HashMap<Concept, Set<Concept>> hierarchy) {
        super(roots, hierarchy);
    }
    
    public ConceptHierarchy(Concept root, HashMap<Concept, Set<Concept>> hierarchy) {
        this(Collections.singleton(root), hierarchy);
    }
    
    public ConceptHierarchy(Hierarchy<Concept> hierarchy) {
        super(hierarchy.getRoots(), hierarchy.getAllChildRelationships());
    }
    
    public Set<Concept> getConceptsInHierarchy() {
        return super.getNodesInHierarchy();
    }
    
    @Override
    public ConceptHierarchy getAncestorHierarchy(Concept concept) {
        Hierarchy<Concept> subhierarchy = super.getAncestorHierarchy(concept);

        ConceptHierarchy conceptSubhierarchy = new ConceptHierarchy(concept, subhierarchy.getAllChildRelationships());

        return conceptSubhierarchy;
    }

    @Override
    public ConceptHierarchy getSubhierarchyRootedAt(Concept concept) {
        Hierarchy<Concept> subhierarchy = super.getSubhierarchyRootedAt(concept);

        ConceptHierarchy conceptSubhierarchy = new ConceptHierarchy(concept, subhierarchy.getAllChildRelationships());

        return conceptSubhierarchy;
    }
}
