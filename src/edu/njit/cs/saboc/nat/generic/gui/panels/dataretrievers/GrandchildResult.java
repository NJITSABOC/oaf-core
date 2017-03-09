package edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;

/**
 * Result consisting of a child concept and the children of the child concept
 * 
 * @author Chris O
 * @param <T>
 */
public class GrandchildResult<T extends Concept> extends ExtendedNeighborhoodResult<T> {
    
    public GrandchildResult(T child, ArrayList<T> grandchildren) {
        super(child, grandchildren);
    }
    
    public T getChild() {
        return super.getConcept();
    }
    
    public ArrayList<T> getGrandChildren() {
        return super.getExtendedNeighborhood();
    }
}
