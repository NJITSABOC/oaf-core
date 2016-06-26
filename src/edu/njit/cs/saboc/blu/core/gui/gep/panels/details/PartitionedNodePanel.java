package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.OverlappingConceptDetails;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class PartitionedNodePanel extends NodeDashboardPanel {

    private final PartitionedNodeSubNodeList groupListPanel;
    
    private final DisjointAbNMetricsPanel disjointMetricsPanel;
    
    private final int disjointMetricsTabIndex;

    public PartitionedNodePanel(
            NodeDetailsPanel containerDetailsPanel, 
            PartitionedAbNConfiguration configuration) {
        
        super(containerDetailsPanel, configuration);
        
        this.groupListPanel = new PartitionedNodeSubNodeList(configuration);
        
        String subnodeListTabTitle = String.format("%s's %s", 
                configuration.getTextConfiguration().getContainerTypeName(false), 
                configuration.getTextConfiguration().getNodeTypeName(true));
        
        super.addInformationTab(groupListPanel, subnodeListTabTitle);
        
        this.disjointMetricsPanel = new DisjointAbNMetricsPanel(configuration);
        
        String overlappingTabTitle = String.format("Overlapping %s Metrics", 
                configuration.getTextConfiguration().getNodeTypeName(false));
                
        this.disjointMetricsTabIndex = super.addInformationTab(disjointMetricsPanel, overlappingTabTitle);
    }

    @Override
    public void clearContents() {
        super.clearContents();
        
        groupListPanel.clearContents();
        disjointMetricsPanel.clearContents();
        
        this.enableInformationTabAt(disjointMetricsTabIndex, true);
    }

    @Override
    public void setContents(Node node) {
        super.setContents(node);
        
        PartitionedNode partitionedNode = (PartitionedNode)node;

        groupListPanel.setContents(node);
        disjointMetricsPanel.setContents(node);
        
        Set<OverlappingConceptDetails> overlaps = partitionedNode.getOverlappingConceptDetails();
        
        if (!overlaps.isEmpty()) {
            disjointMetricsPanel.setContents(node);

            this.enableInformationTabAt(disjointMetricsTabIndex, true);
        } else {
            this.enableInformationTabAt(disjointMetricsTabIndex, false);
        }
    }
}
