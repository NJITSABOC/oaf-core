package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeRootChangeExplanationModel.ChangeExplanationRowEntryFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.DiffAreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.DiffPAreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.DiffPAreaTaxonomyDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;

/**
 *
 * @author Den
 */
public abstract class DiffPAreaTaxonomyUIConfiguration extends PAreaTaxonomyUIConfiguration {

    private final ChangeExplanationRowEntryFactory changeExplanationFactory;
    
    public DiffPAreaTaxonomyUIConfiguration(
            DiffPAreaTaxonomyConfiguration config, 
            AbNDisplayManager displayManager,
            DiffPAreaTaxonomyListenerConfiguration listenerConfig, 
            ChangeExplanationRowEntryFactory changeExplanationFactory) {
        
        super(config, displayManager, listenerConfig);
        
        this.changeExplanationFactory = changeExplanationFactory;
    }
    
    @Override
    public DiffPAreaTaxonomyListenerConfiguration getListenerConfiguration() {
        return (DiffPAreaTaxonomyListenerConfiguration)super.getListenerConfiguration();
    }
    
    @Override
    public DiffPAreaTaxonomyConfiguration getConfiguration() {
        return (DiffPAreaTaxonomyConfiguration)super.getConfiguration();
    }
    
    public ChangeExplanationRowEntryFactory getChangeExplanationEntryFactory() {
        return changeExplanationFactory;
    }

    @Override
    public boolean hasPartitionedNodeDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel createPartitionedNodeDetailsPanel() {
        return new DiffAreaPanel(getConfiguration());
    }

    @Override
    public AbNDetailsPanel createAbNDetailsPanel() {
        return new DiffPAreaTaxonomyDetailsPanel(getConfiguration());
    }

    @Override
    public boolean hasNodeDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel createNodeDetailsPanel() {
        return new DiffPAreaPanel(getConfiguration(), changeExplanationFactory);
    }
}
