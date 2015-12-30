package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericBand;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericCluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.BLUPartitionedAbNUIConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class BLUGenericTANUIConfiguration<
        TAN_T extends TribalAbstractionNetwork, 
        BAND_T extends GenericBand,
        CLUSTER_T extends GenericCluster,
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