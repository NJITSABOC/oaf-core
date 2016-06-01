package edu.njit.cs.saboc.blu.core.abn.node;

import java.util.Set;

/**
 * Generic class representing a node in an abstraction network
 * 
 * @author Chris O
 */
public abstract class Node {
    
    private final Set<? extends Node> parentNodes;
    
    protected Node(Set<? extends Node> parentNodes) {
        this.parentNodes = parentNodes;
    }
    
    public abstract int getConceptCount();
    public abstract String getName();
    
    public Set<? extends Node> getParentNodes() {
        return parentNodes;
    }
    
    public abstract boolean equals(Object o);
    public abstract int hashCode();
}
