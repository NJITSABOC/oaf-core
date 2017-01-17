package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistoryList extends AbstractEntityList<AbNDerivationHistoryEntry> {
    
    public AbNDerivationHistoryList() {
        super(new AbNDerivationHistoryTableModel());
    }

    @Override
    protected String getBorderText(Optional<ArrayList<AbNDerivationHistoryEntry>> entities) {
        
        String base = "Derived Abstraction Networks";
        
        if(entities.isPresent()) {
            return String.format("%s (%d)", base, entities.get().size());
        } else {
            return base;
        }
    }
}
