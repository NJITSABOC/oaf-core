package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.node.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AbstractionNetworkUtils {

    public static Set<ParentNodeDetails> getSinglyRootedNodeParentNodeDetails(
            SinglyRootedNode node, 
            ConceptHierarchy conceptHierarchy, 
            Set<SinglyRootedNode> allNodes) {

        Set<ParentNodeDetails> parentNodeDetails = new HashSet<>();

        Concept root = node.getRoot();

        Set<Concept> parents = conceptHierarchy.getParents(root);

        parents.forEach((parent) -> {
            allNodes.forEach((disjointNode) -> {
                if (disjointNode.getConcepts().contains(parent)) {
                    parentNodeDetails.add(new ParentNodeDetails(parent, disjointNode));
                }
            });
        });

        return parentNodeDetails;
    }
    
    public static Set<ParentNodeDetails> getMultiRootedNodeParentNodeDetails(
            PartitionedNode node, 
            ConceptHierarchy hierarchy, 
            Set<PartitionedNode> allNodes) {
        
        Set<? extends SinglyRootedNode> internalNodes = node.getInternalNodes();
        
        Set<ParentNodeDetails> parentNodeDetails = new HashSet<>();
        
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
