
package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.MinimapPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.NavigationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.ViewportNavigationListener;

/**
 *
 * @author Chris O
 */
public class BaseAbNExplorationPanelInitializer implements AbNExplorationPanelGUIInitializer {
    public BaseAbNExplorationPanelInitializer() {
        
    }

    @Override
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel) {
        NavigationPanel navigationPanel = new NavigationPanel(displayPanel);
        navigationPanel.addNavigationPanelListener(new ViewportNavigationListener(displayPanel));
        
        displayPanel.addZoomFactorChangedListener( (zoomFactor) -> {
            navigationPanel.setZoomLevel(zoomFactor);
        });
        
        displayPanel.addWidget(navigationPanel);
        
        
        MinimapPanel minimapPanel = new MinimapPanel(displayPanel);
        
        displayPanel.addWidget(minimapPanel);
    }

    @Override
    public void initializeAbNDDashboardPanel(AbNDashboardPanel displayPanel) {
        
    }

    @Override
    public AbNInitialDisplayAction getInitialDisplayAction() {
        return new AbNInitialDisplayAction();
    }
}
