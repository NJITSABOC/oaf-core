package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNFactory;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaTaxonomyFactory implements AggregateAbNFactory<PArea, AggregatePArea> {

    @Override
    public AggregatePArea createAggregateNode(
            Hierarchy<PArea> aggregatedNodes, 
            Hierarchy<Concept> sourceHierarchy) {
        
        Hierarchy<Concept> hierarchy = new Hierarchy<>(aggregatedNodes.getRoot().getRoot());
        
        Set<Concept> allConcepts = new HashSet<>();
        
        aggregatedNodes.getNodes().forEach( (parea) -> {
            allConcepts.addAll(parea.getConcepts());
            
            hierarchy.addAllHierarchicalRelationships(parea.getHierarchy());
        });
        
        Set<Concept> conceptsInPArea = hierarchy.getNodes();
        
        aggregatedNodes.getNodes().forEach( (parea) -> {
            Concept root = parea.getRoot();
            
            sourceHierarchy.getParents(root).forEach( (parent) -> {
               if(conceptsInPArea.contains(parent)) {
                   hierarchy.addEdge(root, parent);
               } 
            });
        });
        
        return new AggregatePArea(hierarchy, aggregatedNodes.getRoot().getRelationships(), aggregatedNodes);
    }
}
