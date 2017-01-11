package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.NodeOptionButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.options.EntityOptionsPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.Box;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class NodeOptionsPanel<T extends Node> extends BaseNodeInformationPanel<T> {
    
    private final EntityOptionsPanel<T> basePanel;

    public NodeOptionsPanel() {
        this.setLayout(new BorderLayout());
        
        this.basePanel = new EntityOptionsPanel<>();
        
        this.add(basePanel, BorderLayout.CENTER);
    }
    
    public void addOptionButton(NodeOptionButton<T> optionBtn) {
        basePanel.add(optionBtn);
    }
    
    public Optional<T> getCurrentNode() {
        return basePanel.getCurrentEntity();
    }

    @Override
    public void setContents(T node) {
        basePanel.setContents(node);
    }

    @Override
    public void clearContents() {
        basePanel.clearContents();
    }
}
