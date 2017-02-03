package edu.njit.cs.saboc.nat.generic.data;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.ArrayList;

/**
 * An interface that defines high level functionality that must be implemented by every instance of a NAT
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class ConceptBrowserDataSource<T extends Concept> {

    private final Ontology<T> ontology;
    
    public ConceptBrowserDataSource(Ontology<T> ontology) {
        this.ontology = ontology;
    }
    
    public Ontology<T> getOntology() {
        return ontology;
    }
        
    public abstract ArrayList<NATConceptSearchResult<T>> searchStarting(String str);
    public abstract ArrayList<NATConceptSearchResult<T>> searchExact(String str);
    public abstract ArrayList<NATConceptSearchResult<T>> searchAnywhere(String str);
    public abstract ArrayList<NATConceptSearchResult<T>> searchID(String str);
}
