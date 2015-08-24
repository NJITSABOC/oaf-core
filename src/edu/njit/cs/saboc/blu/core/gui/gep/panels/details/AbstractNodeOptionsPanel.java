package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.BaseOptionButton;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 *
 * @author Chris O
 */
public abstract class AbstractNodeOptionsPanel<NODE_T> extends AbNNodeInformationPanel<NODE_T> {
    
    public AbstractNodeOptionsPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        this.add(Box.createHorizontalStrut(4));
        
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Options"));
    }
    
    protected void addOptionButton(BaseOptionButton optionBtn) {
        this.add(optionBtn);
        this.add(Box.createHorizontalStrut(4));
    }
    
    public abstract void enableOptionsForGroup(NODE_T group);
}
