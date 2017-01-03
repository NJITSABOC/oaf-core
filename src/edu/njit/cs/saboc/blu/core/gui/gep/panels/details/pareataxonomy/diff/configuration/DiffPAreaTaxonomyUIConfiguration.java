package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeRootChangeExplanationModel.ChangeExplanationRowEntryFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.DiffAreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.DiffPAreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.DiffPAreaTaxonomyDetailsPanel;

/**
 *
 * @author Den
 */
public abstract class DiffPAreaTaxonomyUIConfiguration extends PAreaTaxonomyUIConfiguration {

    private final ChangeExplanationRowEntryFactory changeExplanationFactory;
    
    public DiffPAreaTaxonomyUIConfiguration(
            DiffPAreaTaxonomyConfiguration config, 
            DiffPAreaTaxonomyListenerConfiguration listenerConfig, 
            ChangeExplanationRowEntryFactory changeExplanationFactory) {
        
        super(config, listenerConfig);
        
        this.changeExplanationFactory = changeExplanationFactory;
    }
    
    public DiffPAreaTaxonomyListenerConfiguration getListenerConfiguration() {
        return (DiffPAreaTaxonomyListenerConfiguration)super.getListenerConfiguration();
    }
    
    public DiffPAreaTaxonomyConfiguration getConfiguration() {
        return (DiffPAreaTaxonomyConfiguration)super.getConfiguration();
    }
    
    public ChangeExplanationRowEntryFactory getChangeExplanationEntryFactory() {
        return changeExplanationFactory;
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
        return new DiffPAreaPanel(getConfiguration(), changeExplanationFactory);
    }
}
