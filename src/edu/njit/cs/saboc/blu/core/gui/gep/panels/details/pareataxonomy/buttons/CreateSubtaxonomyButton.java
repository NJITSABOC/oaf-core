package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.dialogs.LoadStatusDialog;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.NodeOptionButton;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public abstract class CreateSubtaxonomyButton extends NodeOptionButton {
    
    private final DisplayAbNAction<PAreaTaxonomy> displayTaxonomyListener;
    
    public CreateSubtaxonomyButton(
            String iconName,
            String toolTip,
            DisplayAbNAction<PAreaTaxonomy> displayTaxonomyListener) {
        
        super(iconName, toolTip);
        
        this.displayTaxonomyListener = displayTaxonomyListener;
        
        this.addActionListener((ae) -> {
            createAndDisplaySubtaxonomy();
        });
    }
    
    public final void createAndDisplaySubtaxonomy() {
        if (getCurrentNode().isPresent()) {

            Thread loadThread = new Thread(new Runnable() {

                private LoadStatusDialog loadStatusDialog = null;
                private boolean doLoad = true;

                public void run() {
                    
                    loadStatusDialog = LoadStatusDialog.display(null,
                            "Creating Subtaxonomy", 
                            ( ) -> {
                                doLoad = false;
                    });

                    PAreaTaxonomy subtaxonomy = createSubtaxonomy();

                    SwingUtilities.invokeLater(() -> {
                        if (doLoad) {
                            displayTaxonomyListener.displayAbstractionNetwork(subtaxonomy);

                            loadStatusDialog.setVisible(false);
                            loadStatusDialog.dispose();
                        }
                    });
                }
            });

            loadThread.start();
        }
    }
    
    public abstract PAreaSubtaxonomy createSubtaxonomy();
}
