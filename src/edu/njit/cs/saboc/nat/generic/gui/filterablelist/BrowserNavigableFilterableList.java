package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.gui.listeners.FilterableListSelectionAction;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;

/**
 * A generic filterable list that allows a user to navigate within the NAT via 
 * double clicking on a entry in the list
 * 
 * @author Chris O
 */
public class BrowserNavigableFilterableList<T> extends FilterableList {

    public BrowserNavigableFilterableList(
            GenericNATBrowser mainPanel, 
            FilterableListSelectionAction selectionAction) {

        super.addListMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (!mainPanel.getOptions().navigationLocked() && 
                        selectionAction != null && evt.getClickCount() == 2 && 
                        list.getModel() == entryModel) {
                    
                    Filterable<T> entry = (Filterable<T>) entryModel.getFilterableAtModelIndex(
                            ((JList) evt.getComponent()).getSelectedIndex());
                    
                    selectionAction.handleEntrySelection(entry.getObject());
                }
            }
        });
    }
}
