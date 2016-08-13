package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyUIConfiguration;

/**
 *
 * @author Den
 */
public abstract class DiffPAreaTaxonomyUIConfiguration extends PAreaTaxonomyUIConfiguration {

    public DiffPAreaTaxonomyUIConfiguration(
            DiffPAreaTaxonomyConfiguration config, 
            DiffPAreaTaxonomyListenerConfiguration listenerConfig) {
        
        super(config, listenerConfig);
    }
    
    public DiffPAreaTaxonomyListenerConfiguration getListenerConfiguration() {
        return (DiffPAreaTaxonomyListenerConfiguration)super.getListenerConfiguration();
    }
    
    public DiffPAreaTaxonomyConfiguration getConfiguration() {
        return (DiffPAreaTaxonomyConfiguration)super.getConfiguration();
    }

    @Override
    public boolean hasContainerDetailsPanel() {
        return false;
    }

    @Override
    public NodeDashboardPanel createContainerDetailsPanel() {
        return null;
    }

    @Override
    public AbstractAbNDetailsPanel createAbNDetailsPanel() {
        return new AbstractAbNDetailsPanel(super.getConfiguration());
    }

    @Override
    public boolean hasGroupDetailsPanel() {
        return false;
    }

    @Override
    public NodeDashboardPanel createGroupDetailsPanel() {
        return null;
    }
}
