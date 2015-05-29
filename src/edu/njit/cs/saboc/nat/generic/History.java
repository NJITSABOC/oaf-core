package edu.njit.cs.saboc.nat.generic;

import java.util.ArrayList;

/**
 * A  class that keeps track of the user's browsing history.
 */
public class History<T> {
    private ArrayList<T> conceptHistory = new ArrayList<>();
    
    private int position = -1;

    public History() {
        
    }

    public void addHistoryConcept(T concept) {
        if(!conceptHistory.isEmpty() && conceptHistory.get(getPosition()).equals(concept)) {
            return;
        }
        
        conceptHistory.add(concept);
        position++;
    }

    public void minusPosition() {
        position--;
    }

    public void plusPosition() {
        position++;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int pos) {
        position = pos;
    }

    public ArrayList<T> getHistoryList() {
        return conceptHistory;
    }

    public T getCurrentConcept() {
        return conceptHistory.get(position);
    }

    public void clearFuture() {
        for(int i = (conceptHistory.size() - 1); i > position; i--) {
            conceptHistory.remove(i);
        }
    }

    // Removes all concepts from history
    public void emptyHistory() {
        conceptHistory.clear();
        position = -1;
    }

    public boolean atTopOfHistory() {
        return position == conceptHistory.size() - 1;
    } 
}
