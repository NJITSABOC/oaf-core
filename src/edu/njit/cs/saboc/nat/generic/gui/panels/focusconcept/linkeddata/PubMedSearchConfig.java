package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.linkeddata;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.linkeddata.OpenBrowserButton.OpenBrowserButtonConfig;
import javax.swing.ImageIcon;

/**
 * OpenBrowserButton configuration for searching on PubMed
 * 
 * @author Chris O
 */
public class PubMedSearchConfig implements OpenBrowserButtonConfig {

    @Override
    public ImageIcon getIcon() {
        return ImageManager.getImageManager().getIcon("PubMedIcon.png");
    }

    @Override
    public String getToolTipText() {
        return "Search PubMed";
    }

    @Override
    public String getQueryURL() {
        return "https://www.ncbi.nlm.nih.gov/pubmed?term=";
    }
}
