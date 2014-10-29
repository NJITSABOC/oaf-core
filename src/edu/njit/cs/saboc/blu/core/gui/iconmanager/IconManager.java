package edu.njit.cs.saboc.blu.core.gui.iconmanager;

import javax.swing.ImageIcon;

/**
 *
 * @author Den
 */
public class IconManager {
    private static IconManager iconManager = null;
    
    public static IconManager getIconManager() {
        if(iconManager == null) {
            iconManager = new IconManager();
        }
        
        return iconManager;
    }
    
    public ImageIcon getIcon(String iconFileName) {
        return new ImageIcon(IconManager.class.getResource(String.format("/edu/njit/cs/saboc/blu/core/images/%s", iconFileName)));
    }
}
