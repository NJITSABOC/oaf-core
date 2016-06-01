package edu.njit.cs.saboc.blu.core.abn.node;

import java.util.ArrayList;

/**
 * Generic class representing a node in an abstraction network
 * @author Chris O
 */
public abstract class Node {
    
    private final int id;
    
    private final ArrayList<Node> parentNodes;
    
    protected Node(int id, 
                    ArrayList<Node> parentNodes) {
        
        this.id = id;
        this.parentNodes = parentNodes;
    }
    
    public abstract int getConceptCount();
    public abstract String getName();
    
    public int getID() {
        return id;
    }
    
    public ArrayList<Node> getParentNodes() {
        return parentNodes;
    }
    
    
    
    public boolean equals(Object o) {
        if(o instanceof Node) {
            Node other = (Node)o;
            
            return this.id == other.id;
        }
        
        return false;
    }
    
    public int hashCode() {
        return id;
    }
}
