package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.taskbarpanels;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportAbNButton;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.AbNSearchButton;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.TaskBarPanel;

/**
 *
 * @author Chris O
 */
public class GenericAbNTaskBarPanel extends TaskBarPanel {

    public GenericAbNTaskBarPanel(
            MultiAbNGraphFrame graphFrame,
            AbNConfiguration config) {

        super(graphFrame, config);
    }

    @Override
    protected ExportAbNButton getExportButton(AbNConfiguration config) {
        ExportAbNButton btn = new ExportAbNButton();
        btn.initialize(config);
        
        return btn;
    }
    
    @Override
    protected AbNSearchButton getAbNSearchButton(AbNConfiguration config) {
        AbNSearchButton btn = new AbNSearchButton(
                super.getGraphFrame().getParentFrame(),
                config.getTextConfiguration());
        
        btn.initialize(config);
        
        return btn;
    }

    @Override
    protected String getAbNMetricsLabel(AbNConfiguration config) {
        return String.format("%s: %d | %s: %d", 
                config.getTextConfiguration().getConceptTypeName(true), 
                config.getAbstractionNetwork().getSourceHierarchy().size(),
                config.getTextConfiguration().getNodeTypeName(true),
                config.getAbstractionNetwork().getNodeCount());
    }
}
