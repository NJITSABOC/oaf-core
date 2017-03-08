package edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;

/**
 * Base class for returning concepts that are "two steps away"  from the 
 * given focus concept(e.g., grandparents and grandchildren)
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class ExtendedNeighborhoodResult<T extends Concept> {
    
    private final T concept;
    private final ArrayList<T> extendedNeighborhood;
    
    protected ExtendedNeighborhoodResult(T concept, ArrayList<T> extendedNeighborhood) {
        this.concept = concept;
        this.extendedNeighborhood = extendedNeighborhood;
    }
    
    protected T getConcept() {
        return concept;
    }
    
    protected ArrayList<T> getExtendedNeighborhood() {
        return extendedNeighborhood;
    }
}
