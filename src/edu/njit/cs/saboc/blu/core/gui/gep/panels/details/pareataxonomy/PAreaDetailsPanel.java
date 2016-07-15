package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class PAreaDetailsPanel extends NodeDetailsPanel<PArea> {
    
    public PAreaDetailsPanel(PAreaTaxonomyConfiguration config) {
        
        super(new PAreaSummaryPanel(config), 
                config.getUIConfiguration().getNodeOptionsPanel(), 
                new ConceptList(config),
                config);
    }
}
