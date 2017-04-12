package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * Stores the arguments needed to derive a given aggregate ancestor disjoint
 * abstraction network.
 * 
 * @author Chris O
 */
public class AggregateAncestorDisjointAbNDerivation extends DisjointAbNDerivation
    implements RootedSubAbNDerivation<DisjointAbNDerivation>, AggregateAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation aggregateBase;
    
    private final int minBound;
    private final Concept selectedAggregatePAreaRoot;
    
    public AggregateAncestorDisjointAbNDerivation(
            DisjointAbNDerivation aggregateBase, 
            int minBound,
            Concept selectedAggregatePAreaRoot) {
        
        super(aggregateBase);
        
        this.aggregateBase = aggregateBase;
        this.minBound = minBound;
        this.selectedAggregatePAreaRoot = selectedAggregatePAreaRoot;
    }
    
    public AggregateAncestorDisjointAbNDerivation(AggregateAncestorDisjointAbNDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), 
                deriveTaxonomy.getBound(), 
                deriveTaxonomy.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return selectedAggregatePAreaRoot;
    }

    @Override
    public DisjointAbNDerivation getSuperAbNDerivation() {
        return aggregateBase;
    }

    @Override
    public int getBound() {
        return minBound;
    }

    @Override
    public DisjointAbNDerivation getNonAggregateSourceDerivation() {
        AggregateAbNDerivation<DisjointAbNDerivation> derivedAggregate = (AggregateAbNDerivation<DisjointAbNDerivation>)this.getSuperAbNDerivation();
        
        return derivedAggregate.getNonAggregateSourceDerivation();
    }
  
    @Override
    public String getDescription() {
        return String.format("Derived aggregate ancestordisjoint (%s)", selectedAggregatePAreaRoot.getName());
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        DisjointAbstractionNetwork aggregateDisjointAbN = this.getSuperAbNDerivation().getAbstractionNetwork();
        
        Set<DisjointNode> nodes = aggregateDisjointAbN.getNodesWith(selectedAggregatePAreaRoot);
        
        return aggregateDisjointAbN.getAncestorDisjointAbN(nodes.iterator().next());
    }

    @Override
    public String getName() {
        return String.format("%s %s", selectedAggregatePAreaRoot.getName(), getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return String.format("Ancestor %s", aggregateBase.getAbstractionNetworkTypeName());
    }
}