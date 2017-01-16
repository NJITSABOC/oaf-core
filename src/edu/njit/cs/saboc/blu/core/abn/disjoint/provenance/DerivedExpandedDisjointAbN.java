package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate.AggregateDisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedSubAbN;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DerivedExpandedDisjointAbN<T extends SinglyRootedNode> extends DerivedDisjointAbN<T> 
        implements DerivedSubAbN<DerivedDisjointAbN> {
    
    private final DerivedDisjointAbN sourceDisjointAbNDerivation;
    private final Concept expandedAggregateNodeRoot;
    
    public DerivedExpandedDisjointAbN(
            DerivedDisjointAbN sourceDisjointAbNDerivation, 
            Concept expandedAggregateNodeRoot) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.expandedAggregateNodeRoot = expandedAggregateNodeRoot;
    }

    public Concept getExpandedAggregateNodeRoot() {
        return expandedAggregateNodeRoot;
    }
    
    @Override
    public DerivedDisjointAbN<T> getSuperAbNDerivation() {
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