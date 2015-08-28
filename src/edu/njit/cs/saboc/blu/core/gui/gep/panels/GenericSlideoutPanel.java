package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.gui.gep.utils.GraphSelectionStateMonitor;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class GenericSlideoutPanel extends JPanel  {

    // State variables
    private boolean hidden = false;
    private final int COLLAPSED_SIZE = 16;
    
    private final Dimension CONTENT_PANE_OFFSET = new Dimension(16, 8);
    
    private final GraphSelectionStateMonitor selectionStateMonitor;
    
    // GUI Elements
    private final JPanel contentPane;
    
    private final JButton collapseBtn;
    
    private final JButton resizeBtn;
    
    private final Point initialRelativePosition;
    
    private Dimension gepSize;
    
    private final ImageIcon openPanelIcon;
    
    private final ImageIcon closePanelIcon;

    public GenericSlideoutPanel(GraphSelectionStateMonitor selectionStateMonitor, Dimension startingGEPSize, Point initialRelativePosition, Dimension size) {
        this.setLayout(null);
        
        this.gepSize = startingGEPSize;
        
        this.selectionStateMonitor = selectionStateMonitor;
        
        this.initialRelativePosition = initialRelativePosition;
        
        this.setBackground(new Color(0,0,0,0));
        
        this.contentPane = new JPanel(new BorderLayout());
        this.contentPane.setOpaque(false);
        
        this.collapseBtn = new JButton();
        this.collapseBtn.setBackground(new Color(120, 120, 250));
        this.collapseBtn.addActionListener((ActionEvent ae) -> {
            if(hidden) {
                if(selectionStateMonitor.getSelectedGroupEntry() != null || selectionStateMonitor.getSelectedPartitionEntry() != null) {    
                    doOpen();
                }
            } else {
                doClose();
            }
        });
        
        this.resizeBtn = new JButton(IconManager.getIconManager().getIcon("BluResizePanel.png"));
        this.resizeBtn.setBackground(new Color(120, 120, 250));
        this.resizeBtn.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                 if(!hidden && selectionStateMonitor.getSelectedGroupEntry() == null && selectionStateMonitor.getSelectedPartitionEntry() == null) {    
                    return;
                }
                
                int x = e.getX();
                
                int xPos = getBounds().x + x;
                
                if(getBounds().width - x > size.width && getX() > 100) {                    
                    setBounds(xPos, initialRelativePosition.y, getBounds().width - x, size.height);
                } else {
                    setBounds(gepSize.width + initialRelativePosition.x + CONTENT_PANE_OFFSET.width, initialRelativePosition.y, size.width, size.height);
                }
                
                contentPane.revalidate();
            }
        });
        
        this.resizeBtn.setToolTipText("Resize this panel");
        
        openPanelIcon = IconManager.getIconManager().getIcon("BluOpenPanel.png");
        closePanelIcon = IconManager.getIconManager().getIcon("BluClosePanel.png");
        
        collapseBtn.setIcon(openPanelIcon);

        this.add(contentPane);
        this.add(collapseBtn);
        this.add(resizeBtn);
        
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                e.consume();
            }
        });
        
        this.setBounds(gepSize.width + initialRelativePosition.x, initialRelativePosition.y, size.width, size.height);
    }

    public void setContent(JComponent content) {
        contentPane.removeAll();
        
        contentPane.add(content, BorderLayout.CENTER);

        contentPane.validate();
    }
    
    public void setGEPSet(Dimension gepSize) {
        this.gepSize = gepSize;
    }
    
    public void doOpen() {
        collapseBtn.setIcon(closePanelIcon);
        
        this.hidden = false;
        this.setBounds(gepSize.width - this.getWidth() + COLLAPSED_SIZE, this.getY(), this.getWidth(), this.getHeight());
    }
    
    public void doClose() {
        collapseBtn.setIcon(openPanelIcon);
        
        this.hidden = true;
        this.setBounds(gepSize.width - COLLAPSED_SIZE, this.getY(), this.getWidth(), this.getHeight());
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
                width - CONTENT_PANE_OFFSET.width - 8, 
                height - (2 * CONTENT_PANE_OFFSET.height));
        
        this.collapseBtn.setBounds(2, height / 2 - 64, 16, 128);
        
        this.resizeBtn.setBounds(2, height - 128, 16, 48);
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
