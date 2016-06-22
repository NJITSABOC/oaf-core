package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractTableModel;

/**
 *
 * @author Chris O
 */
public abstract class AbstractGroupList extends AbstractEntityList<Node> {
    public AbstractGroupList(BLUAbstractTableModel<Node> tableModel) {
        super(tableModel);
    }
}
