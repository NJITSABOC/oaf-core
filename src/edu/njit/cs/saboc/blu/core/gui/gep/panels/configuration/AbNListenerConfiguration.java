package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public interface AbNListenerConfiguration<T extends Node> {
    public EntitySelectionListener<Concept> getGroupConceptListListener();
    
    public EntitySelectionListener<T> getChildGroupListener();
    
    public EntitySelectionListener<ParentNodeDetails<T>> getParentGroupListener();
}
