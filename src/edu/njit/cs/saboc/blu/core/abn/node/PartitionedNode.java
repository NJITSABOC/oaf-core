package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.abn.OverlappingConceptResult;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
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
    
    public PartitionedNode(
            Set<? extends PartitionedNode> parentNodes, 
            Set<? extends SinglyRootedNode> internalNodes) {
        
        super(parentNodes);
        
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
        
    public HashSet<OverlappingConcept> getOverlappingConcepts() {

        HashMap<Concept, HashSet<PArea>> conceptsPAreas = new HashMap<>();
        ArrayList<PAREA_T> pareas = this.getAllPAreas();

        for (PArea parea : pareas) {

            ArrayList<Concept> pareaConcepts = parea.getConceptsInPArea();

            for (Concept concept : pareaConcepts) {
                if (!conceptsPAreas.containsKey(concept)) {
                    conceptsPAreas.put(concept, new HashSet<>());
                }
                
                conceptsPAreas.get(concept).add(parea);
            }
        }

        HashSet<OverlappingConceptResult<Concept, PArea>> overlappingResults = new HashSet<>();
        
        conceptsPAreas.forEach( (Concept c, HashSet<PArea> overlappingPAreas) -> {
            if(overlappingPAreas.size() > 1) {
                overlappingResults.add(new OverlappingConceptResult<>(c, overlappingPAreas));
            }
        });
        
        return overlappingResults;
    }
}
