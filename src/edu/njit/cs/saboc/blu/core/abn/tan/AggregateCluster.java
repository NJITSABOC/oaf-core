package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateCluster extends Cluster implements AggregateNode<Cluster> {
    
    private final Hierarchy<Cluster> aggregateHierarchy;
    
    public AggregateCluster(
            Hierarchy<Concept> conceptHierarchy, 
            Set<Concept> patriarchs,
            Hierarchy<Cluster> aggregateHierarchy) {
        
        super(conceptHierarchy, patriarchs);
        
        this.aggregateHierarchy = aggregateHierarchy;
    }

    @Override
    public Hierarchy<Cluster> getAggregatedHierarchy() {
        return aggregateHierarchy;
    }

    @Override
    public Set<Cluster> getAggregatedNodes() {
        Set<Cluster> aggregatedPAreas = new HashSet<>(aggregateHierarchy.getNodes());
        aggregatedPAreas.remove(aggregateHierarchy.getRoot());
        
        return aggregatedPAreas;
    }
}
