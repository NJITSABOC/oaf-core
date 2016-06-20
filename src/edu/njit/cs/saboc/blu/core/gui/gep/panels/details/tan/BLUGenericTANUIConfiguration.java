package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.BLUPartitionedAbNUIConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class BLUGenericTANUIConfiguration<
        TAN_T extends TribalAbstractionNetwork, 
        BAND_T extends Band,
        CLUSTER_T extends Cluster,
        CONCEPT_T,
        T extends BLUGenericTANListenerConfiguration<TAN_T, BAND_T, CLUSTER_T, CONCEPT_T>> extends 
            BLUPartitionedAbNUIConfiguration<TAN_T, BAND_T, CLUSTER_T, CONCEPT_T, BLUGenericTANConfiguration, T> {
    
    public BLUGenericTANUIConfiguration(T listenerConfig) {
        super(listenerConfig);
    }
    
    public T getListenerConfiguration() {
        return super.getListenerConfiguration();
    }
}