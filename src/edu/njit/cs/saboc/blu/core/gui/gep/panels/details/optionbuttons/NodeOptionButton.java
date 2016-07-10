package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Chris O
 */
public abstract class NodeOptionButton extends JButton {
    
    private Optional<Node> currentNode = Optional.empty();
    
    public NodeOptionButton(String iconFileName, String description) {
        
        ImageIcon icon = IconManager.getIconManager().getIcon(iconFileName);

        this.setIcon(icon);
        this.setBackground(new Color(240, 240, 255));

        Dimension sizeDimension = new Dimension(50, 50);

        this.setMinimumSize(sizeDimension);
        this.setMaximumSize(sizeDimension);
        this.setPreferredSize(sizeDimension);
        
        this.setToolTipText(description);
    }
    
    public void setCurrentNode(Node node) {
        this.currentNode = Optional.of(node);
        
        setEnabledFor(node);
    }
    
    public Optional<Node> getCurrentNode() {
        return currentNode;
    }

    public void clearCurrentNode() {
        currentNode = Optional.empty();
        
        this.setEnabled(false);
    }
    
    public abstract void setEnabledFor(Node node);
}
