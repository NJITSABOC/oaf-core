package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate.AggregateDisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * Stores the arguments needed to create an expanded disjoint abstraction network
 * 
 * @author Chris O
 * @param <T>
 */
public class ExpandedDisjointAbNDerivation<T extends SinglyRootedNode> extends DisjointAbNDerivation<T> 
        implements SubAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation sourceDisjointAbNDerivation;
    private final Concept expandedAggregateNodeRoot;
    
    public ExpandedDisjointAbNDerivation(
            DisjointAbNDerivation sourceDisjointAbNDerivation, 
            Concept expandedAggregateNodeRoot) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.expandedAggregateNodeRoot = expandedAggregateNodeRoot;
    }

    public Concept getExpandedAggregateNodeRoot() {
        return expandedAggregateNodeRoot;
    }
    
    @Override
    public DisjointAbNDerivation<T> getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        DisjointAbstractionNetwork disjointAbN = getSuperAbNDerivation().getAbstractionNetwork();
        
         AggregateAbstractionNetwork<AggregateDisjointNode, DisjointAbstractionNetwork> aggregateDisjointAbN = 
                (AggregateAbstractionNetwork<AggregateDisjointNode, DisjointAbstractionNetwork>)disjointAbN;
        
        Set<DisjointNode> nodes = disjointAbN.getNodesWith(expandedAggregateNodeRoot);
        
        return aggregateDisjointAbN.expandAggregateNode((AggregateDisjointNode)nodes.iterator().next());
    }

    @Override
    public String getDescription() {
        return String.format("Expanded %s", expandedAggregateNodeRoot.getName());
    }
}