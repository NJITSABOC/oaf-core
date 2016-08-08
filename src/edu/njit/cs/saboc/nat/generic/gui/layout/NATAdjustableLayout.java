package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.nat.generic.gui.panels.NATLayoutPanel;
import java.util.HashMap;

/**
 *
 * @author Chris
 */
public abstract class NATAdjustableLayout {
    
    private int myWidth;
    private int myHeight;
    
    protected HashMap<NATLayoutPanel, NATPanelNeighborhood> panelNeighborhoods;

    public NATAdjustableLayout() {
        panelNeighborhoods = new HashMap<>();
    }

    public enum PanelSide {
        NORTH, SOUTH, EAST, WEST
    };
    
    public int getLayoutWidth() {
        return myWidth;
    }
    
    public int getLayoutHeight() {
        return myHeight;
    }
    
    public void handleResize(int newWidth, int newHeight) {
        this.myWidth = newWidth;
        this.myHeight = newHeight;
        
        this.resetSplitPanePosition();
    }
    
    public abstract void resetSplitPanePosition();
}
