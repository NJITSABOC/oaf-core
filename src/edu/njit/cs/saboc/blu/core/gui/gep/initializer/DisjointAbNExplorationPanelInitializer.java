package edu.njit.cs.saboc.blu.core.gui.gep.initializer;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.graph.disjointabn.DisjointAbNGraph;
import edu.njit.cs.saboc.blu.core.graph.disjointabn.DisjointNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel.AggregationAction;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.AbNWarningManager;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.DisjointAbNWarningManager;
import java.awt.Color;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class DisjointAbNExplorationPanelInitializer extends AggregateableAbNExplorationPanelInitializer {
    
    private final DisjointAbNConfiguration config;
    private final PartitionedAbNConfiguration parentConfig;
    
    public DisjointAbNExplorationPanelInitializer(
            DisjointAbNConfiguration config, 
            PartitionedAbNConfiguration parentConfig, 
            AggregationAction aggregationAction,
            AbNWarningManager warningManager) {
        
        super(warningManager, aggregationAction);
        
        this.config = config;
        this.parentConfig = parentConfig;
    }
    
    @Override
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel, boolean startUp) {
        super.initializeAbNDisplayPanel(displayPanel, startUp);
    }
    
    @Override
    public void showAbNAlerts(AbNDisplayPanel displayPanel) {
        
        super.showAbNAlerts(displayPanel);

        AbNWarningManager warningManager = this.getWarningManager();
        
        if(warningManager instanceof DisjointAbNWarningManager) {
            DisjointAbNWarningManager disjointWarningManager = (DisjointAbNWarningManager)warningManager;
            
            if(disjointWarningManager.showGrayWarningMessage()) {
                DisjointAbNGraph<?> disjointAbNGraph = (DisjointAbNGraph)displayPanel.getGraph();
                
                Set<SinglyRootedNodeEntry> grayRoots = disjointAbNGraph.getNodeEntries().values().stream().filter( (nodeEntry) -> {
                    
                    DisjointNodeEntry disjointEntry = (DisjointNodeEntry)nodeEntry;
                    
                    DisjointNode node = (DisjointNode)disjointEntry.getNode();
                    
                    if(node.getOverlaps().size() == 1) {
                        Color color = disjointEntry.getColorSet()[0];
                        
                        return color.equals(Color.GRAY);
                    }

                    return false;
                }).collect(Collectors.toSet());
                
                if(grayRoots.size() > 1) {
                    showWarning(disjointWarningManager);
                }
            }
        }
    }
    
    private void showWarning(DisjointAbNWarningManager disjointWarningManager) {
        
        Thread waitThread = new Thread(() -> {

            try {

                Thread.sleep(1000);

            } catch (InterruptedException ie) {

            }

            SwingUtilities.invokeLater(() -> {

                String message = "<html>This disjoint abstraction network has many roots and <br>"
                        + "we've run out of colors to represent them."
                        
                        + "<p><p>Consider viewing a subset of this disjoint abstraction network <br>"
                        + "by clicking on the disjoint abstraction network subset button on the <br>"
                        + "Dashboard panel.";

                String[] options = {"Warn me again", "Do not warn me again"};

                int result = JOptionPane.showOptionDialog(
                        null,
                        message,
                        "Looking a little gray?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (result == 1) {
                    disjointWarningManager.setShowGrayWarningMessage(false);
                }
            });
        });

        waitThread.start();
    }

}
