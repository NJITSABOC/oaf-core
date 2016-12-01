package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;

/**
 *
 * @author Chris
 */
public abstract class BluGraphSearchAction<T> extends SearchAction<T> {    
    protected BluGraphSearchAction(String searchActionName) {
        super(searchActionName);
    }
}
