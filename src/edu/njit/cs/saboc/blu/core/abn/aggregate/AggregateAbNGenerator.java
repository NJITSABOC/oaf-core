package edu.njit.cs.saboc.blu.core.abn.aggregate;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Generates aggregate abstraction networks. Takes as input a hierarchy of
 * non-aggregate nodes from an abstraction network and returns a hierarchy of
 * aggregated nodes that summarizes the input node hierarchy.
 * 
 * @author Chris O
 * 
 * @param <NODE_T>
 * @param <AGGREGATENODE_T>
 */
public class AggregateAbNGenerator <
        NODE_T extends Node,
        AGGREGATENODE_T extends Node & AggregateNode<NODE_T>> {
    
    /**
     * Creates a subhierarchy of aggregated nodes from the given inputs
     * 
     * @param factory
     * @param sourceHierarchy
     * @param sourceConceptHierarchy
     * @param minNodeSize The minimum size node that is preserved
     * @return 
     */
    public Hierarchy<AGGREGATENODE_T> createAggregateAbN(
            AggregateAbNFactory<NODE_T, AGGREGATENODE_T> factory,
            Hierarchy<NODE_T> sourceHierarchy, 
            Hierarchy<Concept> sourceConceptHierarchy,
            int minNodeSize) {
        
        HashMap<NODE_T, Hierarchy<NODE_T>> reducedGroupMembers = new HashMap<>();

        HashMap<NODE_T, Integer> groupParentCounts = new HashMap<>();
        
        // Step 1: Determine which nodes will be the roots of aggregate nodes
        Set<NODE_T> remainingNodes = new HashSet<>();
        remainingNodes.addAll(sourceHierarchy.getRoots()); // The roots are always included
        
        HashMap<NODE_T, HashSet<NODE_T>> groupSet = new HashMap<>();
        
        for(NODE_T group : sourceHierarchy.getNodes()) {
            groupParentCounts.put(group, sourceHierarchy.getParents(group).size());
            
            if (group.getConceptCount() >= minNodeSize) {
                remainingNodes.add(group);
            }

            groupSet.put(group, new HashSet<>());
        }
        
        remainingNodes.forEach((group) -> {
            reducedGroupMembers.put(group, new Hierarchy<>(group));
        });
        
        // Step 2: Do a topological tarversal of the nodes and determine 
        // which nodes will be included in the given aggregate node
        Queue<NODE_T> queue = new LinkedList<>();
        queue.addAll(sourceHierarchy.getRoots());
        
        while(!queue.isEmpty()) {
            
            NODE_T group = queue.remove();
            
            Set<NODE_T> parentGroups = sourceHierarchy.getParents(group);

            if (remainingNodes.contains(group)) {
                groupSet.get(group).add(group);
            } else {
                for (NODE_T parentGroup : parentGroups) {

                    // Mark that this group belongs to the same reduced group as its parents
                    groupSet.get(group).addAll(groupSet.get(parentGroup));

                    // Add this group to that reducing group too
                    groupSet.get(parentGroup).forEach((reducedGroup) -> {
                        reducedGroupMembers.get(reducedGroup).addEdge(group, parentGroup);
                    });
                }
            }

            Set<NODE_T> childGroups = sourceHierarchy.getChildren(group);

            if (!childGroups.isEmpty()) {
                childGroups.forEach((childGroup) -> {
                    int childParentCount = groupParentCounts.get(childGroup);
                    
                    if(childParentCount - 1 == 0) {
                        queue.add(childGroup);
                    } else {
                        groupParentCounts.put(childGroup, childParentCount - 1);
                    }
                });
            }
        }
        
        // Step 3: Create the aggregate nodes
        HashMap<NODE_T, AGGREGATENODE_T> aggregateGroups = new HashMap<>();
        
        remainingNodes.forEach((aggregateGroup) -> {
            aggregateGroups.put(aggregateGroup, 
                    factory.createAggregateNode(reducedGroupMembers.get(aggregateGroup), sourceConceptHierarchy));
        });
        
        Set<AGGREGATENODE_T> rootAggregateNodes = new HashSet<>();
       
        sourceHierarchy.getRoots().forEach((root) -> {
            rootAggregateNodes.add(aggregateGroups.get(root));
        });
        
        // Step 4: Build the aggregate node hierarchy
        Hierarchy<AGGREGATENODE_T> aggregateNodeHierarchy = new Hierarchy<>(rootAggregateNodes);
        
        aggregateGroups.values().forEach((aggregateNode) -> {
            
            if (!aggregateNodeHierarchy.getRoots().contains(aggregateNode)) {
                AggregateNode<NODE_T> theNode = (AggregateNode<NODE_T>) aggregateNode;

                NODE_T rootNode = theNode.getAggregatedHierarchy().getRoot();
                
                Set<NODE_T> parentNodes = sourceHierarchy.getParents(rootNode);
                
                parentNodes.forEach( (parentNode) -> {
                     Set<NODE_T> parentAggregateNodes = groupSet.get(parentNode);
                   
                     parentAggregateNodes.forEach( (parentAggregateNode) -> {
                         aggregateNodeHierarchy.addEdge(aggregateNode, aggregateGroups.get(parentAggregateNode));
                     });
                });
            }
        });
        
        return aggregateNodeHierarchy;
    }
}
