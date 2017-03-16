package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.PAreaTaxonomyGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNExplorationPanelGUIInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.AggregateableAbNExplorationPanelInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateSinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.pareataxonomy.AggregatePAreaTaxonomyPainter;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PartitionedAbNSelectionPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.GraphFrameInitializer;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.TaskBarPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.taskbarpanels.PartitionedAbNTaskBarPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation.AbNDerivationExplanationPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation.PAreaTaxonomyDerivationExplanation;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JToggleButton;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyInitializer implements GraphFrameInitializer<PAreaTaxonomy, PAreaTaxonomyConfiguration> {
    
    public static enum PAreaInitializerType {
        PAreaTaxonomy,
        AreaTaxonomy
    }
    
    public PAreaInitializerType getInitializerType() {
        return PAreaInitializerType.PAreaTaxonomy;
    }

    @Override
    public AbstractionNetworkGraph getGraph(
            JFrame parentFrame, 
            PAreaTaxonomyConfiguration config, 
            SinglyRootedNodeLabelCreator labelCreator) {
        
        return new PAreaTaxonomyGraph(parentFrame, config.getPAreaTaxonomy(), labelCreator, config);
    }

    @Override
    public TaskBarPanel getTaskBar(
            MultiAbNGraphFrame graphFrame, 
            PAreaTaxonomyConfiguration config) {
        
        PartitionedAbNTaskBarPanel taskBar = new PartitionedAbNTaskBarPanel(graphFrame, config);
        
        PartitionedAbNSelectionPanel abnTypeSelectionPanel = new PartitionedAbNSelectionPanel() {

            @Override
            public void showFullClicked() {
                config.getUIConfiguration().getAbNDisplayManager().displayPAreaTaxonomy(config.getPAreaTaxonomy());
            }

            @Override
            public void showBaseClicked() {
                config.getUIConfiguration().getAbNDisplayManager().displayAreaTaxonomy(config.getPAreaTaxonomy());
            }
        };

        abnTypeSelectionPanel.initialize(config, getInitializerType().equals(PAreaInitializerType.PAreaTaxonomy));
        
        taskBar.addOtherOptionsComponent(abnTypeSelectionPanel);
        
        JToggleButton showRegionsBtn = new JToggleButton("Regions");
        showRegionsBtn.addActionListener( (ae) -> {
            
        });
        
        taskBar.addOtherOptionsComponent(showRegionsBtn);
        
        JButton derivationHelpBtn = new JButton("Help!");
        derivationHelpBtn.addActionListener( (ae) -> {
            JDialog derivationExplanationDialog = new JDialog();
            derivationExplanationDialog.setModal(true);
            
            AbNDerivationExplanationPanel explanationPanel = new AbNDerivationExplanationPanel(new PAreaTaxonomyDerivationExplanation(config));
            derivationExplanationDialog.add(explanationPanel);
            derivationExplanationDialog.setSize(1200, 600);
            
            derivationExplanationDialog.setVisible(true);
        });
        
        taskBar.addReportButtonToMenu(derivationHelpBtn);
        
        return taskBar;
    }

    @Override
    public AbNPainter getAbNPainter(PAreaTaxonomy abn) {
        if (abn.isAggregated()) {
            return new AggregatePAreaTaxonomyPainter();
        } else {
            return new AbNPainter();
        }
    }

    @Override
    public SinglyRootedNodeLabelCreator getLabelCreator(PAreaTaxonomy abn) {
        if (abn.isAggregated()) {
            return new AggregateSinglyRootedNodeLabelCreator<>();
        } else {
            return new SinglyRootedNodeLabelCreator<>();
        }
    }

    @Override
    public AbNExplorationPanelGUIInitializer getExplorationGUIInitializer(PAreaTaxonomyConfiguration config) {
        return new AggregateableAbNExplorationPanelInitializer( (bound) -> {
            PAreaTaxonomy aggregateTaxonomy = config.getPAreaTaxonomy().getAggregated(bound);
            config.getUIConfiguration().getAbNDisplayManager().displayPAreaTaxonomy(aggregateTaxonomy);
        });
    }
}
