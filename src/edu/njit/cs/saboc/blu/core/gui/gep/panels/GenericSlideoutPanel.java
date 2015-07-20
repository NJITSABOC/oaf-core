package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class GenericSlideoutPanel extends JPanel {
    
    // State variables
    private boolean hidden = false;
    private final int COLLAPSED_SIZE = 16;
    
    private final Dimension CONTENT_PANE_OFFSET = new Dimension(16, 8);
    
    // GUI Elements
    private final JPanel contentPane;
    
    private final JButton collapseBtn;

    public GenericSlideoutPanel(Point initialPosition, Dimension size) {
        this.setLayout(null);
        
        this.setBackground(new Color(0,0,0,0));
        
        this.contentPane = new JPanel(new BorderLayout());
        this.contentPane.setOpaque(false);
        
        this.collapseBtn = new JButton();
        this.collapseBtn.setBackground(new Color(50, 50, 120));
        this.collapseBtn.addActionListener((ActionEvent ae) -> {
            if(hidden) {
                doOpen();
            } else {
                doClose();
            }
        });

        this.add(contentPane);
        this.add(collapseBtn);
        
        this.setBounds(initialPosition.x, initialPosition.y, size.width, size.height);
    }

    public void setContent(JComponent content) {
        contentPane.add(content, BorderLayout.CENTER);
    }
    
    private void doOpen() {
        this.hidden = false;
        this.setBounds(this.getX() - this.getWidth() + COLLAPSED_SIZE, this.getY(), this.getWidth(), this.getHeight());
    }
    
    private void doClose() {
        this.hidden = true;
        this.setBounds(this.getX() + this.getWidth() - COLLAPSED_SIZE, this.getY(), this.getWidth(), this.getHeight());
    }
    
    
    public boolean isHidden() {
        return hidden;
    }
    
    public int getCollapsedSize() {
        return COLLAPSED_SIZE;
    }
    
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        
        this.contentPane.setBounds(CONTENT_PANE_OFFSET.width, 
                CONTENT_PANE_OFFSET.height, 
                width - CONTENT_PANE_OFFSET.width, 
                height - (2 * CONTENT_PANE_OFFSET.height));
        
        this.collapseBtn.setBounds(2, height / 2 - 64, 12, 128);
    }
    
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
        g2d.setColor(new Color(50, 50, 120));
        
        g2d.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 32, 32);
        g2d.fillRect(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
        
        final int VERTICAL_OFFSET = 2;
        final int HORIZONTAL_OFFSET = 2;
        
        g2d.setColor(new Color(180, 180, 255));
        
        g2d.fillRoundRect(VERTICAL_OFFSET, HORIZONTAL_OFFSET, this.getWidth(), this.getHeight() - (2 * VERTICAL_OFFSET), 32, 32);
        g2d.fillRect(this.getWidth() / 2, VERTICAL_OFFSET, this.getWidth() / 2, this.getHeight() - (2 * VERTICAL_OFFSET));
        
        g.drawImage(bi, 0, 0, null);
    }
    
}
