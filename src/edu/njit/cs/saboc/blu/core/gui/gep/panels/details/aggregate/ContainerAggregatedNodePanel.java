package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.BaseNodeInformationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.AggregatedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class ContainerAggregatedNodePanel extends BaseNodeInformationPanel {
    
    private final AbstractEntityList<AggregatedNodeEntry> aggregatedNodeList;
    
    private final PartitionedAbNConfiguration config;
    
    public ContainerAggregatedNodePanel(PartitionedAbNConfiguration config, OAFAbstractTableModel<AggregatedNodeEntry> model) {
        
        this.config = config;

        this.aggregatedNodeList = new AbstractEntityList<AggregatedNodeEntry>(model) {
            
            @Override
            protected String getBorderText(Optional<ArrayList<AggregatedNodeEntry>> entities) {
                String base = String.format("Aggregated %s", config.getTextConfiguration().getNodeTypeName(true));

                if (entities.isPresent()) {
                    return String.format("%s (%d)", base, entities.get().size());
                } else {
                    return String.format("%s (0)", base);
                }
            }
        };
    }
    
    public ContainerAggregatedNodePanel(PartitionedAbNConfiguration config) {
        this(config, new ContainerAggregatedGroupTableModel(config));
    }

    @Override
    public void setContents(Node node) {
        PartitionedNode partitionedNode = (PartitionedNode)node;
        
        Set<SinglyRootedNode> internalNodes = partitionedNode.getInternalNodes();
        
        Map<SinglyRootedNode, Set<AggregateNode<SinglyRootedNode>>> aggregatedNodes = new HashMap<>();
        
        internalNodes.forEach( (internalNode) -> {
            AggregateNode<SinglyRootedNode> aggregateNode = (AggregateNode)internalNode;
            
            aggregateNode.getAggregatedNodes().forEach( (aggregatedNode) -> {
                if(!aggregatedNodes.containsKey(aggregatedNode)) {
                    aggregatedNodes.put(aggregatedNode, new HashSet<>());
                }
                
                aggregatedNodes.get(aggregatedNode).add(aggregateNode);
            });
        });
        
        ArrayList<AggregatedNodeEntry> entries = new ArrayList<>();
        
        aggregatedNodes.forEach( (aggregatedNode, aggregatedInto) -> {
            entries.add(new AggregatedNodeEntry(aggregatedNode, aggregatedInto));
        });
        
        entries.sort( (a, b) -> a.getAggregatedNode().getName().compareToIgnoreCase(b.getAggregatedNode().getName()));
        
        aggregatedNodeList.setContents(entries);
    }

    @Override
    public void clearContents() {
        aggregatedNodeList.clearContents();
    }
}
