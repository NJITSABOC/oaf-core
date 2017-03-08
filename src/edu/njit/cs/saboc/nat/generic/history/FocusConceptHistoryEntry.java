package edu.njit.cs.saboc.nat.generic.history;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Date;

/**
 * An entry for visiting a focus concept at a specific time.
 * 
 * @author Chris O
 * @param <T>
 */

public class FocusConceptHistoryEntry<T extends Concept> {

    private final T concept;
    private final int position;
    private final Date timeViewed;

    public FocusConceptHistoryEntry(T concept, int position) {
        this.concept = concept;
        
        this.position = position;
        
        this.timeViewed = new Date();
    }

    public T getConcept() {
        return concept;
    }

    public int getPosition() {
        return position;
    }

    public Date getTimeViewed() {
        return timeViewed;
    }
}
