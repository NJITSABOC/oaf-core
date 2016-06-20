package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.ContainerConceptEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractAbNNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;

/**
 *
 * @author Chris O
 */
public class BandConceptTableModel<CONCEPT_T, CLUSTER_T extends Cluster, BAND_T extends Band>
            extends BLUAbstractAbNNodeTableModel<BAND_T, ContainerConceptEntry<CONCEPT_T, CLUSTER_T>> {

    private final BLUGenericTANConfiguration config;
    
    public BandConceptTableModel(BLUGenericTANConfiguration config) {
        super(new String[]{
                String.format("%s Name", config.getTextConfiguration().getConceptTypeName(false)), 
                String.format("%s ID", config.getTextConfiguration().getConceptTypeName(false)), 
                String.format("Overlapping %s", config.getTextConfiguration().getConceptTypeName(false))
            });
        
        this.config = config;
    }

    @Override
    protected Object[] createRow(ContainerConceptEntry<CONCEPT_T, CLUSTER_T> item) {
        String overlappingStr;
        
        if(item.getGroups().size() == 1) {
            overlappingStr = "No";
        } else {
            overlappingStr = String.format("Yes (%d)", item.getGroups().size());
        }
        
        return new Object[] {
            config.getTextConfiguration().getConceptName(item.getConcept()),
            config.getTextConfiguration().getConceptUniqueIdentifier(item.getConcept()),
            overlappingStr
        };
    }    
}
