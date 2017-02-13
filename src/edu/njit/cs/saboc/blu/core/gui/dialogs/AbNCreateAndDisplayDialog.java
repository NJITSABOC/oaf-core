package edu.njit.cs.saboc.blu.core.gui.dialogs;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import javax.swing.SwingUtilities;

/**
 *
 * @author Kevyn
 * @param <T>
 */
public abstract class AbNCreateAndDisplayDialog<T extends AbstractionNetwork> {

    private final AbNDisplayManager displayManager;
    
    private final String dialogText;

    public AbNCreateAndDisplayDialog(String text, AbNDisplayManager displayManager) {
        this.displayManager = displayManager;

        this.dialogText = text;
    }
    
    public void createAbN() {
        
        Thread loadThread = new Thread(new Runnable() {
            
            private LoadStatusDialog loadStatusDialog = null;
            private boolean loadCancelled = false;

            @Override
            public void run() {

                loadStatusDialog = LoadStatusDialog.display(null, dialogText, () -> {
                    loadCancelled = true;
                });

                T abn = deriveAbN();

                SwingUtilities.invokeLater(() -> {
                    if (!loadCancelled) {
                        displayAbN(abn);

                        loadStatusDialog.setVisible(false);
                        loadStatusDialog.dispose();
                    }
                });
            }
        });

        loadThread.start();
    }
    
    public AbNDisplayManager getDisplayFrameListener() {
        return displayManager;
    }

    protected abstract void displayAbN(T abn);
    protected abstract T deriveAbN();
}
