package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.Optional;

/**
 * Represents an abstraction network that partitions another abstraction network.
 * For example, a partial-area taxonomy partitions an area taxonomy.
 * 
 * @author Chris O
 */
public abstract class PartitionedAbstractionNetwork<NODE_T extends SinglyRootedNode, BASENODE_T extends PartitionedNode> extends AbstractionNetwork<NODE_T> {
    
    private final AbstractionNetwork<BASENODE_T> baseAbstractionNetwork;
    
    public PartitionedAbstractionNetwork(
        AbstractionNetwork<BASENODE_T> baseAbstractionNetwork,
        NodeHierarchy<NODE_T> partitionNodeHierarchy, 
        ConceptHierarchy sourceHierarchy) {
        
        super(partitionNodeHierarchy, sourceHierarchy);
        
        this.baseAbstractionNetwork = baseAbstractionNetwork;
    }
    
    public AbstractionNetwork<BASENODE_T> getBaseAbstractionNetwork() {
        return baseAbstractionNetwork;
    }
    
    public BASENODE_T getPartitionNodeFor(NODE_T node) {
        
        Optional<BASENODE_T> containerNode = baseAbstractionNetwork.getNodes().stream().filter((baseNode) -> {
            return baseNode.getInternalNodes().contains(node);
        }).findAny();

        return containerNode.get();
    }
}
