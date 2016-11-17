package edu.njit.cs.saboc.blu.core.gui.frame;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/***
 * A dialog which displays information about the developers of BLUSNO/BLUOWL.
 * @author Chris
 */
public class AboutUsDialog extends JDialog {

    public AboutUsDialog(JFrame parentFrame) {
        super(parentFrame, true);
        setTitle("About Us");


        JPanel aboutPanel = new JPanel();
        JPanel upperPanel = new JPanel();
        JLabel logo1 = new JLabel(IconManager.getIconManager().getIcon("snowflake-th.png"), JLabel.CENTER);

        upperPanel.add(logo1);

        JPanel centerPane = new JPanel();

        try {
            URL about = AboutUsDialog.class.getResource("aboutBLUSNO.html");
            JEditorPane aboutBLUSNOPane = new JEditorPane(about);
            aboutBLUSNOPane.setEditable(false);
            centerPane.add("About BLUSNO", aboutBLUSNOPane);
        }
        catch(Exception ble) {
            ble.printStackTrace();
        }

        aboutPanel.setLayout(new BorderLayout());

        upperPanel.setBackground(Color.WHITE);
        aboutPanel.setBackground(Color.WHITE);

        aboutPanel.add(upperPanel, BorderLayout.NORTH);
        aboutPanel.add(centerPane, BorderLayout.CENTER);

        getContentPane().add(aboutPanel, BorderLayout.CENTER);
        pack();
        setMinimumSize(getPreferredSize());
        this.setSize(768, 512);
        setLocationRelativeTo(parentFrame);
        setResizable(false);
        setVisible(true);
    }
}
