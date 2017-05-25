package edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.AncestorTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.targetbased.DescendantTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.AggregateTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * A target abstraction network that has been aggregated according to the given
 * bound
 * 
 * @author Chris O
 * @param <T>
 */
public class AggregateTargetAbN extends TargetAbstractionNetwork<AggregateTargetGroup> 
        implements AggregateAbstractionNetwork<AggregateTargetGroup, TargetAbstractionNetwork> {

    public static final TargetAbstractionNetwork createAggregated(
            TargetAbstractionNetwork nonAggregated, 
            int smallestNode,
            boolean isWeightedAggregated) {
        
        AggregateTargetAbNGenerator generator = new AggregateTargetAbNGenerator();
        
        return generator.createAggregateTargetAbN(nonAggregated, 
                    new TargetAbstractionNetworkGenerator(), 
                    new AggregateAbNGenerator<>(), 
                    smallestNode,
                    isWeightedAggregated);
    }
    
    public static final AggregateAncestorTargetAbN createAggregateAncestorTargetAbN(
            TargetAbstractionNetwork nonAggregateTargetAbN,
            TargetAbstractionNetwork superAggregateTargetAbN,
            AggregateTargetGroup selectedRoot) {
        
        Hierarchy<TargetGroup> actualTargetGroupHierarchy = 
                AbstractionNetworkUtils.getDeaggregatedAncestorHierarchy(
                    nonAggregateTargetAbN.getTargetGroupHierarchy(), 
                    superAggregateTargetAbN.getTargetGroupHierarchy().getAncestorHierarchy(selectedRoot));
        
        Hierarchy<Concept> sourceHierarchy = AbstractionNetworkUtils.getConceptHierarchy(
                actualTargetGroupHierarchy, 
                nonAggregateTargetAbN.getSourceHierarchy());
        
        TargetAbstractionNetworkGenerator generator = new TargetAbstractionNetworkGenerator();
        
        TargetAbstractionNetwork nonAggregatedAncestorTargetAbN = 
                generator.createTargetAbNFromTargetGroups(
                        nonAggregateTargetAbN.getFactory(),
                        actualTargetGroupHierarchy,
                        sourceHierarchy);

        int aggregateBound = ((AggregateAbstractionNetwork)superAggregateTargetAbN).getAggregateBound();
        boolean isWeightedAggregated = ((AggregateAbstractionNetwork)superAggregateTargetAbN).isWeightedAggregated();
        
        AncestorTargetAbN nonaggregateAncestorTargetAbN = new AncestorTargetAbN(
                nonAggregateTargetAbN,
                selectedRoot.getAggregatedHierarchy().getRoot(),
                nonAggregatedAncestorTargetAbN.getTargetGroupHierarchy(), 
                nonAggregatedAncestorTargetAbN.getSourceHierarchy()); 
        
        TargetAbstractionNetwork aggregateAncestorSubtaxonomy = nonaggregateAncestorTargetAbN.getAggregated(aggregateBound, isWeightedAggregated);
        
        return new AggregateAncestorTargetAbN(
                superAggregateTargetAbN,
                selectedRoot,
                aggregateBound,
                isWeightedAggregated,
                nonaggregateAncestorTargetAbN, 
                aggregateAncestorSubtaxonomy);
    }
    
    public static final AggregateDescendantTargetAbN createAggregateDescendantTargetAbN(
            TargetAbstractionNetwork nonAggregatedTargetAbN,
            TargetAbstractionNetwork sourceAggregatedTargetAbN,
            AggregateTargetGroup selectedGroup) {

        DescendantTargetAbN nonAggregateDescendantTargetAbN = (DescendantTargetAbN) nonAggregatedTargetAbN.createDescendantTargetAbN(
                selectedGroup.getAggregatedHierarchy().getRoot());

        int aggregateBound = ((AggregateAbstractionNetwork) sourceAggregatedTargetAbN).getAggregateBound();
        boolean isWeightedAggregated = ((AggregateAbstractionNetwork)sourceAggregatedTargetAbN).isWeightedAggregated();

        TargetAbstractionNetwork aggregateRootSubtaxonomy = nonAggregateDescendantTargetAbN.getAggregated(aggregateBound, isWeightedAggregated);

        return new AggregateDescendantTargetAbN(
                sourceAggregatedTargetAbN,
                aggregateBound,
                isWeightedAggregated,
                nonAggregateDescendantTargetAbN,
                aggregateRootSubtaxonomy);
    }
    
    
    public static final TargetAbstractionNetwork createExpanded(
            AggregateTargetAbN aggregateAbN, 
            AggregateTargetGroup selectedGroup) {
        
        AggregateTargetAbNGenerator generator = new AggregateTargetAbNGenerator();
        
        return generator.createExpandedTargetAbN(aggregateAbN, selectedGroup);
    }
    
    
    private final TargetAbstractionNetwork sourceTargetAbN;
    
    private final int minBound;
    
    private final boolean isWeightedAggregated;

    public AggregateTargetAbN(
            TargetAbstractionNetwork sourceTargetAbN,
            int minBound,
            Hierarchy<AggregateTargetGroup> groupHierarchy,
            Hierarchy<Concept> sourceHierarchy,
            boolean isWeightedAggregated) {


        super(sourceTargetAbN.getFactory(),
                groupHierarchy, 
                sourceHierarchy, 
                new AggregateTargetAbNDerivation(
                        sourceTargetAbN.getDerivation(), 
                        minBound, 
                        isWeightedAggregated));
        
        this.sourceTargetAbN = sourceTargetAbN;
        this.minBound = minBound;
        
        this.setAggregated(true);

        this.isWeightedAggregated = isWeightedAggregated;
    }
    
    public AggregateTargetAbN(AggregateTargetAbN base) {
        
        this(base.getNonAggregateSourceAbN(), 
                base.getAggregateBound(), 
                base.getNodeHierarchy(), 
                base.getSourceHierarchy(),
                base.getAggregatedProperty().getWeighted()
        );
    }

    @Override
    public TargetAbstractionNetwork expandAggregateNode(AggregateTargetGroup node) {
        AggregateTargetAbNGenerator generator = new AggregateTargetAbNGenerator();

        return generator.createExpandedTargetAbN(this, node);
    }

    @Override
    public TargetAbstractionNetwork getNonAggregateSourceAbN() {
        return sourceTargetAbN;
    }
    
    @Override
    public int getAggregateBound() {
        return minBound;
    }
    
     @Override
    public TargetAbstractionNetwork getAggregated(int smallestNode, boolean isWeightedAggregated) {
        return AggregateTargetAbN.createAggregated(getNonAggregateSourceAbN(), smallestNode, isWeightedAggregated);
    }
    
    public TargetAbstractionNetwork expandAggregateTargetGroup(AggregateTargetGroup targetGroup) {
        return AggregateTargetAbN.createExpanded(this, targetGroup);
    }

    @Override
    public AggregateAncestorTargetAbN createAncestorTargetAbN(AggregateTargetGroup source) {
        return AggregateTargetAbN.createAggregateAncestorTargetAbN(
                this.getNonAggregateSourceAbN(), 
                this, 
                source);
    }

    @Override
    public AggregateDescendantTargetAbN createDescendantTargetAbN(AggregateTargetGroup root) {
        return AggregateTargetAbN.createAggregateDescendantTargetAbN(
                this.getNonAggregateSourceAbN(),
                this,
                root);
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
