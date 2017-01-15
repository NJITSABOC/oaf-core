package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.taskbarpanels;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportAbNButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportPartitionedAbNButton;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.AbNSearchButton;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.PartitionedAbNSearchButton;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.TaskBarPanel;

/**
 *
 * @author Chris O
 */
public class PartitionedAbNTaskBarPanel extends TaskBarPanel {

    public PartitionedAbNTaskBarPanel(
            MultiAbNGraphFrame graphFrame,
            PartitionedAbNConfiguration config) {

        super(graphFrame, config);
    }

    @Override
    protected AbNSearchButton getAbNSearchButton(AbNConfiguration config) {
        
        PartitionedAbNConfiguration partitionedConfig = (PartitionedAbNConfiguration)config;
        
        PartitionedAbNSearchButton btn = new PartitionedAbNSearchButton(
                super.getGraphFrame().getParentFrame(), partitionedConfig.getTextConfiguration());
        
        btn.initialize(config);
        
        return btn;
    }

    @Override
    protected ExportAbNButton getExportButton(AbNConfiguration config) {
        ExportPartitionedAbNButton btn = new ExportPartitionedAbNButton();
        
        btn.initialize(config);
        
        return btn;
    }

    @Override
    protected String getAbNMetricsLabel(AbNConfiguration config) {
        PartitionedAbNConfiguration partitionedConfig = (PartitionedAbNConfiguration)config;
        
        int partitionNodeCount = partitionedConfig.getAbstractionNetwork().getBaseAbstractionNetwork().getNodeCount();
        int singlyRootedNodeCount = partitionedConfig.getAbstractionNetwork().getNodeCount();
        int conceptCount = partitionedConfig.getAbstractionNetwork().getSourceHierarchy().size();
        
        return String.format("%s: %d | %s: %d | %s: %d",
                partitionedConfig.getTextConfiguration().getContainerTypeName(true),
                partitionNodeCount, 
                partitionedConfig.getTextConfiguration().getNodeTypeName(true),
                singlyRootedNodeCount, 
                partitionedConfig.getTextConfiguration().getConceptTypeName(true),
                conceptCount);
    }
}
