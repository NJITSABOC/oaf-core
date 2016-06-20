package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractAbNNodeEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.ContainerConceptEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class BandConceptList<CONCEPT_T, CLUSTER_T extends Cluster, BAND_T extends Band> 
        extends AbstractAbNNodeEntityList<BAND_T, ContainerConceptEntry<CONCEPT_T, CLUSTER_T>> {
    
    private final BLUGenericTANConfiguration config;
    
    public BandConceptList(BLUGenericTANConfiguration config) {
        super(new BandConceptTableModel(config));
        
        this.config = config;
    }

    @Override
    protected String getBorderText(Optional<ArrayList<ContainerConceptEntry<CONCEPT_T, CLUSTER_T>>> entities) {
        
        if(entities.isPresent()) {
            int overlapping = 0;
            
            ArrayList<ContainerConceptEntry<CONCEPT_T, CLUSTER_T>> classes = entities.get();
            
            for(ContainerConceptEntry<CONCEPT_T, CLUSTER_T> entry : classes) {
                if(entry.getGroups().size() > 1) {
                    overlapping++;
                }
            }

            return String.format("%s (%d total, %d overlapping)", config.getTextConfiguration().getConceptTypeName(true), 
                    entities.get().size(), overlapping);
        } else {
            
            if(config == null) {
                return "Concepts";
            } else {
                return config.getTextConfiguration().getConceptTypeName(true);
            }
        }
    }
}
