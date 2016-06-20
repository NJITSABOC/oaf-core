package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeInformationPanel;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class PopoutNodeDetailsButton extends BaseOptionButton {
    
    public interface NodeDetailsPanelGenerator {
        public NodeInformationPanel generatePanel();
    }
    
    private final NodeDetailsPanelGenerator generatorAction;
    
    public PopoutNodeDetailsButton(String nodeType, NodeDetailsPanelGenerator generatorAction) {
        super("BluExpandWindow.png", String.format("Display %s details in new window", nodeType));
        
        this.addActionListener((ActionEvent ae) -> {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    displayDetailsWindow();
                }
            });
        });
        
        this.generatorAction = generatorAction;
    }
    
    private void displayDetailsWindow() {
        JDialog detailsDialog = new JDialog();
        detailsDialog.setSize(700, 600);
        
        detailsDialog.add(generatorAction.generatePanel());
        detailsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        detailsDialog.setVisible(true);
    }
}
