package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNTextConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Atsuki Imamura on 2/12/2017.
 */
public class HelpButton<T extends Node> extends NodeOptionButton<T> {

    public HelpButton(AbNConfiguration config){
        super("BluHelp.png", "Help");
        this.addActionListener((ActionEvent ae) -> {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    displayDetailsWindow(config);
                }
            });
        });
    }

    private void displayDetailsWindow(AbNConfiguration config) {
        String nodeTypeName, nodeHelpDescription;
        Node curNode = getCurrentNode().get();
        if(!(curNode instanceof SinglyRootedNode)){
            nodeTypeName = ((PartitionedAbNTextConfiguration) config.getTextConfiguration()).getContainerTypeName(false);
            nodeHelpDescription = ((PartitionedAbNTextConfiguration) config.getTextConfiguration()).getContainerHelpDescription((PartitionedNode) curNode);
        }
        else{
            nodeTypeName = config.getTextConfiguration().getNodeTypeName(false);
            nodeHelpDescription = config.getTextConfiguration().getNodeHelpDescription(curNode);
        }
        JDialog detailsDialog = new JDialog();
        detailsDialog.setTitle(String.format("(%s) Help / Description",nodeTypeName));
        detailsDialog.setModal(true);
        detailsDialog.setSize(700, 600);
        detailsDialog.setLocationRelativeTo(null);  //sets the location to the center

        JEditorPane nodeDetailsPane = new JEditorPane();
        nodeDetailsPane.setContentType("text/html");
        nodeDetailsPane.setEnabled(true);
        nodeDetailsPane.setEditable(false);
        nodeDetailsPane.setFont(nodeDetailsPane.getFont().deriveFont(Font.BOLD, 14));
        nodeDetailsPane.setText(nodeHelpDescription);

        detailsDialog.add(new JScrollPane(nodeDetailsPane));
        detailsDialog.setResizable(true);

        detailsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        detailsDialog.setVisible(true);
    }

    @Override
    public void setEnabledFor(T entity) {
        this.setEnabled(true);
    }
}
