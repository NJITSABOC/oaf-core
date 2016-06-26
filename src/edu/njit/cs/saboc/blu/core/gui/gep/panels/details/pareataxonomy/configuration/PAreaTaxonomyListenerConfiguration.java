package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;

/**
 *
 * @author Chris O
 */
public interface PAreaTaxonomyListenerConfiguration extends PartitionedAbNListenerConfiguration {
    public EntitySelectionListener<InheritableProperty> getGroupRelationshipSelectedListener();
    public EntitySelectionListener<InheritableProperty> getContainerRelationshipSelectedListener();
}
