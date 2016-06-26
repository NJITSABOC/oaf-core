package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Den
 */
public class AggregatePArea extends PArea implements AggregateNode<PArea> {
    
    private final NodeHierarchy<PArea> aggregateHierarchy;
    
    public AggregatePArea(
            ConceptHierarchy conceptHierarchy, 
            Set<InheritableProperty> relationships,
            NodeHierarchy<PArea> aggregateHierarchy) {
        
        super(conceptHierarchy, relationships);
        
        this.aggregateHierarchy = aggregateHierarchy;
    }

    @Override
    public NodeHierarchy<PArea> getAggregatedHierarchy() {
        return aggregateHierarchy;
    }

    @Override
    public Set<PArea> getAggregatedNodes() {
        Set<PArea> aggregatedPAreas = new HashSet<>(aggregateHierarchy.getNodesInHierarchy());
        aggregatedPAreas.remove(aggregateHierarchy.getRoot());
        
        return aggregatedPAreas;
    }
}
