package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.linkeddata;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.linkeddata.OpenBrowserButton.OpenBrowserButtonConfig;
import javax.swing.ImageIcon;

/**
 * OpenBrowserButton configuration for searching on Google
 * 
 * @author Chris O
 */
public class GoogleSearchConfig implements OpenBrowserButtonConfig {

    @Override
    public ImageIcon getIcon() {
        return ImageManager.getImageManager().getIcon("GoogleIcon.png");
    }

    @Override
    public String getToolTipText() {
        return "Google Search";
    }

    @Override
    public String getQueryURL() {
        return "https://www.google.com/search?q=";
    }
}
