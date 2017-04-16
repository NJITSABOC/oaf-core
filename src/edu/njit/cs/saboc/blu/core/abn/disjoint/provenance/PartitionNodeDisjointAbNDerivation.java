package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class PartitionNodeDisjointAbNDerivation<
        T extends SinglyRootedNode, 
        V extends PartitionedNode<T>> extends DisjointAbNDerivation {

    private final AbNDerivation<PartitionedAbstractionNetwork<T, V>> parentAbNDerivation;
    private final String partitionedNodeName;
    
    public PartitionNodeDisjointAbNDerivation(
            DisjointAbNFactory factory,
            AbNDerivation<PartitionedAbstractionNetwork<T, V>> parentAbNDerivation,
            String partitionedNodeName) {
        
        super(factory, parentAbNDerivation.getSourceOntology());
        
        this.parentAbNDerivation = parentAbNDerivation;
        this.partitionedNodeName = partitionedNodeName;
    }
        
    public AbNDerivation<PartitionedAbstractionNetwork<T, V>> getParentAbNDerivation() {
        return parentAbNDerivation;
    }
    
    @Override
    public String getDescription() {
        return String.format("Disjointed %s", partitionedNodeName);
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return String.format("Disjoint %s", parentAbNDerivation.getAbstractionNetworkTypeName());
    }

    @Override
    public String getName() {
        return String.format("%s %s", partitionedNodeName, getAbstractionNetworkTypeName());
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        
        PartitionedAbstractionNetwork<T, V> partitionedAbN = parentAbNDerivation.getAbstractionNetwork();
        
        Optional<V> theNode = partitionedAbN
                .getBaseAbstractionNetwork()
                .getNodes()
                .stream()
                .filter( (node) -> {
                    PartitionedNode pNode = (PartitionedNode)node;
                    
                    return pNode.getName().equalsIgnoreCase(partitionedNodeName);
                }).findAny();
        
        V partitionedNode = theNode.get();

        Set<SinglyRootedNode> overlappingNodes = new HashSet<>();
        
        partitionedNode.getOverlappingConceptDetails().forEach( (details) -> {
            if(details.getNodes().size() > 1) {
                overlappingNodes.addAll(details.getNodes());
            }
        });
        
        DisjointAbNGenerator generator = new DisjointAbNGenerator<>();

        return generator.generateDisjointAbstractionNetwork(
                this.getFactory(),
                partitionedAbN,
                overlappingNodes);
    }
}
