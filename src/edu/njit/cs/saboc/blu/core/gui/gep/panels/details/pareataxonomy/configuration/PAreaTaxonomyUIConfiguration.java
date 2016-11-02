package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area.AggregateAreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea.AggregatePAreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area.AreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea.ChildPAreaTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea.PAreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea.PAreaTaxonomyDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea.ParentPAreaTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.PropertyTableModel;

/**
 *
 * @author Den
 */
public abstract class PAreaTaxonomyUIConfiguration extends PartitionedAbNUIConfiguration<PArea, Area> {
    
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
        if(config.getPAreaTaxonomy().isAggregated()) {
            return new AggregateAreaPanel(config);
        } else {
            return new AreaPanel(config);
        }
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
        if(config.getPAreaTaxonomy().isAggregated()) {
            return new AggregatePAreaPanel(config);
        } else {
            return new PAreaPanel(config);
        }
    }
    
    public OAFAbstractTableModel<InheritableProperty> getPropertyTableModel(boolean forArea) {
        return new PropertyTableModel(config, forArea);
    }

    @Override
    public OAFAbstractTableModel<ParentNodeDetails<PArea>> getParentNodeTableModel() {
        return new ParentPAreaTableModel(config);
    }

    @Override
    public OAFAbstractTableModel<PArea> getChildNodeTableModel() {
        return new ChildPAreaTableModel(config);
    }
    
}
