package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.abn.node.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.Set;

/**
 * @author Chris
 * 
 * @param <NODE_T>
 */
public abstract class AbstractionNetwork<NODE_T extends Node> {

    private final NodeHierarchy<NODE_T> nodeHierarchy;
    private final ConceptHierarchy sourceHierarchy;
    
    protected AbstractionNetwork(
            NodeHierarchy<NODE_T> nodeHierarchy,
            ConceptHierarchy sourceHierarchy) {
        
        this.nodeHierarchy = nodeHierarchy;
        this.sourceHierarchy = sourceHierarchy;
    }

    public int getNodeCount() {
        return nodeHierarchy.size();
    }
    
    public ConceptHierarchy getSourceHierarchy() {
        return sourceHierarchy;
    }
    
    public Set<NODE_T> getNodes() {
        return nodeHierarchy.getNodesInHierarchy();
    }
    
    public NodeHierarchy<NODE_T> getNodeHierarchy() {
        return nodeHierarchy;
    }
    
    public abstract Set<ParentNodeDetails> getParentNodeDetails(NODE_T node);
}
