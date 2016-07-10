package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.NodeOptionButton;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 *
 * @author Chris O
 */
public class NodeOptionsPanel extends BaseNodeInformationPanel {
    
    private final ArrayList<NodeOptionButton> nodeOptionButtons = new ArrayList<>();
    
    private Optional<Node> currentNode = Optional.empty();
    
    public NodeOptionsPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        this.add(Box.createHorizontalStrut(4));
        
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Options"));
    }
    
    public void addOptionButton(NodeOptionButton optionBtn) {
        nodeOptionButtons.add(optionBtn);
        
        this.add(optionBtn);
        this.add(Box.createHorizontalStrut(4));
    }
    
    public Optional<Node> getCurrentNode() {
        return currentNode;
    }

    @Override
    public void setContents(Node node) {
        currentNode = Optional.of(node);
        
        nodeOptionButtons.forEach( (btn) -> {
            btn.setCurrentNode(node);
        });
    }

    @Override
    public void clearContents() {
        currentNode = Optional.empty();
        
        nodeOptionButtons.forEach((btn) -> {
            btn.clearCurrentNode();
        });
    }
}
