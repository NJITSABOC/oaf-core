package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ChildNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ParentNodeTableModel;

/**
 *
 * @author Den
 */
public abstract class TargetAbNUIConfiguration extends AbNUIConfiguration<TargetGroup> {
    
    private final TargetAbNConfiguration config;
    
    public TargetAbNUIConfiguration(TargetAbNConfiguration config, 
            TargetAbNListenerConfiguration listenerConfig) {
        
        super(listenerConfig);
        
        this.config = config;
    }
    
    public TargetAbNListenerConfiguration getListenerConfiguration() {
        return (TargetAbNListenerConfiguration)super.getListenerConfiguration();
    }
    
    public TargetAbNConfiguration getConfiguration() {
        return config;
    }

    @Override
    public AbstractAbNDetailsPanel createAbNDetailsPanel() {
        return new AbstractAbNDetailsPanel(config);
    }

    @Override
    public boolean hasGroupDetailsPanel() {
        return false;
    }

    @Override
    public NodeDashboardPanel createGroupDetailsPanel() {
        return null;
    }
    
    @Override
    public OAFAbstractTableModel<ParentNodeDetails<TargetGroup>> getParentNodeTableModel() {
        return new ParentNodeTableModel(config);
    }

    @Override
    public OAFAbstractTableModel<TargetGroup> getChildNodeTableModel() {
        return new ChildNodeTableModel<>(config);
    }
    
}
