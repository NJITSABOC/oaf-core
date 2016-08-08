package edu.njit.cs.saboc.nat.generic.data;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;

/**
 * An interface that defines high level functionality that must be implemented by every instance of a NAT
 * @author Chris O
 */
public abstract class ConceptBrowserDataSource {

    private final Ontology<Concept> ontology;
    
    public ConceptBrowserDataSource(Ontology<Concept> ontology) {
        this.ontology = ontology;
    }
    
    public Ontology<Concept> getOntology() {
        return ontology;
    }
}
