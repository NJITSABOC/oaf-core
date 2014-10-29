package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.gui.graphframe.GenericInternalGraphFrame;

/**
 *
 * @author Chris
 */
public abstract class BluGraphSearchAction extends SearchAction {
    protected GenericInternalGraphFrame graphFrame;
    
    protected BluGraphSearchAction(String searchActionName, GenericInternalGraphFrame graphFrame) {
        super(searchActionName);
        
        this.graphFrame = graphFrame;
    }
}
