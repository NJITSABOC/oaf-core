package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class NATLayout<T> {
    public abstract JPanel doLayout(GenericNATBrowser<T> mainPanel);
}
