package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.DiffAreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.DiffPAreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.DiffPAreaTaxonomyDetailsPanel;

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
        return true;
    }

    @Override
    public NodeDashboardPanel createContainerDetailsPanel() {
        return new DiffAreaPanel(getConfiguration());
    }

    @Override
    public AbstractAbNDetailsPanel createAbNDetailsPanel() {
        return new DiffPAreaTaxonomyDetailsPanel(getConfiguration());
    }

    @Override
    public boolean hasGroupDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel createGroupDetailsPanel() {
        return new DiffPAreaPanel(getConfiguration());
    }
}
