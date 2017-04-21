package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class AbNHistoryNavigationPanel extends JPanel {

    private final JButton backBtn;
    private final JButton forwardBtn;

    private final JButton viewHistoryBtn;

    public AbNHistoryNavigationPanel() {
        backBtn = new JButton();
        backBtn.setIcon(ImageManager.getImageManager().getIcon("left-arrow.png"));
        backBtn.addActionListener((ae) -> {

        });
        
        backBtn.setPreferredSize(new Dimension(60, 24));

        forwardBtn = new JButton();
        forwardBtn.setIcon(ImageManager.getImageManager().getIcon("right-arrow.png"));
        forwardBtn.addActionListener((ae) -> {

        });
        
        forwardBtn.setPreferredSize(new Dimension(60, 24));

        viewHistoryBtn = new JButton("View History");
        
        this.add(backBtn);
        this.add(Box.createHorizontalStrut(4));
        this.add(viewHistoryBtn);
        this.add(Box.createHorizontalStrut(4));
        this.add(forwardBtn);
    }
}
