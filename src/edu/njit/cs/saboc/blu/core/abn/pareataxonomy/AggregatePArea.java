package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.SingleRootedConceptHierarchy;
import java.util.Set;

/**
 *
 * @author Den
 */
public class AggregatePArea extends PArea implements AggregateNode<PArea> {
    
    private final NodeHierarchy<PArea> aggregateHierarchy;
    
    public AggregatePArea(
            SingleRootedConceptHierarchy conceptHierarchy, 
            Set<InheritableProperty> relationships,
            NodeHierarchy<PArea> aggregateHierarchy) {
        
        super(conceptHierarchy, relationships);
        
        this.aggregateHierarchy = aggregateHierarchy;
    }

    @Override
    public NodeHierarchy<PArea> getAggregatedHierarchy() {
        return aggregateHierarchy;
    }
}
