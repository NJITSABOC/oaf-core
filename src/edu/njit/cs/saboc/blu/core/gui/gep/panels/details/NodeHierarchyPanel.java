package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ChildNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
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
        this(config, 
                config.getUIConfiguration().getParentNodeTableModel(), 
                config.getUIConfiguration().getChildNodeTableModel());
    }
    
    public NodeHierarchyPanel(AbNConfiguration config, 
            OAFAbstractTableModel<ParentNodeDetails> parentModel, 
            OAFAbstractTableModel<Node> childModel) {
        
        this.config = config;

        this.setLayout(new BorderLayout());
        
        parentNodeList = new AbstractEntityList<ParentNodeDetails>(parentModel) {
            public String getBorderText(Optional<ArrayList<ParentNodeDetails>> entries) {
                String baseStr = String.format("Root's %s's %s", 
                        config.getTextConfiguration().getParentConceptTypeName(false), 
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
        AbstractionNetwork abn = config.getAbstractionNetwork();
        
        ArrayList<ParentNodeDetails> parents = new ArrayList<>(abn.getParentNodeDetails(node));
        ArrayList<Node> children = new ArrayList<>(abn.getNodeHierarchy().getChildren(node));
        
        parents.sort( (a, b) -> {
            return a.getParentNode().getName().compareToIgnoreCase(b.getParentNode().getName());
        });
        
        children.sort( (a, b) -> {
            return a.getName().compareTo(b.getName());
        });
        
        parentNodeList.setContents(parents);
        childNodeList.setContents(children);
    }

    @Override
    public void clearContents() {
        parentNodeList.clearContents();
        childNodeList.clearContents();
    }   
}
