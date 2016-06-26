package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNUIConfiguration;

/**
 *
 * @author Den
 */
public abstract class PAreaTaxonomyUIConfiguration extends PartitionedAbNUIConfiguration {
    
    public PAreaTaxonomyUIConfiguration(PAreaTaxonomyListenerConfiguration listenerConfig) {
        super(listenerConfig);
    }
    
    public PAreaTaxonomyListenerConfiguration getListenerConfiguration() {
        return (PAreaTaxonomyListenerConfiguration)super.getListenerConfiguration();
    }
}
