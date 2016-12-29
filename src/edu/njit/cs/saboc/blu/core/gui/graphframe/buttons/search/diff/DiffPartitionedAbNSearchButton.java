package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.diff;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.PartitionedAbNSearchButton;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.SearchButtonResult;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class DiffPartitionedAbNSearchButton extends PartitionedAbNSearchButton {
    public DiffPartitionedAbNSearchButton(
            JFrame parentFrame, 
            PartitionedAbNTextConfiguration textConfig) {
        
        super(parentFrame, textConfig);
        
        super.getSearchResultList().setDefaultTableRenderer(
                SearchButtonResult.class, 
                new DiffSearchResultCellRenderer(super.getSearchResultList().getEntityTable()));
    }
}
