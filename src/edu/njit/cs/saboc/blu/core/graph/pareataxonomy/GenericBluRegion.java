package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericRegion;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public class GenericBluRegion<REGION_T extends GenericRegion, AREAENTRY_T extends GenericBluArea> extends GenericPartitionEntry {
    
    public GenericBluRegion(REGION_T region, String regionName,
            int width, int height, BluGraph g, AREAENTRY_T area, Color c, boolean treatAsArea) {

        super(region, regionName, width, height, g, area, c, treatAsArea);
    }
    
    public GenericBluRegion(REGION_T region, String regionName,
            int width, int height, BluGraph g, AREAENTRY_T p, Color c, boolean treatAsArea, JLabel label) {

        super(region, regionName, width, height, g, p, c, treatAsArea);
        
        this.remove(partitionLabel);
        
        this.partitionLabel = label;
        
        
        this.add(label);
    }
    
    public REGION_T getRegion() {
        return (REGION_T)partition;
    }
}
