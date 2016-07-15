package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.GenericInternalGraphFrame;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class PartitionedAbNSearchButton extends AbNSearchButton {

    public PartitionedAbNSearchButton(JFrame parent, 
            GenericInternalGraphFrame igf,
            PartitionedAbNTextConfiguration config) {
        
        super(parent, igf, config);
        
        String partitionedNodeTypeName = config.getContainerTypeName(true);
        
        this.addSearchAction(new BluGraphSearchAction<PartitionedNode>(partitionedNodeTypeName, igf) {
            public ArrayList<SearchButtonResult<PartitionedNode>> doSearch(String query) {
                
                PartitionedAbstractionNetwork abn = (PartitionedAbstractionNetwork)getGraphFrame().getGraph().getAbstractionNetwork();
                
                Set<PartitionedNode> areas = abn.getBaseAbstractionNetwork().searchNodes(query);
                
                ArrayList<SearchButtonResult<PartitionedNode>> results = new ArrayList<>();

                areas.stream().forEach((node) -> {
                    results.add(new SearchButtonResult<>(node.getName(), node));
                });
                
                results.sort( (a, b) -> {
                    return a.getResult().getName().compareTo(b.getResult().getName());
                });

                return results;
            }
            
            public void resultSelected(SearchButtonResult<PartitionedNode> o) {
                PartitionedNode node = o.getResult();

                getGraphFrame().focusOnComponent(graph.getContainerEntries().get(node));
            }
        });
    }
}
