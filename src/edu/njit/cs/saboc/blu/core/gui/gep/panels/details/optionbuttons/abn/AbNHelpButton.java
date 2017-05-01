package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNTextConfiguration;
import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class AbNHelpButton<T extends AbstractionNetwork> extends AbNOptionsButton<T> {

    public AbNHelpButton(AbNConfiguration config){
        super("BluHelp.png", "Help");
        
        this.addActionListener((ae) -> {
            SwingUtilities.invokeLater(() -> {
                displayDetailsWindow(config);
            });
        });
    }

    protected void displayDetailsWindow(AbNConfiguration config) {
       
        JDialog detailsDialog = new JDialog();
        detailsDialog.setTitle(String.format("(%s) Help / Description", config.getTextConfiguration().getAbNTypeName(false)));
        detailsDialog.setModal(true);
        detailsDialog.setSize(400, 400);
        detailsDialog.setLocationRelativeTo(null);  //sets the location to the center

        JEditorPane abnDetailsPanel = new JEditorPane();
        abnDetailsPanel.setContentType("text/html");
        abnDetailsPanel.setEnabled(true);
        abnDetailsPanel.setEditable(false);
        abnDetailsPanel.setFont(abnDetailsPanel.getFont().deriveFont(Font.BOLD, 14));
        abnDetailsPanel.setText(config.getTextConfiguration().getAbNHelpDescription());

        detailsDialog.add(new JScrollPane(abnDetailsPanel));
        detailsDialog.setResizable(true);

        detailsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        detailsDialog.setVisible(true);
    }

    @Override
    public void setEnabledFor(T entity) {
        this.setEnabled(true);
    }
}
