package edu.njit.cs.saboc.nat.generic;

import java.util.ArrayList;

/**
 * A  class that keeps track of the user's browsing history.
 * 
 * @param <T> The type of concept stored in this history
 */
public class History<T> {
    /**
     * ArrayList which stores the history of visited concepts
     */
    private final ArrayList<T> conceptHistory = new ArrayList<>();
    
    /**
     * The initial position in the history
     */
    private int position = -1;

    public History() {
        
    }

    /**
     * 
     * @param concept Concept to be added to the end of the history (most recently visited)
     */
    public void addHistoryConcept(T concept) {
        if(!conceptHistory.isEmpty() && conceptHistory.get(getPosition()).equals(concept)) {
            return;
        }
        
        conceptHistory.add(concept);
        setPosition(conceptHistory.size() - 1);
    }

    /**
     * Moves the current position in history one backwards
     */
    public void minusPosition() {
        setPosition(position - 1);
    }

    /**
     * Moves the current position in history one forwards
     */
    public void plusPosition() {
        setPosition(position + 1);
    }

    /**
     * 
     * @return The current position in the browser history
     */
    public int getPosition() {
        return position;
    }

    /**
     * 
     * @param pos The position in history
     */
    public void setPosition(int pos) {
        position = pos;
    }

    /**
     * 
     * @return The list of visited concepts in chronological order (index 0 is the first visited).
     */
    public ArrayList<T> getHistoryList() {
        return conceptHistory;
    }

    /**
     * 
     * @return The currently selected concept in the browser history
     */
    public T getCurrentConcept() {
        return conceptHistory.get(position);
    }

    /**
     * Removes all of the concepts ahead of the current position.
     */
    public void clearFuture() {
        for(int i = (conceptHistory.size() - 1); i > position; i--) {
            conceptHistory.remove(i);
        }
    }

    /**
     * Removes all concepts from the history
     */
    public void emptyHistory() {
        conceptHistory.clear();
        position = -1;
    }

    /**
     * 
     * @return If the current position in history is the last entered concept
     */
    public boolean atTopOfHistory() {
        return position == conceptHistory.size() - 1;
    } 
}
