package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.dialogs.LoadStatusDialog;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class CreateDisjointAbNButton<T extends Node> extends NodeOptionButton<T> {
    
    private final PartitionedAbNConfiguration config;
    private final DisplayAbNAction<DisjointAbstractionNetwork> displayAbNAction;
    
    public CreateDisjointAbNButton(
            PartitionedAbNConfiguration config,
            DisplayAbNAction<DisjointAbstractionNetwork> displayAbNAction) {
        
        super("BluDisjointAbN.png", 
                String.format("Display disjoint %s", 
                        config.getTextConfiguration().getAbNTypeName(false)));
        
        this.config = config;
        this.displayAbNAction = displayAbNAction;
        
        this.addActionListener((ActionEvent ae) -> {
            createAndDisplayDisjointAbNAction();
        });
    }
    
    public final void createAndDisplayDisjointAbNAction() {
        final PartitionedNode node = (PartitionedNode) super.getCurrentNode().get();

        Thread loadThread = new Thread(new Runnable() {

            private LoadStatusDialog loadStatusDialog = null;
            private boolean doLoad = true;

            public void run() {

                loadStatusDialog = LoadStatusDialog.display(null,
                        String.format("Creating %s Disjoint %s", 
                                node.getName(),
                                config.getTextConfiguration().getAbNTypeName(false)),
                        new LoadStatusDialog.LoadingDialogClosedListener() {

                            @Override
                            public void dialogClosed() {
                                doLoad = false;
                            }
                        });

                DisjointAbstractionNetwork disjointAbN = config.getDisjointAbstractionNetworkFor(node);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (doLoad) {
                            displayAbNAction.displayAbstractionNetwork(disjointAbN);

                            loadStatusDialog.setVisible(false);
                            loadStatusDialog.dispose();
                        }
                    }
                });
            }
        });

        loadThread.start();
    }

    @Override
    public void setEnabledFor(Node node) {
        PartitionedNode partitionedNode = (PartitionedNode)node;
        
        this.setEnabled(partitionedNode.hasOverlappingConcepts());
    }
}
