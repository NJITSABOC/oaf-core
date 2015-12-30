package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericBandPartition;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public class GenericBluBandPartition<BANDNODE_T extends GenericBluBand> extends GenericPartitionEntry {

    public GenericBluBandPartition(GenericBandPartition partition, String partitionName,
            int width, int height, BluGraph g, BANDNODE_T p, Color c, boolean treatAsOverlapSet) {

        super(partition, partitionName, width, height, g, p, c, treatAsOverlapSet);
    }
    
    public GenericBluBandPartition(GenericBandPartition partition, String partitionName,
            int width, int height, BluGraph g, BANDNODE_T p, Color c, boolean treatAsOverlapSet, JLabel label) {

        this(partition, partitionName, width, height, g, p, c, treatAsOverlapSet);
        
        this.remove(partitionLabel);
        
        this.partitionLabel = label;
        
        this.add(label);
    }

    public GenericBandPartition getOverlapPartition() {
        return (GenericBandPartition) partition;
    }
}