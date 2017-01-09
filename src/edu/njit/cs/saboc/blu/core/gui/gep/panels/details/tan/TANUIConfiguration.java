package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band.AggregateBandPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band.BandPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster.AggregateClusterPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster.ClusterPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;

/**
 *
 * @author Chris O
 */
public abstract class TANUIConfiguration extends PartitionedAbNUIConfiguration<Cluster, Band> {
    
    private final TANConfiguration config;
    
    public TANUIConfiguration(
            TANConfiguration config, 
            AbNDisplayManager displayManager,
            TANListenerConfiguration listenerConfig) {
        
        super(displayManager, listenerConfig);
        
        this.config = config;
    }
    
    @Override
    public TANListenerConfiguration getListenerConfiguration() {
        return (TANListenerConfiguration)super.getListenerConfiguration();
    }
    
    @Override
    public OAFAbstractTableModel<ParentNodeDetails<Cluster>> getParentNodeTableModel() {
        return new ParentClusterTableModel(config);
    }

    @Override
    public OAFAbstractTableModel<Cluster> getChildNodeTableModel() {
        return new ChildClusterTableModel(config);
    }

    @Override
    public boolean hasContainerDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel createContainerDetailsPanel() {
        if(config.getTribalAbstractionNetwork().isAggregated()) {
            return new AggregateBandPanel(config);
        } else {
            return new BandPanel(config);
        }
    }

    @Override
    public AbstractAbNDetailsPanel createAbNDetailsPanel() {
        return new TANDetailsPanel(config);
    }

    @Override
    public boolean hasGroupDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel createGroupDetailsPanel() {
        if(config.getTribalAbstractionNetwork().isAggregated()) {
            return new AggregateClusterPanel(config);
        } else {
            return new ClusterPanel(config);
        }
    }
}