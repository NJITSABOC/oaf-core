package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * Represents a partial-area in a partial-area taxonomy
 * @author Chris O
 */
public class PArea extends SinglyRootedNode {
    
    private final Set<InheritableProperty> relationships;
    
    public PArea(
            Hierarchy<Concept> conceptHierarchy, 
            Set<InheritableProperty> relationships) {
        
        super(conceptHierarchy);
        
        this.relationships = relationships;
    }
    
    public boolean equals(Object o) {
        if(o instanceof PArea) {
            PArea other = (PArea)o;
            
            return super.equals(other) && this.getRelationships().equals(other.getRelationships());
        }
        
        return false;
    }
    
    public Set<InheritableProperty> getRelationships() {
        return relationships;
    }
}
