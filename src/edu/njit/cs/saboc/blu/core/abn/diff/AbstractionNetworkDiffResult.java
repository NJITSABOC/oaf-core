package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.OntologyChanges;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Chris O
 */
public class AbstractionNetworkDiffResult {
    private final AbstractionNetwork from;
    private final AbstractionNetwork to;
    
    private final OntologyChanges ontDifferences;
    
    private final Set<DiffNode> diffNodes;
    
    private final DiffNodeHierarchy diffHierarchy;
    
    public AbstractionNetworkDiffResult(
            AbstractionNetwork from, 
            AbstractionNetwork to, 
            OntologyChanges ontDifferences,
            Set<DiffNode> diffNodes,
            DiffNodeHierarchy diffHierarchy) {
        
        this.from = from;
        this.to = to;
        
        this.ontDifferences = ontDifferences; 
        
        this.diffNodes = diffNodes;
        this.diffHierarchy = diffHierarchy;
    }

    public AbstractionNetwork getFrom() {
        return from;
    }

    public AbstractionNetwork getTo() {
        return to;
    }
    
    public OntologyChanges getOntologyDifferences() {
        return ontDifferences;
    }
    
    public Set<DiffNode> getDiffNodes() {
        return diffNodes;
    }
    
    public Set<DiffNode> getDiffNodesOfType(ChangeState state) {
        
        Set<DiffNode> nodesOfType = diffNodes.stream().filter( (diffNode) -> {
           return diffNode.getChangeDetails().getNodeState().equals(state);
        }).collect(Collectors.toSet());
        
        return nodesOfType;
    }
    
    public DiffNodeHierarchy getDiffNodeHierarchy() {
        return diffHierarchy;
    }
}
