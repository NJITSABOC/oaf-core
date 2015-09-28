package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUAbNConfiguration;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class GenericAbNSummaryPanel<ABN_T extends AbstractionNetwork> extends JPanel {
    
    private final JLabel abnNameLabel;
    
    private final JEditorPane abnDetailsPane;
    
    public GenericAbNSummaryPanel(BLUAbNConfiguration config) {
        
        abnNameLabel = new JLabel(" ") {
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
                g2d.fillRect(getWidth() / 2 , BORDER_SIZE, getWidth() / 2, getHeight() - (2 * BORDER_SIZE));
                
                g2d.setFont(getFont());
                g2d.setColor(Color.BLACK);
                
                g2d.drawString(getText(), 8, 28);
                
                g.drawImage(bi, 0, 0, null);
            }
        };
        
        abnNameLabel.setText(config.getAbNName());
        abnNameLabel.setFont(abnNameLabel.getFont().deriveFont(Font.BOLD, 20));
        abnNameLabel.setPreferredSize(new Dimension(100, 40));
        
        abnDetailsPane = new JEditorPane();
        abnDetailsPane.setContentType("text/html");
        abnDetailsPane.setEnabled(true);
        abnDetailsPane.setEditable(false);
        abnDetailsPane.setFont(abnDetailsPane.getFont().deriveFont(Font.BOLD, 14));
        abnDetailsPane.setText(config.getAbNSummary());
               
        this.setLayout(new BorderLayout());
        
        this.add(abnNameLabel, BorderLayout.NORTH);
        this.add(abnDetailsPane, BorderLayout.CENTER);
    }
}
