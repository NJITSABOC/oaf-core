package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.SimpleAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn.AggregateTargetGroupPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn.ChildTargetGroupTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn.ParentTargetGroupTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn.TargetAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn.TargetGroupPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;

/**
 *
 * @author Den
 */
public abstract class TargetAbNUIConfiguration extends AbNUIConfiguration<TargetGroup> {
    
    private final TargetAbNConfiguration config;
    
    public TargetAbNUIConfiguration(
            TargetAbNConfiguration config, 
            AbNDisplayManager displayManager,
            TargetAbNListenerConfiguration listenerConfig) {
        
        super(displayManager, listenerConfig);
        
        this.config = config;
    }
    
    @Override
    public TargetAbNListenerConfiguration getListenerConfiguration() {
        return (TargetAbNListenerConfiguration)super.getListenerConfiguration();
    }
    
    public TargetAbNConfiguration getConfiguration() {
        return config;
    }

    @Override
    public SimpleAbNDetailsPanel createAbNDetailsPanel() {
        return new TargetAbNDetailsPanel(config);
    }

    @Override
    public boolean hasGroupDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel createGroupDetailsPanel() {
        
        if(config.getTargetAbstractionNetwork().isAggregated()) {
            return new AggregateTargetGroupPanel(config);
        } else {
            return new TargetGroupPanel(config);
        }
    }
    
    @Override
    public OAFAbstractTableModel<ParentNodeDetails<TargetGroup>> getParentNodeTableModel() {
        return new ParentTargetGroupTableModel(config);
    }

    @Override
    public OAFAbstractTableModel<TargetGroup> getChildNodeTableModel() {
        return new ChildTargetGroupTableModel(config);
    }
}
