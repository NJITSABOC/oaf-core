package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
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

        internalNodes.forEach((internalNode) -> {
            Concept root = internalNode.getRoot();

            Set<Concept> parents = hierarchy.getParents(root);

            parents.forEach((parent) -> {
                allNodes.forEach((otherNode) -> {
                    if (otherNode.getConcepts().contains(parent)) {
                        parentNodeDetails.add(new ParentNodeDetails(parent, otherNode));
                    }
                });
            });
        });

        return parentNodeDetails;
    }

    public static <T extends SinglyRootedNode, V extends AggregateNode<T>>
            Hierarchy<T> getDeaggregatedAncestorHierarchy(
                    Hierarchy<T> fullNonAggregatedHierarchy,
                    Hierarchy<V> aggregatedAncestorHierarchy) {

                Set<T> allAggregatedNodes = new HashSet<>();

                aggregatedAncestorHierarchy.getNodes().forEach((aggregateNode) -> {
                    allAggregatedNodes.addAll(aggregateNode.getAggregatedHierarchy().getNodes());
                });
                
                Set<T> actualRoots = new HashSet<>();
                
                aggregatedAncestorHierarchy.getRoots().forEach( (root) -> {
                    actualRoots.add(root.getAggregatedHierarchy().getRoot());
                });

                //TODO: This can be done with a topological traversal, probably
                Hierarchy<T> potentialHierarchy = fullNonAggregatedHierarchy.getAncestorHierarchy(allAggregatedNodes);

                Hierarchy<T> resultHierarchy = new Hierarchy<>(actualRoots);

                allAggregatedNodes.forEach((parea) -> {
                    resultHierarchy.addNode(parea);
                });

                potentialHierarchy.getEdges().forEach((edge) -> {
                    if (allAggregatedNodes.contains(edge.getFrom()) && allAggregatedNodes.contains(edge.getTo())) {
                        resultHierarchy.addEdge(edge);
                    }
                });

                return resultHierarchy;
            }

    public static <T extends SinglyRootedNode> Hierarchy<Concept> getConceptHierarchy(Hierarchy<T> nodeHierarchy, Hierarchy<Concept> fullHierarchy) {
        
        Set<Concept> roots = new HashSet<>();
        
        nodeHierarchy.getRoots().forEach( (rootNode) -> { 
            roots.add(rootNode.getRoot());
        });
        
        Hierarchy<Concept> conceptHierarchy = new Hierarchy(roots);

        nodeHierarchy.getNodes().forEach((parea) -> {
            conceptHierarchy.addAllHierarchicalRelationships(parea.getHierarchy());
        });

        nodeHierarchy.getNodes().forEach((parea) -> {
            fullHierarchy.getParents(parea.getRoot()).forEach((p) -> {
                Concept parent = (Concept) p;

                if (conceptHierarchy.contains(parent)) {
                    conceptHierarchy.addEdge(parea.getRoot(), parent);
                }
            });
        });
        
        return conceptHierarchy;
    }
}
