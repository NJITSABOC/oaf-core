package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.listener.BLUPartitionedAbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;

/**
 *
 * @author Chris O
 */
public interface BLUGenericTANListenerConfiguration<
        TAN_T extends TribalAbstractionNetwork, 
        BAND_T extends Band,
        CLUSTER_T extends Cluster,
        CONCEPT_T> extends BLUPartitionedAbNListenerConfiguration<TAN_T, BAND_T, CLUSTER_T, CONCEPT_T> {

    public abstract EntitySelectionListener<CONCEPT_T> getClusterPatriarchSelectedListener();

    public abstract EntitySelectionListener<CONCEPT_T> getBandPatriarchSelectedListener();
}

