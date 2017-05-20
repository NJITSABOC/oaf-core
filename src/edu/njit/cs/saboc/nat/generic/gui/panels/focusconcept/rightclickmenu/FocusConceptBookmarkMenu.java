package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.history.BookmarkManager;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class FocusConceptBookmarkMenu<T extends Concept> extends JMenuItem {

    public FocusConceptBookmarkMenu(NATBrowserPanel<T> browserPanel) {
        super("Bookmarks");
        
        
    }
}
