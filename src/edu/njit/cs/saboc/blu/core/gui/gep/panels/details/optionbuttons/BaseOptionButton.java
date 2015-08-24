package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Chris O
 */
public abstract class BaseOptionButton extends JButton {
    protected BaseOptionButton(String iconFileName, String description) {
        ImageIcon icon = IconManager.getIconManager().getIcon(iconFileName);

        this.setIcon(icon);
        this.setBackground(new Color(240, 240, 255));

        Dimension sizeDimension = new Dimension(50, 50);

        this.setMinimumSize(sizeDimension);
        this.setMaximumSize(sizeDimension);
        this.setPreferredSize(sizeDimension);
        
        this.setToolTipText(description);
    }
}
