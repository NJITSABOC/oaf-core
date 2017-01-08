package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AncestorSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.ExpandedSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RootSubtaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaTaxonomy extends PAreaTaxonomy<AggregatePArea> 
        implements AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy> {
    
    public static final PAreaTaxonomy generateAggregatePAreaTaxonomy(PAreaTaxonomy nonAggregateTaxonomy, int bound) {
        AggregatePAreaTaxonomyGenerator generator = new AggregatePAreaTaxonomyGenerator();
        
        return generator.createAggregatePAreaTaxonomy(
                nonAggregateTaxonomy, 
                new PAreaTaxonomyGenerator(),
                new AggregateAbNGenerator<>(),
                bound);
    }
    
    public static final PAreaTaxonomy generateAggregateRootSubtaxonomy(
            PAreaTaxonomy nonAggregateTaxonomy,
            PAreaTaxonomy superAggregateTaxonomy,
            AggregatePArea selectedRoot) {
        
        RootSubtaxonomy nonAggregateRootSubtaxonomy = (RootSubtaxonomy)nonAggregateTaxonomy.createRootSubtaxonomy(
                selectedRoot.getAggregatedHierarchy().getRoot());
        
        int aggregateBound = ((AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy>)superAggregateTaxonomy).getAggregateBound();
        
        PAreaTaxonomy aggregateRootSubtaxonomy = nonAggregateRootSubtaxonomy.getAggregated(aggregateBound);
        
        return new AggregateRootSubtaxonomy(
                superAggregateTaxonomy,
                aggregateBound,
                nonAggregateRootSubtaxonomy, 
                aggregateRootSubtaxonomy);
    }
    
    public static final PAreaTaxonomy generateAggregateAncestorSubtaxonomy(
            PAreaTaxonomy nonAggregateTaxonomy,
            PAreaTaxonomy superAggregateTaxonomy,
            AggregatePArea selectedRoot) {
        
        Hierarchy<AggregatePArea> ancestors = superAggregateTaxonomy.getPAreaHierarchy().getAncestorHierarchy(selectedRoot);
        
        Set<PArea> aggregatedPAreas = new HashSet<>();
        
        ancestors.getNodes().forEach( (aggregatePArea) -> {
            aggregatedPAreas.addAll(aggregatePArea.getAggregatedHierarchy().getNodes());
        });
        
        
        //TODO: This can be done with a topological traversal, probably
        Hierarchy<PArea> potentialPAreaHierarchy = nonAggregateTaxonomy.getPAreaHierarchy().getAncestorHierarchy(aggregatedPAreas);
        
        Hierarchy<PArea> actualPAreaHierarchy = new Hierarchy<>(potentialPAreaHierarchy.getRoot());
        
        aggregatedPAreas.forEach( (parea) -> {
            actualPAreaHierarchy.addNode(parea);
        });
        
        potentialPAreaHierarchy.getEdges().forEach( (edge) -> {
            if(aggregatedPAreas.contains(edge.getFrom()) && aggregatedPAreas.contains(edge.getTo())) {
                actualPAreaHierarchy.addEdge(edge);
            }
        });
        
        Hierarchy<Concept> sourceHierarchy = new Hierarchy(actualPAreaHierarchy.getRoot().getRoot());
        
        actualPAreaHierarchy.getNodes().forEach( (parea) -> {
            sourceHierarchy.addAllHierarchicalRelationships(parea.getHierarchy());
        });
        
        actualPAreaHierarchy.getNodes().forEach( (parea) -> {
            nonAggregateTaxonomy.getSourceHierarchy().getParents(parea.getRoot()).forEach( (p) -> {
                Concept parent = (Concept)p;
                
                if(sourceHierarchy.contains(parent)) {
                    sourceHierarchy.addEdge(parea.getRoot(), parent);
                }
            });
        });
        
        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
        PAreaTaxonomy nonAggregatedAncestorSubtaxonomy = generator.createTaxonomyFromPAreas(
                nonAggregateTaxonomy.getPAreaTaxonomyFactory(), 
                actualPAreaHierarchy, 
                sourceHierarchy);

        int aggregateBound = ((AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy>)superAggregateTaxonomy).getAggregateBound();
        
        // Convert to ancestor subhierarchy
        AncestorSubtaxonomy subtaxonomy = new AncestorSubtaxonomy(
                nonAggregateTaxonomy,
                selectedRoot.getAggregatedHierarchy().getRoot(),
                nonAggregatedAncestorSubtaxonomy); 
        
        PAreaTaxonomy aggregateAncestorSubtaxonomy = subtaxonomy.getAggregated(aggregateBound);
        
        return new AggregateAncestorSubtaxonomy(
                superAggregateTaxonomy,
                aggregateBound,
                selectedRoot,
                subtaxonomy, 
                aggregateAncestorSubtaxonomy);
    }
    
    public static final ExpandedSubtaxonomy generateExpandedSubtaxonomy(
            PAreaTaxonomy aggregateTaxonomy, 
            AggregatePArea parea) {
        
         AggregatePAreaTaxonomyGenerator generator = new AggregatePAreaTaxonomyGenerator();
        
        return generator.createExpandedSubtaxonomy(aggregateTaxonomy, parea, new PAreaTaxonomyGenerator());
    }

    
    
    private final PAreaTaxonomy nonAggregateBaseTaxonomy;
    
    private final int minBound;
    
    public AggregatePAreaTaxonomy(
            PAreaTaxonomy nonAggregateBaseTaxonomy,
            int minBound,
            PAreaTaxonomy aggregatedPAreaTaxonomy) {
    
        super(aggregatedPAreaTaxonomy.getAreaTaxonomy(), 
                aggregatedPAreaTaxonomy.getPAreaHierarchy(), 
                nonAggregateBaseTaxonomy.getSourceHierarchy());
        
        this.nonAggregateBaseTaxonomy = nonAggregateBaseTaxonomy;
        this.minBound = minBound;
    }
    
    @Override
    public PAreaTaxonomy getNonAggregateSource() {
        return nonAggregateBaseTaxonomy;
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
    public PAreaTaxonomy expandAggregateNode(AggregatePArea parea) {
       return AggregatePAreaTaxonomy.generateExpandedSubtaxonomy(this, parea);
    }
    
    @Override
    public PAreaTaxonomy getAggregated(int aggregateBound) {
        return AggregatePAreaTaxonomy.generateAggregatePAreaTaxonomy(this.getNonAggregateSource(), aggregateBound);
    }

    @Override
    public PAreaTaxonomy createRootSubtaxonomy(AggregatePArea root) {
        return AggregatePAreaTaxonomy.generateAggregateRootSubtaxonomy(this.getNonAggregateSource(), this, root);
    }

    @Override
    public PAreaTaxonomy createAncestorSubtaxonomy(AggregatePArea source) {
        return AggregatePAreaTaxonomy.generateAggregateAncestorSubtaxonomy(this.getNonAggregateSource(), this, source);
    }
}