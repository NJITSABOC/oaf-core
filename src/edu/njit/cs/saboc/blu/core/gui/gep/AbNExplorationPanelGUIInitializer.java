
package edu.njit.cs.saboc.blu.core.gui.gep;

/**
 *
 * @author Chris O
 */
public interface AbNExplorationPanelGUIInitializer {
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel);
    public void initializeAbNDDashboardPanel(AbNDashboardPanel displayPanel);
    
    public AbNInitialDisplayAction getInitialDisplayAction();
}
