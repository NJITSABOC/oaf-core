package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNTextConfiguration;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class PartitionedAbNSearchButton extends AbNSearchButton {

    public PartitionedAbNSearchButton(JFrame parent,
            PartitionedAbNTextConfiguration textConfig) {
        
        super(parent, textConfig);
        
        String partitionedNodeTypeName = textConfig.getContainerTypeName(true);
        
        this.addSearchAction(new BluGraphSearchAction<PartitionedNode>(partitionedNodeTypeName) {
            
            public ArrayList<SearchButtonResult<PartitionedNode>> doSearch(String query) {
                
                PartitionedAbstractionNetwork abn = (PartitionedAbstractionNetwork)getConfiguration().getAbstractionNetwork();
                
                Set<PartitionedNode> partitionedNodes = abn.getBaseAbstractionNetwork().searchNodes(query);
                
                ArrayList<SearchButtonResult<PartitionedNode>> results = new ArrayList<>();

                partitionedNodes.stream().forEach((node) -> {
                    results.add(new SearchButtonResult<>(node.getName(), node));
                });
                
                results.sort( (a, b) -> {
                    return a.getResult().getName().compareTo(b.getResult().getName());
                });
                
                getConfiguration().getUIConfiguration().getDisplayPanel().highlightPartitionedNodes(partitionedNodes);

                return results;
            }
            
            public void resultSelected(SearchButtonResult<PartitionedNode> o) {
                PartitionedNode node = o.getResult();
                
                BluGraph graph = getConfiguration().getUIConfiguration().getDisplayPanel().getGraph();
                
                PartitionedNodeEntry entry = graph.getContainerEntries().get(node);
                getConfiguration().getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(entry);
            }
        });
    }
}
