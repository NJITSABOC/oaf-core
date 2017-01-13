package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedSubAbN;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateCluster;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author cro3
 */
public class DerivedExpandedSubTAN extends DerivedClusterTAN 
        implements DerivedSubAbN<DerivedClusterTAN> {
    
    private final Concept aggregateClusterRoot;
    private final DerivedClusterTAN base;
    
    public DerivedExpandedSubTAN(
            DerivedClusterTAN base, 
            Concept aggregateClusterRoot) {
        
        super(base);
        
        this.base = base;
        this.aggregateClusterRoot = aggregateClusterRoot;
    }
    
    public DerivedExpandedSubTAN(DerivedExpandedSubTAN derivedTaxonomy) {
        this(derivedTaxonomy.getSuperAbNDerivation(), derivedTaxonomy.getExpandedAggregatePAreaRoot());
    }
    
    public Concept getExpandedAggregatePAreaRoot() {
        return aggregateClusterRoot;
    }

    @Override
    public DerivedClusterTAN getSuperAbNDerivation() {
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
