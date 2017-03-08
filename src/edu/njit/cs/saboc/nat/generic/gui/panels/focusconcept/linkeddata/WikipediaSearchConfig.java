package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.linkeddata;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.linkeddata.OpenBrowserButton.OpenBrowserButtonConfig;
import javax.swing.ImageIcon;

/**
 * OpenBrowserButton configuration for searching on Wikipedia
 * 
 * @author Chris O
 */
public class WikipediaSearchConfig implements OpenBrowserButtonConfig {

    @Override
    public ImageIcon getIcon() {
        return ImageManager.getImageManager().getIcon("WikipediaIcon.png");
    }

    @Override
    public String getToolTipText() {
        return "Search Wikipedia";
    }

    @Override
    public String getQueryURL() {
        return "https://en.wikipedia.org/w/index.php?search=";
    }
}
