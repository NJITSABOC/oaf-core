package edu.njit.cs.saboc.nat.generic.gui.layout;

import java.util.HashMap;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 */
public class NATPanelNeighborhood {

    private HashMap<NATAdjustableLayout.PanelSide, JSplitPane> paneMap;

    public NATPanelNeighborhood() {
        this.paneMap = new HashMap<>();
    }

    public void registerSplitPane(JSplitPane pane, NATAdjustableLayout.PanelSide side) {
        paneMap.remove(side);
        paneMap.put(side, pane);
    }

    public JSplitPane getSplitPaneOn(NATAdjustableLayout.PanelSide side) {
        return paneMap.get(side);
    }
}
