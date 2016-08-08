package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffEdge.EdgeState;
import edu.njit.cs.saboc.blu.core.abn.diff.utils.SetUtilities;
import java.util.Set;

/**
 *
 * @author Chris O
 */

public class DiffNodeHierarchy {

    private final Set<DiffNode> addedRoots;
    private final Set<DiffNode> removedRoots;
    private final Set<DiffNode> transferredRoots;
    
    private final DiffNodeGraph diffGraph = new DiffNodeGraph();
    
    public DiffNodeHierarchy(Set<DiffNode> beforeRoots, Set<DiffNode> afterRoots) {
        addedRoots = SetUtilities.getSetDifference(afterRoots, beforeRoots);
        removedRoots = SetUtilities.getSetDifference(afterRoots, beforeRoots);
        transferredRoots = SetUtilities.getSetIntersection(afterRoots, beforeRoots);
    }
    
    public void addEdge(DiffNode from, DiffNode to, EdgeState edgeState) {
        diffGraph.addEdge(from, to, edgeState);
    }
    
    public Set<DiffNode> getAddedRoots() {
        return addedRoots;
    }
    
    public Set<DiffNode> getRemovedRoots() {
        return removedRoots;
    }
    
    public Set<DiffNode> getTransferredRoots() {
        return transferredRoots;
    }
    
    public Set<DiffNode> getParents(DiffNode node) {
        return diffGraph.getOutgoingEdges(node);
    }
    
    public Set<DiffNode> getChildren(DiffNode node) {
        return diffGraph.getIncomingEdges(node);
    }
    
    public Set<DiffNode> getParents(DiffNode node, EdgeState state) {
        return diffGraph.getOutgoingEdges(node, state);
    }
    
    public Set<DiffNode> getChildren(DiffNode node, EdgeState state) {
        return diffGraph.getIncomingEdges(node, state);
    }
}
