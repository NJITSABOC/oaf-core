package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class RelationshipPanel extends AbstractEntityList<InheritableProperty>  {
    public RelationshipPanel(OAFAbstractTableModel<InheritableProperty> tableModel) {
        super(tableModel);
    }

    @Override
    protected String getBorderText(Optional<ArrayList<InheritableProperty>> entities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
