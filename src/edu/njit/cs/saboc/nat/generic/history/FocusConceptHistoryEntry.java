package edu.njit.cs.saboc.nat.generic.history;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Date;
import java.util.Objects;

/**
 * An entry for visiting a focus concept at a specific time.
 * 
 * @author Chris O
 * @param <T>
 */

public class FocusConceptHistoryEntry<T extends Concept> {

    private final T concept;
    private final Date timeViewed;
    
    public FocusConceptHistoryEntry(T concept, Date timeViewed) {
        this.concept = concept;
        this.timeViewed = timeViewed;
    }

    public FocusConceptHistoryEntry(T concept) {
        this.concept = concept;

        this.timeViewed = new Date();
    }

    public T getConcept() {
        return concept;
    }

    public Date getTimeViewed() {
        return timeViewed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final FocusConceptHistoryEntry<?> other = (FocusConceptHistoryEntry<?>) obj;
        
        if (!Objects.equals(this.concept, other.concept)) {
            return false;
        }
        
        if (!Objects.equals(this.timeViewed, other.timeViewed)) {
            return false;
        }
        
        return true;
    }
    
    

}
