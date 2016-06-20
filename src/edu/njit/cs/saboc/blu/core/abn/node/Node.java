package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

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
    
    public abstract Set<Concept> getConcepts();
    
    public abstract boolean equals(Object o);
    public abstract int hashCode();
}
