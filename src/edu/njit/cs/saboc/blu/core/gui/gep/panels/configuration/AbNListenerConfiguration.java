package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public interface AbNListenerConfiguration {
    public EntitySelectionListener<Concept> getGroupConceptListListener();
    
    public EntitySelectionListener<Node> getChildGroupListener();
    
    public EntitySelectionListener<ParentNodeDetails> getParentGroupListener();
}
