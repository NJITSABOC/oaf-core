package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.dialogs.LoadStatusDialog;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public abstract class CreateTANButton extends NodeOptionButton {
    
    private final DisplayAbNListener<ClusterTribalAbstractionNetwork> displayTAN;
    
    public CreateTANButton(String forNodeType, DisplayAbNListener<ClusterTribalAbstractionNetwork> displayTAN) {
        
        super("BluTAN.png", String.format("Derive Tribal Abstraction Network (TAN) from %s", forNodeType));
        
        this.addActionListener((ActionEvent ae) -> {
            createAndDisplayTAN();
        });
        
        this.displayTAN = displayTAN;
    }
    
    public void createAndDisplayTAN() {
        if (getCurrentNode().isPresent()) {

            Thread loadThread = new Thread(new Runnable() {

                private LoadStatusDialog loadStatusDialog = null;
                private boolean doLoad = true;

                public void run() {
                    
                    loadStatusDialog = LoadStatusDialog.display(null,
                            "Creating Tribal Abstraction Network (TAN)", 
                            ( ) -> {
                                doLoad = false;
                    });

                    ClusterTribalAbstractionNetwork tan = deriveTAN();

                    SwingUtilities.invokeLater(() -> {
                        if (doLoad) {
                            displayTAN.displayAbstractionNetwork(tan);

                            loadStatusDialog.setVisible(false);
                            loadStatusDialog.dispose();
                        }
                    });
                }
            });

            loadThread.start();
        }
    }
    
    public abstract ClusterTribalAbstractionNetwork deriveTAN();
    
    
}

