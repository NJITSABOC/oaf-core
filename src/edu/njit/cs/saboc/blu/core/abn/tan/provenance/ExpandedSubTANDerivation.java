package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateCluster;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * Stores the arguments needed to create an expanded Sub TAN
 * 
 * @author Chris O
 */
public class ExpandedSubTANDerivation extends ClusterTANDerivation 
        implements SubAbNDerivation<ClusterTANDerivation> {
    
    private final Concept aggregateClusterRoot;
    private final ClusterTANDerivation base;
    
    public ExpandedSubTANDerivation(
            ClusterTANDerivation base, 
            Concept aggregateClusterRoot) {
        
        super(base);
        
        this.base = base;
        this.aggregateClusterRoot = aggregateClusterRoot;
    }
    
    public ExpandedSubTANDerivation(ExpandedSubTANDerivation derivedTaxonomy) {
        this(derivedTaxonomy.getSuperAbNDerivation(), derivedTaxonomy.getExpandedAggregatePAreaRoot());
    }
    
    public Concept getExpandedAggregatePAreaRoot() {
        return aggregateClusterRoot;
    }

    @Override
    public ClusterTANDerivation getSuperAbNDerivation() {
        return base;
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        ClusterTribalAbstractionNetwork baseAggregated = base.getAbstractionNetwork();
        
        AggregateAbstractionNetwork<AggregateCluster, ClusterTribalAbstractionNetwork> aggregateTAN = 
                (AggregateAbstractionNetwork<AggregateCluster, ClusterTribalAbstractionNetwork>)baseAggregated;
        
        Set<AggregateCluster> pareas = baseAggregated.getNodesWith(aggregateClusterRoot);
        
        return aggregateTAN.expandAggregateNode(pareas.iterator().next());
    }

    @Override
    public String getDescription() {
        return String.format("Expanded aggregate cluster (%s)", aggregateClusterRoot.getName());
    }
    
}