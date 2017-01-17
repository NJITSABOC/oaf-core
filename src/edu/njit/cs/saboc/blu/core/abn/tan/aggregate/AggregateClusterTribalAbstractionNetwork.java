package edu.njit.cs.saboc.blu.core.abn.tan.aggregate;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.AncestorSubTAN;
import edu.njit.cs.saboc.blu.core.abn.tan.BandTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.RootSubTAN;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AggregateTANDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class AggregateClusterTribalAbstractionNetwork extends ClusterTribalAbstractionNetwork<AggregateCluster> 
        implements AggregateAbstractionNetwork<AggregateCluster, ClusterTribalAbstractionNetwork> {
    
    public static final ClusterTribalAbstractionNetwork generateAggregatedClusterTAN(
        ClusterTribalAbstractionNetwork nonAggregatedClusterTAN, 
        int minBound) {
        
        AggregateTANGenerator generator = new AggregateTANGenerator();
        
        ClusterTribalAbstractionNetwork aggregateTAN = generator.createAggregateTAN(
            nonAggregatedClusterTAN, 
            new TribalAbstractionNetworkGenerator(),
            new AggregateAbNGenerator<>(),
            minBound);
        
        return aggregateTAN;
    }
    
    public static final AggregateRootSubTAN generateAggregateRootSubTAN(
            ClusterTribalAbstractionNetwork nonAggregatedClusterTAN, 
            ClusterTribalAbstractionNetwork sourceAggregatedClusterTAN,
            AggregateCluster selectedRoot) {
        
        RootSubTAN nonAggregateRootSubtaxonomy = (RootSubTAN)nonAggregatedClusterTAN.createRootSubTAN(
                selectedRoot.getAggregatedHierarchy().getRoot());
        
        int aggregateBound = ((AggregateAbstractionNetwork)sourceAggregatedClusterTAN).getAggregateBound();
        
        ClusterTribalAbstractionNetwork aggregateRootSubtaxonomy = nonAggregateRootSubtaxonomy.getAggregated(aggregateBound);
        
        return new AggregateRootSubTAN(
                sourceAggregatedClusterTAN,
                aggregateBound,
                nonAggregateRootSubtaxonomy, 
                aggregateRootSubtaxonomy);
    }
    
    public static final AggregateAncestorSubTAN generateAggregateAncestorSubTAN(
            ClusterTribalAbstractionNetwork nonAggregateTaxonomy,
            ClusterTribalAbstractionNetwork superAggregateTaxonomy,
            AggregateCluster selectedRoot) {
        
        Hierarchy<Cluster> actualClusterHierarchy = AbstractionNetworkUtils.getDeaggregatedAncestorHierarchy(
                nonAggregateTaxonomy.getClusterHierarchy(), 
                superAggregateTaxonomy.getClusterHierarchy().getAncestorHierarchy(selectedRoot));
        
        Hierarchy<Concept> sourceHierarchy = AbstractionNetworkUtils.getConceptHierarchy(
                actualClusterHierarchy, 
                nonAggregateTaxonomy.getSourceHierarchy());
        
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();
        ClusterTribalAbstractionNetwork nonAggregatedAncestorSubTAN = generator.createTANFromClusters(
                actualClusterHierarchy, 
                sourceHierarchy, 
                nonAggregateTaxonomy.getSourceFactory());

        int aggregateBound = ((AggregateAbstractionNetwork)superAggregateTaxonomy).getAggregateBound();
        
        // Convert to ancestor subhierarchy
        AncestorSubTAN subTAN = new AncestorSubTAN(
                nonAggregateTaxonomy,
                selectedRoot.getAggregatedHierarchy().getRoot(),
                nonAggregatedAncestorSubTAN.getBandTAN(),
                nonAggregatedAncestorSubTAN.getClusterHierarchy(), 
                nonAggregatedAncestorSubTAN.getSourceHierarchy()); 
        
        ClusterTribalAbstractionNetwork aggregateAncestorSubtaxonomy = subTAN.getAggregated(aggregateBound);
        
        return new AggregateAncestorSubTAN(
                superAggregateTaxonomy,
                selectedRoot,
                aggregateBound,
                subTAN, 
                aggregateAncestorSubtaxonomy);
    }
    
    private final ClusterTribalAbstractionNetwork nonAggregateSourceTAN;
    private final int minBound;
    
    public AggregateClusterTribalAbstractionNetwork(
            ClusterTribalAbstractionNetwork nonAggregateSourceTAN,
            int minBound,
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<AggregateCluster> clusterHierarchy,
            Hierarchy<Concept> conceptHierarchy) {
        
        super(bandTAN, 
                clusterHierarchy, 
                conceptHierarchy, 
                new AggregateTANDerivation(nonAggregateSourceTAN.getDerivation(), minBound));
        
        this.nonAggregateSourceTAN = nonAggregateSourceTAN;
        this.minBound = minBound;
    }
    
    @Override
    public AggregateTANDerivation getDerivation() {
        return (AggregateTANDerivation)super.getDerivation();
    }
    
    @Override
    public ClusterTribalAbstractionNetwork getNonAggregateSourceAbN() {
        return nonAggregateSourceTAN;
    }
    
    @Override
    public int getAggregateBound() {
        return minBound;
    }

    @Override
    public boolean isAggregated() {
        return true;
    }
    
    @Override
    public ClusterTribalAbstractionNetwork getAggregated(int smallestNode) {
        return AggregateClusterTribalAbstractionNetwork.generateAggregatedClusterTAN(this.getNonAggregateSourceAbN(), smallestNode);
    }

    @Override
    public ClusterTribalAbstractionNetwork expandAggregateNode(AggregateCluster cluster) {    
        return new AggregateTANGenerator().createExpandedTAN(this, 
                cluster,
                new TribalAbstractionNetworkGenerator());
    }

    @Override
    public AggregateAncestorSubTAN createAncestorTAN(AggregateCluster source) {
        return AggregateClusterTribalAbstractionNetwork.generateAggregateAncestorSubTAN(this.getNonAggregateSourceAbN(), this, source);
    }

    @Override
    public AggregateRootSubTAN createRootSubTAN(AggregateCluster root) {
        return AggregateClusterTribalAbstractionNetwork.generateAggregateRootSubTAN(this.getNonAggregateSourceAbN(), this, root);
    }
    
    
}
