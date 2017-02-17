package edu.njit.cs.saboc.nat.generic.data;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.DescendantsVisitor;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.ArrayList;
import java.util.Map;

/**
 * An interface that defines high level functionality that must be implemented by every instance of a NAT
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class ConceptBrowserDataSource<T extends Concept> {

    private final Ontology<T> ontology;
    
    private final Map<T, Integer> descendantCount;
    
    public ConceptBrowserDataSource(Ontology<T> ontology) {
        this.ontology = ontology;
        
        Hierarchy<T> conceptHierarchy = ontology.getConceptHierarchy();
        
        DescendantsVisitor<T> descendantsVisitor = new DescendantsVisitor<>(conceptHierarchy);
        
        conceptHierarchy.topologicalUp(conceptHierarchy.getLeaves(), descendantsVisitor);
        
        this.descendantCount = descendantsVisitor.getDescendantCounts();
    }
    
    public Ontology<T> getOntology() {
        return ontology;
    }
    
    public int getDescendantCount(T concept) {
        return descendantCount.get(concept);
    }
    
    public String getFocusConceptText(T concept) {
        return String.format("<html><font face='Arial' size = '5'><b>%s</b><br>%s", 
                concept.getName(), 
                concept.getIDAsString());
    }
        
    public abstract ArrayList<NATConceptSearchResult<T>> searchStarting(String str);
    public abstract ArrayList<NATConceptSearchResult<T>> searchExact(String str);
    public abstract ArrayList<NATConceptSearchResult<T>> searchAnywhere(String str);
    public abstract ArrayList<NATConceptSearchResult<T>> searchID(String str);
}
