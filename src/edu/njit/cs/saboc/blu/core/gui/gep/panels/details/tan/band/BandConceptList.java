package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.PartitionedNodeConceptEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class BandConceptList<CONCEPT_T, CLUSTER_T extends Cluster, BAND_T extends Band> 
        extends NodeEntityList<BAND_T, PartitionedNodeConceptEntry<CONCEPT_T, CLUSTER_T>> {
    
    private final BLUGenericTANConfiguration config;
    
    public BandConceptList(BLUGenericTANConfiguration config) {
        super(new BandConceptTableModel(config));
        
        this.config = config;
    }

    @Override
    protected String getBorderText(Optional<ArrayList<PartitionedNodeConceptEntry<CONCEPT_T, CLUSTER_T>>> entities) {
        
        if(entities.isPresent()) {
            int overlapping = 0;
            
            ArrayList<PartitionedNodeConceptEntry<CONCEPT_T, CLUSTER_T>> classes = entities.get();
            
            for(PartitionedNodeConceptEntry<CONCEPT_T, CLUSTER_T> entry : classes) {
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
