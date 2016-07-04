package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band.BandPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster.ClusterPanel;

/**
 *
 * @author Chris O
 */
public abstract class TANUIConfiguration extends PartitionedAbNUIConfiguration {
    
    private final TANConfiguration config;
    
    public TANUIConfiguration(TANConfiguration config, 
            TANListenerConfiguration listenerConfig) {
        
        super(listenerConfig);
        
        this.config = config;
    }
    
    @Override
    public TANListenerConfiguration getListenerConfiguration() {
        return (TANListenerConfiguration)super.getListenerConfiguration();
    }
    
    @Override
    public OAFAbstractTableModel<ParentNodeDetails> getParentNodeTableModel() {
        return new ParentClusterTableModel(config);
    }

    @Override
    public OAFAbstractTableModel<Node> getChildNodeTableModel() {
        return new ChildClusterTableModel(config);
    }

    @Override
    public boolean hasContainerDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel createContainerDetailsPanel() {
        return new BandPanel(config);
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
        return new ClusterPanel(config);
    }
}