package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public interface TANListenerConfiguration extends PartitionedAbNListenerConfiguration {
    public EntitySelectionListener<Concept> getClusterPatriarchSelectedListener();

    public EntitySelectionListener<Concept> getBandPatriarchSelectedListener();
}

