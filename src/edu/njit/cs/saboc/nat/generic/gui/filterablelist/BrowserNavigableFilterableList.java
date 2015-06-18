package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.nat.generic.gui.listeners.FilterableListSelectionAction;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;

/**
 * A generic filterable list that allows a user to navigate within the NAT via 
 * double clicking on a entry in the list
 * @author Chris O
 */
public class BrowserNavigableFilterableList<T> extends FilterableList {

    public BrowserNavigableFilterableList(final FilterableListSelectionAction<T> selectionAction) {

        super.addListMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && list.getModel() == conceptModel) {
                    
                    Filterable<T> entry = (Filterable<T>) conceptModel.getFilterableAtModelIndex(
                            ((JList) evt.getComponent()).getSelectedIndex());
                    
                    selectionAction.handleEntrySelection(entry.getObject());
                }
            }
        });
    }

}
