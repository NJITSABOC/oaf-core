package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AbstractionNetworkUtils {

    public static <T extends SinglyRootedNode> Set<ParentNodeDetails<T>> getSinglyRootedNodeParentNodeDetails(
            T node, 
            Hierarchy<Concept> conceptHierarchy, 
            Set<T> allNodes) {

        Set<ParentNodeDetails<T>> parentNodeDetails = new HashSet<>();

        Concept root = node.getRoot();

        Set<Concept> parents = conceptHierarchy.getParents(root);

        parents.forEach((parent) -> {
            allNodes.forEach((disjointNode) -> {
                if (disjointNode.getConcepts().contains(parent)) {
                    parentNodeDetails.add(new ParentNodeDetails<>(parent, disjointNode));
                }
            });
        });

        return parentNodeDetails;
    }
    
    public static <T extends PartitionedNode> Set<ParentNodeDetails<T>> getMultiRootedNodeParentNodeDetails(
            PartitionedNode node, 
            Hierarchy<Concept> hierarchy, 
            Set<PartitionedNode> allNodes) {
        
        Set<SinglyRootedNode> internalNodes = node.getInternalNodes();
        
        Set<ParentNodeDetails<T>> parentNodeDetails = new HashSet<>();
        
        internalNodes.forEach( (internalNode) -> {
            Concept root = internalNode.getRoot();
            
            Set<Concept> parents = hierarchy.getParents(root);
            
            parents.forEach( (parent) -> {
                allNodes.forEach( (otherNode) -> { 
                    if(otherNode.getConcepts().contains(parent)) {
                        parentNodeDetails.add(new ParentNodeDetails(parent, otherNode));
                    }
                });
            });
        });
        
        return parentNodeDetails;
    }
}