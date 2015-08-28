package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import java.awt.event.ActionEvent;

/**
 *
 * @author Den
 */
public abstract class ExportButton extends BaseOptionButton {
    public ExportButton(String toolTip) {
        super("BluExport.png", toolTip);
        
        this.addActionListener((ActionEvent ae) -> {
            exportAction();
        });
    }
    
    public abstract void exportAction();
}