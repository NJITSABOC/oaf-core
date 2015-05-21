package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.gui.dialogs.PrintSelectDialog;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The upper left panel of the NAT.  Displays the NJIT log as well as the
 * sources, print, help, and about buttons.
 */
public class LogoPanel extends BaseNavPanel {
    private JLabel njitLogoLabel;
    private JLabel printLabel;

    public LogoPanel(GenericNATBrowser mainPanel, ConceptBrowserDataSource dataSource) {
        super(mainPanel, dataSource);
        
        setLayout(new BorderLayout(5, 5));

        JPanel centerPanel = new JPanel(new BorderLayout(0, 5));

        
        njitLogoLabel = new JLabel(IconManager.getIconManager().getIcon("combinednew.png"), 
                JLabel.CENTER);
        
        centerPanel.add(njitLogoLabel, BorderLayout.CENTER);

        JPanel eastPanel = new JPanel(new BorderLayout(5, 5));
        
        eastPanel.add(new JLabel("                 "), BorderLayout.SOUTH);
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(1, 1, 0, 5));

        printLabel = new JLabel(IconManager.getIconManager().getIcon("printerS.png"));
        optionsPanel.add(printLabel);
        printLabel.setToolTipText("<html><b>Print Options</b></html>");

        eastPanel.add(optionsPanel, BorderLayout.EAST);
        
        optionsPanel.setPreferredSize(new Dimension(48,0));

        printLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                imagelabelMouseClicked(evt);
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                imagelabelMouseEntered(evt);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                imagelabelMouseExited(evt);
            }
        });

        add(eastPanel, BorderLayout.EAST);

        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void imagelabelMouseEntered(MouseEvent evt) {
        if(evt.getSource() == printLabel) {
            printLabel.setIcon(IconManager.getIconManager().getIcon("printer.png"));
            printLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    private void imagelabelMouseExited(MouseEvent evt) {
        if(evt.getSource() == printLabel) {
            printLabel.setIcon(IconManager.getIconManager().getIcon("printerS.png"));
        }
    }

    private void imagelabelMouseClicked(MouseEvent evt) {
        if(evt.getSource() == printLabel) {
            PrintSelectDialog psd = new PrintSelectDialog(mainPanel);
        }
    }
}
