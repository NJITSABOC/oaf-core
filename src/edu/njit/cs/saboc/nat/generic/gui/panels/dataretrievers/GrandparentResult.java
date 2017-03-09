package edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;

/**
 * Result for storing the parent of a concept and the parents of the parent
 * 
 * @author Chris O
 * @param <T>
 */
public class GrandparentResult<T extends Concept> extends ExtendedNeighborhoodResult<T> {
    
    public GrandparentResult(T parent, ArrayList<T> grandparents) {
        super(parent, grandparents);
    }
    
    public T getParent() {
        return super.getConcept();
    }
    
    public ArrayList<T> getGrandparents() {
        return super.getExtendedNeighborhood();
    }
}
