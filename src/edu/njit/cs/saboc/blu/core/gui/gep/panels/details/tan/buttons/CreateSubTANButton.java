
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.buttons;

import edu.njit.cs.saboc.blu.core.abn.tan.SubTAN;
import edu.njit.cs.saboc.blu.core.gui.dialogs.LoadStatusDialog;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.NodeOptionButton;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public abstract class CreateSubTANButton extends NodeOptionButton {
    
    private final DisplayAbNAction displayTaxonomyListener;
    
    public CreateSubTANButton(
            String iconName,
            String toolTip,
            DisplayAbNAction displayTaxonomyListener) {
        
        super(iconName, toolTip);
        
        this.displayTaxonomyListener = displayTaxonomyListener;
        
        this.addActionListener((ae) -> {
            createAndDisplaySubTAN();
        });
    }
    
    public final void createAndDisplaySubTAN() {
        if (getCurrentNode().isPresent()) {

            Thread loadThread = new Thread(new Runnable() {

                private LoadStatusDialog loadStatusDialog = null;
                private boolean doLoad = true;

                public void run() {
                    
                    loadStatusDialog = LoadStatusDialog.display(null,
                            "Creating Sub TAN", 
                            ( ) -> {
                                doLoad = false;
                    });

                    SubTAN subTAN = createSubTAN();

                    SwingUtilities.invokeLater(() -> {
                        if (doLoad) {
                            displayTaxonomyListener.displayAbstractionNetwork(subTAN);

                            loadStatusDialog.setVisible(false);
                            loadStatusDialog.dispose();
                        }
                    });
                }
            });

            loadThread.start();
        }
    }
    
    public abstract SubTAN createSubTAN();
}