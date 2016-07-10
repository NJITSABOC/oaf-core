package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.AreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.ChildPAreaTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.PAreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.PAreaTaxonomyDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.ParentPAreaTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.PropertyTableModel;

/**
 *
 * @author Den
 */
public abstract class PAreaTaxonomyUIConfiguration extends PartitionedAbNUIConfiguration {
    
    private final PAreaTaxonomyConfiguration config;
    
    public PAreaTaxonomyUIConfiguration(PAreaTaxonomyConfiguration config, 
            PAreaTaxonomyListenerConfiguration listenerConfig) {
        
        super(listenerConfig);
        
        this.config = config;
    }
    
    public PAreaTaxonomyListenerConfiguration getListenerConfiguration() {
        return (PAreaTaxonomyListenerConfiguration)super.getListenerConfiguration();
    }
    
    public PAreaTaxonomyConfiguration getConfiguration() {
        return config;
    }

    @Override
    public boolean hasContainerDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel createContainerDetailsPanel() {
        return new AreaPanel(config);
    }

    @Override
    public AbstractAbNDetailsPanel createAbNDetailsPanel() {
        return new PAreaTaxonomyDetailsPanel(config);
    }

    @Override
    public boolean hasGroupDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel createGroupDetailsPanel() {
        return new PAreaPanel(config);
    }
    
    public OAFAbstractTableModel<InheritableProperty> getPropertyTableModel(boolean forArea) {
        return new PropertyTableModel(config, forArea);
    }

    @Override
    public OAFAbstractTableModel<ParentNodeDetails> getParentNodeTableModel() {
        return new ParentPAreaTableModel(config);
    }

    @Override
    public OAFAbstractTableModel<Node> getChildNodeTableModel() {
        return new ChildPAreaTableModel(config);
    }
    
}
