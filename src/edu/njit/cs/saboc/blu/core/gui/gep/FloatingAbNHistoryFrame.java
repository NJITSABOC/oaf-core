package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author Chris O
 */
public class FloatingAbNHistoryFrame extends JInternalFrame {
    
    private final JButton backBtn;
    private final JButton forwardBtn;
    
    private final JButton viewHistoryBtn;
    
    public FloatingAbNHistoryFrame() {
        
        super("", false, false, false, false);
        
        this.setSize(150, 60);

        JComponent titlePanel = (BasicInternalFrameTitlePane)((BasicInternalFrameUI)getUI()).getNorthPane();
        titlePanel.setPreferredSize(new Dimension(-1, 10));
        titlePanel.setBackground(new Color(100, 100, 255));
        
        this.setFrameIcon(null);
        
        backBtn = new JButton();
        backBtn.setIcon(ImageManager.getImageManager().getIcon("left-arrow.png"));
        backBtn.addActionListener((ae) -> {
            
        });

        forwardBtn = new JButton();
        forwardBtn.setIcon(ImageManager.getImageManager().getIcon("right-arrow.png"));
        forwardBtn.addActionListener((ae) -> {
          
        });
        
        viewHistoryBtn = new JButton("View History");
        
        JPanel contentPanel = new JPanel();
        contentPanel.add(backBtn);
        contentPanel.add(Box.createHorizontalStrut(8));
        contentPanel.add(forwardBtn);
        
        this.add(contentPanel);
        
        this.setVisible(true);
    }
}