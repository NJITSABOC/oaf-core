package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class BandTribesDetailsPanel<CONCEPT_T> extends AbstractEntityList<CONCEPT_T> {

    public BandTribesDetailsPanel(BLUGenericTANConfiguration config) {
        super(new BandPatriarchTableModel(config));
    }

    @Override
    protected String getBorderText(Optional<ArrayList<CONCEPT_T>> entities) {
        if(entities.isPresent()) {
            return String.format("Tribes (%d)", entities.get().size());
        } else {
            return "Tribes";
        }
    }
}
