package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.SingleRootedConceptHierarchy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an abstraction network node that consists of a singly rooted hierarchy of concepts.
 * 
 * Examples include partial-areas and clusters
 * 
 * @author Chris O
 */
public abstract class SinglyRootedNode extends Node {
    
    private final SingleRootedConceptHierarchy hierarchy;
    
    private Set<ParentConceptNode> rootParentConceptNodes = Collections.emptySet();
    
    protected SinglyRootedNode(
            Set<? extends SinglyRootedNode> parentNodes, 
            SingleRootedConceptHierarchy hierarchy) {
        
        super(parentNodes);
        
        this.hierarchy = hierarchy;
    }
    
    public SingleRootedConceptHierarchy getHierarchy() {
        return hierarchy;
    }
    
    public Concept getRoot() {
        return hierarchy.getRoot();
    }
    
    public HashSet<Concept> getConcepts() {
        return hierarchy.getConceptsInHierarchy();
    }
    
    public void setRootParentConceptNodes(Set<ParentConceptNode> rootParentConceptNodes) {
        this.rootParentConceptNodes = rootParentConceptNodes;
    }
    
    public Set<ParentConceptNode> getRootParentConceptNodes() {
        return rootParentConceptNodes;
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
