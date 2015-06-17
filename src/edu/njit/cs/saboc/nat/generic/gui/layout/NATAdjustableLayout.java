package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.nat.generic.gui.panels.NATLayoutPanel;
import java.util.HashMap;

/**
 *
 * @author Chris
 */
public abstract class NATAdjustableLayout<T> extends NATLayout<T> {
    
    protected int myWidth;
    protected int myHeight;
    
    protected HashMap<NATLayoutPanel, NATPanelNeighborhood> panelNeighborhoods;

    public NATAdjustableLayout() {
        panelNeighborhoods = new HashMap<>();
    }

    public enum PanelSide {
        NORTH, SOUTH, EAST, WEST
    };
    
    public abstract void resetSplitPanePosition();
    
    public void handleResize(int newWidth, int newHeight) {
        this.myWidth = newWidth;
        this.myHeight = newHeight;
        
        this.resetSplitPanePosition();
    }
}
