package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.SingleRootedConceptHierarchy;
import java.util.ArrayList;

/**
 * Represents an abstraction network node that consists of a singly rooted hierarchy of concepts.
 * 
 * Examples include partial-areas and clusters
 * 
 * @author Chris O
 */
public abstract class SinglyRootedNode extends Node {
    
    private final SingleRootedConceptHierarchy hierarchy;
    
    private ArrayList<ParentConceptNode> rootParentConceptNodes = new ArrayList<>();
    
    protected SinglyRootedNode(int id, 
            ArrayList<Node> parentNodes, 
            SingleRootedConceptHierarchy hierarchy) {
        
        super(id, parentNodes);
        
        this.hierarchy = hierarchy;
    }
    
    public SingleRootedConceptHierarchy getHierarchy() {
        return hierarchy;
    }
    
    public Concept getRoot() {
        return hierarchy.getRoot();
    }
    
    public void setRootParentConceptNodes(ArrayList<ParentConceptNode> rootParentConceptNodes) {
        this.rootParentConceptNodes = rootParentConceptNodes;
    }
    
    public ArrayList<ParentConceptNode> getRootParentConceptNodes() {
        return rootParentConceptNodes;
    }
    
    public int getConceptCount() {
        return hierarchy.size();
    }
    
    public String getName() {
        return String.format("%s (%d)", getRoot().getName(), getConceptCount());
    }
}
