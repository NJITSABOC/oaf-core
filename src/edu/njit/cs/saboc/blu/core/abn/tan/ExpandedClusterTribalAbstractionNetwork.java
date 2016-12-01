package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class ExpandedClusterTribalAbstractionNetwork extends SubTAN {
    
    private final AggregateCluster aggregateCluster;
    
    public ExpandedClusterTribalAbstractionNetwork(
            AggregateClusterTribalAbstractionNetwork sourceTaxonomy,
            AggregateCluster aggregateCluster,
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<Cluster> clusterHierarchy,
            Hierarchy<Concept> conceptHierarchy) {

        super(sourceTaxonomy, bandTAN, clusterHierarchy, conceptHierarchy);
        
        this.aggregateCluster = aggregateCluster;
    }
    
    public AggregateClusterTribalAbstractionNetwork getSourceTaxonomy() {
        return (AggregateClusterTribalAbstractionNetwork)super.getSourceTAN();
    }
    
    public AggregateCluster getAggregatePArea() {
        return aggregateCluster;
    }
}