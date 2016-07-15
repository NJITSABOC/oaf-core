package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.awt.event.ActionEvent;

/**
 *
 * @author Den
 */
public abstract class ExportButton<T extends Node> extends NodeOptionButton<T> {
    public ExportButton(String toolTip) {
        super("BluExport.png", toolTip);
        
        this.addActionListener((ActionEvent ae) -> {
            exportAction();
        });
    }
    
    @Override
    public void setEnabledFor(T node) {
        this.setEnabled(true);
    }
    
    public abstract void exportAction();
}