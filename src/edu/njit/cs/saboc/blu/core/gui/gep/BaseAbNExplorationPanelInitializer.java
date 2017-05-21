
package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.NavigationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.NavigationTutorialPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.ViewportNavigationListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class BaseAbNExplorationPanelInitializer implements AbNExplorationPanelGUIInitializer {
    
    public BaseAbNExplorationPanelInitializer() {
        
    }

    @Override
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel, boolean startUp) {
        NavigationPanel navigationPanel = new NavigationPanel(displayPanel);
        navigationPanel.addNavigationPanelListener(new ViewportNavigationListener(displayPanel));
        
        displayPanel.addZoomFactorChangedListener( (zoomFactor) -> {
            navigationPanel.setZoomLevel(zoomFactor);
        });
        
        displayPanel.addWidget(navigationPanel);
        
        if (startUp) {
            NavigationTutorialPanel tutorialPanel = new NavigationTutorialPanel(displayPanel);

            displayPanel.addWidget(tutorialPanel);
        }
    }

    @Override
    public void initializeAbNDDashboardPanel(AbNDashboardPanel displayPanel) {
        
    }

    @Override
    public AbNInitialDisplayAction getInitialDisplayAction() {
        return new AbNInitialDisplayAction();
    }

    @Override
    public void showAbNAlerts(AbNDisplayPanel displayPanel) {
        AbstractionNetwork abn = displayPanel.getGraph().getAbstractionNetwork();
        
        
        if (abn instanceof AggregateableAbstractionNetwork) {

            if (abn.getNodeCount() >= 1000) {

                Thread waitThread = new Thread(() -> {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {

                    }

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null,
                                "<html>This abstraction network is relatively large."
                                + "<p>To reduce its size use the aggregation slider at the bottom right. "
                                + "<p>This will hide nodes that summarize fewer than the specified"
                                + "<p>number of concepts.");
                    });
                });
                
                waitThread.start();
            }
        }
    }
}
