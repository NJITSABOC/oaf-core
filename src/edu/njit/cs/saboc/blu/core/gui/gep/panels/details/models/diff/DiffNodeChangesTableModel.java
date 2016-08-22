package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.AbNChange;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class DiffNodeChangesTableModel extends OAFAbstractTableModel<AbNChange> {
    
    public DiffNodeChangesTableModel() {
        super(new String[] {
            "Change type",
            "Change description"
        });
    }

    @Override
    protected Object[] createRow(AbNChange item) {
        return new Object [] {
            item.getChangeName(),
            item.getChangeDescription()
        };
    }
}
