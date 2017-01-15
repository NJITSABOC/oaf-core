package edu.njit.cs.saboc.blu.core.gui.graphframe;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.disjointabn.DisjointAbNGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.DisjointAbNExplorationPanelInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportAbNButton;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateDisjointAbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateSinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.DisjointAbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.AbNSearchButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class DisjointAbNInternalGraphFrame 
        extends GenericInternalGraphFrame<DisjointAbstractionNetwork> {
    
    // lol
    public interface DisjointAbNConfigurationConfiguration {
        public DisjointAbNConfiguration getDisjointAbNConfiguration(
                DisjointAbstractionNetwork disjointAbN, 
                AbNDisplayManager displayListener);
        
        public PartitionedAbNConfiguration getParentAbNConfiguration(
                DisjointAbstractionNetwork disjointAbN, 
                AbNDisplayManager displayListener);
    }

    private final AbNDisplayManager displayListener;
    
    private final AbNSearchButton searchBtn;
    
    private final ExportAbNButton exportBtn;
    
    private final DisjointAbNConfigurationConfiguration configConfig;

    public DisjointAbNInternalGraphFrame(
            JFrame parentFrame, 
            DisjointAbNConfigurationConfiguration configConfig,
            AbNDisplayManager displayListener) {
        
        super(parentFrame, configConfig.getDisjointAbNConfiguration(null, displayListener).getTextConfiguration().getAbNTypeName(false));
        
        this.configConfig = configConfig;
        
        this.displayListener = displayListener;
        
        this.exportBtn = new ExportAbNButton();
        
        super.addReportButtonToMenu(exportBtn);
        
        this.searchBtn = new AbNSearchButton(
                parentFrame, 
                configConfig.getDisjointAbNConfiguration(null, displayListener).getTextConfiguration());
        
        super.addToggleableButtonToMenu(searchBtn);
    }

    public final void displayDisjointAbN(DisjointAbstractionNetwork disjointAbstractionNetwork) {
        
        Thread loadThread = new Thread(() -> {
            getAbNExplorationPanel().showLoading();
            
            SinglyRootedNodeLabelCreator labelCreator;

            if (disjointAbstractionNetwork.isAggregated()) {
                labelCreator = new AggregateSinglyRootedNodeLabelCreator();
            } else {
                labelCreator = new SinglyRootedNodeLabelCreator();
            }
            
            DisjointAbNPainter painter;

            if (disjointAbstractionNetwork.isAggregated()) {
                painter = new AggregateDisjointAbNPainter();
            } else {
                painter = new DisjointAbNPainter();
            }
            
            DisjointAbNGraph graph = new DisjointAbNGraph(getParentFrame(), disjointAbstractionNetwork, labelCreator);

            DisjointAbNConfiguration currentConfiguration = configConfig.getDisjointAbNConfiguration(
                    disjointAbstractionNetwork, 
                    displayListener);
            
            
            PartitionedAbNConfiguration parentConfig = configConfig.getParentAbNConfiguration(
                    disjointAbstractionNetwork, 
                    displayListener);
            
            exportBtn.initialize(currentConfiguration);
            searchBtn.initialize(currentConfiguration);
            
            SwingUtilities.invokeLater(() -> {
                displayAbstractionNetwork(graph,
                        painter,
                        currentConfiguration,
                        new DisjointAbNExplorationPanelInitializer(
                                currentConfiguration,
                                parentConfig,
                                (bound) -> {
                                    DisjointAbstractionNetwork disjointAbN = getGraph().get().getAbstractionNetwork().getAggregated(bound);
                                    
                                    displayDisjointAbN(disjointAbN);
                                },
                                (disjointAbN) -> {
                                    displayDisjointAbN(disjointAbN);
                                }));
            });
        });
        
        loadThread.start();
    }
}
