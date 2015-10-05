package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.label;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public class DetailsPanelLabel extends JLabel {
    
    public DetailsPanelLabel(String label) {
        super(label);
    }
    
    public void setText(String label) {
        super.setText(label);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);

        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
        g2d.fillRect(getWidth() / 2, 0, getWidth() / 2, getHeight());

        g2d.setColor(Color.WHITE);

        final int BORDER_SIZE = 2;

        g2d.fillRoundRect(BORDER_SIZE, BORDER_SIZE, getWidth() - (2 * BORDER_SIZE), getHeight() - (2 * BORDER_SIZE), 16, 16);
        g2d.fillRect(getWidth() / 2, BORDER_SIZE, getWidth() / 2, getHeight() - (2 * BORDER_SIZE));

        g2d.setFont(getFont());
        g2d.setColor(Color.BLACK);

        g2d.drawString(getText(), 8, 28);

        g.drawImage(bi, 0, 0, null);
    }
}
