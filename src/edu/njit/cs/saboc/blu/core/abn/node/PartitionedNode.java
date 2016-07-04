package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an abstraction network node that can be partitioned into sub-nodes
 * 
 * @author Chris O
 */
public abstract class PartitionedNode extends Node {
    
    private final Set<? extends SinglyRootedNode> internalNodes;
    private final Set<Concept> concepts;
    
    public PartitionedNode(Set<? extends SinglyRootedNode> internalNodes) {
        this.internalNodes = internalNodes;
        
        this.concepts = new HashSet<>();
        
        this.internalNodes.forEach( (n) -> {
            concepts.addAll(n.getHierarchy().getConceptsInHierarchy());
        });
    }
    
    public Set<Concept> getRoots() {
        Set<Concept> roots = new HashSet<>();
        
        getInternalNodes().forEach( (node) -> {
            roots.add(node.getRoot());
        });
        
        return roots;
    }

    public Set<SinglyRootedNode> getInternalNodes() {
        return (Set<SinglyRootedNode>)internalNodes;
    }
    
    public Set<Concept> getConcepts() {
        return concepts;
    }
    
    public int getConceptCount() {
        return concepts.size();
    }
    
    public ConceptHierarchy getHierarchy() {
        ConceptHierarchy hierarchy = new ConceptHierarchy(getRoots());
        
        internalNodes.forEach( (node) -> {
            hierarchy.addAllHierarchicalRelationships(node.getHierarchy());
        });
        
        return hierarchy;
    }
    
    public boolean hasOverlappingConcepts() {
        Set<Concept> processedConcepts = new HashSet<>();
        
        Set<SinglyRootedNode> nodes = this.getInternalNodes();
        
        for(SinglyRootedNode node : nodes) {
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
}
