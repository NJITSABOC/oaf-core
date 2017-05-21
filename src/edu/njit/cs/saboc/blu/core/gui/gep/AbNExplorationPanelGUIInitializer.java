
package edu.njit.cs.saboc.blu.core.gui.gep;

/**
 *
 * @author Chris O
 */
public interface AbNExplorationPanelGUIInitializer {
    
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel, boolean startUp);
    public void initializeAbNDDashboardPanel(AbNDashboardPanel displayPanel);
    
    public void showAbNAlerts(AbNDisplayPanel displayPanel);
    
    public AbNInitialDisplayAction getInitialDisplayAction();
}
