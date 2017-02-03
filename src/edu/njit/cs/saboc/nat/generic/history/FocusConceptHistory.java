package edu.njit.cs.saboc.nat.generic.history;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;

/**
 * A  class that keeps track of the user's browsing history.
 * @param <T>
 */

public class FocusConceptHistory<T extends Concept> {
    
    private final ArrayList<FocusConceptHistoryEntry<T>> conceptHistory = new ArrayList<>();
    
    private int position = -1;

    public FocusConceptHistory() {
        
    }

    public void addHistoryEntry(T concept) {
        conceptHistory.add(new FocusConceptHistoryEntry<>(concept, conceptHistory.size()));
        
        setPosition(conceptHistory.size() - 1);
    }

    public void minusPosition() {
        setPosition(position - 1);
    }


    public void plusPosition() {
        setPosition(position + 1);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int pos) {
        position = pos;
    }

    public ArrayList<FocusConceptHistoryEntry<T>> getHistory() {
        return conceptHistory;
    }
}
