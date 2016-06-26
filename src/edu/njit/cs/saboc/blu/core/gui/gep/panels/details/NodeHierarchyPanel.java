package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ChildNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ParentNodeTableModel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 */
public class NodeHierarchyPanel extends BaseNodeInformationPanel {

    private final JSplitPane splitPane;
    
    private final AbstractEntityList<ParentNodeDetails> parentNodeList;
    
    private final NodeList childNodeList;
        
    private final AbNConfiguration config;
    
    public NodeHierarchyPanel(AbNConfiguration config) {
        this(config, new ParentNodeTableModel(config), new ChildNodeTableModel(config));
    }
    
    public NodeHierarchyPanel(AbNConfiguration config, ParentNodeTableModel parentModel, ChildNodeTableModel childModel) {
        this.config = config;

        this.setLayout(new BorderLayout());
        
        parentNodeList = new AbstractEntityList<ParentNodeDetails>(parentModel) {
            public String getBorderText(Optional<ArrayList<ParentNodeDetails>> entries) {
                String baseStr = String.format("Root's Parent %s %s", 
                        config.getTextConfiguration().getConceptTypeName(true), 
                        config.getTextConfiguration().getNodeTypeName(true));
                
                if(entries.isPresent()) {
                    return String.format("%s (%d)", baseStr, entries.get().size());
                } else {
                    return baseStr;
                }
            }
        };
        
        childNodeList = new NodeList(childModel, config) {
            public String getBorderText(Optional<ArrayList<Node>> entries) {
                String baseStr = String.format("Child %s",
                        config.getTextConfiguration().getNodeTypeName(true));
                
                if(entries.isPresent()) {
                    return String.format("%s (%d)", baseStr, entries.get().size());
                } else {
                    return baseStr;
                }
            }
        };
        
        parentNodeList.addEntitySelectionListener(config.getUIConfiguration().getListenerConfiguration().getParentGroupListener());
        childNodeList.addEntitySelectionListener(config.getUIConfiguration().getListenerConfiguration().getChildGroupListener());
        
        splitPane = NodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        splitPane.setTopComponent(parentNodeList);
        splitPane.setBottomComponent(childNodeList);
        
        splitPane.setDividerLocation(250);

        this.add(splitPane, BorderLayout.CENTER);
    }


    public AbNConfiguration getConfiguration() {
        return config;
    }
    
    @Override
    public void setContents(Node node) {
        // TODO: Set contents for parent and child lists...
    }

    @Override
    public void clearContents() {
        parentNodeList.clearContents();
        childNodeList.clearContents();
    }   
}
