package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 */
public class PartitionedNodeSubNodeList extends BaseNodeInformationPanel {
    
    private final JSplitPane splitPane;

    private final NodeList nodeList;
    
    private final ConceptList conceptList;
    
    private final BLUPartitionedConfiguration configuration;
    
    public PartitionedNodeSubNodeList(BLUPartitionedConfiguration configuration) {
        
        this.configuration = configuration;
        
        this.setLayout(new BorderLayout());

        this.splitPane = NodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        this.nodeList = new NodeList(configuration);
        
        this.nodeList.addEntitySelectionListener(new EntitySelectionAdapter<Node>() {
            public void entityClicked(Node node) {
                ArrayList<Concept> sortedConcepts = new ArrayList<>(node.getConcepts());
                sortedConcepts.sort((a,b) -> a.getName().compareTo(b.getName()));
                
                conceptList.setContents(sortedConcepts);
            }
            
            public void noEntitySelected() {
                conceptList.clearContents();
            }
        });
        
        this.conceptList = new ConceptList(configuration);
        
        splitPane.setTopComponent(nodeList);
        splitPane.setBottomComponent(conceptList);

        this.add(splitPane, BorderLayout.CENTER);
    }
    
    protected NodeList getGroupList() {
        return nodeList;
    }

    @Override
    public void setContents(Node container) {
        
        PartitionedNode partitionedNode = (PartitionedNode)container;
        
        splitPane.setDividerLocation(300);
        
        ArrayList<Node> sortedNodes = new ArrayList<>(partitionedNode.getInternalNodes());
        
        sortedNodes.sort((a,b) -> a.getName().compareTo(b.getName()));
        
        nodeList.setContents(sortedNodes);
        
        conceptList.clearContents();
    }

    @Override
    public void clearContents() {
        nodeList.clearContents();
        conceptList.clearContents();
    }
}
