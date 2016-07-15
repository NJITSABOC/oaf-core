package edu.njit.cs.saboc.blu.core.ontology;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class Ontology<T extends Concept> {
    private final Hierarchy<T> conceptHierarchy;
    
    private final Map<Object, Concept> concepts = new HashMap<>();
    
    public Ontology(Hierarchy<T> conceptHierarchy) {
        this.conceptHierarchy = conceptHierarchy;
        
        conceptHierarchy.getNodes().forEach( (concept -> {
            concepts.put(concept.getID(), concept);
        }));
    }
    
    public Hierarchy<T> getConceptHierarchy() {
        return conceptHierarchy;
    }
    
    public Optional<Concept> getConceptFromID(Object id) {
        return Optional.ofNullable(concepts.get(id));
    }
}
