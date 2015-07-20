package edu.njit.cs.saboc.blu.core.graph.nodes;

import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class AbNNodeEntry extends JPanel {

    public enum HighlightState {
        None,
        Selected,
        Parent,
        Child,
        SearchResult
    }
    
    private boolean isMousedOver;
    
    private HighlightState highlightState;
    
    public AbNNodeEntry() {
        this.isMousedOver = false;
        this.highlightState = HighlightState.None;
    }
    
    public boolean isMousedOver() {
        return isMousedOver;
    }
    
    public void setMousedOver(boolean value) {
        this.isMousedOver = value;
    }
    
    public HighlightState getHighlightState() {
        return highlightState;
    }
    
    public void setHighlightState(HighlightState state) {
        this.highlightState = state;
    }
}
