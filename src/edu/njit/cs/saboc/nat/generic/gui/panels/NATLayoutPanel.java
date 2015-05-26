package edu.njit.cs.saboc.nat.generic.gui.panels;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class NATLayoutPanel extends JPanel {

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1, 1);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(1, 1);
    }
}
