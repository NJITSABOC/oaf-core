package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.nat.generic.FocusConcept;
import edu.njit.cs.saboc.nat.generic.Options;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;

/**
 *
 * @author Chris O
 */
public class BrowserNavigableFilterableList extends FilterableList {

    public BrowserNavigableFilterableList(final FocusConcept focusConcept, final Options options) {

        super.addListMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && list.getModel() == conceptModel) {
                    NavigableEntry entry = (NavigableEntry) conceptModel.getFilterableAtModelIndex(
                            ((JList) evt.getComponent()).getSelectedIndex());

                    focusConcept.navigate(entry.getNavigableConcept());
                }
            }
        });
    }

}
