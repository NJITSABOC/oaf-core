package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Chris O
 * 
 * @param <NODE_T> The type of node in the abstraction network
 */
public abstract class AbstractionNetwork<NODE_T extends Node> {

    private final NodeHierarchy<NODE_T> nodeHierarchy;
    private final ConceptHierarchy sourceHierarchy;
    
    protected AbstractionNetwork(
            NodeHierarchy<NODE_T> nodeHierarchy,
            ConceptHierarchy sourceHierarchy) {
        
        this.nodeHierarchy = nodeHierarchy;
        this.sourceHierarchy = sourceHierarchy;
    }

    public int getNodeCount() {
        return nodeHierarchy.size();
    }
    
    public ConceptHierarchy getSourceHierarchy() {
        return sourceHierarchy;
    }
    
    public Set<NODE_T> getNodes() {
        return nodeHierarchy.getNodesInHierarchy();
    }
    
    public NodeHierarchy<NODE_T> getNodeHierarchy() {
        return nodeHierarchy;
    }
    
    /**
     * Returns the set of nodes that contain the given concept
     * @param concept
     * @return 
     */
    public Set<Node> getNodesWith(Concept concept) {
        Set<Node> nodes = nodeHierarchy.getNodesInHierarchy().stream().filter( 
                (node) -> {return node.getConcepts().contains(concept); }).collect(Collectors.toSet());
        
        return nodes;
    }
    
    /**
     * Returns the set of nodes that contain the given query (searches anywhere in node name).
     * 
     * Does not consider case when searching.
     * 
     * @param query
     * @return 
     */
    public Set<Node> searchNodes(String query) {
        Set<Node> nodes = nodeHierarchy.getNodesInHierarchy().stream().filter( 
                (node) -> {return node.getName().toLowerCase().contains(query.toLowerCase()); }).collect(Collectors.toSet());
        
        return nodes;
    }
    
    /**
     * A linear search through the abstraction network's nodes to find concepts.
     * The results will consists of concepts that contain the given query.
     * 
     * Does not consider case when searching.
     * 
     * @param query
     * @return 
     */
    public Set<ConceptNodeDetails<NODE_T>> searchConcepts(String query) {
        Set<ConceptNodeDetails<NODE_T>> result = new HashSet<>();
        
        Map<Concept, Set<NODE_T>> conceptNodes = new HashMap<>();
        
        nodeHierarchy.getNodesInHierarchy().forEach( (node) -> {
            Set<Concept> conceptResults = node.getConcepts().stream().filter( (concept) -> {
                return concept.getName().toLowerCase().contains(query);
            }).collect(Collectors.toSet());
            
            conceptResults.forEach( (concept) -> {
               if(!conceptNodes.containsKey(concept)) {
                   conceptNodes.put(concept, new HashSet<>());
               }
               
               conceptNodes.get(concept).add(node);
            });
        });
        
        conceptNodes.forEach( (concept, nodes) -> {
            result.add(new ConceptNodeDetails<>(concept, nodes));
        });
        
        return result;
    }
        
    public abstract Set<ParentNodeDetails> getParentNodeDetails(NODE_T node);
}
