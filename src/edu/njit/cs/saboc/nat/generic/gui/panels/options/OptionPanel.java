package edu.njit.cs.saboc.nat.generic.gui.panels.options;

import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.gui.panels.NATLayoutPanel;

/**
 *
 * @author Chris O
 */
public abstract class OptionPanel<T> extends NATLayoutPanel<T> {
    protected OptionPanel(GenericNATBrowser<T> mainPanel) {
        super(mainPanel);
    }
}
