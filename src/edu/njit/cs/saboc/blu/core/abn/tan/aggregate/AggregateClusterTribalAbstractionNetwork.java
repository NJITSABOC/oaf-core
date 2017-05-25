package edu.njit.cs.saboc.blu.core.abn.tan.aggregate;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
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
 * A cluster TAN that has been aggregated. Consists of a hierarchy of aggregate clusters.
 * 
 * @author Chris O
 */
public class AggregateClusterTribalAbstractionNetwork extends ClusterTribalAbstractionNetwork<AggregateCluster> 
        implements AggregateAbstractionNetwork<AggregateCluster, ClusterTribalAbstractionNetwork> {
    
    /**
     * Creates an aggregate TAN from a non-aggregate TAN
     * 
     * @param nonAggregatedClusterTAN
     * @param minBound
     * @param isWeightedAggregated
     * @return 
     */
    public static final ClusterTribalAbstractionNetwork generateAggregatedClusterTAN(
        ClusterTribalAbstractionNetwork nonAggregatedClusterTAN, 
        int minBound,
        boolean isWeightedAggregated) {
        
        AggregateTANGenerator generator = new AggregateTANGenerator();
        
        ClusterTribalAbstractionNetwork aggregateTAN = generator.createAggregateTAN(
            nonAggregatedClusterTAN, 
            new TribalAbstractionNetworkGenerator(),
            new AggregateAbNGenerator<>(),
            minBound,
            isWeightedAggregated);
        
        return aggregateTAN;
    }
    
    /**
     * Creates an aggregate TAN that consists of a chosen aggregate clusters
     * and all of its descendant aggregate clusters
     * 
     * @param nonAggregatedClusterTAN
     * @param sourceAggregatedClusterTAN
     * @param selectedRoot
     * @return 
     */
    public static final AggregateRootSubTAN generateAggregateRootSubTAN(
            ClusterTribalAbstractionNetwork nonAggregatedClusterTAN, 
            ClusterTribalAbstractionNetwork sourceAggregatedClusterTAN,
            AggregateCluster selectedRoot) {
        
        RootSubTAN nonAggregateRootSubtaxonomy = (RootSubTAN)nonAggregatedClusterTAN.createRootSubTAN(
                selectedRoot.getAggregatedHierarchy().getRoot());
        
        int aggregateBound = ((AggregateAbstractionNetwork)sourceAggregatedClusterTAN).getAggregateBound();
        boolean isWeightedAggregated = ((AggregateAbstractionNetwork)sourceAggregatedClusterTAN).getAggregatedProperty().getWeighted();
        
        ClusterTribalAbstractionNetwork aggregateRootSubtaxonomy = nonAggregateRootSubtaxonomy.getAggregated(aggregateBound, isWeightedAggregated);
        
        return new AggregateRootSubTAN(
                sourceAggregatedClusterTAN,
                aggregateBound,
                nonAggregateRootSubtaxonomy, 
                aggregateRootSubtaxonomy,
                isWeightedAggregated
        );
    }
    
    /**
     * Creates an aggregate TAN consisting of a chosen aggregate cluster and all of its 
     * ancestor aggregate clusters
     * 
     * @param nonAggregateTAN
     * @param superAggregateTAN
     * @param selectedRoot
     * @return 
     */
    public static final AggregateAncestorSubTAN generateAggregateAncestorSubTAN(
            ClusterTribalAbstractionNetwork nonAggregateTAN,
            ClusterTribalAbstractionNetwork superAggregateTAN,
            AggregateCluster selectedRoot) {
        
        Hierarchy<Cluster> actualClusterHierarchy = AbstractionNetworkUtils.getDeaggregatedAncestorHierarchy(nonAggregateTAN.getClusterHierarchy(), 
                superAggregateTAN.getClusterHierarchy().getAncestorHierarchy(selectedRoot));
        
        Hierarchy<Concept> sourceHierarchy = AbstractionNetworkUtils.getConceptHierarchy(actualClusterHierarchy, 
                nonAggregateTAN.getSourceHierarchy());
        
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();
        ClusterTribalAbstractionNetwork nonAggregatedAncestorSubTAN = generator.createTANFromClusters(actualClusterHierarchy, 
                sourceHierarchy, 
                nonAggregateTAN.getSourceFactory());

        int aggregateBound = ((AggregateAbstractionNetwork)superAggregateTAN).getAggregateBound();
        boolean isWeightedAggregated = ((AggregateAbstractionNetwork)superAggregateTAN).getAggregatedProperty().getWeighted();
        
        // Convert to ancestor subhierarchy
        AncestorSubTAN subTAN = new AncestorSubTAN(
                nonAggregateTAN,
                selectedRoot.getAggregatedHierarchy().getRoot(),
                nonAggregatedAncestorSubTAN.getBandTAN(),
                nonAggregatedAncestorSubTAN.getClusterHierarchy(), 
                nonAggregatedAncestorSubTAN.getSourceHierarchy()); 
        
        ClusterTribalAbstractionNetwork aggregateAncestorSubtaxonomy = subTAN.getAggregated(aggregateBound, isWeightedAggregated);
        
        return new AggregateAncestorSubTAN(
                superAggregateTAN,
                selectedRoot,
                aggregateBound,
                subTAN, 
                aggregateAncestorSubtaxonomy,
                isWeightedAggregated
        );
    }
    
    private final ClusterTribalAbstractionNetwork nonAggregateSourceTAN;
    private final int minBound;
    private final boolean isWeightedAggregated;
    
    public AggregateClusterTribalAbstractionNetwork(
            ClusterTribalAbstractionNetwork nonAggregateSourceTAN,
            int minBound,
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<AggregateCluster> clusterHierarchy,
            Hierarchy<Concept> conceptHierarchy,
            boolean isWeightedAggregated) {
        
        super(bandTAN, 
                clusterHierarchy, 
                conceptHierarchy, 
                new AggregateTANDerivation(nonAggregateSourceTAN.getDerivation(), minBound, isWeightedAggregated));
        
        this.nonAggregateSourceTAN = nonAggregateSourceTAN;
        this.minBound = minBound;
        this.isWeightedAggregated = isWeightedAggregated;
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
    public ClusterTribalAbstractionNetwork getAggregated(int smallestNode, boolean isWeightedAggregated) {
        return AggregateClusterTribalAbstractionNetwork.generateAggregatedClusterTAN(this.getNonAggregateSourceAbN(), smallestNode, isWeightedAggregated);
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
    
    @Override
    public AggregatedProperty getAggregatedProperty() {
        return new AggregatedProperty(minBound, isWeightedAggregated);
    }

    @Override
    public boolean isWeightedAggregated() {
        return this.isWeightedAggregated;
    }
}
