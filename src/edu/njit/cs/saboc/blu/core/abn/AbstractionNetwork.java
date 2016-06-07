package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import java.util.Set;

/**
 * @author Chris
 * 
 * @param <NODE_T>
 */
public abstract class AbstractionNetwork<NODE_T extends Node> {

    private final NodeHierarchy<NODE_T> nodeHierarchy;
    
    protected AbstractionNetwork(NodeHierarchy<NODE_T> nodeHierarchy) {
        this.nodeHierarchy = nodeHierarchy;
    }

    protected int getGroupCount() {
        return nodeHierarchy.size();
    }
    
    public Set<NODE_T> getNodes() {
        return nodeHierarchy.getNodesInHierarchy();
    }
    
    public Set<NODE_T> getChildNodes(NODE_T node) {
        return nodeHierarchy.getChildren(node);
    }
    
    public Set<NODE_T> getParentGroups(NODE_T node) {
        return nodeHierarchy.getParents(node);
    }
    
    public Set<NODE_T> getDescendantGroups(NODE_T node) {
        return nodeHierarchy.getSubhierarchyRootedAt(node).getDescendants(node);
    }
    
    public NodeHierarchy<NODE_T> getNodeHierarchy() {
        return nodeHierarchy;
    }
}
