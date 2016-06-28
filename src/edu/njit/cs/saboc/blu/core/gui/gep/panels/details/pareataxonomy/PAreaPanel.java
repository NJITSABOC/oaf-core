package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.SinglyRootedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class PAreaPanel extends SinglyRootedNodePanel {

    public PAreaPanel(PAreaTaxonomyConfiguration configuration) {
        
        super(new PAreaDetailsPanel(configuration),
                new NodeHierarchyPanel(configuration), 
                new ConceptHierarchyPanel(configuration), 
                configuration);
    }
}
