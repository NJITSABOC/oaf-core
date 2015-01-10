package edu.njit.cs.saboc.blu.core.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author Chris
 */
public class AbNLoadStatusDialog extends JDialog {
    
    public static AbNLoadStatusDialog display(final JFrame parentFrame) {
        return new AbNLoadStatusDialog(parentFrame);
    }
    
    private JProgressBar loadProgressBar;
    
    private AbNLoadStatusDialog(JFrame parentFrame) {
        super(parentFrame, false);
        
        this.setTitle("Please wait...");
        
        loadProgressBar = new JProgressBar();
        loadProgressBar.setStringPainted(true);
        loadProgressBar.setString("Please wait...");
        loadProgressBar.setIndeterminate(true);
        
        this.setSize(256, 128);
        
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.add(new JLabel("Creating Abstraction Network"), BorderLayout.NORTH);
        
        dialogPanel.add(loadProgressBar, BorderLayout.CENTER);
        
        this.add(dialogPanel);

        this.setLocationRelativeTo(parentFrame);
        this.setResizable(false);
         
        this.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                
            }
        });

        this.setVisible(true);
    }
}
