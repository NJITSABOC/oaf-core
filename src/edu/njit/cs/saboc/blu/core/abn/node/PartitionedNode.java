package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an abstraction network node that can be partitioned into sub-nodes
 * 
 * @author Chris O
 */
public abstract class PartitionedNode<T extends SinglyRootedNode> extends Node {
    
    private final Set<T> internalNodes;
    private final Set<Concept> concepts;
    
    public PartitionedNode(Set<T> internalNodes) {
        this.internalNodes = internalNodes;
        
        this.concepts = new HashSet<>();
        
        this.internalNodes.forEach( (n) -> {
            concepts.addAll(n.getHierarchy().getNodes());
        });
    }
    
    public Set<Concept> getRoots() {
        Set<Concept> roots = new HashSet<>();
        
        getInternalNodes().forEach( (node) -> {
            roots.add(node.getRoot());
        });
        
        return roots;
    }

    public Set<T> getInternalNodes() {
        return internalNodes;
    }
    
    public Set<Concept> getConcepts() {
        return concepts;
    }
    
    public int getConceptCount() {
        return concepts.size();
    }
    
    public Hierarchy<Concept> getHierarchy() {
        Hierarchy<Concept> hierarchy = new Hierarchy<>(getRoots());
        
        internalNodes.forEach( (node) -> {
            hierarchy.addAllHierarchicalRelationships(node.getHierarchy());
        });
        
        return hierarchy;
    }
    
    public boolean hasOverlappingConcepts() {
        Set<Concept> processedConcepts = new HashSet<>();
        
        Set<T> nodes = this.getInternalNodes();
        
        for(T node : nodes) {
            Set<Concept> pareaConcepts = node.getConcepts();
            
            for(Concept concept : pareaConcepts) {
                if(processedConcepts.contains(concept)) {
                    return true;
                } else {
                    processedConcepts.add(concept);
                }
            }
        }
        
        return false;
    }
    
    public Set<Concept> getOverlappingConcepts() {
        Set<OverlappingConceptDetails> details = this.getOverlappingConceptDetails();
        
        Set<Concept> overlappingConcepts = new HashSet<>();
        
        details.forEach( (detail) -> {
            overlappingConcepts.add(detail.getConcept());
        });
        
        return overlappingConcepts;
    }
        
    public Set<OverlappingConceptDetails> getOverlappingConceptDetails() {

        HashMap<Concept, Set<SinglyRootedNode>> conceptNodes = new HashMap<>();

        for (SinglyRootedNode node : internalNodes) {

            Set<Concept> nodeConcepts = node.getConcepts();

            for (Concept concept : nodeConcepts) {
                if (!conceptNodes.containsKey(concept)) {
                    conceptNodes.put(concept, new HashSet<>());
                }
                
                conceptNodes.get(concept).add(node);
            }
        }

        HashSet<OverlappingConceptDetails> overlappingResults = new HashSet<>();
        
        conceptNodes.forEach( (Concept c, Set<SinglyRootedNode> overlappingNodes) -> {
            if(overlappingNodes.size() > 1) {
                overlappingResults.add(new OverlappingConceptDetails(c, overlappingNodes));
            }
        });
        
        return overlappingResults;
    }
    
    public abstract String getName(String separator);
}
