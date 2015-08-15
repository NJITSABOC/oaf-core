
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 */
public abstract class AbstractNodePanel<NODE_T, CONCEPT_T> extends AbNNodeInformationPanel<NODE_T> {
    
    private final JLabel groupNameLabel;
    
    private final ArrayList<AbNNodeInformationPanel<NODE_T>> groupDetailsPanels = new ArrayList<>();
    
    private final JTabbedPane tabbedPane;
    
    private AbstractNodeDetailsPanel<NODE_T, CONCEPT_T> groupDetailsPanel;

    protected AbstractNodePanel() {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        
        groupNameLabel = new JLabel(" ") {
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
                
        groupNameLabel.setFont(groupNameLabel.getFont().deriveFont(Font.BOLD, 20));
        groupNameLabel.setPreferredSize(new Dimension(100, 40));
        
        tabbedPane = new JTabbedPane();

        this.add(groupNameLabel, BorderLayout.NORTH);
        this.add(tabbedPane, BorderLayout.CENTER);
    }
    
    public void setContents(NODE_T node) {
        groupNameLabel.setText(getNodeTitle(node));
        
        groupDetailsPanels.forEach((AbNNodeInformationPanel<NODE_T> gdp) -> {
            gdp.setContents(node);
        });
    }
    
    public void clearContents() {
        groupNameLabel.setText("");
        
        groupDetailsPanels.forEach((AbNNodeInformationPanel<NODE_T> gdp) -> {
            gdp.clearContents();
        });
    }
    
    public void initUI() {
        this.groupDetailsPanel = createNodeDetailsPanel();

        addGroupDetailsTab(groupDetailsPanel, String.format("%s Details", getNodeType()));
        
        groupDetailsPanel.initUI();
    }
    
    public void addGroupDetailsTab(AbNNodeInformationPanel<NODE_T> panel, String tabName) {
        tabbedPane.addTab(tabName, panel);
        groupDetailsPanels.add(panel);
    }
    
    protected abstract String getNodeTitle(NODE_T node);
    
    protected abstract String getNodeType();
    
    protected abstract AbstractNodeDetailsPanel<NODE_T, CONCEPT_T> createNodeDetailsPanel();
}
