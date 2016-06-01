package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Represents an abstraction network node that can be partitioned into sub-nodes
 * 
 * @author Chris O
 */
public abstract class PartitionedNode extends Node {
    
    private final HashSet<SinglyRootedNode> internalNodes;
    private final HashSet<Concept> concepts;
    
    public PartitionedNode(int id, 
            ArrayList<Node> parentNodes, 
            HashSet<SinglyRootedNode> internalNodes) {
        
        super(id, parentNodes);
        
        this.internalNodes = internalNodes;
        
        this.concepts = new HashSet<>();
        
        this.internalNodes.forEach( (n) -> {
            concepts.addAll(n.getHierarchy().getConceptsInHierarchy());
        });
    }
    
    public int getConceptCount() {
        return concepts.size();
    }
   
}
