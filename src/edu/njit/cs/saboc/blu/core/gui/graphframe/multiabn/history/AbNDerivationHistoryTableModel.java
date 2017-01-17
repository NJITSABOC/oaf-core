package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistoryTableModel extends OAFAbstractTableModel<AbNDerivationHistoryEntry> {
    
    public AbNDerivationHistoryTableModel() {
        super(new String [] {
            "Abstraction Network Type",
            "Description"
        });
    }

    @Override
    protected Object[] createRow(AbNDerivationHistoryEntry item) {
        return new Object[] {
            item.getAbNTypeName(),
            item.getDerivation().getDescription()
        };
    }
}
