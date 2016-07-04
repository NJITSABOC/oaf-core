package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.Set;

/**
 * Represents a partial-area in a partial-area taxonomy
 * @author Chris O
 */
public class PArea extends SinglyRootedNode {
    
    private final Set<InheritableProperty> relationships;
    
    public PArea(
            ConceptHierarchy<Concept> conceptHierarchy, 
            Set<InheritableProperty> relationships) {
        
        super(conceptHierarchy);
        
        this.relationships = relationships;
    }
    
    public Set<InheritableProperty> getRelationships() {
        return relationships;
    }
}
