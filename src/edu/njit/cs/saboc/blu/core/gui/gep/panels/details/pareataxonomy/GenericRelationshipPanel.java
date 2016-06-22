package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public abstract class GenericRelationshipPanel<T> extends AbstractEntityList<T>  {

    public GenericRelationshipPanel(OAFAbstractTableModel<T> tableModel) {
        super(tableModel);
    }
}
