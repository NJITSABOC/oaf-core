package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;

/**
 *
 * @author Chris O
 */
public class ClusterPatriarchsTableModel<CONCEPT_T> extends OAFAbstractTableModel<CONCEPT_T> {

    private final BLUGenericTANConfiguration config;
    
    public ClusterPatriarchsTableModel(BLUGenericTANConfiguration config) {
        super(new String[]{"Tribe Patriarch Name"});
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(CONCEPT_T patriarch) {
        return new Object[] {
            config.getTextConfiguration().getConceptName(patriarch)
        };
    }
}
