
package edu.njit.cs.saboc.blu.core.gui.gep.utils;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericContainerPartition;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;


/**
 *
 * @author Chris
 */
public interface GEPActionListener {
    public void groupSelected(GenericConceptGroup group, AbstractionNetwork abn);
    public void containerPartitionSelected(GenericContainerPartition partition, boolean treatAsContainer, AbstractionNetwork abn);
}
