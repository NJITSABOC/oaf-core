package edu.njit.cs.saboc.blu.core.graph.targetabn;

import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public class TargetPartitionEntry extends GenericPartitionEntry {
    
    public TargetPartitionEntry(int width, int height, BluGraph graph, TargetContainerEntry parentBandEntry, Color c) {
        super(null, "", width, height, graph, parentBandEntry, c);
    }
    
    public TargetPartitionEntry(int width, int height, BluGraph graph, TargetContainerEntry parentBandEntry, Color c, JLabel label) {

        super(null, "", width, height, graph, parentBandEntry, c);
        
        this.remove(partitionLabel);
        
        this.partitionLabel = label;

        this.add(label);
    }
}