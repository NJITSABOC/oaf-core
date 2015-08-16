package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractTableModel;

/**
 *
 * @author Chris O
 */
public abstract class AbstractGroupList<GROUP_T extends GenericConceptGroup> extends AbstractEntityList<GROUP_T> {
    
    public AbstractGroupList(BLUAbstractTableModel<GROUP_T> tableModel) {
        super(tableModel);
    }
}
