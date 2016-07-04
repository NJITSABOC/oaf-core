package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.Set;

/**
 * Represents an abstraction network node that consists of a singly rooted hierarchy of concepts.
 * 
 * Examples include partial-areas and clusters
 * 
 * @author Chris O
 */
public abstract class SinglyRootedNode extends Node {
    
    private final ConceptHierarchy<Concept> hierarchy;

    protected SinglyRootedNode(ConceptHierarchy<Concept> hierarchy) {
        this.hierarchy = hierarchy;
    }
    
    public ConceptHierarchy<Concept> getHierarchy() {
        return hierarchy;
    }
    
    public Concept getRoot() {
        return hierarchy.getRoot();
    }
    
    public Set<Concept> getConcepts() {
        return hierarchy.getConceptsInHierarchy();
    }
        
    public boolean equals(Object o) {
        if(o instanceof SinglyRootedNode) {
            SinglyRootedNode other = (SinglyRootedNode)o;
            
            return this.getRoot().equals(other.getRoot());
        }
        
        return false;
    }
    
    public int hashCode() {
        return this.getRoot().hashCode();
    }
    
    public int getConceptCount() {
        return hierarchy.size();
    }
    
    public String getName() {
        return String.format("%s (%d)", getRoot().getName(), getConceptCount());
    }
}
