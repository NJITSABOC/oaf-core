
package edu.njit.cs.saboc.blu.core.gui.gep.utils;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;


/**
 *
 * @author Chris
 */
public interface GEPActionListener {
    public void groupSelected(SinglyRootedNode group, AbstractionNetwork abn);
    public void containerPartitionSelected(PartitionedNode partition, boolean treatAsContainer, AbstractionNetwork abn);
}
