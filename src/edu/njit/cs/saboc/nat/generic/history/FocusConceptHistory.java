package edu.njit.cs.saboc.nat.generic.history;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;

/**
 * A  class that keeps track of the user's browsing history.
 * @param <T>
 */

public class FocusConceptHistory<T extends Concept> {
    
    public interface HistoryListener {
        public void historyEntryAdded();
        public void historyBack();
        public void historyForward();
    }
    
    private final ArrayList<FocusConceptHistoryEntry<T>> conceptHistory = new ArrayList<>();
    
    private int position = -1;
    
    public ArrayList<HistoryListener> listeners = new ArrayList<>();

    public FocusConceptHistory() {
        
    }
    
    public void addHistoryListener(HistoryListener listener) {
        listeners.add(listener);
    }
    
    public void removeHistoryListener(HistoryListener listener) {
        listeners.remove(listener);
    }

    public void addHistoryEntry(T concept) {
        if (conceptHistory.isEmpty() || !getLastVisitedEntry().getConcept().equals(concept)) {
            conceptHistory.add(new FocusConceptHistoryEntry<>(concept, conceptHistory.size()));

            setPosition(conceptHistory.size() - 1);

            listeners.forEach((listener) -> {
                listener.historyEntryAdded();
            });
        }
    }

    public void minusPosition() {
        setPosition(position - 1);
        
        listeners.forEach( (listener) -> {
            listener.historyBack();
        });
    }

    public void plusPosition() {
        setPosition(position + 1);
        
        listeners.forEach( (listener) -> {
            listener.historyForward();
        });
    }
    
    public FocusConceptHistoryEntry<T> getLastVisitedEntry() {
        return conceptHistory.get(conceptHistory.size() - 1);
    }

    public int getPosition() {
        return position;
    }

    private void setPosition(int pos) {
        position = pos;
    }

    public ArrayList<FocusConceptHistoryEntry<T>> getHistory() {
        return conceptHistory;
    }
}
