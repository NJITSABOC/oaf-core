
package edu.njit.cs.saboc.blu.core.gui.workspace;

import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Chris O
 */
public class AbNWorkspaceButton extends JButton {
    
    public AbNWorkspaceButton(MultiAbNGraphFrame graphFrame) {
        
        Image scaledIcon = ImageManager.getImageManager().
                getIcon("BLUWorkspace.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        
        super.setIcon(new ImageIcon(scaledIcon));
        
        super.setToolTipText("Ontology Abstraction Network Workspaces");
        
        
        
    }
    
}
