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
public class NodeOptionsPanel<T extends Node> extends BaseNodeInformationPanel<T> {
    
    private final ArrayList<NodeOptionButton<T>> nodeOptionButtons = new ArrayList<>();
    
    private Optional<T> currentNode = Optional.empty();
    
    public NodeOptionsPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        this.add(Box.createHorizontalStrut(4));
        
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Options"));
    }
    
    public void addOptionButton(NodeOptionButton<T> optionBtn) {
        nodeOptionButtons.add(optionBtn);
        
        this.add(optionBtn);
        this.add(Box.createHorizontalStrut(4));
    }
    
    public Optional<T> getCurrentNode() {
        return currentNode;
    }

    @Override
    public void setContents(T node) {
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
