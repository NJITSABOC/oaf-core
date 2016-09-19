package edu.njit.cs.saboc.blu.core.gui.iconmanager;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 *
 * @author Den
 */
public class IconManager {
    private static IconManager iconManager = null;
    
    private final Map<String, ImageIcon> icons = new HashMap<>();
    
    public static IconManager getIconManager() {
        if(iconManager == null) {
            iconManager = new IconManager();
        }
        
        return iconManager;
    }
    
    public ImageIcon getIcon(String iconFileName) {
        
        if(!icons.containsKey(iconFileName)) {
            URL fileName = IconManager.class.getResource(String.format("/edu/njit/cs/saboc/blu/core/images/%s", iconFileName));
                        
            icons.put(iconFileName, new ImageIcon(fileName));
        }
        
        return icons.get(iconFileName);
    }
}
