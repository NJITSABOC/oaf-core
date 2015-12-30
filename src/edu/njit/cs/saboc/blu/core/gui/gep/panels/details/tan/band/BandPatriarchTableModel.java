package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractConceptTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;

/**
 *
 * @author Chris O
 */
public class BandPatriarchTableModel<CONCEPT_T> extends BLUAbstractConceptTableModel<CONCEPT_T> {

    private final BLUGenericTANConfiguration config;
    
    public BandPatriarchTableModel(BLUGenericTANConfiguration config) {
        super(new String[]{"Tribal Patriarch"});
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(CONCEPT_T patriarch) {

        return new Object[] {
            config.getTextConfiguration().getConceptName(patriarch)
        };
    }
}
