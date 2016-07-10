package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Den
 */
public class AggregatePArea extends PArea implements AggregateNode<PArea> {
    
    private final Hierarchy<PArea> aggregateHierarchy;
    
    public AggregatePArea(
            Hierarchy<Concept> conceptHierarchy, 
            Set<InheritableProperty> relationships,
            Hierarchy<PArea> aggregateHierarchy) {
        
        super(conceptHierarchy, relationships);
        
        this.aggregateHierarchy = aggregateHierarchy;
    }

    @Override
    public Hierarchy<PArea> getAggregatedHierarchy() {
        return aggregateHierarchy;
    }

    @Override
    public Set<PArea> getAggregatedNodes() {
        Set<PArea> aggregatedPAreas = new HashSet<>(aggregateHierarchy.getNodesInHierarchy());
        aggregatedPAreas.remove(aggregateHierarchy.getRoot());
        
        return aggregatedPAreas;
    }
}
