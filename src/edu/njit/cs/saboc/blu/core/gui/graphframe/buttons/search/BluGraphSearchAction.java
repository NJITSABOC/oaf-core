package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.gui.graphframe.GenericInternalGraphFrame;

/**
 *
 * @author Chris
 */
public abstract class BluGraphSearchAction<T> extends SearchAction<T> {
    private final GenericInternalGraphFrame graphFrame;
    
    protected BluGraphSearchAction(String searchActionName, GenericInternalGraphFrame graphFrame) {
        super(searchActionName);
        
        this.graphFrame = graphFrame;
    }
    
    public GenericInternalGraphFrame getGraphFrame() {
        return graphFrame;
    }
}
