package edu.njit.cs.saboc.blu.core.gui.gep.panels;

/**
 *
 * @author Chris
 */
public abstract class GroupOptionsPanelConfiguration {
    private final GroupOptionsPanelActionListener [] actions;
    
    public GroupOptionsPanelConfiguration() {
        this. actions = new GroupOptionsPanelActionListener[5];
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
