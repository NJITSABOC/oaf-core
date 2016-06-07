package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
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

    protected Set<? extends SinglyRootedNode> getInternalNodes() {
        return internalNodes;
    }
    
    public int getConceptCount() {
        return concepts.size();
    }
        
    public Set<OverlappingConcept> getOverlappingConcepts() {

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

        HashSet<OverlappingConcept> overlappingResults = new HashSet<>();
        
        conceptNodes.forEach( (Concept c, Set<SinglyRootedNode> overlappingNodes) -> {
            if(overlappingNodes.size() > 1) {
                overlappingResults.add(new OverlappingConcept(c, overlappingNodes));
            }
        });
        
        return overlappingResults;
    }
}
