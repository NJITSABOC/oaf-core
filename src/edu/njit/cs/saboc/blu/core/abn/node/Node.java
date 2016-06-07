package edu.njit.cs.saboc.blu.core.abn.node;

/**
 * Generic class representing a node in an abstraction network
 * 
 * @author Chris O
 */
public abstract class Node {
    
    protected Node() {
        
    }
    
    public abstract int getConceptCount();
    public abstract String getName();
    
    public abstract boolean equals(Object o);
    public abstract int hashCode();
}
