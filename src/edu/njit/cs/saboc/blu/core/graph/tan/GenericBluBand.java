package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericBand;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericContainerEntry;
import java.awt.Rectangle;

/**
 *
 * @author Chris O
 */
public class GenericBluBand<BAND_T extends GenericBand> extends GenericContainerEntry {
    public GenericBluBand(BAND_T band, BluGraph g, int aX, GraphLevel parent, Rectangle prefBounds) {
        super(band, g, aX, parent, prefBounds);
    }

    public BAND_T getBand() {
        return (BAND_T)container;
    }
}