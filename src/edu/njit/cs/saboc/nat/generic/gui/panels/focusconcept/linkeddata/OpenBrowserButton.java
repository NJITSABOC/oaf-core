package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.linkeddata;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Base class for buttons that open a web browser to do a web search 
 * on a specific website. Searches for the name of the current focus concept.
 * 
 * @author Chris O
 * @param <T>
 */
public class OpenBrowserButton<T extends Concept> extends JButton {
    
    public interface OpenBrowserButtonConfig {
        public ImageIcon getIcon();
        
        public String getToolTipText();
        
        public String getQueryURL();
    }
    
    private final NATBrowserPanel<T> browserPanel;
    
    public OpenBrowserButton(NATBrowserPanel<T> browserPanel, OpenBrowserButtonConfig config) {
        
        this.browserPanel = browserPanel;
        
        this.setIcon(config.getIcon());
        this.setToolTipText(config.getToolTipText());
        
        this.addActionListener( (ae) -> {
            doSearch(config.getQueryURL());
        });
    }
    
    public final void doSearch(String searchURL) {
        
        if(Desktop.isDesktopSupported()) {
            T focusConcept = browserPanel.getFocusConceptManager().getActiveFocusConcept();
            
            try {
                String query = String.format("%s%s", searchURL, URLEncoder.encode(focusConcept.getName(), "UTF-8"));

                Desktop.getDesktop().browse(new URI(query));
                
            } catch(URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
        
    }
}
