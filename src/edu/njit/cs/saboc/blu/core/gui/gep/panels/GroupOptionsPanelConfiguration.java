package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import java.util.Optional;
import javax.swing.JComponent;

/**
 *
 * @author Chris
 */
public abstract class GroupOptionsPanelConfiguration {
    private final GroupOptionsPanelActionListener [] actions;
    
    private Optional<JComponent> slideoutComp = Optional.empty();
    
    public GroupOptionsPanelConfiguration() {
        this.actions = new GroupOptionsPanelActionListener[6];
    }
    
    public GroupOptionsPanelConfiguration(JComponent slideoutComp) {
        this();
        
        this.slideoutComp = Optional.ofNullable(slideoutComp);
    }
    
    public Optional<JComponent> getNavigatePanel() {
        return slideoutComp;
    }
    
    public boolean isButtonEnabled(int index) {
        if(index >= actions.length) {
            return false;
        }
        
        return !(actions[index] == null);
    }
    
    protected void enableButtonWithAction(int index, GroupOptionsPanelActionListener actionListener) {
        if(index >= actions.length) {
            return;
        }
        
        actions[index] = actionListener;
    }
    
    public GroupOptionsPanelActionListener getAction(int index) {
        return actions[index];
    }
}
